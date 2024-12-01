package com.vikingz.unitycoon.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import com.vikingz.unitycoon.global.GameGlobals;
import com.vikingz.unitycoon.menus.AchievementsMenu;
import com.vikingz.unitycoon.menus.UsernameMenu;

/**
 * This class contains checks for each achievement.
 */
public class Achievements {

    private static int removedBuildings;

    // Achievements completed in this game
    private static boolean[] completedAchievements;
    // Achievements this user has completed
    private static Boolean[] usernameAchievements = {false, false, false, false, false, 
        false, false, false, false, false, false, false};
        
    private boolean masterOfChangePossible;
    private boolean bareMinimumPossible;
    private int mikeFreemanAwardStartTime;
    private int unluckyCounter;
    private int luckyCounter;
    private boolean cleanSlateMinReached;
    private int bonus;
    
    public static Queue<String> achievementsToDisplay;

    private static ArrayList<String> allUserAchievements;
    private final static File achievementsFile = new File("achievements.txt");

    private static String username;
    
    public Achievements() {
        completedAchievements = new boolean[AchievementsMenu.NUM_OF_ACHIEVEMENTS];
        achievementsToDisplay = new LinkedList<>();
        masterOfChangePossible = true;
        bareMinimumPossible = true;
        mikeFreemanAwardStartTime = -1;
        unluckyCounter = 0;
        luckyCounter = 0;
        removedBuildings = 0;
        cleanSlateMinReached = false;
        bonus = 0;
    }
    
    /**
     * Increases the removed buildings counter by one.
     */
    public static void incrementRemovedBuildings() {
        removedBuildings++;
    }
    
    public int getBonus() {
        return bonus;
    }

    public static Boolean[] getUsernameAchievements() {
        return usernameAchievements;
    }

    /**
     * Saves achievement to text file. 
     * Format: username followed by true/false for each achievement seperated by
     * spaces. Each new username is put on a new line.
     */
    public static void saveAchievements(){
        // Don't save if user is a guest.
        if(username != "Guest") {
            String valueToSave = "";
            boolean updated = false;

            for (String userAchievement : allUserAchievements) {
                // Only update the data for the current username
                if (username.equals(userAchievement.substring(0, username.length()))) {
                    updated = true;
                    valueToSave += username + " ";
                    for (int i = 0; i < AchievementsMenu.NUM_OF_ACHIEVEMENTS; i++) {
                        valueToSave += Boolean.toString(usernameAchievements[i]);
                        if (i < 11) {valueToSave += " ";} else {valueToSave += "\n";}
                    }
                }
                else {
                    valueToSave += userAchievement + "\n";
                }
            }  

            // If username wasn't updated, add username to the file
            if (!updated) {
                valueToSave += username + " ";
                for (int i = 0; i < AchievementsMenu.NUM_OF_ACHIEVEMENTS; i++) {
                    valueToSave += Boolean.toString(usernameAchievements[i]);
                    if (i < 11) {valueToSave += " ";} else {valueToSave += "\n";}
                }
            }
            //Removes the additional \n at the end
            valueToSave = valueToSave.substring(0, valueToSave.length()-1);

            try {
                FileWriter fileWriter = new FileWriter(achievementsFile);
                fileWriter.write(valueToSave);
                fileWriter.close();
                
                System.out.println("Successfully wrote to the achievements file.");

            } catch (IOException i) {
                System.out.println("FILE NOT FOUND");
            }
        }
    }

    /**
     * Loads leaderboard from text file.
     */
    public static void loadAchievements(){
        username = UsernameMenu.getUsername();
        allUserAchievements = new ArrayList<String>();

        if (username != "Guest") {
            try {
                achievementsFile.createNewFile();
                Scanner fileReader = new Scanner(achievementsFile);
                while (fileReader.hasNextLine()) {
                    String nextUser = fileReader.nextLine();
                    allUserAchievements.add(nextUser);
                    // If the next line is the current user. Save the achievement info.
                    if (username.equals(nextUser.substring(0, username.length()))) {
                        String[] achievements = nextUser.split(" ");
                        for (int i = 1; i < achievements.length; i++) {
                            usernameAchievements[i-1] = Boolean.valueOf(achievements[i]);
                        }
                    }
                }
                fileReader.close();

                System.out.println("\n\nLoaded Achievements");

            } catch (IOException i) {
                System.out.println("FILE NOT FOUND");
            }
        }
    }

    /**
     * Makes a list of achievements that have been completed during the game
     * @return String containing the list to be output
     */
    public static String allAchievementsCompleted() {
        String output = "Achievements:";
        for (int i = 0; i < AchievementsMenu.NUM_OF_ACHIEVEMENTS; i++) {
            if (completedAchievements[i]) {
                output += "\n  -  " + AchievementsMenu.ACHIEVEMENT_TITLES[i];
                if(i == 8 || i == 10) {
                    if (i == 10) {output += "\t \t    ";}
                    output += "\t(+5% satisfaction)";
                }
            }
        }   

        if (output == "Achievements:") {
            output += "\n  -  None";
        }

        return output;
    }
    
