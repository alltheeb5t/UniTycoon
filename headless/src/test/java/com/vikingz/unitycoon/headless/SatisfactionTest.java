package com.vikingz.unitycoon.headless;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.vikingz.unitycoon.building.Building;
import com.vikingz.unitycoon.building.BuildingStats.BuildingType;
import com.vikingz.unitycoon.building.BuildingsMap;
import com.vikingz.unitycoon.global.GameGlobals;

import java.util.List;
public class SatisfactionTest extends TestSuper{

     /**
     * Test that the game initial Satisfaction starts at 10
     */
    @Test
    public void testInitialSatisfaction() {
        assertEquals(10, GameGlobals.SATISFACTION.getSatisfaction(), "Initial satisfaction should be 10");
    }

     /**
     * Test that the game Satisfaction dose not go over 100 and dose not go below 0
     */
    @Test
    public void testSatisfactionDoesNotExceedLimits() {
        GameGlobals.SATISFACTION.addBonus(200);
        assertEquals(100, GameGlobals.SATISFACTION.getSatisfaction(), "Satisfaction should not exceed 100");

        GameGlobals.SATISFACTION.applyPenalty(300);
        assertEquals(0, GameGlobals.SATISFACTION.getSatisfaction(), "Satisfaction should not go below 0");
    }

     /**
     * Test that the AddBonus adds the right number.
     */
    @Test
    public void testAddBonus() {
        GameGlobals.SATISFACTION.addBonus(15);
        assertEquals(25, GameGlobals.SATISFACTION.getSatisfaction(), "Satisfaction should increase by the bonus amount");
    }

     /**
     * Test that the ApplyPenalty minus the right number 
     */
    @Test
    public void testApplyPenalty() {
        GameGlobals.SATISFACTION.applyPenalty(5);
        assertEquals(5, GameGlobals.SATISFACTION.getSatisfaction(), "Satisfaction should decrease by the penalty amount");
    }

    /**
     * Test that if 4 kinds of building increases the right amount of satisfaction 
     */ 
      @Test
    public void testRecalculateSatisfactionWithBuildingProximity() {
        BuildingsMap testMap = getTestMap();

        // Add buildings using TestSuper's methods by putting all 4 kinds of bulidings together
        addBasicTestBuilding(testMap, BuildingType.FOOD, 928, 384, true, true);
        addBasicTestBuilding(testMap, BuildingType.RECREATIONAL, 1056, 384, true, true);
        addBasicTestBuilding(testMap, BuildingType.ACADEMIC, 928, 288, true, true);
        addBasicTestBuilding(testMap, BuildingType.ACCOMODATION, 1056, 288, true, true);

        // Get the placed buildings
        List<Building> placedBuildings = testMap.getPlacedBuildings();

        GameGlobals.SATISFACTION.recalculateSatisfaction(placedBuildings);
        
        

        
        assertTrue(GameGlobals.SATISFACTION.getSatisfaction() >= 22, "Satisfaction should be recalculated correctly");
        System.out.println(GameGlobals.SATISFACTION.getSatisfaction());
    }

    /**
     * Test that two of the same building adjacent to each other have an appropriate effect
     */ 
     @Test
     public void testDuplicationBiilding () {
         BuildingsMap testMap = getTestMap();
 
         // Add buildings using TestSuper's methods by putting two same type
         // buliding together
         addBasicTestBuilding(testMap, BuildingType.FOOD, 928, 384, true);
         addBasicTestBuilding(testMap, BuildingType.FOOD, 1056, 384, true);
         
 
         // Get the placed buildings
         List<Building> placedBuildings = testMap.getPlacedBuildings();
 
         GameGlobals.SATISFACTION.recalculateSatisfaction(placedBuildings);
         assertTrue(GameGlobals.SATISFACTION.getSatisfaction() >= 6, "Satisfaction should be recalculated correctly");
         System.out.println(GameGlobals.SATISFACTION.getSatisfaction());
     }
    
}
