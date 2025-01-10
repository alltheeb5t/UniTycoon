package com.vikingz.unitycoon.achievements;

import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This new class creates a lucky achievement object.
 * It was implemented to complete FR_LUCKY_UNLOCK.
 * 
 */
public class LuckyAchievement extends Achievement {

    public static final String NAME = "Lucky";
    public static final String DESCRIPTION = "Get 3 positive events in one game.";

    public LuckyAchievement() {
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
        if (GameGlobals.EVENT.getPositiveEvent() == 3) {
            return true;
        }

        return false;
    }
}
