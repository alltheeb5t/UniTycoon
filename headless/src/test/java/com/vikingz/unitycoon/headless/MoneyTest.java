package com.vikingz.unitycoon.headless;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.vikingz.unitycoon.building.BuildingStats.BuildingType;
import com.vikingz.unitycoon.building.BuildingStats;
import com.vikingz.unitycoon.building.BuildingsMap;
import com.vikingz.unitycoon.global.GameGlobals;

public class MoneyTest extends TestSuper {

    @Test
    public void testBuildingReducesBalance() {
        BuildingsMap testMap = getTestMap();

        float initialBalance = GameGlobals.MONEY.getBalance();

        addBasicTestBuilding(testMap, BuildingType.ACADEMIC, 110, 10, true, false);
        assertEquals(initialBalance-Integer.valueOf(BuildingStats.BuildingPriceDict.get(BuildingType.ACADEMIC)[0]), GameGlobals.MONEY.getBalance());
    }
}
