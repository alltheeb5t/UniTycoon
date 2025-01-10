package com.vikingz.unitycoon.achievements;

import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This new class creates a clean slate achievement object.
 * It was implemented to complete FR_CS_UNLOCK.
 */
public class CleanSlateAchievement extends Achievement {
    
    public static final String NAME = "Clean Slate";
    public static final String DESCRIPTION = "Place 10 or more buildings and remove them all.";
    
    boolean cleanSlateMinReached;

    public CleanSlateAchievement() {
        super();
        cleanSlateMinReached = false;
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
        cleanSlateMinReached = false;
    }

    @Override
    public boolean isCompleted() {
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
