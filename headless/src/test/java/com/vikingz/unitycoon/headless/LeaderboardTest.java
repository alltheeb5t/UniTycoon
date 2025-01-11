package com.vikingz.unitycoon.headless;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.vikingz.unitycoon.util.Leaderboard;

public class LeaderboardTest extends TestSuper {
    
    @Test
    public void testLoadLeaderboard() {
        Leaderboard.loadLeaderboard();

        String leaderboardValue = Leaderboard.getLeaderboardValue();
        assertEquals(4, leaderboardValue.length()-leaderboardValue.replaceAll("\n", "").length(),
                     "The leaderboard should have 5 lines");
    }

    @Test
    public void testLoadEmptyLeaderboard() {
        File testLeaderboardFile = new File("testLoadEmptyLeaderboardFile.txt");
        testLeaderboardFile.deleteOnExit();
        
        Leaderboard.loadLeaderboard(testLeaderboardFile);
        String leaderboardValue = Leaderboard.getLeaderboardValue();
        assertEquals(10, leaderboardValue.length()-leaderboardValue.replaceAll("0%", "").length(),
                     "The leaderboard should have 5 entries of 0%");

    }

    @Test
    public void testAddLeaderboardEntriesTop() {
        File testLeaderboardFile = new File("testAddLeaderboardEntriesTopLeaderboardFile.txt");
        testLeaderboardFile.deleteOnExit();
        
        Leaderboard.loadLeaderboard(testLeaderboardFile);
        Leaderboard.addScoreToLeaderBoard(10, "UsernameA");

        String leaderboardValue = Leaderboard.getLeaderboardValue();
        String firstLine = leaderboardValue.substring(0,leaderboardValue.indexOf("\n"));

        assertEquals("10% UsernameA", firstLine, "Only one score should be on top");

        // A new, better score has been achieved
        Leaderboard.addScoreToLeaderBoard(19, "UsernameB");

        leaderboardValue = Leaderboard.getLeaderboardValue();
        firstLine = leaderboardValue.substring(0,leaderboardValue.indexOf("\n"));
        String secondLine = leaderboardValue.substring(firstLine.length()+1,leaderboardValue.indexOf("\n", firstLine.length()+1));

        assertEquals("19% UsernameB", firstLine, "Only one score should be on top");
        assertEquals("10% UsernameA", secondLine, "Original best should be pushed down by 1");
    }

    @Test
    public void testAddLeaderboardEntriesWhenFull() {
        File testLeaderboardFile = new File("testAddLeaderboardEntriesWhenFullLeaderboardFile.txt");
        testLeaderboardFile.deleteOnExit();
        
        Leaderboard.loadLeaderboard(testLeaderboardFile);
        Leaderboard.addScoreToLeaderBoard(10, "UsernameA");
        Leaderboard.addScoreToLeaderBoard(13, "UsernameB");
        Leaderboard.addScoreToLeaderBoard(14, "UsernameA");
        Leaderboard.addScoreToLeaderBoard(17, "UsernameC");
        Leaderboard.addScoreToLeaderBoard(80, "UsernameG");

        String[] lines = Leaderboard.getLeaderboardValue().split("\n");
        
        assertEquals("80% UsernameG", lines[0], "UsernameG should currently have top score");
        assertEquals("17% UsernameC", lines[1], "UsernameC should be in second place");

        Leaderboard.addScoreToLeaderBoard(65, "UsernameL");

        lines = Leaderboard.getLeaderboardValue().split("\n");

        assertEquals("80% UsernameG", lines[0], "UsernameG should still have top score");
        assertEquals("65% UsernameL", lines[1], "UsernameL should now be in second place");
        assertEquals("17% UsernameC", lines[2], "UsernameC should now be in third place");
        assertEquals("14% UsernameA", lines[3], "UsernameA should now be in forth place");
        assertEquals("13% UsernameB", lines[4], "UsernameB should now be in fifth place");

        assertEquals(5, lines.length, "The leaderboard should still only have 5 lines");
    }

    @Test
    public void testSaveLeaderboard() {
        File testLeaderboardFile1 = new File("testSaveLeaderboardLeaderboardFile.txt");
        
        Leaderboard.loadLeaderboard(testLeaderboardFile1);
        Leaderboard.addScoreToLeaderBoard(10, "UsernameA");
        Leaderboard.addScoreToLeaderBoard(13, "UsernameB");
        Leaderboard.addScoreToLeaderBoard(14, "UsernameA");
        Leaderboard.addScoreToLeaderBoard(17, "UsernameC");
        Leaderboard.addScoreToLeaderBoard(80, "UsernameG");

        Leaderboard.saveLeaderboard(testLeaderboardFile1);

        // Load alternative leaderboard
        File testLeaderboardFile2 = new File("testSaveLeaderboardLeaderboardFile2.txt");
        testLeaderboardFile2.deleteOnExit();

        Leaderboard.loadLeaderboard(testLeaderboardFile2);

        String[] lines = Leaderboard.getLeaderboardValue().split("\n");
        assertNotEquals("80% UsernameG", lines[0], "No values should be read after loading leaderboardFile2.txt");
        assertNotEquals("17% UsernameC", lines[1], "No values should be read after loading leaderboardFile2.txt");
        assertNotEquals("14% UsernameA", lines[2], "No values should be read after loading leaderboardFile2.txt");
        assertNotEquals("13% UsernameB", lines[3], "No values should be read after loading leaderboardFile2.txt");
        assertNotEquals("10% UsernameA", lines[4], "No values should be read after loading leaderboardFile2.txt");

        // Load original leaderboard
        testLeaderboardFile1.deleteOnExit();
        Leaderboard.loadLeaderboard(testLeaderboardFile1);

        lines = Leaderboard.getLeaderboardValue().split("\n");
        assertEquals("80% UsernameG", lines[0], "Original leaderboard should be reloaded after loading leaderboardFile1.txt");
        assertEquals("17% UsernameC", lines[1], "Original leaderboard should be reloaded after loading leaderboardFile1.txt");
        assertEquals("14% UsernameA", lines[2], "Original leaderboard should be reloaded after loading leaderboardFile1.txt");
        assertEquals("13% UsernameB", lines[3], "Original leaderboard should be reloaded after loading leaderboardFile1.txt");
        assertEquals("10% UsernameA", lines[4], "Original leaderboard should be reloaded after loading leaderboardFile1.txt");

    }
}
