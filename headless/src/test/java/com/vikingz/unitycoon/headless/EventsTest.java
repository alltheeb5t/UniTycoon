package com.vikingz.unitycoon.headless;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.vikingz.unitycoon.building.BuildingsMap;
import com.vikingz.unitycoon.building.EarnSchedule;
import com.vikingz.unitycoon.building.Building;
import com.vikingz.unitycoon.building.BuildingStats.BuildingType;
import com.vikingz.unitycoon.events.EventHandler;
import com.vikingz.unitycoon.events.eventfiles.AlumniEvent;
import com.vikingz.unitycoon.events.eventfiles.AwardEvent;
import com.vikingz.unitycoon.events.eventfiles.BusChangeEvent;
import com.vikingz.unitycoon.events.eventfiles.Event;
import com.vikingz.unitycoon.events.eventfiles.FeeIncreaseEvent;
import com.vikingz.unitycoon.events.eventfiles.FireEvent;
import com.vikingz.unitycoon.events.eventfiles.FloodEvent;
import com.vikingz.unitycoon.events.eventfiles.RosesEvent;
import com.vikingz.unitycoon.events.eventfiles.RosesLoseEvent;
import com.vikingz.unitycoon.events.eventfiles.RosesWinEvent;
import com.vikingz.unitycoon.events.eventfiles.SponsorEvent;
import com.vikingz.unitycoon.events.eventfiles.StrikesEvent;
import com.vikingz.unitycoon.events.eventfiles.StrikesResolvedEvent;
import com.vikingz.unitycoon.global.GameGlobals;

public class EventsTest extends TestSuper {
    
    /**
     * Test that appropriate times are generated for all events
     */
    @Test
    public void testEventTimeGeneration() {
        EventHandler testEventHandler = new EventHandler();

        int[] chosenTimes = testEventHandler.getEventTimes();

        assertEquals(3, chosenTimes.length, "Confirm 3 events have been scheduled");

        assertTrue(chosenTimes[0] >= 200 && chosenTimes [0] < 300, "Confirm first event happens in first year");
        assertTrue(chosenTimes[1] >= 100 && chosenTimes [1] < 200, "Confirm next event happens in second year");
        assertTrue(chosenTimes[2] >= 000 && chosenTimes [2] < 100, "Confirm final event happens in third year");
    }


    // ─── Alumni Donation ─────────────────────────────────────────────────

    @Test
    public void testAlumniEvent() {
        Event relevantEvent = new AlumniEvent();

        float originalBalance = GameGlobals.MONEY.getBalance();
        float originalSatisfaction = GameGlobals.SATISFACTION.getSatisfaction();

        relevantEvent.leftRun.run();

        assertEquals(originalBalance+1000, GameGlobals.MONEY.getBalance(), "This event should increase balance by £1000k");
        assertEquals(originalSatisfaction, GameGlobals.SATISFACTION.getSatisfaction(), "This event should have no effect on satisfaction");

        assertEquals(1, GameGlobals.EVENT.getPositiveEvent(), "Check the event is registered as positive event");
    }

    
    // ─── University Receives An Award ────────────────────────────────────

    @Test
    public void testAwardEvent() {
        Event relevantEvent = new AwardEvent();

        float originalBalance = GameGlobals.MONEY.getBalance();
        float originalSatisfaction = GameGlobals.SATISFACTION.getSatisfaction();

        // Literally nothing should happen when the award event occurs
        relevantEvent.leftRun.run();
        relevantEvent.rightRun.run();

        assertEquals(originalBalance, GameGlobals.MONEY.getBalance(), "This event should have no effect on balance");
        assertEquals(originalSatisfaction, GameGlobals.SATISFACTION.getSatisfaction(), "This event should have no effect on satisfaction");

        assertEquals(1, GameGlobals.EVENT.getPositiveEvent(), "Check the event is registered as positive event");
    }


