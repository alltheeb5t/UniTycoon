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
import com.vikingz.unitycoon.global.GameGlobals;
import com.vikingz.unitycoon.building.BuildingsMap;
import com.vikingz.unitycoon.render.BackgroundRenderer;
import com.vikingz.unitycoon.util.FileHandler;

public class TestSuper {
    @BeforeEach
    public void setup() {
        Gdx.gl = Gdx.gl20 = mock(GL20.class);
        HeadlessLauncher.main(new String[0]);
        GameGlobals.resetGlobals(0);
    }

    // A list of locations in which buildings can be placed simultaneously
    Integer[][] validCoords = {
        {0, 832}, {128, 832}, {256, 832}, {384, 832}, {0, 736}, {128, 736}, {256, 736}, {128, 640}, {0, 640}, {384, 384}, {384, 288}, {384, 192},
        {512, 384}, {512, 288}, {512, 192}, {640, 384}, {640, 288}, {640, 192}, {768, 384}, {768, 288}, {768, 192}, {896, 384}, {896, 288}, {896, 192},
        {1024, 384}, {1024, 288}, {1024, 192}, {1152, 384}, {1152, 288}, {1152, 192}, {1280, 384}, {1280, 288}, {1280, 192}, {1408, 384}, {1408, 288},
        {1408, 192}, {1536, 384}, {1536, 288}, {1536, 192}, {1664, 384}, {1664, 288}, {1664, 192}, {1504, 864}, {1632, 864}, {1504, 768}, {1632, 768}
    };

    /**
     * Attempt to create a new building and then return it as by the attemptAddBuilding method
     * @param testMap
     * @param testBuildingType
     * @param x
     * @param y
     * @param skipConstruction
     * @return
     */
    protected List<Building> addGetBasicTestBuilding(BuildingsMap testMap, BuildingType testBuildingType, float x, float y, boolean skipConstruction, boolean ignoreCost) {
        int testBuildingIndex = 0;  // Represents what variant we want. ACADEMIC 0 is a Ron Cooke Hub
        BuildingInfo testBuildingInfo = BuildingStats.getInfo(testBuildingType, testBuildingIndex);
        TextureRegion testBuildingTexture = BuildingStats.getTextureOfBuilding(BuildingStats.buildingDict.get(testBuildingType)[testBuildingIndex]);

        List<Building> addedBuilding = testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, x, y, ignoreCost);

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
    protected boolean addBasicTestBuilding(BuildingsMap testMap, BuildingType testBuildingType, float x, float y, boolean skipConstruction, boolean ignoreCost) {
        return !addGetBasicTestBuilding(testMap, testBuildingType, x, y, skipConstruction, ignoreCost).isEmpty();
    }

    // ignoreCost defaults to false
    protected boolean addBasicTestBuilding(BuildingsMap testMap, BuildingType testBuildingType, float x, float y, boolean skipConstruction) {
        return addBasicTestBuilding(testMap, testBuildingType, x, y, skipConstruction, true);
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
