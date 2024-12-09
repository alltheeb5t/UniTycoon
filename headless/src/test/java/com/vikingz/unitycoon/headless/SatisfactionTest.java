package com.vikingz.unitycoon.headless;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.vikingz.unitycoon.building.Building;
import com.vikingz.unitycoon.building.BuildingStats.BuildingType;
import com.vikingz.unitycoon.building.BuildingsMap;
import com.vikingz.unitycoon.util.SatisfactionHandler;

import java.util.List;
public class SatisfactionTest extends TestSuper{
    private SatisfactionHandler satisfactionHandler;

    @BeforeEach
    public void setup() {
        satisfactionHandler = new SatisfactionHandler();
        Gdx.gl = Gdx.gl20 = mock(GL20.class);
        HeadlessLauncher.main(new String[0]);
    }


     /**
     * Test that the game initial Satisfaction starts at 10
     */
    @Test
    public void testInitialSatisfaction() {
        assertEquals(10, satisfactionHandler.getSatisfaction(), "Initial satisfaction should be 10");
    }

     /**
     * Test that the game Satisfaction dose not go over 100 and dose not go below 0
     */
    @Test
    public void testSatisfactionDoesNotExceedLimits() {
        satisfactionHandler.addBonus(200);
        assertEquals(100, satisfactionHandler.getSatisfaction(), "Satisfaction should not exceed 100");

        satisfactionHandler.applyPenalty(300);
        assertEquals(0, satisfactionHandler.getSatisfaction(), "Satisfaction should not go below 0");
    }

     /**
     * Test that the AddBonus adds the right number.
     */
    @Test
    public void testAddBonus() {
        satisfactionHandler.addBonus(15);
        assertEquals(25, satisfactionHandler.getSatisfaction(), "Satisfaction should increase by the bonus amount");
    }

     /**
     * Test that the ApplyPenalty minus the right number 
     */
    @Test
    public void testApplyPenalty() {
        satisfactionHandler.applyPenalty(5);
        assertEquals(5, satisfactionHandler.getSatisfaction(), "Satisfaction should decrease by the penalty amount");
    }

    /**
     * Test that if 4 kinds of buliding increses the right amout of statisfaction 
     */ 
      @Test
    public void testRecalculateSatisfactionWithBuildingProximity() {
        BuildingsMap testMap = getTestMap();

        // Add buildings using TestSuper's methods by putting all 4 kinds of bulidings together
        addBasicTestBuilding(testMap, BuildingType.FOOD, 928, 384, true);
        addBasicTestBuilding(testMap, BuildingType.RECREATIONAL, 1056, 384, true);
        addBasicTestBuilding(testMap, BuildingType.ACADEMIC, 928, 288, true);
        addBasicTestBuilding(testMap, BuildingType.ACCOMODATION, 1056, 288, true);

        // Get the placed buildings
        List<Building> placedBuildings = testMap.getPlacedBuildings();

        satisfactionHandler.recalculateSatisfaction(placedBuildings);
        
        

        
        assertTrue(satisfactionHandler.getSatisfaction() >= 22, "Satisfaction should be recalculated correctly");
        System.out.println(satisfactionHandler.getSatisfaction());
    }
    /**
     * Test that if 4 kinds of buliding increses the right amout of statisfaction 
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
 
         satisfactionHandler.recalculateSatisfaction(placedBuildings);
         assertTrue(satisfactionHandler.getSatisfaction() >= 6, "Satisfaction should be recalculated correctly");
         System.out.println(satisfactionHandler.getSatisfaction());
     }
    
}
