package com.vikingz.unitycoon.headless;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.vikingz.unitycoon.building.BuildingsMap;
import com.vikingz.unitycoon.util.AchievementsHandler;
import com.vikingz.unitycoon.util.MoneyHandler;
import com.vikingz.unitycoon.achievements.Achievement;

import com.vikingz.unitycoon.achievements.BareMinimumAchievement;
import com.vikingz.unitycoon.achievements.BusyCampusAchievement;
import com.vikingz.unitycoon.achievements.CleanSlateAchievement;
import com.vikingz.unitycoon.achievements.IndecisiveAchievement;
import com.vikingz.unitycoon.achievements.IsThisAUniversityAchievement;
import com.vikingz.unitycoon.achievements.LuckyAchievement;
import com.vikingz.unitycoon.achievements.MasterOfChangeAchievement;
import com.vikingz.unitycoon.achievements.MikeFreemanAwardAchievement;
import com.vikingz.unitycoon.achievements.PrioritiesAchievement;
import com.vikingz.unitycoon.achievements.RealisticAchievement;
import com.vikingz.unitycoon.achievements.SaviourAchievement;
import com.vikingz.unitycoon.achievements.UnluckyAchievement;
import com.vikingz.unitycoon.building.BuildingStats.BuildingType;
import com.vikingz.unitycoon.global.GameGlobals;

public class AchievementsTest extends TestSuper {

    /**
     * Retrieve an instance of a specific achievement by referencing its name
     * @param handler An instance of AchievementsHandler where achievements are stored
     * @param achievementName Name of the achievement to find
     * @return an instance of Achievement
     */
    private Achievement getRelevantAchievement(AchievementsHandler handler, String achievementName) {
        Achievement relevantAchievement = new BareMinimumAchievement(); // The fact that this is BareMinimum is irrelevant
        for (Achievement achievement : handler.getAchievements()) {
            if (achievement.getName() == achievementName) {
                relevantAchievement = achievement;
            }
        }

        return relevantAchievement;
    }
    
    @Test
    public void testStudyPrioritiesAchievement() {
        BuildingsMap testMap = getTestMap();
        AchievementsHandler achievementsHandler = new AchievementsHandler();

        Achievement relevantAchievement = getRelevantAchievement(achievementsHandler, PrioritiesAchievement.NAME);

        assertFalse(relevantAchievement.isCompleted(), "Confirm that the achievement is initially not completed");

        // Add 6 academic buildings
        for (int i = 0; i < 6; i++) {
            assertTrue(addBasicTestBuilding(testMap, BuildingType.ACADEMIC, validCoords[i][0], validCoords[i][1], true, true));
        }

        // Add 3 recreation buildings
        for (int i = 6; i < 9; i++) {
            assertTrue(addBasicTestBuilding(testMap, BuildingType.RECREATIONAL, validCoords[i][0], validCoords[i][1], true, true));
        }

        // Add a 11 other buildings to make up to 20
        for (int i = 9; i < 20; i++) {
            assertTrue(addBasicTestBuilding(testMap, BuildingType.FOOD, validCoords[i][0], validCoords[i][1], true, true));
        }

        assertTrue(relevantAchievement.isCompleted(), "Confirm that the achievement is subsequently achieved");

        assertFalse(testMap.attemptBuildingDeleteAt(validCoords[0][0]+10, validCoords[0][1]+10).isEmpty());
        assertFalse(relevantAchievement.isCompleted(), "Confirm the achievement is no longer achieved if a building is deleted.");
    }

