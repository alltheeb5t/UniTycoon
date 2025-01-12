package com.vikingz.unitycoon.headless;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vikingz.unitycoon.building.BuildingStats;
import com.vikingz.unitycoon.building.BuildingStats.BuildingType;
import com.vikingz.unitycoon.util.FileHandler;

/**
 * Tests relating to loading and saving of file content
 */
public class FileHandlerTest extends TestSuper {
    
    /**
     * Test that the building file is correctly loaded and formed such that all building
     * dictionaries are populated.
     * Relates to: FR_MAP, FR_EATING_BUILDING, FR_ACCOMMODATION_BUILDING, FR_RECREATIONAL_BUILDING,
     *             FR_LEARNING_BUILDING
     */
    @Test
    public void loadBuildingFiles() {
        FileHandler.loadBuildings("buildingInfo","TextureAtlasMap");

        assertNotNull(BuildingStats.buildingDict);

        // Check that all buildings have all relevant details in the file
        Iterator<BuildingType> buildingTypeIterator = BuildingStats.buildingDict.keys().asIterator();

        String currentBuilding = "";

        try {
            while (buildingTypeIterator.hasNext()) {
                BuildingType buildingType = buildingTypeIterator.next();
                int buildingsOfType = BuildingStats.buildingDict.get(buildingType).length;

                for (int i = 0; i < buildingsOfType; i++) { // Need to check each variant of buildings
                    currentBuilding = "Building "+buildingType+" Index "+1;
                    assertNotNull(BuildingStats.buildingNameDict.get(buildingType)[i]);
                    assertNotNull(BuildingStats.buildingPriceDict.get(buildingType)[i]);
                    assertNotNull(BuildingStats.buildingCoinDict.get(buildingType)[i]);
                    assertNotNull(BuildingStats.buildingStudentDict.get(buildingType)[i]);
                    
                    // Test texture can be loaded
                    if (buildingType != BuildingType.NONE) { // None-type building have no texture
                        assertEquals(TextureRegion.class, BuildingStats.getTextureOfBuilding(BuildingStats.buildingDict.get(buildingType)[i]).getClass());
                    }
                }
            }
        } catch (NullPointerException e) {
            fail("One or more of the properties missing for: ("+currentBuilding+")");
        }
        
    }

    /**
     * Test loading the map
     * Relates to: FR_MAP
     */
    @Test
    public void loadMap() {
        // Test that map1 is successfully loaded
        assertTrue(FileHandler.loadMap("map1") != "");

        // Test that loading an invalid file name returns the empty string
        assertTrue(FileHandler.loadMap("unknownMAP") == "");
    }
}
