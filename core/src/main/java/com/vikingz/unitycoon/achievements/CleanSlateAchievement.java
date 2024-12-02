package com.vikingz.unitycoon.achievements;

import com.vikingz.unitycoon.global.GameGlobals;

public class CleanSlateAchievement extends Achievements {

    private boolean cleanSlateMinReached;
    
    private final String NAME = "Clean Slate";
    private final String DESCRIPTION = "Place 10 or more buildings and remove them all.";

    public CleanSlateAchievement() {
        cleanSlateMinReached = false;
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
                + GameGlobals.FOOD_BUILDINGS_COUNT + GameGlobals.RECREATIONAL_BUILDINGS_COUNT == 0
                && cleanSlateMinReached) {
            return true;
        }
        
        if (GameGlobals.ACADEMIC_BUILDINGS_COUNT + GameGlobals.ACCOMODATION_BUILDINGS_COUNT
                + GameGlobals.FOOD_BUILDINGS_COUNT + GameGlobals.RECREATIONAL_BUILDINGS_COUNT >= 10) {
            cleanSlateMinReached = true;
        }
        return false;
    }
}