    @Test
    public void testIsThisAUniversityAchievement() {
        BuildingsMap testMap = getTestMap();
        AchievementsHandler achievementsHandler = new AchievementsHandler();

        // Locate the 'Is this a university?' Achievement in the list of achievements
        Achievement relevantAchievement = getRelevantAchievement(achievementsHandler, IsThisAUniversityAchievement.NAME);

        assertFalse(relevantAchievement.isCompleted(), "Confirm that the achievement is initially not completed");

        // Add 6 recreation buildings
        for (int i = 0; i < 6; i++) {
            assertTrue(addBasicTestBuilding(testMap, BuildingType.RECREATIONAL, validCoords[i][0], validCoords[i][1], true, true));
        }

        // Add 3 academic buildings
        for (int i = 6; i < 9; i++) {
            assertTrue(addBasicTestBuilding(testMap, BuildingType.ACADEMIC, validCoords[i][0], validCoords[i][1], true, true));
        }

        // Add a 10 other buildings to make up to 19
        for (int i = 9; i < 19; i++) {
            assertTrue(addBasicTestBuilding(testMap, BuildingType.FOOD, validCoords[i][0], validCoords[i][1], true, true));
        }

        assertFalse(relevantAchievement.isCompleted(), "Confirm the achievement isn't completed until the 20th building placed");
        
        assertTrue(addBasicTestBuilding(testMap, BuildingType.ACCOMODATION, validCoords[19][0], validCoords[19][1], true, true));

        assertTrue(relevantAchievement.isCompleted(), "Confirm that the achievement is subsequently achieved");

        assertFalse(testMap.attemptBuildingDeleteAt(validCoords[2][0]+10, validCoords[2][1]+10).isEmpty());
        assertFalse(relevantAchievement.isCompleted(), "Confirm the achievement is no longer achieved if a building is deleted.");
    }

    @Test
    public void testCleanSlateAchievement() {
        BuildingsMap testMap = getTestMap();
        AchievementsHandler achievementsHandler = new AchievementsHandler();

        // Locate the 'Clean Slate' Achievement in the list of achievements
        Achievement relevantAchievement = getRelevantAchievement(achievementsHandler, CleanSlateAchievement.NAME);

        assertFalse(relevantAchievement.isCompleted(), "Confirm that the achievement is initially not completed");

        BuildingType[] typesOrder = {
            BuildingType.ACADEMIC, BuildingType.FOOD, BuildingType.ACCOMODATION, BuildingType.ACCOMODATION, BuildingType.RECREATIONAL, BuildingType.ACCOMODATION, BuildingType.ACCOMODATION, BuildingType.ACCOMODATION, BuildingType.ACADEMIC, BuildingType.ACADEMIC, 
            BuildingType.ACADEMIC, BuildingType.FOOD, BuildingType.FOOD, BuildingType.RECREATIONAL, BuildingType.RECREATIONAL, BuildingType.RECREATIONAL, BuildingType.RECREATIONAL, BuildingType.ACADEMIC, BuildingType.ACADEMIC, BuildingType.ACADEMIC
        };

        // Add 20 buildings
        for (int i = 0; i < 20; i++) {
            assertTrue(addBasicTestBuilding(testMap, typesOrder[i], validCoords[i][0], validCoords[i][1], true, true));
        }

        assertFalse(relevantAchievement.isCompleted(), "Confirm that the achievement is still not achieved");

        // Remove 20 buildings
        for (int i = 0; i < 20; i++) {
            assertFalse(testMap.attemptBuildingDeleteAt(validCoords[i][0]+10, validCoords[i][1]+10).isEmpty());
        }

        assertTrue(relevantAchievement.isCompleted(), "Confirm that the achievement is subsequently achieved");
        
    }

    @Test
    public void testBareMinimumAchievement() {
        BuildingsMap testMap = getTestMap();

        AchievementsHandler achievementsHandler = new AchievementsHandler();
        Achievement relevantAchievement = getRelevantAchievement(achievementsHandler, BareMinimumAchievement.NAME);

        GameGlobals.TIME_REMAINING = 0; // This achievement is only applicable at the end of the game

        assertFalse(relevantAchievement.isCompleted(), "Confirm that achievement isn't completed initially");

        assertTrue(addBasicTestBuilding(testMap, BuildingType.ACCOMODATION, validCoords[0][0], validCoords[0][1], true, true));
        assertTrue(addBasicTestBuilding(testMap, BuildingType.FOOD, validCoords[1][0], validCoords[1][1], true, true));
        assertTrue(addBasicTestBuilding(testMap, BuildingType.ACADEMIC, validCoords[2][0], validCoords[2][1], true, true));
        assertTrue(addBasicTestBuilding(testMap, BuildingType.RECREATIONAL, validCoords[3][0], validCoords[3][1], true, true));

        assertTrue(relevantAchievement.isCompleted(), "Confirm that achievement matches successfully");
    }

