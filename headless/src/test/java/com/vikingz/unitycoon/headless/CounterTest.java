package com.vikingz.unitycoon.headless;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vikingz.unitycoon.building.BuildingInfo;
import com.vikingz.unitycoon.building.BuildingStats;
import com.vikingz.unitycoon.building.BuildingStats.BuildingType;
import com.vikingz.unitycoon.global.GameGlobals;
import com.vikingz.unitycoon.building.BuildingsMap;
import com.vikingz.unitycoon.render.BackgroundRenderer;
import com.vikingz.unitycoon.util.FileHandler;

public class CounterTest {
    @BeforeEach
    public void setup() {
        Gdx.gl = Gdx.gl20 = mock(GL20.class);
        HeadlessLauncher.main(new String[0]);
    }

    private boolean addBasicTestBuilding(BuildingsMap testMap, BuildingType testBuildingType, float x, float y) {
        int testBuildingIndex = 0;  // Represents what variant we want. ACADEMIC 0 is a Ron Cooke Hub
        BuildingInfo testBuildingInfo = BuildingStats.getInfo(testBuildingType, testBuildingIndex);
        TextureRegion testBuildingTexture = BuildingStats.getTextureOfBuilding(BuildingStats.BuildingDict.get(testBuildingType)[testBuildingIndex]);

        return testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, x, y, true);
    }

    private BuildingsMap getTestMap() {
        FileHandler.loadBuildings("buildingInfo","TextureAtlasMap"); // Load list of buildings from file

        BackgroundRenderer testBackgroundRenderer = new BackgroundRenderer("map1");
        return new BuildingsMap(testBackgroundRenderer);
    }

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
