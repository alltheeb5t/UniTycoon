package com.vikingz.unitycoon.achievements;

import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This is a new class which creates a bare minimum achievement object.
 * It was implemented to complete FR_BM_UNLOCK.
 */
public class BareMinimumAchievement extends Achievement {
    
    public static final String NAME = "Bare Minimum";
    static final String DESCRIPTION = "Place exactly 1 of each building type.";

    boolean bareMinimumPossible;

    public BareMinimumAchievement() {
        super();
        bareMinimumPossible = true;
    }

    public String getName() {
        return NAME;
    }

    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public void reset() {
        super.reset();
        bareMinimumPossible = true;
    }
    
    @Override
    public boolean isCompleted() {
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