    /**
     * Tests that achievement can't be fooled by deleting buildings
     */
    @Test
    public void testBareMinimumAchievementErroneous() {
        BuildingsMap testMap = getTestMap();

        AchievementsHandler achievementsHandler = new AchievementsHandler();
        Achievement relevantAchievement = getRelevantAchievement(achievementsHandler, BareMinimumAchievement.NAME);

        assertFalse(relevantAchievement.isCompleted(), "Confirm that achievement isn't completed initially");

        assertTrue(addBasicTestBuilding(testMap, BuildingType.ACCOMODATION, validCoords[0][0], validCoords[0][1], true, true));
        assertTrue(addBasicTestBuilding(testMap, BuildingType.FOOD, validCoords[1][0], validCoords[1][1], true, true));
        assertTrue(addBasicTestBuilding(testMap, BuildingType.ACADEMIC, validCoords[2][0], validCoords[2][1], true, true));
        assertTrue(addBasicTestBuilding(testMap, BuildingType.RECREATIONAL, validCoords[3][0], validCoords[3][1], true, true));

        assertTrue(addBasicTestBuilding(testMap, BuildingType.RECREATIONAL, validCoords[4][0], validCoords[4][1], true, true));
        achievementsHandler.checkAllAchievements(); // This is called in the render() method normally so will have run between placing and deleting.
        assertFalse(testMap.attemptBuildingDeleteAt(validCoords[4][0]+10, validCoords[4][1]+10).isEmpty());

        GameGlobals.TIME_REMAINING = 0; // This achievement is only applicable at the end of the game
        assertFalse(relevantAchievement.isCompleted(), "Confirm that achievement isn't completed, even if a building is deleted");
    }

    @Test
    public void testBusyCampusAchievement() {
        BuildingsMap testMap = getTestMap();

        AchievementsHandler achievementsHandler = new AchievementsHandler();
        Achievement relevantAchievement = getRelevantAchievement(achievementsHandler, BusyCampusAchievement.NAME);

        assertFalse(relevantAchievement.isCompleted(), "Confirm that achievement isn't completed initially");

        for (int i = 0; i < 40; i++) {
            assertTrue(addBasicTestBuilding(testMap, BuildingType.FOOD, validCoords[i][0], validCoords[i][1], true, true));
        }

        assertFalse(relevantAchievement.isCompleted(), "BOUNDARY: Should still not be achieved with 40 buildings");

        assertTrue(addBasicTestBuilding(testMap, BuildingType.FOOD, validCoords[40][0], validCoords[40][1], true, true));
        assertTrue(relevantAchievement.isCompleted(), "Confirm that achievement is achieved after placing 41 buildings");
    }

    @Test
    public void testIndecisiveAchievement() {
        BuildingsMap testMap = getTestMap();

        Achievement relevantAchievement = getRelevantAchievement(GameGlobals.ACHIEVEMENTS, IndecisiveAchievement.NAME);

        assertFalse(relevantAchievement.isCompleted(), "Confirm that achievement isn't completed initially");

        // Add 10 buildings to the campus
        for (int i = 0; i < 10; i++) {
            assertTrue(addBasicTestBuilding(testMap, BuildingType.FOOD, validCoords[i][0], validCoords[i][1], true, true));
        }

        // Remove all number of previously added buildings
        for (int i = 0; i < 5; i++) {
            assertFalse(testMap.attemptBuildingDeleteAt(validCoords[i][0]+10, validCoords[i][1]+10).isEmpty());
        }

        // Add another 15 buildings to the campus
        for (int i = 10; i < 25; i++) {
            assertTrue(addBasicTestBuilding(testMap, BuildingType.FOOD, validCoords[i][0], validCoords[i][1], true, true));
        }

        // Remove another 16 buildings
        for (int i = 5; i < 21; i++) {
            assertFalse(testMap.attemptBuildingDeleteAt(validCoords[i][0]+10, validCoords[i][1]+10).isEmpty());
        }

        assertTrue(relevantAchievement.isCompleted(), "Confirm that achievement is completed after removing 21 buildings");
    }

