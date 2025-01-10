package com.vikingz.unitycoon.achievements;

import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This class creates an is this a university achievement object.
 */
public class IsThisAUniversityAchievement extends Achievement {
    
    private final String NAME = "Is This A University";
    private final String DESCRIPTION = "Have twice as many recreation as study buildings after 20 buildings.";

    public IsThisAUniversityAchievement() {
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
        if (GameGlobals.ACADEMIC_BUILDINGS_COUNT + GameGlobals.ACCOMODATION_BUILDINGS_COUNT
                + GameGlobals.FOOD_BUILDINGS_COUNT + GameGlobals.RECREATIONAL_BUILDINGS_COUNT >= 20) {
            if (2 * GameGlobals.ACADEMIC_BUILDINGS_COUNT <= GameGlobals.RECREATIONAL_BUILDINGS_COUNT) {
                return true;
            }
        }
        return false;
    }
}
