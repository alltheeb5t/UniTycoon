package com.vikingz.unitycoon.achievements;

/**
 * This class creates a unlucky achievement object.
 */
public class UnluckyAchievement extends Achievements {

    private int unluckyCounter;
    
    private final String NAME = "Unlucky";
    private final String DESCRIPTION = "Get 3 negative events in one game.";

    public UnluckyAchievement() {
        unluckyCounter = 0;
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
    protected boolean isCompleted() {
        if (unluckyCounter == 3) {
            return true;
        }

        //if(negativeEventOccurred) {
        //    unluckyCounter++;
        //}
        return false;
    }   
}
