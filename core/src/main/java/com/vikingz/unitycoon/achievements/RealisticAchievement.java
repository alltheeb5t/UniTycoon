package com.vikingz.unitycoon.achievements;

import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This new class creates a realistic achievement object.
 * It was implemented to complete FR_REALISTIC_UNLOCK.
 */
public class RealisticAchievement extends Achievement {

    public static final String NAME = "Realistic";
    public static final String DESCRIPTION = "Balance drops below £24000 in debt.";

    public RealisticAchievement() {
        super();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public boolean isCompleted() {
        if (GameGlobals.MONEY.getBalance() < -24000) {
            return true;
        }
        return false;
    }
}