    @Test
    public void testLuckyAchievement() {
        AchievementsHandler achievementsHandler = new AchievementsHandler();
        Achievement relevantAchievement = getRelevantAchievement(achievementsHandler, LuckyAchievement.NAME);

        assertFalse(relevantAchievement.isCompleted(), "Confirm that achievement isn't completed initially");

        GameGlobals.EVENT.incrementPositiveEvent();
        GameGlobals.EVENT.incrementPositiveEvent();

        assertFalse(relevantAchievement.isCompleted(), "BOUNDARY: Confirm that achievement isn't completed after 2 events");

        GameGlobals.EVENT.incrementPositiveEvent();

        assertTrue(relevantAchievement.isCompleted(), "Confirm that Master Of Change is awarded after 3 positive events");
    }

    @Test
    public void testMasterOfChangeAchievement() {
        AchievementsHandler achievementsHandler = new AchievementsHandler();
        Achievement relevantAchievement = getRelevantAchievement(achievementsHandler, MasterOfChangeAchievement.NAME);

        assertFalse(relevantAchievement.isCompleted(), "Confirm that achievement isn't completed initially");

        // Simulate not doing anything for 3 mins (satisfaction will be 10%)
        GameGlobals.TIME_REMAINING = 120;

        assertFalse(relevantAchievement.isCompleted(), "Confirm that achievement is still not met");

        // Game has ended and player has won
        GameGlobals.TIME_REMAINING = 0;
        GameGlobals.gameWon = true;

        assertTrue(relevantAchievement.isCompleted(), "Confirm that Master Of Change is awarded correctly");
    }

    @Test
    public void testMikeFreemanAwardAchievement() {
        AchievementsHandler achievementsHandler = new AchievementsHandler();
        Achievement relevantAchievement = getRelevantAchievement(achievementsHandler, MikeFreemanAwardAchievement.NAME);

        assertFalse(relevantAchievement.isCompleted(), "Confirm that achievement isn't completed initially");

        // The first minute is just wasted
        GameGlobals.TIME_REMAINING = 240;

        // Satisfaction is raised to above 80%
        GameGlobals.SATISFACTION.addBonus(70);
        achievementsHandler.checkAllAchievements(); // This is normally called in render(). Needed to start the timer

        // 3 Minutes passes
        GameGlobals.TIME_REMAINING -= 181;
        assertTrue(relevantAchievement.isCompleted(), "Confirm that Mike Freeman Award is awarded correctly");
    }

    @Test
    public void testMikeFreemanAwardAchievementErroneous() {
        AchievementsHandler achievementsHandler = new AchievementsHandler();
        Achievement relevantAchievement = getRelevantAchievement(achievementsHandler, MikeFreemanAwardAchievement.NAME);

        assertFalse(relevantAchievement.isCompleted(), "Confirm that achievement isn't completed initially");
        
        // The first minute is just wasted
        GameGlobals.TIME_REMAINING = 240;

        // Satisfaction is raised to above 80%
        GameGlobals.SATISFACTION.addBonus(60);
        achievementsHandler.checkAllAchievements(); // This is normally called in render(). Needed to start the timer

        // 59 seconds passes
        GameGlobals.TIME_REMAINING -= 59;

        // At some point, the satisfaction dips briefly
        GameGlobals.SATISFACTION.applyPenalty(3);
        achievementsHandler.checkAllAchievements(); // This is normally called in render(). Needed to notice the dip
        GameGlobals.SATISFACTION.addBonus(3);

        // Remaining time to go above 3 mins passes
        GameGlobals.TIME_REMAINING -= 122;

        assertFalse(relevantAchievement.isCompleted(), "Confirm that Mike Freeman Award is not awarded if satisfaction dropped at any point");
    }

    @Test
    public void testRealisticAchievement() {
        AchievementsHandler achievementsHandler = new AchievementsHandler();
        Achievement relevantAchievement = getRelevantAchievement(achievementsHandler, RealisticAchievement.NAME);

        assertFalse(relevantAchievement.isCompleted(), "Confirm that achievement isn't completed initially");

        GameGlobals.MONEY.withdraw(24001 + MoneyHandler.STARTING_BALANCE);

        assertTrue(relevantAchievement.isCompleted(), "Confirm that achievement is completed if the balance falls below 24,000 at any point");
    }

