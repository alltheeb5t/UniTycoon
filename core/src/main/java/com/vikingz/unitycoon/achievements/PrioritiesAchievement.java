package com.vikingz.unitycoon.achievements;

import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This new class creates a priorities achievement object.
 * It was implemented to complete FR_PRIORITIES_UNLOCK.
 */
public class PrioritiesAchievement extends Achievement {
    
    public static final String NAME = "Priorities";
    public static final String DESCRIPTION = "Have twice as many study as recreation buildings after"
                                           + " 20 buildings.";

    public PrioritiesAchievement() {
        super();
    }

    public String getName() {
        return NAME;
    }

    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public boolean isCompleted() {
        if (GameGlobals.ACADEMIC_BUILDINGS_COUNT + GameGlobals.ACCOMODATION_BUILDINGS_COUNT
                + GameGlobals.FOOD_BUILDINGS_COUNT + GameGlobals.RECREATIONAL_BUILDINGS_COUNT >= 20) {
            if (GameGlobals.ACADEMIC_BUILDINGS_COUNT > 2 * GameGlobals.RECREATIONAL_BUILDINGS_COUNT) {
                return true;
            }
        }
        return false;
    }
}
