package tech.sjostrom.entsoepriceanalyzer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class ExchangeRateService {

    @Value("${exchange.rate.eur.sek}")
    private BigDecimal fallbackRate;

    private final RestTemplate restTemplate = new RestTemplate();

    private BigDecimal cachedRate;
    private long cacheTimestamp = 0;
    private static final long CACHE_DURATION_MS = 3600000; // 1 timme

    public BigDecimal getEurToSek() {
        if (cachedRate != null && System.currentTimeMillis() - cacheTimestamp < CACHE_DURATION_MS) {
            return cachedRate;
        }

        try {
            String url = "https://api.exchangerate-api.com/v4/latest/EUR";
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response != null && response.containsKey("rates")) {
                Map<String, Number> rates = (Map<String, Number>) response.get("rates");
                if (rates.containsKey("SEK")) {
                    cachedRate = new BigDecimal(rates.get("SEK").toString());
                    cacheTimestamp = System.currentTimeMillis();
                    System.out.println("Fetched live exchange rate: 1 EUR = " + cachedRate + " SEK");
                    return cachedRate;
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to fetch exchange rate, using fallback: " + e.getMessage());
        }

        return fallbackRate;
    }
}