    @Test
    public void testSaviourAchievements() {
        AchievementsHandler achievementsHandler = new AchievementsHandler();
        Achievement relevantAchievement = getRelevantAchievement(achievementsHandler, SaviourAchievement.NAME);

        assertFalse(relevantAchievement.isCompleted(), "Confirm that achievement isn't completed initially");

        ((SaviourAchievement) relevantAchievement).burningBuildingSaved();

        assertTrue(relevantAchievement.isCompleted(), "Confirm that saviour achievement is awarded");
    }

    @Test
    public void testUnluckyAchievement() {
        AchievementsHandler achievementsHandler = new AchievementsHandler();
        Achievement relevantAchievement = getRelevantAchievement(achievementsHandler, UnluckyAchievement.NAME);

        assertFalse(relevantAchievement.isCompleted(), "Confirm that achievement isn't completed initially");

        GameGlobals.EVENT.incrementNegativeEvent();
        GameGlobals.EVENT.incrementNegativeEvent();

        assertFalse(relevantAchievement.isCompleted(), "BOUNDARY: Confirm that achievement isn't completed after 2 events");

        GameGlobals.EVENT.incrementNegativeEvent();

        assertTrue(relevantAchievement.isCompleted(), "Confirm that Master Of Change is awarded after 3 negative events");
    }


    // ─── Testing Of The AchievementsHandler Utility Functions ────────────

    @Test
    public void testGetAchievement() {
        AchievementsHandler achievementsHandler = new AchievementsHandler();

        assertDoesNotThrow(() -> {
            Achievement testAchievement = achievementsHandler.getAchievement(UnluckyAchievement.NAME);
            @SuppressWarnings("unused")
            UnluckyAchievement attemptCast = ((UnluckyAchievement) testAchievement);
        }, "Confirm that getAchievement returns an object of the correct type.");
        
    }

    @Test
    public void testAchievementsLoadSave() {
        AchievementsHandler achievementsHandler = new AchievementsHandler();

        // Set username to random string so that subsequent tests are independent
        achievementsHandler.setUsername(java.util.UUID.randomUUID().toString());

        Achievement testAchievement = achievementsHandler.getAchievement(UnluckyAchievement.NAME);
        testAchievement.usernameAchieved = true;

        assertDoesNotThrow(() -> achievementsHandler.saveAchievements(), "Save achievements should not throw an error");

        testAchievement.usernameAchieved = false;

        assertDoesNotThrow(() -> achievementsHandler.loadAchievements());

        assertTrue(testAchievement.usernameAchieved);
    }

    @Test
    public void testMultiUserAchievementsLoadSave() {
        AchievementsHandler achievementsHandler = new AchievementsHandler();

        String user1Username = java.util.UUID.randomUUID().toString();
        String user2Username = java.util.UUID.randomUUID().toString();

        // User 1 plays the game
        achievementsHandler.setUsername(user1Username);
        assertDoesNotThrow(() -> achievementsHandler.loadAchievements());

        Achievement testAchievement1 = achievementsHandler.getAchievement(UnluckyAchievement.NAME);
        testAchievement1.usernameAchieved = true;

        assertDoesNotThrow(() -> achievementsHandler.saveAchievements(), "Save achievements should not throw an error");

        // User 2 plays the game
        achievementsHandler.setUsername(user2Username);
        assertDoesNotThrow(() -> achievementsHandler.loadAchievements());

        Achievement testAchievement2 = achievementsHandler.getAchievement(MasterOfChangeAchievement.NAME);
        testAchievement2.usernameAchieved = true;

        assertDoesNotThrow(() -> achievementsHandler.saveAchievements(), "Save achievements should not throw an error");

        // Check that User1's achievements were saved
        achievementsHandler.setUsername(user1Username);
        assertDoesNotThrow(() -> achievementsHandler.loadAchievements());

        assertTrue(testAchievement1.usernameAchieved, "User 1 should have completed 'Unlucky'");
        assertFalse(testAchievement2.usernameAchieved, "User 1 should not have completed 'Master Of Change'");

        // Check that User2's achievements were saved
        achievementsHandler.setUsername(user2Username);
        assertDoesNotThrow(() -> achievementsHandler.loadAchievements());

        assertFalse(testAchievement1.usernameAchieved, "User 2 should not have completed 'Unlucky'");
        assertTrue(testAchievement2.usernameAchieved, "User 2 should have completed 'Master Of Change'");
        
    }

