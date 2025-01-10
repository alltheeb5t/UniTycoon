package com.vikingz.unitycoon.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

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
import com.vikingz.unitycoon.menus.UsernameMenu;

/**
 * This class contains manages all achievements during the game.
 */
public class AchievementsHandler {
    
    // All Achievements
    private Achievement[] gameAchievements = {new BareMinimumAchievement(), new BusyCampusAchievement(),
        new CleanSlateAchievement(), new IndecisiveAchievement(), new IsThisAUniversityAchievement(), 
        new LuckyAchievement(), new MasterOfChangeAchievement(), new MikeFreemanAwardAchievement(), 
        new PrioritiesAchievement(), new RealisticAchievement(), new SaviourAchievement(), 
        new UnluckyAchievement()};

    private int bonus;
    
    public Queue<String> achievementsToDisplay;

    private ArrayList<String> allUserAchievements;
    private final File achievementsFile = new File("achievements.txt");

    private String username;
    
    public AchievementsHandler() {
        achievementsToDisplay = new LinkedList<>();
        bonus = 0;
    }

    public int getBonus() {
        return bonus;
    }

    public Achievement[] getAchievements() {
        return gameAchievements;
    }

    /**
     * Saves achievement to text file. 
     * Format: username followed by true/false for each achievement seperated by
     * spaces. Each new username is put on a new line.
     */
    public void saveAchievements(){
        // Don't save if user is a guest.
        if(username != "Guest") {
            String valueToSave = "";
            boolean updated = false;

            for (String userAchievement : allUserAchievements) {
                // Only update the data for the current username
                if (username.equals(userAchievement.substring(0, username.length()))) {
                    updated = true;
                    valueToSave += username + " ";
                    for (int i = 0; i < gameAchievements.length; i++) {
                        valueToSave += Boolean.toString(gameAchievements[i].usernameAchieved);
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
                for (int i = 0; i < gameAchievements.length; i++) {
                    valueToSave += Boolean.toString(gameAchievements[i].usernameAchieved);
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
     * Loads achievements from text file.
     */
    public void loadAchievements(){
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
                            gameAchievements[i-1].usernameAchieved = Boolean.valueOf(achievements[i]);
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
    public String allAchievementsCompleted() {
        String output = "Achievements:";
        for (int i = 0; i < gameAchievements.length; i++) {
            if (gameAchievements[i].achieved) {
                output += "\n  -  " + gameAchievements[i].getName();
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
     * queue so they can be displayed. 
     * @param i The achievement number as index.
     */
    private void updateAchievements(int i) {
        gameAchievements[i].achieved = true;
        if (gameAchievements[i].usernameAchieved == false) {
            gameAchievements[i].usernameAchieved = true;
            if (gameAchievements[i].getHidden()) {
                achievementsToDisplay.add("NEW HIDDEN ACHIEVEMENT: " + gameAchievements[i].getName()
                    + "\n"+ gameAchievements[i].getDescription());
            }
            else {
                achievementsToDisplay.add("NEW ACHIEVEMENT: " + gameAchievements[i].getName()
                    + "\n"+ gameAchievements[i].getDescription());
            }
        }
        else {
            achievementsToDisplay.add(gameAchievements[i].getName()
                   + "\n"+ gameAchievements[i].getDescription());
        }
    }

    /**
     * Checks if any achievements have been completed and adds bonus.
     */
    public void checkAllAchievements() {
        for (int i = 0; i < gameAchievements.length; i++) {
            if(gameAchievements[i].isCompleted() && !gameAchievements[i].achieved) {
                updateAchievements(i);
                bonus += gameAchievements[i].getSatisfactionBonus();
            }
        }
    }

    /**
     * Resets all the achievements.
     */
    public void resetAllAchievements() {
        for (int i = 0; i < gameAchievements.length; i++) {
            gameAchievements[i].reset();
        }
    }

    /**
     * Returns the achievement with the desired type
     * @param achievementName the name of the achievement wanted
     * @return
     */
    public Achievement getAchievement(String achievementName) {
        for (Achievement achievement : gameAchievements) {
            if (achievementName == achievement.getName()) {
                return achievement;
            }
        }
        return null;
    }
}
