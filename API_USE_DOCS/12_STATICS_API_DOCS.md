# Statics APIs for Hunger & Food Wastage

## Data Sources & Assumptions

**🛠 DATA SOURCES USED**

- **Global Hunger Data (2015-2025):**
  - Source: FAO – SOFI Reports
  - https://www.fao.org/state-of-food-security-nutrition
- **India Hunger Estimate:**
  - Source: Global Hunger Index
  - https://www.globalhungerindex.org/
- **Global Food Waste:**
  - Source: UNEP Food Waste Index 2021
  - https://www.unep.org/resources/report/unep-food-waste-index-report-2021
- **India Food Waste:**
  - Source: MoFPI, WRI, WRAP, FAO
  - https://mofpi.nic.in

**Hunger Data Sources:**

- FAO - SOFI Reports (https://www.fao.org/state-of-food-security-nutrition)
- GHI Report (https://www.globalhungerindex.org/)

**Food Waste Data Sources:**

- UNEP Food Waste Index Report 2021 (https://www.unep.org/resources/report/unep-food-waste-index-report-2021)
- MoFPI India (https://www.mofpi.gov.in/)

**Waste Source Distribution (Global + India):**

- Households: 61%
- Food Services (Restaurants/Hotels): 26%
- Retail: 10%
- Post-Harvest & Agriculture: 3%

**Assumptions:**

- 0.5 kg food/day feeds 1 person
- 1 tonne = 1000 kg => can feed 2000 people/day

---

# API Documentation

These APIs provide statistics on global and Indian hunger and food wastage data (2015-2025), suitable for analytics, dashboards, and visualizations.

---

# Statics API Query Parameter Examples

## 1. Yearly Data

**Endpoint:** `/api/analytics/statistics/yearly`

**Parameters:**

- `dataType`: `hunger`, `foodWaste`, `both`
- `region`: `India`, `Global`
- `startYear`: 2015-2025
- `endYear`: 2015-2025

**Example Requests:**

- `/api/analytics/statistics/yearly?dataType=hunger&region=India&startYear=2020&endYear=2025`
- `/api/analytics/statistics/yearly?dataType=foodWaste&region=Global&startYear=2015&endYear=2023`
- `/api/analytics/statistics/yearly?dataType=both&region=India&startYear=2018&endYear=2022`

**Sample Response:**

```json
[
  {
    "year": 2020,
    "hunger": 204,
    "foodWaste": 74.0,
    "foodWasteUnit": "Million Tonnes",
    "foodWasteBreakdown": {
      "Households": 45.14,
      "Food Services": 19.24,
      "Retail": 7.4,
      "Post-Harvest & Agriculture": 2.22
    }
  }
  // ...
]
```

---

## 2. Growth Rate (CAGR)

**Endpoint:** `/api/analytics/statistics/growth-rate`

**Parameters:**

- `type`: `hunger`, `foodWaste`
- `region`: `India`, `Global`

**Example Requests:**

- `/api/analytics/statistics/growth-rate?type=hunger&region=India`
- `/api/analytics/statistics/growth-rate?type=foodWaste&region=Global`

**Sample Response:**

```json
{
  "type": "hunger",
  "region": "India",
  "cagr": 1.39
}
```

---

## 3. Daily Comparison

**Endpoint:** `/api/analytics/statistics/daily-comparison`

**Parameters:**

- `region`: `India`, `Global`
- `year`: 2015-2025

**Example Requests:**

- `/api/analytics/statistics/daily-comparison?region=India&year=2024`
- `/api/analytics/statistics/daily-comparison?region=Global&year=2020`

**Sample Response:**

```json
{
  "region": "India",
  "year": 2024,
  "dailyHungry": 602739,
  "peopleFed": 432054,
  "enoughFood": false,
  "shortBy": 170685
}
```

---

## 4. Food Waste Source Breakdown

**Endpoint:** `/api/analytics/statistics/food-waste-sources`

**Parameters:**

- `region`: `India`, `Global`
- `year`: 2015-2025

**Example Requests:**

- `/api/analytics/statistics/food-waste-sources?region=India&year=2024`
- `/api/analytics/statistics/food-waste-sources?region=Global&year=2021`

**Sample Response:**

```json
{
  "region": "India",
  "year": 2024,
  "totalFoodWaste": 79.0,
  "unit": "Million Tonnes",
  "sourceBreakdown": {
    "Households": 48.19,
    "Food Services": 20.54,
    "Retail": 7.9,
    "Post-Harvest & Agriculture": 2.37
  }
}
```

---

## 5. Statics Summary (Dashboard)

**Endpoint:** `/api/analytics/statistics/summary`

**Parameters:**

- `region`: `India`, `Global`
- `year`: 2015-2025

**Example Requests:**

- `/api/analytics/statistics/summary?region=India&year=2024`
- `/api/analytics/statistics/summary?region=Global&year=2020`

**Sample Response:**

```json
{
  "region": "India",
  "year": 2024,
  "hunger": 220,
  "foodWaste": 79.0,
  "cagrHunger": 1.39,
  "cagrFoodWaste": 1.98,
  "dailyHungry": 602739,
  "peopleFed": 432054,
  "enoughFood": false,
  "shortBy": 170685,
  "foodWasteSourceBreakdown": {
    "Households": 48.19,
    "Food Services": 20.54,
    "Retail": 7.9,
    "Post-Harvest & Agriculture": 2.37
  },
  "foodWasteUnit": "Million Tonnes"
}
```

---

## 6. Hunger vs Food Waste Bar Chart

**Endpoint:** `/api/analytics/statistics/hunger-vs-foodwaste-bar`

**Parameters:**

- `region`: `India`, `Global`
- `startYear`: 2015-2025
- `endYear`: 2015-2025

**Example Requests:**

- `/api/analytics/statistics/hunger-vs-foodwaste-bar?region=India&startYear=2015&endYear=2025`
- `/api/analytics/statistics/hunger-vs-foodwaste-bar?region=Global&startYear=2018&endYear=2022`

**Sample Response:**

```json
[
  { "year": 2015, "hunger": 190, "foodWaste": 65.0 },
  { "year": 2016, "hunger": 185, "foodWaste": 66.0 }
  // ...
]
```

---

## Raw Data Tables (2015-2025)

### Global Hunger (Million People)

| Year | Value |
| ---- | ----- |
| 2015 | 628   |
| 2016 | 612   |
| 2017 | 613   |
| 2018 | 678   |
| 2019 | 690   |
| 2020 | 738   |
| 2021 | 768   |
| 2022 | 735   |
| 2023 | 735   |
| 2024 | 724   |
| 2025 | 715   |

### India Hunger (Million People)

| Year | Value |
| ---- | ----- |
| 2015 | 190   |
| 2016 | 185   |
| 2017 | 185   |
| 2018 | 189   |
| 2019 | 190   |
| 2020 | 204   |
| 2021 | 224   |
| 2022 | 224   |
| 2023 | 222   |
| 2024 | 220   |
| 2025 | 218   |

### Global Food Waste (Billion Tonnes)

| Year | Value |
| ---- | ----- |
| 2015 | 1.30  |
| 2016 | 1.31  |
| 2017 | 1.32  |
| 2018 | 1.33  |
| 2019 | 1.35  |
| 2020 | 1.40  |
| 2021 | 1.41  |
| 2022 | 1.41  |
| 2023 | 1.42  |
| 2024 | 1.43  |
| 2025 | 1.45  |

### India Food Waste (Million Tonnes)

| Year | Value |
| ---- | ----- |
| 2015 | 65    |
| 2016 | 66    |
| 2017 | 67    |
| 2018 | 68    |
| 2019 | 70    |
| 2020 | 74    |
| 2021 | 76    |
| 2022 | 77    |
| 2023 | 78    |
| 2024 | 79    |
| 2025 | 80    |

### Food Waste Source Breakdown (All Years)

| Source                             | Percentage |
| ---------------------------------- | ---------- |
| Households                         | 61%        |
| Food Services (Restaurants/Hotels) | 26%        |
| Retail                             | 10%        |
| Post-Harvest & Agriculture         | 3%         |

---

For more details, see comments in the codebase: `ExtrabiteDataEngine.java`
