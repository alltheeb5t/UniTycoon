package com.vikingz.unitycoon.achievements;

import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This class creates a bare minimum achievement object.
 */
public class BareMinimumAchievement extends Achievements {
    
    private boolean bareMinimumPossible;
    
    private final String NAME = "Bare Minimum";
    private final String DESCRIPTION = "Place exactly 1 of each building type.";

    public BareMinimumAchievement() {
        bareMinimumPossible = true;
        achieved = false;
    }

    @Override
    public void reset() {
        bareMinimumPossible = true;
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
     * Checks if achievement Bare Minimum has been completed.
     * @return true if achievement has been completed
     */
    @Override
    protected boolean isCompleted() {
        if (GameGlobals.TIME_REMAINING <= 0 && bareMinimumPossible) {
            if (GameGlobals.ACADEMIC_BUILDINGS_COUNT == 1 && GameGlobals.ACCOMODATION_BUILDINGS_COUNT == 1
                && GameGlobals.FOOD_BUILDINGS_COUNT == 1 && GameGlobals.RECREATIONAL_BUILDINGS_COUNT == 1) {
                return true;
            }
        }

        // Checks no extra buildings are ever placed
        if (GameGlobals.ACADEMIC_BUILDINGS_COUNT > 1 || GameGlobals.ACCOMODATION_BUILDINGS_COUNT > 1
                || GameGlobals.FOOD_BUILDINGS_COUNT > 1 || GameGlobals.RECREATIONAL_BUILDINGS_COUNT > 1) {
                bareMinimumPossible = false;
        }

        return false;
    }
}