    /**
     * Updates game and username record for completed achievements and adds completed achievements to the
     * queu so they can be displayed. 
     * @param i The achievement number as index.
     */
    private void updateAchievements(int i) {
        completedAchievements[i] = true;
        if (usernameAchievements[i] == false) {
            usernameAchievements[i] = true;
            if (i == 7 || i == 8) {
                achievementsToDisplay.add("NEW HIDDEN ACHIEVEMENT: " + AchievementsMenu.ACHIEVEMENT_TITLES[i] 
                    + "\n"+ AchievementsMenu.ACHIEVEMENT_DESCRIPTIONS[i]);
            }
            else {
                achievementsToDisplay.add("NEW ACHIEVEMENT: " + AchievementsMenu.ACHIEVEMENT_TITLES[i] 
                    + "\n"+ AchievementsMenu.ACHIEVEMENT_DESCRIPTIONS[i]);
            }
        }
        else {
            achievementsToDisplay.add(AchievementsMenu.ACHIEVEMENT_TITLES[i] 
                   + "\n"+ AchievementsMenu.ACHIEVEMENT_DESCRIPTIONS[i]);
        }
    }

    /**
     * Checks if any achievements have been completed and adds bonus where required.
     */
    public void checkAllAchievements() {
        if(completedBankruptcy() && !completedAchievements[0]) {
            updateAchievements(0);
        }
        if(completedBareMinimum() && !completedAchievements[1]) {
            updateAchievements(1);
        }
        if(completedBusyCampus() && !completedAchievements[2]) {
            updateAchievements(2);
        }
        if(completedCleanSlate() && !completedAchievements[3]) {
            updateAchievements(3);
        }
        if(completedIndecisive() && !completedAchievements[4]) {
            updateAchievements(4);
        }
        if(completedIsThisAUniversity() && !completedAchievements[5]) {
            updateAchievements(5);
        }
        if(completedLucky() && !completedAchievements[6]) {
            updateAchievements(6);
        }
        if(completedMasterOfChange() && !completedAchievements[7]) {
            updateAchievements(7);
        }
        if(completedMikeFreemanAward() && !completedAchievements[8]) {
            updateAchievements(8);
            bonus += 5;
        }
        if(completedPriorities() && !completedAchievements[9]) {
            updateAchievements(9);
        }
        if(completedSaviour() && !completedAchievements[10]) {
            updateAchievements(10);
            bonus += 5;
        }
        if(completedUnlucky() && !completedAchievements[11]) {
            updateAchievements(11);
        }
    }
    
    /**
     * Checks if achievement Busy Campus has been completed.
     * Busy Campus: More than 40 buildings have been placed
     *              on one map.
     * @return true if achievement has been completed
     */
    private Boolean completedBusyCampus() {
        if (GameGlobals.ACADEMIC_BUILDINGS_COUNT + GameGlobals.ACCOMODATION_BUILDINGS_COUNT
                + GameGlobals.FOOD_BUILDINGS_COUNT + GameGlobals.RECREATIONAL_BUILDINGS_COUNT > 40) {
            return true;
        }
        return false;
    }

    /**
     * Checks if achievement Bankruptcy has been completed.
     * Bankruptcy: Balance went below 0.
     * @return true if achievement has been completed
     */
    private Boolean completedBankruptcy() {
        if (GameGlobals.BALANCE < 0) {
            return true;
        }
        return false;
    }

    /**
     * Checks if achievement MasterOfChange has been completed.
     * Master Of Change: Maintained under 50% satisfaction for 
     *                   the first 3 minutes but still won the 
     *                   game.
     * @return true if achievement has been completed
     */
    private Boolean completedMasterOfChange() {
        if (GameGlobals.ELAPSED_TIME <= 0 && masterOfChangePossible) { //&& gameWon) {
            return true;
        }
        
        if (GameGlobals.ELAPSED_TIME >= 120 && GameGlobals.SATISFACTION >= 50) {
            masterOfChangePossible = false;
        }

        return false;
    }

    /**
     * Checks if achievement Bare Minimum has been completed.
     * Bare Minimum: Place exactly 1 of each type of building
     *               during the game.
     * @return true if achievement has been completed
     */
    private Boolean completedBareMinimum() {
        if (GameGlobals.ELAPSED_TIME <= 0 && bareMinimumPossible) {
            if (GameGlobals.ACADEMIC_BUILDINGS_COUNT == 1 && GameGlobals.ACCOMODATION_BUILDINGS_COUNT == 1
                && GameGlobals.FOOD_BUILDINGS_COUNT == 1 && GameGlobals.RECREATIONAL_BUILDINGS_COUNT == 1) {
                return true;
            }
            return false;
        }

        // Checks no extra buildings are ever placed
        if (GameGlobals.ACADEMIC_BUILDINGS_COUNT > 1 || GameGlobals.ACCOMODATION_BUILDINGS_COUNT > 1
                || GameGlobals.FOOD_BUILDINGS_COUNT > 1 || GameGlobals.RECREATIONAL_BUILDINGS_COUNT > 1) {
                bareMinimumPossible = false;
        }

        return false;
    }

