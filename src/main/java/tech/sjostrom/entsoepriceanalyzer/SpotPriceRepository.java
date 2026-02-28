package tech.sjostrom.entsoepriceanalyzer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface SpotPriceRepository extends JpaRepository<SpotPrice, Long> {

    List<SpotPrice> findByTimestampBetweenOrderByTimestamp(LocalDateTime start, LocalDateTime end);

    @Query("SELECT AVG(s.price) FROM SpotPrice s WHERE s.timestamp BETWEEN :start AND :end")
    Double findAveragePrice(LocalDateTime start, LocalDateTime end);

    @Query("SELECT MIN(s.price) FROM SpotPrice s WHERE s.timestamp BETWEEN :start AND :end")
    Double findMinPrice(LocalDateTime start, LocalDateTime end);

    @Query("SELECT MAX(s.price) FROM SpotPrice s WHERE s.timestamp BETWEEN :start AND :end")
    Double findMaxPrice(LocalDateTime start, LocalDateTime end);
}