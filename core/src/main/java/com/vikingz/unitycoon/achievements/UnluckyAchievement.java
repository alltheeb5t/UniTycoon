package com.vikingz.unitycoon.achievements;

import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This new class creates an unlucky achievement object.
 * It was implemented to complete FR_UNLUCKY_UNLOCK.
 */
public class UnluckyAchievement extends Achievement {
    
    public static final String NAME = "Unlucky";
    public static final String DESCRIPTION = "Get 3 negative events in one game.";

    public UnluckyAchievement() {
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
        if (GameGlobals.EVENT.getNegativeEvent() == 3) {
            return true;
        }

        return false;
    }   
}
