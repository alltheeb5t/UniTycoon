package com.vikingz.unitycoon.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class contains all the leaderboard functions.
 * 
 */
public class Leaderboard {
    
    private static String leaderboardValue;
    private static File leaderboardFile = new File("leaderboard.txt");

    /**
     * Saves leaderboard to text file.
     */
    public static void saveLeaderboard(){
        try {
            FileWriter fileWriter = new FileWriter(leaderboardFile);
            fileWriter.write(leaderboardValue);
            fileWriter.close();
            
            System.out.println("Successfully wrote to the leaderboard file.");

        } catch (IOException i) {
            System.out.println("FILE NOT FOUND");
        }
    }


    /**
     * Loads leaderboard from text file.
     */
    public static void loadLeaderboard(){

        leaderboardValue = "";
        try {
            leaderboardFile.createNewFile();
            Scanner fileReader = new Scanner(leaderboardFile);
            while (fileReader.hasNextLine()) {
                leaderboardValue += fileReader.nextLine() + "\n";
            }
            fileReader.close();

            if (leaderboardValue == "") {
                //Puts 5 empty scores on the leaderboard if the file is empty.
                leaderboardValue = "0 -\n0 -\n0 -\n0 -\n0 -\n"; 
            }
            //Updates the leaderboard removing the additional \n at the end."
            leaderboardValue = leaderboardValue.substring(0, leaderboardValue.length()-1);

            System.out.println("\n\nLoaded Leaderboard");

        } catch (IOException i) {
            System.out.println("FILE NOT FOUND");
        }
    }

    /**
     *  Determines if a final score needs to be stored on the leaderboard.
     * @return True if needs to be added to leaderboard. Else false.
     */
    public static Boolean isLeaderboardScore(int finalScore) {
        String[] scores = leaderboardValue.split("\n");
        //Finds the 5th placed score stored on the leaderboard and converts to integer.  
        int lowestSavedScore = Integer.valueOf(scores[4].split(" ")[0]);

        if (lowestSavedScore < finalScore) {
            return true;
        }

        return false;
    }

    /**
     * Adds a given result to the leaderboard in the correct place.
     * @param finalScore Final score which needs to be added.
     * @param Username Username to be added with score.
     */
    public static void addScoreToLeaderBoard(int finalScore, String Username) {
        String[] scores = leaderboardValue.split("\n");
        String updatedLeaderboardValue = "";
        Boolean addedNewScore = false;

        for (int i = 0; i < scores.length; i++) {
            //Finds score stored on the leaderboard at place i and converts to integer.
            int score = Integer.valueOf(scores[i].split(" ")[0]);

            if (score < finalScore && !addedNewScore) {
                addedNewScore = true;
                updatedLeaderboardValue += Integer.toString(finalScore) + " " + Username + "\n";
            }
            
            //Adds all except the previous 5th placed score to the leaderboard.
            if (i < 4) {
                updatedLeaderboardValue += scores[i] + "\n";
            }
        }
        
        //Updates the leaderboard removing the additional \n at the end."
        leaderboardValue = updatedLeaderboardValue.substring(0, updatedLeaderboardValue.length()-1);
    }

    public static String getLeaderboardValue() {
        return leaderboardValue;
    }
}
