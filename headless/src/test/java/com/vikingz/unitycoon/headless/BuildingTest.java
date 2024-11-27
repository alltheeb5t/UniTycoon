package com.vikingz.unitycoon.headless;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vikingz.unitycoon.building.BuildingInfo;
import com.vikingz.unitycoon.building.BuildingStats;
import com.vikingz.unitycoon.building.BuildingStats.BuildingType;
import com.vikingz.unitycoon.building.BuildingsMap;
import com.vikingz.unitycoon.render.BackgroundRenderer;
import com.vikingz.unitycoon.util.FileHandler;

public class BuildingTest {
    @BeforeEach
    public void setup() {
        System.out.println("Starting LibGDX Headless");
        Gdx.gl = Gdx.gl20 = mock(GL20.class);
        HeadlessLauncher.main(new String[0]);
        
        System.out.println("Started Headless Mode");
    }

    private BuildingsMap getTestMap() {
        FileHandler.loadBuildings("buildingInfo","TextureAtlasMap"); // Load list of buildings from file

        BackgroundRenderer testBackgroundRenderer = new BackgroundRenderer("map1");
        return new BuildingsMap(testBackgroundRenderer);
    }

    /**
     * Test placement of building in empty space
     */
    @Test
    public void validPlacement() {
        BuildingsMap testMap = getTestMap();

        BuildingType testBuildingType = BuildingType.ACADEMIC;
        int testBuildingIndex = 1;  // Represents what variant we want. ACADEMIC 1 is a Ron Cooke Hub
        BuildingInfo testBuildingInfo = BuildingStats.getInfo(testBuildingType, testBuildingIndex);
        TextureRegion testBuildingTexture = BuildingStats.getTextureOfBuilding(BuildingStats.BuildingDict.get(testBuildingType)[testBuildingIndex]);

        assertEquals(true, testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 100, 10));
    }

    /**
     * Test that buildings can't be placed on top of other buildings
     */
    @Test
    public void buildingOnBuildingCollision() {
        BuildingsMap testMap = getTestMap();

        BuildingType testBuildingType = BuildingType.ACADEMIC;
        int testBuildingIndex = 1;  // Represents what variant we want. ACADEMIC 1 is a Ron Cooke Hub
        BuildingInfo testBuildingInfo = BuildingStats.getInfo(testBuildingType, testBuildingIndex);
        TextureRegion testBuildingTexture = BuildingStats.getTextureOfBuilding(BuildingStats.BuildingDict.get(testBuildingType)[testBuildingIndex]);

        assertEquals(true, testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 100, 10));

        // Fully on top of existing building
        assertEquals(false, testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 100, 10));

        // Partially on top of existing building
        assertEquals(false, testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 125, 10));
    }
}

