package tech.sjostrom.entsoepriceanalyzer;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "spot_prices")
public class SpotPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;
    private BigDecimal price;
    private String currency;
    private String area;

    public Long getId() { return id; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public BigDecimal getPrice() { return price; }
    public String getCurrency() { return currency; }
    public String getArea() { return area; }
}