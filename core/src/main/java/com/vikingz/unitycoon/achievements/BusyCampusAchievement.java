package com.vikingz.unitycoon.achievements;

import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This new class creates a busy campus achievement object.
 * It was implemented to complete FR_BUSY_UNLOCK.
 */
public class BusyCampusAchievement extends Achievement {

    public static final String NAME = "Busy Campus";
    public static final String DESCRIPTION = "Place more than 40 buildings.";
    
    public BusyCampusAchievement() {
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
                + GameGlobals.FOOD_BUILDINGS_COUNT + GameGlobals.RECREATIONAL_BUILDINGS_COUNT > 40) {
            return true;
        }
        return false;
    }
}
