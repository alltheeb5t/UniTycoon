package com.vikingz.unitycoon.headless;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    protected boolean addBasicTestBuilding(BuildingsMap testMap, BuildingType testBuildingType, float x, float y) {
        int testBuildingIndex = 0;  // Represents what variant we want. ACADEMIC 0 is a Ron Cooke Hub
        BuildingInfo testBuildingInfo = BuildingStats.getInfo(testBuildingType, testBuildingIndex);
        TextureRegion testBuildingTexture = BuildingStats.getTextureOfBuilding(BuildingStats.BuildingDict.get(testBuildingType)[testBuildingIndex]);

        return testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, x, y, true);
    }

    protected BuildingsMap getTestMap() {
        FileHandler.loadBuildings("buildingInfo","TextureAtlasMap"); // Load list of buildings from file

        BackgroundRenderer testBackgroundRenderer = new BackgroundRenderer("map1");
        return new BuildingsMap(testBackgroundRenderer);
    }
}
