package com.vikingz.unitycoon.achievements;

import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This new class creates an is this a university achievement object.
 * It was implemented to complete FR_ITAU_UNLOCK.
 */
public class IsThisAUniversityAchievement extends Achievement {
    
    public static final String NAME = "Is This A University";
    public static final String DESCRIPTION = "Have twice as many recreation as study buildings after"
                                           + " 20 buildings.";

    public IsThisAUniversityAchievement() {
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
        if (GameGlobals.ACADEMIC_BUILDINGS_COUNT + GameGlobals.ACCOMODATION_BUILDINGS_COUNT
                + GameGlobals.FOOD_BUILDINGS_COUNT + GameGlobals.RECREATIONAL_BUILDINGS_COUNT >= 20) {
            if (2 * GameGlobals.ACADEMIC_BUILDINGS_COUNT <= GameGlobals.RECREATIONAL_BUILDINGS_COUNT) {
                return true;
            }
        }
        return false;
    }
}
