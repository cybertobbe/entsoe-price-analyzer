# Arkitektur

## Systemöversikt
```
ENTSOE API
    ↓ (quartz kl 00:10)
entsoe-api-service (8086)
    ↓ (ActiveMQ)
entsoe-db-service (8087) → MySQL
    ↓ (JPA query)
entsoe-price-analyzer (8088)
    ↓ (live exchange rate)
elpris-frontend (8089)
```

## Komponenter

### entsoe-api-service
- Hämtar rådata från ENTSOE
- Schemalagd med Quartz
- Skickar till ActiveMQ

### entsoe-db-service
- Lyssnar på ActiveMQ
- Parsar XML
- Sparar till MySQL

### entsoe-price-analyzer
- Läser från MySQL
- Konverterar valutor
- REST API för frontend

## Teknologier

- Spring Boot 3.5
- Apache Camel
- ActiveMQ Artemis
- MySQL
- Vue.js