    /**
     * Checks if achievement Mike Freeman Award has been completed.
     * Mike Freeman Award: Maintain a 80% or higher satisfaction for
     *                     3 minutes.
     * @return true if achievement has been completed
     */
    private Boolean completedMikeFreemanAward() {
        if (mikeFreemanAwardStartTime - GameGlobals.ELAPSED_TIME > 180) {
            return true;
        }

        if (GameGlobals.SATISFACTION < 80) {
            mikeFreemanAwardStartTime = -1;
        }
        
        // Starts timer when satisfaction goes over 80%
        if (GameGlobals.SATISFACTION >= 80 && mikeFreemanAwardStartTime == -1){
            mikeFreemanAwardStartTime = GameGlobals.ELAPSED_TIME;
        }

        return false;
    }

    /**
     * Checks if achievement Priorities has been completed.
     * Priorities: Have more than 2 times the number of academic
     *             buildings than recreational buildings. At 
     *             least 20 buildings must have been placed.
     * @return true if achievement has been completed
     */
    private Boolean completedPriorities() {
        if (GameGlobals.ACADEMIC_BUILDINGS_COUNT + GameGlobals.ACCOMODATION_BUILDINGS_COUNT
                + GameGlobals.FOOD_BUILDINGS_COUNT + GameGlobals.RECREATIONAL_BUILDINGS_COUNT >= 20) {
            if (GameGlobals.ACADEMIC_BUILDINGS_COUNT > 2 * GameGlobals.RECREATIONAL_BUILDINGS_COUNT) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if achievement Is This A University has been completed.
     * Is This A University: Have more than 2 times the number of 
     *                       recreational buildings than academic 
     *                       buildings. At least 20 buildings must 
     *                       have been placed.
     * @return true if achievement has been completed
     */
    private Boolean completedIsThisAUniversity() {
        if (GameGlobals.ACADEMIC_BUILDINGS_COUNT + GameGlobals.ACCOMODATION_BUILDINGS_COUNT
                + GameGlobals.FOOD_BUILDINGS_COUNT + GameGlobals.RECREATIONAL_BUILDINGS_COUNT >= 20) {
            if (2 * GameGlobals.ACADEMIC_BUILDINGS_COUNT < GameGlobals.RECREATIONAL_BUILDINGS_COUNT) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if achievement Unlucky has been completed.
     * Unlucky: 3 Negative events occurred in one game.
     * @return true if achievement has been completed
     */
    private Boolean completedUnlucky() {
        if (unluckyCounter == 3) {
            return true;
        }

        //if(negativeEventOccurred) {
        //    unluckyCounter++;
        //}
        return false;
    }

    /**
     * Checks if achievement Lucky has been completed.
     * Lucky: 3 Positive events occurred in one game.
     * @return true if achievement has been completed
     */
    private Boolean completedLucky() {
        if (luckyCounter == 3) {
            return true;
        }

        //if(positiveEventOccurred) {
        //    unluckyCounter++;
        //}
        return false;
    }

    /**
     * Checks if achievement Saviour has been completed.
     * Saviour: Saved a burning building.
     * @return true if achievement has been completed
     */
    private Boolean completedSaviour() {
        //if(savedBurningBuilding) {
        //    return true;
        //}
        return true;
    }

    /**
     * Checks if achievement Indecisive has been completed.
     * Indesicive: Remove more than 20 buildings in one game.
     * @return true if achievement has been completed
     */
    private Boolean completedIndecisive() {
        if (removedBuildings > 20) {
            return true;
        }

        return false;
    }

    /**
     * Checks if achievement Clean Slate has been completed.
     * Clean Slate: Remove every building from the game. At
     *              least 10 buildings must have been placed
     *              on the map at the same time.
     * @return true if achievement has been completed
     */
    private Boolean completedCleanSlate() {
        if (GameGlobals.ACADEMIC_BUILDINGS_COUNT + GameGlobals.ACCOMODATION_BUILDINGS_COUNT
                + GameGlobals.FOOD_BUILDINGS_COUNT + GameGlobals.RECREATIONAL_BUILDINGS_COUNT == 0
                && cleanSlateMinReached) {
            return true;
        }
        
        if (GameGlobals.ACADEMIC_BUILDINGS_COUNT + GameGlobals.ACCOMODATION_BUILDINGS_COUNT
                + GameGlobals.FOOD_BUILDINGS_COUNT + GameGlobals.RECREATIONAL_BUILDINGS_COUNT >= 10) {
            cleanSlateMinReached = true;
        }
        return false;
    }
}
