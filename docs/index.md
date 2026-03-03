# ENTSOE Price Analyzer

Tjänst för att analysera och konvertera elpriser från EUR/MWh till SEK/kWh.

## Funktioner

- Hämtar spotpriser från ENTSOE API
- Konverterar EUR/MWh → SEK/kWh
- Live växelkurs från exchangerate-api.com
- Beräknar snitt, min, max per dag
- Identifierar billigaste/dyraste timmar

## Snabbstart
```bash
# Bygg
mvn clean package -DskipTests

# Kör lokalt
java -jar target/entsoe-price-analyzer-0.0.1-SNAPSHOT.jar

# Docker
docker build -t entsoe-price-analyzer .
docker run -p 8088:8088 entsoe-price-analyzer
```

## Endpoints

| Endpoint | Beskrivning |
|----------|-------------|
| `/api/analysis/today` | Dagens priser |
| `/api/analysis/summary/today` | Sammanfattning idag |
| `/api/analysis/exchange-rate` | Aktuell växelkurs |