    // ─── Bus Routes Change ───────────────────────────────────────────────

    @Test
    public void testBusChangeEvent() {
        Event relevantEvent = new BusChangeEvent();

        float originalBalance = GameGlobals.MONEY.getBalance();
        float originalSatisfaction = GameGlobals.SATISFACTION.getSatisfaction();

        relevantEvent.leftRun.run();

        assertEquals(originalBalance, GameGlobals.MONEY.getBalance(), "This event should have no effect on balance");
        assertEquals(originalSatisfaction-10, GameGlobals.SATISFACTION.getSatisfaction(), "This event should decrease satisfaction by 10%");

        assertEquals(1, GameGlobals.EVENT.getNegativeEvent(), "Check the event is registered as negative event");
    }


    // ─── Fee Increases ───────────────────────────────────────────────────

    @Test
    public void testFeeIncreaseEvent() {
        BuildingsMap testMap = getTestMap();
        GameGlobals.BUILDINGS_MAP = testMap; // Necessary since this event depends on the map

        // Add a building for testing
        assertTrue(addBasicTestBuilding(testMap, BuildingType.ACCOMODATION, validCoords[0][0], validCoords[0][1], true, true));

        Event relevantEvent = new FeeIncreaseEvent();

        float originalBalance = GameGlobals.MONEY.getBalance();
        float originalSatisfaction = GameGlobals.SATISFACTION.getSatisfaction();

        GameGlobals.MONEY.earn(testMap.getPlacedBuildings(), EarnSchedule.SEMESTERLY);

        float preEventEarnings = GameGlobals.MONEY.getBalance() - originalBalance;
        
        relevantEvent.leftRun.run();

        GameGlobals.MONEY.earn(testMap.getPlacedBuildings(), EarnSchedule.SEMESTERLY);
        float postEventEarnings = GameGlobals.MONEY.getBalance() - originalBalance - preEventEarnings;

        assertTrue(postEventEarnings > preEventEarnings, "The accommodation building should yield more income after the event");

        // Confirm that satisfaction is handled as expected
        assertEquals(originalSatisfaction-10, GameGlobals.SATISFACTION.getSatisfaction(), "This event should apply a 10% satisfaction penalty");

        assertEquals(0, GameGlobals.EVENT.getNegativeEvent(), "Check the event is registered as neutral event");
        assertEquals(0, GameGlobals.EVENT.getPositiveEvent(), "Check the event is registered as neutral event");
    }


    // ─── Fire ────────────────────────────────────────────────────────────

    @Test
    public void testFireEventExtinguish() {
        BuildingsMap testMap = getTestMap();
        GameGlobals.BUILDINGS_MAP = testMap; // Necessary since this event depends on the map

        // Add a building for testing
        List<Building> myTestBuilding = addGetBasicTestBuilding(testMap, BuildingType.ACCOMODATION, validCoords[0][0], validCoords[0][1], true, true);
        assertEquals(1, myTestBuilding.size());

        Event relevantEvent = new FireEvent();

        float originalBalance = GameGlobals.MONEY.getBalance();
        float originalSatisfaction = GameGlobals.SATISFACTION.getSatisfaction();

        myTestBuilding.get(0).setOnFire(false); // Extinguish building (usually done by right clicking)

        relevantEvent.leftRun.run();

        // Usually run in game screen two seconds after original event
        GameGlobals.EVENT.getEventQueue().get(GameGlobals.TIME_REMAINING-2).run();
        GameGlobals.EVENT.reduceEventQueue(GameGlobals.TIME_REMAINING-2);

        assertEquals(originalBalance, GameGlobals.MONEY.getBalance(), "This event should have no effect on balance");
        assertEquals(originalSatisfaction, GameGlobals.SATISFACTION.getSatisfaction(), "This event should have no effect on satisfaction");

        assertEquals(1, GameGlobals.EVENT.getNegativeEvent(), "Check the event is registered as negative event");
    }

