package com.vikingz.unitycoon.achievements;

import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This class creates a unlucky achievement object.
 */
public class UnluckyAchievement extends Achievement {
    
    private final String NAME = "Unlucky";
    private final String DESCRIPTION = "Get 3 negative events in one game.";

    public UnluckyAchievement() {
        achieved = false;
    }

    @Override
    public void reset() {
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
        if (GameGlobals.EVENT.getNegativeEvent() == 3) {
            return true;
        }

        return false;
    }   
}
