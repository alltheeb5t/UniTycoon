package com.vikingz.unitycoon.achievements;

import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This new class creates a master of change achievement object.
 * It was implemented to complete FR_CHANGE_UNLOCK.
 */
public class MasterOfChangeAchievement extends Achievement {

    public static final String NAME = "Master Of Change";
    public static final String DESCRIPTION = "Remain under 30% satisfaction for the first 3 minutes"
                                           + " and then win the game.";
    static final boolean HIDDEN = true;

    boolean masterOfChangePossible;

    public MasterOfChangeAchievement() {
        super();
        masterOfChangePossible = true;
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
    public void reset() {
        super.reset();
        masterOfChangePossible = true;
    }

    @Override
    public boolean getHidden() {
        return HIDDEN;
    }

    @Override
    public boolean isCompleted() {
        if (GameGlobals.TIME_REMAINING <= 0 && masterOfChangePossible && GameGlobals.gameWon) {
            return true;
        }
        
        if (GameGlobals.TIME_REMAINING >= 120 && GameGlobals.SATISFACTION.getSatisfaction() >= 50) {
            masterOfChangePossible = false;
        }

        return false;
    }
}