    @Test
    public void testFireEventDestroy() {
        BuildingsMap testMap = getTestMap();
        GameGlobals.BUILDINGS_MAP = testMap; // Necessary since this event depends on the map

        // Add a building for testing
        List<Building> myTestBuilding = addGetBasicTestBuilding(testMap, BuildingType.ACCOMODATION, validCoords[0][0], validCoords[0][1], true, true);
        assertEquals(1, myTestBuilding.size());

        Event relevantEvent = new FireEvent();

        float originalBalance = GameGlobals.MONEY.getBalance();
        float originalSatisfaction = GameGlobals.SATISFACTION.getSatisfaction();

        int originalBuildingCount = GameGlobals.ACADEMIC_BUILDINGS_COUNT + GameGlobals.ACCOMODATION_BUILDINGS_COUNT +
                                    GameGlobals.FOOD_BUILDINGS_COUNT + GameGlobals.RECREATIONAL_BUILDINGS_COUNT;
        
        relevantEvent.leftRun.run();

        // Usually run in game screen two seconds after original event
        GameGlobals.EVENT.getEventQueue().get(GameGlobals.TIME_REMAINING-2).run();
        GameGlobals.EVENT.reduceEventQueue(GameGlobals.TIME_REMAINING-2);

        // Confirm building deleted
        int newBuildingCount = GameGlobals.ACADEMIC_BUILDINGS_COUNT + GameGlobals.ACCOMODATION_BUILDINGS_COUNT +
                                GameGlobals.FOOD_BUILDINGS_COUNT + GameGlobals.RECREATIONAL_BUILDINGS_COUNT;

        assertEquals(originalBuildingCount-1, newBuildingCount, "Confirm that a single building is removed");

        // Confirm that balance is handled as expected
        assertEquals(originalBalance+750, GameGlobals.MONEY.getBalance(), "Balance should have increased by £750k (represents insurance payout).");
        assertEquals(originalSatisfaction, GameGlobals.SATISFACTION.getSatisfaction(), "This event should have no effect on satisfaction");

        assertEquals(1, GameGlobals.EVENT.getNegativeEvent(), "Check the event is registered as negative event");
    }

    @Test
    public void testFireEventNoBuildings() {
        BuildingsMap testMap = getTestMap();
        GameGlobals.BUILDINGS_MAP = testMap; // Necessary since this event depends on the map
        
        Event relevantEvent = new FireEvent();

        float originalBalance = GameGlobals.MONEY.getBalance();
        float originalSatisfaction = GameGlobals.SATISFACTION.getSatisfaction();

        // There is no choice by the user so action is in leftRun.
        // Nothing should happen if there are no buildings placed
        relevantEvent.leftRun.run();

        assertEquals(originalBalance, GameGlobals.MONEY.getBalance(), "This event should have no effect on balance");
        assertEquals(originalSatisfaction, GameGlobals.SATISFACTION.getSatisfaction(), "This event should have no effect on satisfaction");

        assertEquals(1, GameGlobals.EVENT.getNegativeEvent(), "Check the event is registered as negative event");
    }


    // ─── Flooding ────────────────────────────────────────────────────────

    @Test
    public void testFloodEventNoBuildings() {
        BuildingsMap testMap = getTestMap();
        GameGlobals.BUILDINGS_MAP = testMap; // Necessary since this event depends on the map

        Event relevantEvent = new FloodEvent();

        float originalBalance = GameGlobals.MONEY.getBalance();
        float originalSatisfaction = GameGlobals.SATISFACTION.getSatisfaction();

        // There is no choice by the user so action is in leftRun.
        // Nothing should happen if there are no buildings placed
        relevantEvent.leftRun.run();

        assertEquals(originalBalance, GameGlobals.MONEY.getBalance(), "This event should have no effect on balance");
        assertEquals(originalSatisfaction, GameGlobals.SATISFACTION.getSatisfaction(), "This event should have no effect on satisfaction");

        assertEquals(1, GameGlobals.EVENT.getNegativeEvent(), "Check the event is registered as negative event");
    }

