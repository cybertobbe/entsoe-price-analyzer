package tech.sjostrom.entsoepriceanalyzer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PriceAnalysis {
    private LocalDateTime timestamp;
    private BigDecimal priceEurMwh;
    private BigDecimal priceSekKwh;
    private BigDecimal priceSekKwhInclVat;

    public PriceAnalysis(LocalDateTime timestamp, BigDecimal priceEurMwh, BigDecimal priceSekKwh, BigDecimal priceSekKwhInclVat) {
        this.timestamp = timestamp;
        this.priceEurMwh = priceEurMwh;
        this.priceSekKwh = priceSekKwh;
        this.priceSekKwhInclVat = priceSekKwhInclVat;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public BigDecimal getPriceEurMwh() { return priceEurMwh; }
    public BigDecimal getPriceSekKwh() { return priceSekKwh; }
    public BigDecimal getPriceSekKwhInclVat() { return priceSekKwhInclVat; }
}