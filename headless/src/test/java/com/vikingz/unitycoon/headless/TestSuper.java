package com.vikingz.unitycoon.headless;

import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vikingz.unitycoon.building.Building;
import com.vikingz.unitycoon.building.BuildingInfo;
import com.vikingz.unitycoon.building.BuildingStats;
import com.vikingz.unitycoon.building.BuildingStats.BuildingType;
import com.vikingz.unitycoon.building.BuildingsMap;
import com.vikingz.unitycoon.render.BackgroundRenderer;
import com.vikingz.unitycoon.util.FileHandler;

public class TestSuper {
    @BeforeEach
    public void setup() {
        Gdx.gl = Gdx.gl20 = mock(GL20.class);
        HeadlessLauncher.main(new String[0]);
    }

    /**
     * Attempt to create a new building and then return it as by the attemptAddBuilding method
     * @param testMap
     * @param testBuildingType
     * @param x
     * @param y
     * @param skipConstruction
     * @return
     */
    protected List<Building> addGetBasicTestBuilding(BuildingsMap testMap, BuildingType testBuildingType, float x, float y, boolean skipConstruction) {
        int testBuildingIndex = 0;  // Represents what variant we want. ACADEMIC 0 is a Ron Cooke Hub
        BuildingInfo testBuildingInfo = BuildingStats.getInfo(testBuildingType, testBuildingIndex);
        TextureRegion testBuildingTexture = BuildingStats.getTextureOfBuilding(BuildingStats.BuildingDict.get(testBuildingType)[testBuildingIndex]);

        List<Building> addedBuilding = testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, x, y, true);

        // Immediately complete construction
        if (!addedBuilding.isEmpty() && skipConstruction) {
            addedBuilding.get(0).setConstructing(false);
            testMap.builtBuilding(addedBuilding.get(0));
        }

        return addedBuilding;
    }

    /**
     * Simple helper method to add a test building and test if placement was successful.
     * @param testMap
     * @param testBuildingType
     * @param x
     * @param y
     * @param skipConstruction
     * @return
     */
    protected boolean addBasicTestBuilding(BuildingsMap testMap, BuildingType testBuildingType, float x, float y, boolean skipConstruction) {
        return !addGetBasicTestBuilding(testMap, testBuildingType, x, y, skipConstruction).isEmpty();
    }

    // skipConstruction defaults to false
    protected boolean addBasicTestBuilding(BuildingsMap testMap, BuildingType testBuildingType, float x, float y) {
        return addBasicTestBuilding(testMap, testBuildingType, x, y, false);
    }

    protected BuildingsMap getTestMap() {
        FileHandler.loadBuildings("buildingInfo","TextureAtlasMap"); // Load list of buildings from file

        BackgroundRenderer testBackgroundRenderer = new BackgroundRenderer("map1");
        return new BuildingsMap(testBackgroundRenderer);
    }
}