    @Test
    public void testFloodEvent() {
        BuildingsMap testMap = getTestMap();
        GameGlobals.BUILDINGS_MAP = testMap; // Necessary since this event depends on the map

        assertTrue(addBasicTestBuilding(testMap, BuildingType.ACCOMODATION, validCoords[0][0], validCoords[0][1], true, true));
        assertTrue(addBasicTestBuilding(testMap, BuildingType.ACCOMODATION, validCoords[1][0], validCoords[1][1], true, true));
        assertTrue(addBasicTestBuilding(testMap, BuildingType.ACCOMODATION, validCoords[2][0], validCoords[2][1], true, true));

        Event relevantEvent = new FloodEvent();

        float originalBalance = GameGlobals.MONEY.getBalance();

        int originalBuildingCount = GameGlobals.ACADEMIC_BUILDINGS_COUNT + GameGlobals.ACCOMODATION_BUILDINGS_COUNT +
                                    GameGlobals.FOOD_BUILDINGS_COUNT + GameGlobals.RECREATIONAL_BUILDINGS_COUNT;

        // There is no choice by the user so action is in leftRun
        relevantEvent.leftRun.run();

        int newBuildingCount = GameGlobals.ACADEMIC_BUILDINGS_COUNT + GameGlobals.ACCOMODATION_BUILDINGS_COUNT +
                                GameGlobals.FOOD_BUILDINGS_COUNT + GameGlobals.RECREATIONAL_BUILDINGS_COUNT;

        assertEquals(originalBuildingCount-1, newBuildingCount, "Confirm that a single building is removed");

        assertEquals(originalBalance + 750, GameGlobals.MONEY.getBalance(), "Balance should have increased by £750k (represents insurance payout)");
        // We're not testing satisfaction since removing a building WILL affect satisfaction

        assertEquals(1, GameGlobals.EVENT.getNegativeEvent(), "Check the event is registered as negative event");
    }


    // ─── Roses Event ─────────────────────────────────────────────────────

    @Test
    public void testRosesEvent() {
        BuildingsMap testMap = getTestMap();
        GameGlobals.BUILDINGS_MAP = testMap; // Necessary since this event depends on the map

        // Add a building for testing
        assertTrue(addBasicTestBuilding(testMap, BuildingType.RECREATIONAL, validCoords[0][0], validCoords[0][1], true, true));

        Event relevantEvent = new RosesEvent();

        float originalBalance = GameGlobals.MONEY.getBalance();
        float originalSatisfaction = GameGlobals.SATISFACTION.getSatisfaction();

        GameGlobals.MONEY.earn(testMap.getPlacedBuildings(), EarnSchedule.DAILY);

        float preEventEarnings = GameGlobals.MONEY.getBalance() - originalBalance;
        
        relevantEvent.leftRun.run();

        GameGlobals.MONEY.earn(testMap.getPlacedBuildings(), EarnSchedule.DAILY);
        float postEventEarnings = GameGlobals.MONEY.getBalance() - originalBalance - preEventEarnings;

        assertTrue(postEventEarnings > preEventEarnings, "The recreational buildings should yield more income after the event");

        // Confirm that satisfaction is handled as expected
        assertEquals(originalSatisfaction, GameGlobals.SATISFACTION.getSatisfaction(), "This event should have no effect on satisfaction");

        assertEquals(1, GameGlobals.EVENT.getPositiveEvent(), "Check the event is registered as a positive event");
    }

