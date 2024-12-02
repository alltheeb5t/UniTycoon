package com.vikingz.unitycoon.achievements;

import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This class creates a bankruptcy achievement object.
 */
public class BankruptcyAchievement extends Achievements {

    private final String NAME = "Bankruptcy";
    private final String DESCRIPTION = "Balance drops below 0.";

    public BankruptcyAchievement() {
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
        if (GameGlobals.BALANCE < 0) {
            return true;
        }
        return false;
    }
}
