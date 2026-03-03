# Deployment

## Förutsättningar

- Docker
- Tillgång till MySQL-databas
- ActiveMQ Artemis

## Miljövariabler

| Variabel | Beskrivning | Default |
|----------|-------------|---------|
| `MYSQL_USER` | Databasanvändare | entsoe |
| `MYSQL_PASSWORD` | Databaslösenord | entsoe |
| `EXCHANGE_RATE_EUR_SEK` | Fallback växelkurs | 11.50 |

## Docker Compose
```yaml
entsoe-price-analyzer:
  image: cybertobbe/entsoe-price-analyzer:latest
  ports:
    - "8088:8088"
  env_file:
    - .env
  networks:
    - camel-network
```

## Manuell deploy
```bash
mvn clean package -DskipTests
docker build -t entsoe-price-analyzer .
docker tag entsoe-price-analyzer cybertobbe/entsoe-price-analyzer
docker push cybertobbe/entsoe-price-analyzer
```