    @Test
    public void testAchievementsExistingUserLoadSave() {
        AchievementsHandler achievementsHandler = new AchievementsHandler();

        String username1 = java.util.UUID.randomUUID().toString();

        achievementsHandler.setUsername(username1);
        achievementsHandler.loadAchievements();

        // Achieve the 'Unlucky' Achievement
        Achievement testAchievement = achievementsHandler.getAchievement(UnluckyAchievement.NAME);
        testAchievement.usernameAchieved = true;

        assertDoesNotThrow(() -> achievementsHandler.saveAchievements(), "Save achievements should not throw an error");

        // Load achievements for another random user
        achievementsHandler.setUsername(java.util.UUID.randomUUID().toString());
        achievementsHandler.loadAchievements();

        // Reload achievements for the original user
        achievementsHandler.setUsername(username1);
        achievementsHandler.loadAchievements();

        Achievement testAchievement2 = achievementsHandler.getAchievement(SaviourAchievement.NAME);
        testAchievement2.usernameAchieved = true;

        // Save additional achievement
        assertDoesNotThrow(() -> achievementsHandler.saveAchievements(), "Save achievements should not throw an error");

        // Load achievements for another random user
        achievementsHandler.setUsername(java.util.UUID.randomUUID().toString());
        achievementsHandler.loadAchievements();

        assertFalse(testAchievement.usernameAchieved, "The brand new user should not have completed any achievements");
        assertFalse(testAchievement2.usernameAchieved, "The brand new user should not have completed any achievements");

        // Reload achievements for the original user
        achievementsHandler.setUsername(username1);
        achievementsHandler.loadAchievements();

        assertTrue(testAchievement.usernameAchieved, "Our test user should have completed 'Unlucky'");
        assertTrue(testAchievement2.usernameAchieved, "Our test user should have completed 'Saviour'");
    }


    // ─── Presentation Of Achievements To Users ───────────────────────────

    @Test
    public void testNewAchievementUnlocked() {
        AchievementsHandler achievementsHandler = new AchievementsHandler();

        achievementsHandler.setUsername("Guest");
        achievementsHandler.loadAchievements();

        Achievement testAchievement = achievementsHandler.getAchievement(SaviourAchievement.NAME);
        ((SaviourAchievement) testAchievement).burningBuildingSaved();

        achievementsHandler.checkAllAchievements();

        assertEquals(1, achievementsHandler.achievementsToDisplay.size());
        assertTrue(achievementsHandler.achievementsToDisplay.peek().contains("NEW ACHIEVEMENT"), "'Saviour' should be a new achievement");
        assertFalse(achievementsHandler.achievementsToDisplay.remove().contains("NEW HIDDEN ACHIEVEMENT"), "'Savior' is not a hidden achievement");

        assertTrue(testAchievement.achieved, "achieved should be updated within the achievement itself");
        assertTrue(testAchievement.usernameAchieved, "usernameAchieved should be updated");
    }

    @Test
    public void testAllAchievementsOutput() {
        AchievementsHandler achievementsHandler = new AchievementsHandler();
        
        achievementsHandler.setUsername("Guest");
        achievementsHandler.loadAchievements();

        String initialOutput = achievementsHandler.allAchievementsCompleted();

        // Test that with no achievement achieved, non appear in the output
        for (Achievement achievement : achievementsHandler.getAchievements()) {
            assertFalse(initialOutput.contains(achievement.getName()), "The achievement " + achievement.getName() + " should not be listed");
        }
        
        Achievement testAchievement = achievementsHandler.getAchievement(SaviourAchievement.NAME);
        testAchievement.achieved = true;

        Achievement testAchievement2 = achievementsHandler.getAchievement(MasterOfChangeAchievement.NAME);
        testAchievement2.achieved = true;

        String laterOutput = achievementsHandler.allAchievementsCompleted();

        assertTrue(laterOutput.contains(MasterOfChangeAchievement.NAME), "Should list Master Of Change as being achieved");

        assertTrue(laterOutput.contains(SaviourAchievement.NAME), "Saviour should have been listed as being achieved");
        assertTrue(laterOutput.contains(Integer.toString(testAchievement.getSatisfactionBonus())), "'Saviour's' Bonus should be shown");
    }
}
