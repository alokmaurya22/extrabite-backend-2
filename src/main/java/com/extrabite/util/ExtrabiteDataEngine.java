package com.extrabite.util;

import java.util.*;

/*
🛠 DATA SOURCES USED
  - Global Hunger Data (2015-2025):
    Source: FAO – SOFI Reports
    https://www.fao.org/state-of-food-security-nutrition

  - India Hunger Estimate:
    Source: Global Hunger Index (https://www.globalhungerindex.org/)

  - Global Food Waste:
    Source: UNEP Food Waste Index 2021
    https://www.unep.org/resources/report/unep-food-waste-index-report-2021

  - India Food Waste:
    Source: MoFPI, WRI, WRAP, FAO
    https://mofpi.nic.in
* ➤ Hunger Data Sources:
 *    FAO - SOFI Reports (https://www.fao.org/state-of-food-security-nutrition)
 *    GHI Report (https://www.globalhungerindex.org/)
 *
 * ➤ Food Waste Data Sources:
 *    UNEP Food Waste Index Report 2021 (https://www.unep.org/resources/report/unep-food-waste-index-report-2021)
 *    MoFPI India (https://www.mofpi.gov.in/)
 *
 * ➤ Waste Source Distribution (Global + India):
 *    - Households: 61%
 *    - Food Services (Restaurants/Hotels): 26%
 *    - Retail: 10%
 *    - Post-Harvest & Agriculture: 3%
 *
 * ➤ Assumptions:
 *    - 0.5 kg food/day feeds 1 person
 *    - 1 tonne = 1000 kg => can feed 2000 people/day
 */

public class ExtrabiteDataEngine {

    // Year-wise data for 2015–2025
    static final Map<Integer, Integer> globalHunger = Map.ofEntries(
            Map.entry(2015, 628), Map.entry(2016, 612), Map.entry(2017, 613),
            Map.entry(2018, 678), Map.entry(2019, 690), Map.entry(2020, 738),
            Map.entry(2021, 768), Map.entry(2022, 735), Map.entry(2023, 735),
            Map.entry(2024, 724), Map.entry(2025, 715));

    static final Map<Integer, Integer> indiaHunger = Map.ofEntries(
            Map.entry(2015, 190), Map.entry(2016, 185), Map.entry(2017, 185),
            Map.entry(2018, 189), Map.entry(2019, 190), Map.entry(2020, 204),
            Map.entry(2021, 224), Map.entry(2022, 224), Map.entry(2023, 222),
            Map.entry(2024, 220), Map.entry(2025, 218));

    static final Map<Integer, Double> globalFoodWasteTonnes = Map.ofEntries(
            Map.entry(2015, 1.30), Map.entry(2016, 1.31), Map.entry(2017, 1.32),
            Map.entry(2018, 1.33), Map.entry(2019, 1.35), Map.entry(2020, 1.40),
            Map.entry(2021, 1.41), Map.entry(2022, 1.41), Map.entry(2023, 1.42),
            Map.entry(2024, 1.43), Map.entry(2025, 1.45));

    static final Map<Integer, Integer> indiaFoodWasteTonnes = Map.ofEntries(
            Map.entry(2015, 65), Map.entry(2016, 66), Map.entry(2017, 67),
            Map.entry(2018, 68), Map.entry(2019, 70), Map.entry(2020, 74),
            Map.entry(2021, 76), Map.entry(2022, 77), Map.entry(2023, 78),
            Map.entry(2024, 79), Map.entry(2025, 80));

    static final Map<String, Double> foodWasteSources = Map.of(
            "Households", 0.61,
            "Food Services", 0.26,
            "Retail", 0.10,
            "Post-Harvest & Agriculture", 0.03);

    public static Map<Integer, Integer> getGlobalHunger() {
        return globalHunger;
    }

    public static Map<Integer, Integer> getIndiaHunger() {
        return indiaHunger;
    }

    public static Map<Integer, Double> getGlobalFoodWasteTonnes() {
        return globalFoodWasteTonnes;
    }

    public static Map<Integer, Integer> getIndiaFoodWasteTonnes() {
        return indiaFoodWasteTonnes;
    }

    public static Map<String, Double> getFoodWasteSources() {
        return foodWasteSources;
    }
}