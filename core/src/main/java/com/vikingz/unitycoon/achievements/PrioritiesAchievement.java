package com.vikingz.unitycoon.achievements;

import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This class creates a priorities achievement object.
 */
public class PrioritiesAchievement extends Achievements {
    
    private final String NAME = "Priorities";
    private final String DESCRIPTION = "Have twice as many study as recreation buildings after 20 buildings.";

    public PrioritiesAchievement() {
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
        if (GameGlobals.ACADEMIC_BUILDINGS_COUNT + GameGlobals.ACCOMODATION_BUILDINGS_COUNT
                + GameGlobals.FOOD_BUILDINGS_COUNT + GameGlobals.RECREATIONAL_BUILDINGS_COUNT >= 20) {
            if (GameGlobals.ACADEMIC_BUILDINGS_COUNT > 2 * GameGlobals.RECREATIONAL_BUILDINGS_COUNT) {
                return true;
            }
        }
        return false;
    }
}
