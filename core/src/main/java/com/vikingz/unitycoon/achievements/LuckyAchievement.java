package com.vikingz.unitycoon.achievements;

import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This class creates a lucky achievement object.
 */
public class LuckyAchievement extends Achievement {

    private final String NAME = "Lucky";
    private final String DESCRIPTION = "Get 3 positive events in one game.";

    public LuckyAchievement() {
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
        if (GameGlobals.EVENT.getPositiveEvent() == 3) {
            return true;
        }

        return false;
    }
}
