# API Referens

## GET /api/analysis/today

Returnerar alla priser för idag.

**Response:**
```json
[
  {
    "timestamp": "2026-02-28T00:00:00",
    "priceEurMwh": 73.40,
    "priceSekKwh": 0.7832,
    "priceSekKwhInclVat": 0.9790
  }
]
```

## GET /api/analysis/summary/today

Returnerar sammanfattning för idag.

**Response:**
```json
{
  "date": "2026-02-28",
  "averageSekKwh": 0.7562,
  "averageSekKwhInclVat": 0.9453,
  "minSekKwh": 0.5405,
  "maxSekKwh": 1.1244,
  "cheapestHours": [...],
  "expensiveHours": [...]
}
```

## GET /api/analysis/summary/{date}

Returnerar sammanfattning för specifikt datum.

**Parameter:** `date` - format YYYY-MM-DD

## GET /api/analysis/exchange-rate

Returnerar aktuell växelkurs EUR → SEK.

**Response:**
```json
{
  "from": "EUR",
  "to": "SEK",
  "rate": 10.67
}
```