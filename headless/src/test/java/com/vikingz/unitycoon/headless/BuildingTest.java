package com.vikingz.unitycoon.headless;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vikingz.unitycoon.building.BuildingInfo;
import com.vikingz.unitycoon.building.BuildingStats;
import com.vikingz.unitycoon.building.BuildingStats.BuildingType;
import com.vikingz.unitycoon.building.BuildingsMap;

public class BuildingTest extends TestSuper {
    /**
     * Test placement of building in empty space
     * Relates to: FR_MAP
     */
    @Test
    public void validPlacement() {
        BuildingsMap testMap = getTestMap();

        BuildingType testBuildingType = BuildingType.ACADEMIC;
        int testBuildingIndex = 0;  // Represents what variant we want. ACADEMIC 0 is a Ron Cooke Hub
        BuildingInfo testBuildingInfo = BuildingStats.getInfo(testBuildingType, testBuildingIndex);
        TextureRegion testBuildingTexture = BuildingStats.getTextureOfBuilding(BuildingStats.buildingDict.get(testBuildingType)[testBuildingIndex]);

        assertFalse(testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 100, 10, true).isEmpty());
    }

    /**
     * Test that buildings can't be placed on top of other buildings
     * Relates to: FR_MAP, FR_NO_OVERLAP
     */
    @Test
    public void buildingOnBuildingCollision() {
        BuildingsMap testMap = getTestMap();

        BuildingType testBuildingType = BuildingType.ACADEMIC;
        int testBuildingIndex = 0;  // Represents what variant we want. ACADEMIC 0 is a Ron Cooke Hub
        BuildingInfo testBuildingInfo = BuildingStats.getInfo(testBuildingType, testBuildingIndex);
        TextureRegion testBuildingTexture = BuildingStats.getTextureOfBuilding(BuildingStats.buildingDict.get(testBuildingType)[testBuildingIndex]);

        assertFalse(testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 100, 10, true).isEmpty());

        // Fully on top of existing building
        assertTrue(testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 100, 10, true).isEmpty());

        // Partially on top of existing building
        assertTrue(testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 125, 10, true).isEmpty());
    }

    /**
     * Test that buildings can be placed very close to each other
     * Relates to: FR_MAP, FR_NO_OVERLAP, FR_EATING_BUILDING
     */
    @Test
    public void testValidPlacementClose() {
        BuildingsMap testMap = getTestMap();
        System.out.println(BuildingStats.buildingDict.get(BuildingType.FOOD));

        BuildingType testBuildingType = BuildingType.FOOD;
        int testBuildingIndex = 0;  // Represents what variant we want. FOOD 0 is McDonalds
        BuildingInfo testBuildingInfo = BuildingStats.getInfo(testBuildingType, testBuildingIndex);
        TextureRegion testBuildingTexture = BuildingStats.getTextureOfBuilding(BuildingStats.buildingDict.get(testBuildingType)[testBuildingIndex]);

        BuildingType testBuilding2Type = BuildingType.RECREATIONAL;
        int testBuilding2Index = 0;  // Represents what variant we want. RECREATIONAL 0 is York Sport Village
        BuildingInfo testBuilding2Info = BuildingStats.getInfo(testBuilding2Type, testBuilding2Index);
        TextureRegion testBuilding2Texture = BuildingStats.getTextureOfBuilding(BuildingStats.buildingDict.get(testBuilding2Type)[testBuilding2Index]);

        // Right
        assertFalse(testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 480, 288, true).isEmpty());
        assertFalse(testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 608, 288, true).isEmpty(),
                                                               "Testing closest valid placement to right of reference building (McDonalds)");

        // Top
        assertFalse(testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 896, 192, true).isEmpty());
        assertFalse(testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 896, 288, true).isEmpty(),
                                                               "Testing closest valid placement above reference building (McDonalds)");
        
        assertFalse(testMap.attemptAddBuilding(testBuilding2Info, testBuilding2Texture, 1536, 352, true).isEmpty());
        assertFalse(testMap.attemptAddBuilding(testBuilding2Info, testBuilding2Texture, 1530, 448, true).isEmpty(),
                                                                "Testing closest valid placement above reference building (York Sport Village)");

        // Bottom
        assertFalse(testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 1088, 288, true).isEmpty());
        assertFalse(testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 1088, 192, true).isEmpty(),
                                                               "Testing closest valid placement below reference building (McDonalds)");
    }

    
    /**
     * Test placing buildings in close proximity to obstacles. All locations should be valid though
     * Relates to: FR_MAP, FR_NO_OVERLAP, FR_LEARNING_BUILDING, FR_RECREATION_BUILDING
     */
    @Test
    public void testBuildingObstaclePlacementValid() {
        BuildingsMap testMap = getTestMap();

        BuildingType testBuildingType = BuildingType.ACADEMIC;
        int testBuildingIndex = 0;  // Represents what variant we want. ACADEMIC 0 is a Ron Cooke Hub
        BuildingInfo testBuildingInfo = BuildingStats.getInfo(testBuildingType, testBuildingIndex);
        TextureRegion testBuildingTexture = BuildingStats.getTextureOfBuilding(BuildingStats.buildingDict.get(testBuildingType)[testBuildingIndex]);

        // Road & RCH
        assertFalse(testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 320, 544, true).isEmpty(),
                                                               "Test placing RCH directly next to road (above)");
        
        assertFalse(testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 384, 352, true).isEmpty(),
                                                               "Test placing RCH directly next to road (left)");
        
        assertFalse(testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 192, 352, true).isEmpty(),
                                                               "Test placing RCH directly next to road (right)");

        assertFalse(testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 672, 384, true).isEmpty(),
                                                               "Test placing RCH directly next to road (below)");
        
        // Lake & RCH
        assertFalse(testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 1280, 736, true).isEmpty(),
                                                               "Test placing RCH directly next to lake (right)");

        assertFalse(testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 1248, 640, true).isEmpty(),
                                                               "Test placing RCH directly next to lake (below)");

        BuildingType testBuilding2Type = BuildingType.RECREATIONAL;
        int testBuilding2Index = 0;  // Represents what variant we want. RECREATIONAL 0 is York Sport Village
        BuildingInfo testBuilding2Info = BuildingStats.getInfo(testBuilding2Type, testBuilding2Index);
        TextureRegion testBuilding2Texture = BuildingStats.getTextureOfBuilding(BuildingStats.buildingDict.get(testBuilding2Type)[testBuilding2Index]);

        // Road & YSV
        assertFalse(testMap.attemptAddBuilding(testBuilding2Info, testBuilding2Texture, 540, 384, true).isEmpty(),
                                                               "Test placing YSV directly next to road (below)");
        
        // Lake & YSV
        assertFalse(testMap.attemptAddBuilding(testBuilding2Info, testBuilding2Texture, 704, 544, true).isEmpty(),
                                                               "Test placing YSV directly next to lake (below)");
    }

    /**
     * Test that buildings can't be placed when they partially or fully intersect with obstacles
     * Relates to: FR_MAP, FR_NO_OVERLAP, FR_LEARNING_BUILDING
     */
    @Test
    public void testObstacleCollision() {
        BuildingsMap testMap = getTestMap();

        BuildingType testBuildingType = BuildingType.ACADEMIC;
        int testBuildingIndex = 0;  // Represents what variant we want. ACADEMIC 0 is a Ron Cooke Hub
        BuildingInfo testBuildingInfo = BuildingStats.getInfo(testBuildingType, testBuildingIndex);
        TextureRegion testBuildingTexture = BuildingStats.getTextureOfBuilding(BuildingStats.buildingDict.get(testBuildingType)[testBuildingIndex]);

        assertTrue(testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 896, 608, true).isEmpty(),
                                                               "RCH partial intersection top edge");
        
        assertTrue(testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 1248, 736, true).isEmpty(),
                                                               "Test placing RCH partially to left of lake");
        
        assertTrue(testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 384, 480, true).isEmpty(),
                                                               "Test placing RCH fully on top of road");
    }

    /**
     * Testing that building placement interacts with edges of the map correctly
     * Relates to: FR_MAP, FR_NO_OVERLAP, FR_EATING_BUILDING, FR_ACCOMMODATION_BUILDING,
     *             FR_RECREATIONAL_BUILDING, FR_LEARNING_BUILDING
     */
    @Test
    public void testEdgeBoundary() {
        BuildingsMap testMap = getTestMap();

        BuildingType testBuildingType = BuildingType.FOOD;
        int testBuildingIndex = 0;  // Represents what variant we want. FOOD 0 is a MacDonalds
        BuildingInfo testBuildingInfo = BuildingStats.getInfo(testBuildingType, testBuildingIndex);
        TextureRegion testBuildingTexture = BuildingStats.getTextureOfBuilding(BuildingStats.buildingDict.get(testBuildingType)[testBuildingIndex]);

        BuildingType testBuilding2Type = BuildingType.RECREATIONAL;
        int testBuilding2Index = 0;  // Represents what variant we want. RECREATIONAL 0 is a YSV
        BuildingInfo testBuilding2Info = BuildingStats.getInfo(testBuilding2Type, testBuilding2Index);
        TextureRegion testBuilding2Texture = BuildingStats.getTextureOfBuilding(BuildingStats.buildingDict.get(testBuilding2Type)[testBuilding2Index]);

        BuildingType testBuilding3Type = BuildingType.ACADEMIC;
        int testBuilding3Index = 0;  // Represents what variant we want. ACADEMIC 0 is a RCH
        BuildingInfo testBuilding3Info = BuildingStats.getInfo(testBuilding3Type, testBuilding3Index);
        TextureRegion testBuilding3Texture = BuildingStats.getTextureOfBuilding(BuildingStats.buildingDict.get(testBuilding3Type)[testBuilding3Index]);

        BuildingType testBuilding4Type = BuildingType.ACADEMIC;
        int testBuilding4Index = 1;  // Represents what variant we want. ACADEMIC 1 is a PZA
        BuildingInfo testBuilding4Info = BuildingStats.getInfo(testBuilding4Type, testBuilding4Index);
        TextureRegion testBuilding4Texture = BuildingStats.getTextureOfBuilding(BuildingStats.buildingDict.get(testBuilding4Type)[testBuilding4Index]);

        BuildingType testBuilding5Type = BuildingType.ACCOMODATION;
        int testBuilding5Index = 0;  // Represents what variant we want. ACCOMODATION 0 is a DK
        BuildingInfo testBuilding5Info = BuildingStats.getInfo(testBuilding5Type, testBuilding5Index);
        TextureRegion testBuilding5Texture = BuildingStats.getTextureOfBuilding(BuildingStats.buildingDict.get(testBuilding5Type)[testBuilding5Index]);

        assertTrue(testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, -64, 384, true).isEmpty(),
                                                               "MacDonalds attempt to place too far left");
        
        assertTrue(testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 1728, 384, true).isEmpty(),
                                                               "MacDonalds attempt to place too far right");
        
        assertFalse(testMap.attemptAddBuilding(testBuilding2Info, testBuilding2Texture, 1588, 896, true).isEmpty(),
                                                               "Test placing YSV Very near top of map");

        testMap = getTestMap(); // Reset map to ensure enough space at the top
        
        assertFalse(testMap.attemptAddBuilding(testBuilding3Info, testBuilding3Texture, 1536, 864, true).isEmpty(),
                                                               "Test placing RCH Very near top of map");
                                                               
        assertFalse(testMap.attemptAddBuilding(testBuilding4Info, testBuilding4Texture, 1664, 864, true).isEmpty(),
                                                               "Test placing PZA Very near top of map");

        assertTrue(testMap.attemptAddBuilding(testBuilding5Info, testBuilding5Texture, 1088, -64, true).isEmpty(),
                                                               "Test can't place David Kato beyond bottom of map");
        
        assertFalse(testMap.attemptAddBuilding(testBuilding5Info, testBuilding5Texture, 1664, 608, true).isEmpty(),
                                                               "Test can place a David Kato building next to right side");
    }

    /**
     * Tests that a building can be deleted and removed from the map
     * Relates to: FR_MAP
     */
    @Test
    public void testBuildingDeletion() {
        BuildingsMap testMap = getTestMap();
        System.out.println("Running Building deletion test");
        System.out.println(testMap.getPlacedBuildings());

        BuildingType testBuildingType = BuildingType.FOOD;
        int testBuildingIndex = 0;  // Represents what variant we want. FOOD 0 is McDonalds
        BuildingInfo testBuildingInfo = BuildingStats.getInfo(testBuildingType, testBuildingIndex);
        TextureRegion testBuildingTexture = BuildingStats.getTextureOfBuilding(BuildingStats.buildingDict.get(testBuildingType)[testBuildingIndex]);

        testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 480, 288, true);

        assertTrue(testMap.attemptBuildingDeleteAt(412,300).isEmpty(), "Confirm that buildings aren't deleted incorrectly");

        // Assume user clicks to delete in x centre, y bottom + 10
        assertFalse(testMap.attemptBuildingDeleteAt(512, 298).isEmpty(), "Test building is deleted when centre coordinates are supplied");

        assertFalse(testMap.attemptAddBuilding(testBuildingInfo, testBuildingTexture, 480, 288, true).isEmpty(), "Check that new building can be placed covering a deleted building");
    }
}

