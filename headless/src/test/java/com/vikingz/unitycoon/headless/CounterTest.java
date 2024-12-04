package com.vikingz.unitycoon.headless;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.vikingz.unitycoon.building.BuildingStats.BuildingType;
import com.vikingz.unitycoon.global.GameGlobals;
import com.vikingz.unitycoon.building.BuildingsMap;

public class CounterTest extends TestSuper {
    /**
     * Test that building counters are appropriately incremented when a new building is placed and decremented when a 
     */
    @Test
    public void testCounterIncreaseDecrease() {
        BuildingsMap testMap = getTestMap();

        // Test Academic Counter
        assertEquals(0, GameGlobals.ACADEMIC_BUILDINGS_COUNT);

        addBasicTestBuilding(testMap, BuildingType.ACADEMIC, 110, 10);
        assertEquals(1, GameGlobals.ACADEMIC_BUILDINGS_COUNT);

        assertTrue(testMap.attemptBuildingDeleteAt(111, 11));
        assertEquals(0, GameGlobals.ACADEMIC_BUILDINGS_COUNT);

        // Test Food Counter
        assertEquals(0, GameGlobals.FOOD_BUILDINGS_COUNT);

        addBasicTestBuilding(testMap, BuildingType.FOOD, 110, 10);
        assertEquals(1, GameGlobals.FOOD_BUILDINGS_COUNT);

        assertTrue(testMap.attemptBuildingDeleteAt(111, 11));
        assertEquals(0, GameGlobals.FOOD_BUILDINGS_COUNT);

        // Test Academic Counter
        assertEquals(0, GameGlobals.RECREATIONAL_BUILDINGS_COUNT);

        addBasicTestBuilding(testMap, BuildingType.RECREATIONAL, 110, 10);
        assertEquals(1, GameGlobals.RECREATIONAL_BUILDINGS_COUNT);

        assertTrue(testMap.attemptBuildingDeleteAt(111, 11));
        assertEquals(0, GameGlobals.RECREATIONAL_BUILDINGS_COUNT);

        // Test Academic Counter
        assertEquals(0, GameGlobals.ACCOMODATION_BUILDINGS_COUNT);

        addBasicTestBuilding(testMap, BuildingType.ACCOMODATION, 110, 10);
        assertEquals(1, GameGlobals.ACCOMODATION_BUILDINGS_COUNT);

        assertTrue(testMap.attemptBuildingDeleteAt(111, 11));
        assertEquals(0, GameGlobals.ACCOMODATION_BUILDINGS_COUNT);
    }
}
