package com.vikingz.unitycoon.achievements;

/**
 * This class creates a lucky achievement object.
 */
public class LuckyAchievement extends Achievement {

    private int luckyCounter;

    private final String NAME = "Lucky";
    private final String DESCRIPTION = "Get 3 positive events in one game.";

    public LuckyAchievement() {
        luckyCounter = 0;
        achieved = false;
    }

    @Override
    public void reset() {
        luckyCounter = 0;
        achieved = false;
    }
    
    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    /**
     * Checks if achievement has been completed.
     * @return true if achievement has been completed
     */
    @Override
    public boolean isCompleted() {
        if (luckyCounter == 3) {
            return true;
        }

        //if(positiveEventOccurred) {
        //    unluckyCounter++;
        //}
        return false;
    }
}