    @Test
    public void testRosesWinEvent() {
        BuildingsMap testMap = getTestMap();
        GameGlobals.BUILDINGS_MAP = testMap; // Necessary since this event depends on the map

        // Add a building for testing
        assertTrue(addBasicTestBuilding(testMap, BuildingType.RECREATIONAL, validCoords[0][0], validCoords[0][1], true, true));

        Event relevantEvent = new RosesWinEvent();

        float originalBalance = GameGlobals.MONEY.getBalance();
        float originalSatisfaction = GameGlobals.SATISFACTION.getSatisfaction();

        // Testing that earnings return to normal
        GameGlobals.MONEY.earn(testMap.getPlacedBuildings(), EarnSchedule.DAILY);

        float preEventEarnings = GameGlobals.MONEY.getBalance() - originalBalance;
        
        relevantEvent.leftRun.run();

        GameGlobals.MONEY.earn(testMap.getPlacedBuildings(), EarnSchedule.DAILY);
        float postEventEarnings = GameGlobals.MONEY.getBalance() - originalBalance - preEventEarnings;

        assertTrue(postEventEarnings < preEventEarnings, "The recreational buildings should return to their normal (lower-earning) state");

        assertEquals(originalSatisfaction+20, GameGlobals.SATISFACTION.getSatisfaction(), "This event should increase satisfaction by 20%");
    }

    @Test
    public void testRosesLoseEvent() {
        BuildingsMap testMap = getTestMap();
        GameGlobals.BUILDINGS_MAP = testMap; // Necessary since this event depends on the map

        // Add a building for testing
        assertTrue(addBasicTestBuilding(testMap, BuildingType.RECREATIONAL, validCoords[0][0], validCoords[0][1], true, true));

        Event relevantEvent = new RosesLoseEvent();

        float originalBalance = GameGlobals.MONEY.getBalance();
        float originalSatisfaction = GameGlobals.SATISFACTION.getSatisfaction();

        // Testing that earnings return to normal
        GameGlobals.MONEY.earn(testMap.getPlacedBuildings(), EarnSchedule.DAILY);

        float preEventEarnings = GameGlobals.MONEY.getBalance() - originalBalance;
        
        relevantEvent.leftRun.run();

        GameGlobals.MONEY.earn(testMap.getPlacedBuildings(), EarnSchedule.DAILY);
        float postEventEarnings = GameGlobals.MONEY.getBalance() - originalBalance - preEventEarnings;

        assertTrue(postEventEarnings < preEventEarnings, "The recreational buildings should return to their normal (lower-earning) state");

        assertEquals(originalSatisfaction-10, GameGlobals.SATISFACTION.getSatisfaction(), "This event should decrease satisfaction by 10%");
    }

    
    // ─── Sponsorship Event (Free Building) ───────────────────────────────

    @Test
    public void testSponsorEvent() {
        BuildingsMap testMap = getTestMap();

        Event relevantEvent = new SponsorEvent();

        float originalBalance = GameGlobals.MONEY.getBalance();
        float originalSatisfaction = GameGlobals.SATISFACTION.getSatisfaction();
                                    

        // There is no choice by the user so action is in leftRun
        relevantEvent.leftRun.run();

        assertTrue(addBasicTestBuilding(testMap, BuildingType.ACCOMODATION, validCoords[0][0], validCoords[0][1], true, false));

        assertEquals(originalBalance, GameGlobals.MONEY.getBalance(), "This event should have no effect on balance, even after placing a building");
        assertEquals(originalSatisfaction, GameGlobals.SATISFACTION.getSatisfaction(), "This event should have no effect on satisfaction");

        assertEquals(1, GameGlobals.EVENT.getPositiveEvent(), "Check the event is registered as positive event");
    }


    // ─── Strikes Event ───────────────────────────────────────────────────

