package tech.sjostrom.entsoepriceanalyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

    @Autowired
    private PriceAnalyzerService service;

    @GetMapping("/today")
    public List<PriceAnalysis> getTodayPrices() {
        return service.getTodayPrices();
    }

    @GetMapping("/date/{date}")
    public List<PriceAnalysis> getPricesForDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.getPricesForDate(date);
    }

    @GetMapping("/summary/today")
    public DailySummary getTodaySummary() {
        return service.getDailySummary(LocalDate.now());
    }

    @GetMapping("/summary/{date}")
    public DailySummary getSummaryForDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.getDailySummary(date);
    }

    @GetMapping("/exchange-rate")
    public Map<String, Object> getExchangeRate() {
        return Map.of(
                "from", "EUR",
                "to", "SEK",
                "rate", service.getCurrentExchangeRate()
        );
    }
}