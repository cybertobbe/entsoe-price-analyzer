package tech.sjostrom.entsoepriceanalyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriceAnalyzerService {

    @Autowired
    private SpotPriceRepository repository;

    @Value("${exchange.rate.eur.sek}")
    private BigDecimal exchangeRate;

    private static final BigDecimal MWH_TO_KWH = new BigDecimal("1000");
    private static final BigDecimal VAT = new BigDecimal("1.25");

    public List<PriceAnalysis> getTodayPrices() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1);
        return convertPrices(repository.findByTimestampBetweenOrderByTimestamp(start, end));
    }

    public List<PriceAnalysis> getPricesForDate(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = start.plusDays(1);
        return convertPrices(repository.findByTimestampBetweenOrderByTimestamp(start, end));
    }

    public DailySummary getDailySummary(LocalDate date) {
        List<PriceAnalysis> prices = getPricesForDate(date);

        if (prices.isEmpty()) {
            return null;
        }

        DailySummary summary = new DailySummary();
        summary.setDate(date);

        // Beräkna snitt
        BigDecimal avg = prices.stream()
                .map(PriceAnalysis::getPriceSekKwh)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(new BigDecimal(prices.size()), 4, RoundingMode.HALF_UP);

        summary.setAverageSekKwh(avg);
        summary.setAverageSekKwhInclVat(avg.multiply(VAT).setScale(4, RoundingMode.HALF_UP));

        // Min/Max
        summary.setMinSekKwh(prices.stream()
                .map(PriceAnalysis::getPriceSekKwh)
                .min(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO));

        summary.setMaxSekKwh(prices.stream()
                .map(PriceAnalysis::getPriceSekKwh)
                .max(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO));

        // Billigaste 3 timmarna
        summary.setCheapestHours(prices.stream()
                .sorted(Comparator.comparing(PriceAnalysis::getPriceSekKwh))
                .limit(3)
                .collect(Collectors.toList()));

        // Dyraste 3 timmarna
        summary.setExpensiveHours(prices.stream()
                .sorted(Comparator.comparing(PriceAnalysis::getPriceSekKwh).reversed())
                .limit(3)
                .collect(Collectors.toList()));

        return summary;
    }

    private List<PriceAnalysis> convertPrices(List<SpotPrice> spotPrices) {
        return spotPrices.stream()
                .map(this::convertPrice)
                .collect(Collectors.toList());
    }

    private PriceAnalysis convertPrice(SpotPrice spot) {
        // EUR/MWh -> SEK/kWh
        BigDecimal sekKwh = spot.getPrice()
                .multiply(exchangeRate)
                .divide(MWH_TO_KWH, 4, RoundingMode.HALF_UP);

        BigDecimal sekKwhInclVat = sekKwh.multiply(VAT).setScale(4, RoundingMode.HALF_UP);

        return new PriceAnalysis(
                spot.getTimestamp(),
                spot.getPrice(),
                sekKwh,
                sekKwhInclVat
        );
    }
}