    @Test
    public void testStrikesEventIgnore() {
        Event relevantEvent = new StrikesEvent();

        float originalBalance = GameGlobals.MONEY.getBalance();
        float originalSatisfaction = GameGlobals.SATISFACTION.getSatisfaction();
        
        relevantEvent.leftRun.run(); // The left option is where a player choses not to pay off the strikes

        // Confirm that satisfaction is handled as expected
        assertEquals(originalBalance, GameGlobals.MONEY.getBalance(), "This event should have no effect on balance.");
        assertEquals(originalSatisfaction-5, GameGlobals.SATISFACTION.getSatisfaction(), "This event should apply a 5% satisfaction penalty");

        // Confirm that a recurring event has been scheduled
        assertNotNull(GameGlobals.EVENT.getEventQueue().get(GameGlobals.TIME_REMAINING-10), "Confirm that recurring strike event has been scheduled");

        // Confirm that building is not allowed
        assertFalse(GameGlobals.buildingAllowed);

        assertEquals(1, GameGlobals.EVENT.getNegativeEvent(), "Check the event is registered as negative event");
    }

    @Test
    public void testStrikesEventPayUp() {
        BuildingsMap testMap = getTestMap();
        GameGlobals.BUILDINGS_MAP = testMap; // Necessary since this event depends on the map

        // Add a building for testing
        assertTrue(addBasicTestBuilding(testMap, BuildingType.ACADEMIC, validCoords[0][0], validCoords[0][1], true, true));
        assertTrue(addBasicTestBuilding(testMap, BuildingType.RECREATIONAL, validCoords[1][0], validCoords[1][1], true, true));

        Event relevantEvent = new StrikesEvent();

        float originalBalance = GameGlobals.MONEY.getBalance();
        float originalSatisfaction = GameGlobals.SATISFACTION.getSatisfaction();

        GameGlobals.MONEY.earn(testMap.getPlacedBuildings(), EarnSchedule.SEMESTERLY);
        float preEventEarningsSemesterly = GameGlobals.MONEY.getBalance() - originalBalance;

        GameGlobals.MONEY.earn(testMap.getPlacedBuildings(), EarnSchedule.DAILY);
        float preEventEarningsDaily = GameGlobals.MONEY.getBalance() - originalBalance - preEventEarningsSemesterly;
        
        relevantEvent.rightRun.run(); // The right option is where a player choses to pay off the strikes

        GameGlobals.MONEY.earn(testMap.getPlacedBuildings(), EarnSchedule.SEMESTERLY);
        float postEventEarningsSemesterly = GameGlobals.MONEY.getBalance() - originalBalance - preEventEarningsSemesterly - preEventEarningsDaily;

        GameGlobals.MONEY.earn(testMap.getPlacedBuildings(), EarnSchedule.DAILY);
        float postEventEarningsDaily = GameGlobals.MONEY.getBalance() - originalBalance - preEventEarningsSemesterly - preEventEarningsDaily - postEventEarningsSemesterly;

        assertTrue(postEventEarningsDaily < preEventEarningsDaily, "The recreation building should yield less money every day");
        assertTrue(postEventEarningsSemesterly < preEventEarningsSemesterly, "The academic buildings should now cost more every semester");

        // Confirm that satisfaction is handled as expected
        assertEquals(originalSatisfaction-5, GameGlobals.SATISFACTION.getSatisfaction(), "This event should apply a 5% satisfaction penalty");

        // Confirm that building is now allowed
        assertTrue(GameGlobals.buildingAllowed);
    }

    @Test
    public void testStrikesResolvedEvent() {
        Event relevantEvent = new StrikesResolvedEvent();

        float originalBalance = GameGlobals.MONEY.getBalance();
        float originalSatisfaction = GameGlobals.SATISFACTION.getSatisfaction();

        // Literally nothing should happen when the award event occurs
        relevantEvent.leftRun.run();
        relevantEvent.rightRun.run();

        assertEquals(originalBalance, GameGlobals.MONEY.getBalance(), "This event should have no effect on balance");
        assertEquals(originalSatisfaction, GameGlobals.SATISFACTION.getSatisfaction(), "This event should have no effect on satisfaction");

        // Confirm that building is now allowed
        assertTrue(GameGlobals.buildingAllowed);
    }
}
