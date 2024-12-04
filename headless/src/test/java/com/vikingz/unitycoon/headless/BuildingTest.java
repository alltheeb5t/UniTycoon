package com.vikingz.unitycoon.headless;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
        int testBuildingIndex = 0;  // Represents what variant we want. ACADEMIC 0 is a Ron Cooke Hub
        BuildingInfo testBuildingInfo = BuildingStats.getInfo(testBuildingType, testBuildingIndex);
        TextureRegion testBuildingTexture = BuildingStats.getTextureOfBuilding(BuildingStats.BuildingDict.get(testBuildingType)[testBuildingIndex]);

        assertEquals(true, testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 100, 10, true));
    }

    /**
     * Test that buildings can't be placed on top of other buildings
     */
    @Test
    public void buildingOnBuildingCollision() {
        BuildingsMap testMap = getTestMap();

        BuildingType testBuildingType = BuildingType.ACADEMIC;
        int testBuildingIndex = 0;  // Represents what variant we want. ACADEMIC 0 is a Ron Cooke Hub
        BuildingInfo testBuildingInfo = BuildingStats.getInfo(testBuildingType, testBuildingIndex);
        TextureRegion testBuildingTexture = BuildingStats.getTextureOfBuilding(BuildingStats.BuildingDict.get(testBuildingType)[testBuildingIndex]);

        assertEquals(true, testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 100, 10, true));

        // Fully on top of existing building
        assertEquals(false, testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 100, 10, true));

        // Partially on top of existing building
        assertEquals(false, testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 125, 10, true));
    }

    /**
     * Test that buildings can be placed very close to each other
     */
    @Test
    public void testValidPlacementClose() {
        BuildingsMap testMap = getTestMap();
        System.out.println(BuildingStats.BuildingDict.get(BuildingType.FOOD));

        BuildingType testBuildingType = BuildingType.FOOD;
        int testBuildingIndex = 0;  // Represents what variant we want. FOOD 0 is McDonalds
        BuildingInfo testBuildingInfo = BuildingStats.getInfo(testBuildingType, testBuildingIndex);
        TextureRegion testBuildingTexture = BuildingStats.getTextureOfBuilding(BuildingStats.BuildingDict.get(testBuildingType)[testBuildingIndex]);

        BuildingType testBuilding2Type = BuildingType.RECREATIONAL;
        int testBuilding2Index = 0;  // Represents what variant we want. RECREATIONAL 0 is York Sport Village
        BuildingInfo testBuilding2Info = BuildingStats.getInfo(testBuilding2Type, testBuilding2Index);
        TextureRegion testBuilding2Texture = BuildingStats.getTextureOfBuilding(BuildingStats.BuildingDict.get(testBuilding2Type)[testBuilding2Index]);

        // Right
        testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 480, 288);
        assertEquals(true, testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 608, 288, true),
                                                               "Testing closest valid placement to right of reference building (McDonalds)");

        // Top
        testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 896, 192);
        assertEquals(true, testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 896, 256, true),
                                                               "Testing closest valid placement above reference building (McDonalds)");
        
        testMap.attemptAddBuilding(testBuilding2Info, testBuilding2Texture, 1536, 352);
        assertEquals(true, testMap.attemptAddBuilding(testBuilding2Info, testBuilding2Texture, 1530, 448, true),
                                                                "Testing closest valid placement above reference building (York Sport Village)");

        // Bottom
        testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 1088, 288);
        assertEquals(true, testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 1088, 224, true),
                                                               "Testing closest valid placement below reference building (McDonalds)");
    }

    
    /**
     * Test placing buildings in close proximity to obstacles. All locations should be valid though
     */
    @Test
    public void testBuildingObstaclePlacementValid() {
        BuildingsMap testMap = getTestMap();

        BuildingType testBuildingType = BuildingType.ACADEMIC;
        int testBuildingIndex = 0;  // Represents what variant we want. ACADEMIC 0 is a Ron Cooke Hub
        BuildingInfo testBuildingInfo = BuildingStats.getInfo(testBuildingType, testBuildingIndex);
        TextureRegion testBuildingTexture = BuildingStats.getTextureOfBuilding(BuildingStats.BuildingDict.get(testBuildingType)[testBuildingIndex]);

        // Road & RCH
        assertEquals(true, testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 320, 544, true),
                                                               "Test placing RCH directly next to road (above)");
        
        assertEquals(true, testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 384, 352, true),
                                                               "Test placing RCH directly next to road (left)");
        
        assertEquals(true, testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 192, 352, true),
                                                               "Test placing RCH directly next to road (right)");

        assertEquals(true, testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 672, 384, true),
                                                               "Test placing RCH directly next to road (below)");
        
        // Lake & RCH
        assertEquals(true, testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 1280, 736, true),
                                                               "Test placing RCH directly next to lake (right)");

        assertEquals(true, testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 1248, 640, true),
                                                               "Test placing RCH directly next to lake (below)");

        BuildingType testBuilding2Type = BuildingType.RECREATIONAL;
        int testBuilding2Index = 0;  // Represents what variant we want. RECREATIONAL 0 is York Sport Village
        BuildingInfo testBuilding2Info = BuildingStats.getInfo(testBuilding2Type, testBuilding2Index);
        TextureRegion testBuilding2Texture = BuildingStats.getTextureOfBuilding(BuildingStats.BuildingDict.get(testBuilding2Type)[testBuilding2Index]);

        // Road & YSV
        assertEquals(true, testMap.attemptAddBuilding(testBuilding2Info, testBuilding2Texture, 540, 384, true),
                                                               "Test placing YSV directly next to road (below)");
        
        // Lake & YSV
        assertEquals(true, testMap.attemptAddBuilding(testBuilding2Info, testBuilding2Texture, 704, 544, true),
                                                               "Test placing YSV directly next to lake (below)");
    }

    /**
     * Test that buildings can't be placed when they partially or fully intersect with obstacles
     */
    @Test
    public void testObstacleCollision() {
        BuildingsMap testMap = getTestMap();

        BuildingType testBuildingType = BuildingType.ACADEMIC;
        int testBuildingIndex = 0;  // Represents what variant we want. ACADEMIC 0 is a Ron Cooke Hub
        BuildingInfo testBuildingInfo = BuildingStats.getInfo(testBuildingType, testBuildingIndex);
        TextureRegion testBuildingTexture = BuildingStats.getTextureOfBuilding(BuildingStats.BuildingDict.get(testBuildingType)[testBuildingIndex]);

        assertEquals(false, testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 896, 608, true),
                                                               "RCH partial intersection top edge");
        
        assertEquals(false, testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 1248, 736, true),
                                                               "Test placing RCH partially to left of lake");
        
        assertEquals(false, testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 384, 480, true),
                                                               "Test placing RCH fully on top of road");
    }

    /**
     * Testing that building placement interacts with edges of the map correctly
     */
    @Test
    public void testEdgeBoundary() {
        BuildingsMap testMap = getTestMap();

        BuildingType testBuildingType = BuildingType.FOOD;
        int testBuildingIndex = 0;  // Represents what variant we want. FOOD 0 is a MacDonalds
        BuildingInfo testBuildingInfo = BuildingStats.getInfo(testBuildingType, testBuildingIndex);
        TextureRegion testBuildingTexture = BuildingStats.getTextureOfBuilding(BuildingStats.BuildingDict.get(testBuildingType)[testBuildingIndex]);

        BuildingType testBuilding2Type = BuildingType.RECREATIONAL;
        int testBuilding2Index = 0;  // Represents what variant we want. RECREATIONAL 0 is a YSV
        BuildingInfo testBuilding2Info = BuildingStats.getInfo(testBuilding2Type, testBuilding2Index);
        TextureRegion testBuilding2Texture = BuildingStats.getTextureOfBuilding(BuildingStats.BuildingDict.get(testBuilding2Type)[testBuilding2Index]);

        BuildingType testBuilding3Type = BuildingType.ACADEMIC;
        int testBuilding3Index = 0;  // Represents what variant we want. ACADEMIC 0 is a RCH
        BuildingInfo testBuilding3Info = BuildingStats.getInfo(testBuilding3Type, testBuilding3Index);
        TextureRegion testBuilding3Texture = BuildingStats.getTextureOfBuilding(BuildingStats.BuildingDict.get(testBuilding3Type)[testBuilding3Index]);

        BuildingType testBuilding4Type = BuildingType.ACADEMIC;
        int testBuilding4Index = 1;  // Represents what variant we want. ACADEMIC 1 is a PZA
        BuildingInfo testBuilding4Info = BuildingStats.getInfo(testBuilding4Type, testBuilding4Index);
        TextureRegion testBuilding4Texture = BuildingStats.getTextureOfBuilding(BuildingStats.BuildingDict.get(testBuilding4Type)[testBuilding4Index]);

        BuildingType testBuilding5Type = BuildingType.ACCOMODATION;
        int testBuilding5Index = 0;  // Represents what variant we want. ACCOMODATION 0 is a DK
        BuildingInfo testBuilding5Info = BuildingStats.getInfo(testBuilding5Type, testBuilding5Index);
        TextureRegion testBuilding5Texture = BuildingStats.getTextureOfBuilding(BuildingStats.BuildingDict.get(testBuilding5Type)[testBuilding5Index]);

        assertEquals(false, testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, -64, 384, true),
                                                               "MacDonalds attempt to place too far left");
        
        assertEquals(false, testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 1728, 384, true),
                                                               "MacDonalds attempt to place too far right");
        
        assertEquals(true, testMap.attemptAddBuilding(testBuilding2Info, testBuilding2Texture, 1588, 896, true),
                                                               "Test placing YSV Very near top of map");

        testMap = getTestMap(); // Reset map to ensure enough space at the top
        
        assertEquals(true, testMap.attemptAddBuilding(testBuilding3Info, testBuilding3Texture, 1536, 928, true),
                                                               "Test placing RCH Very near top of map");
                                                               
        assertEquals(true, testMap.attemptAddBuilding(testBuilding4Info, testBuilding4Texture, 1664, 928, true),
                                                               "Test placing PZA Very near top of map");

        assertEquals(false, testMap.attemptAddBuilding(testBuilding5Info, testBuilding5Texture, 1088, -64, true),
                                                               "Test can't place David Kato beyond bottom of map");
        
        assertEquals(true, testMap.attemptAddBuilding(testBuilding5Info, testBuilding5Texture, 1664, 608, true),
                                                               "Test can place a David Kato building next to right side");
    }

    @Test
    public void testBuildingDeletion() {
        BuildingsMap testMap = getTestMap();
        System.out.println("Running Building deletion test");
        System.out.println(testMap.getPlacedBuildings());

        BuildingType testBuildingType = BuildingType.FOOD;
        int testBuildingIndex = 0;  // Represents what variant we want. FOOD 0 is McDonalds
        BuildingInfo testBuildingInfo = BuildingStats.getInfo(testBuildingType, testBuildingIndex);
        TextureRegion testBuildingTexture = BuildingStats.getTextureOfBuilding(BuildingStats.BuildingDict.get(testBuildingType)[testBuildingIndex]);

        testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 480, 288, true);

        assertFalse(testMap.attemptBuildingDeleteAt(412,300), "Confirm that buildings aren't deleted incorrectly");

        // Assume user clicks to delete in x centre, y bottom + 10
        assertTrue(testMap.attemptBuildingDeleteAt(512, 298), "Test building is deleted when centre coordinates are supplied");

        assertTrue(testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 480, 288, true), "Check that new building can be placed covering a deleted building");
    }
}

