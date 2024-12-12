package com.vikingz.unitycoon.achievements;

import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This class creates a bankruptcy achievement object.
 */
public class RealisticAchievement extends Achievements {

    private final String NAME = "Realistic";
    private final String DESCRIPTION = "Balance drops below £24m.";

    public RealisticAchievement() {
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
    protected boolean isCompleted() {
        if (GameGlobals.MONEY.getBalance() < -24000) {
            return true;
        }
        return false;
    }
}
