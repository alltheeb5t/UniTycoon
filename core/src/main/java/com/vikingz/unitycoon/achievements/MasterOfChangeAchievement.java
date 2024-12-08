package com.vikingz.unitycoon.achievements;

import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This class creates a master of change achievement object.
 */
public class MasterOfChangeAchievement extends Achievements {
    
    private boolean masterOfChangePossible;

    private final String NAME = "Master Of Change";
    private final String DESCRIPTION = "Remain under 50% satisfaction for the first 3 minutes and then win the game.";
    private final boolean HIDDEN = true;

    public MasterOfChangeAchievement() {
        masterOfChangePossible = true;
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

    @Override
    public boolean getHidden() {
        return HIDDEN;
    }

    /**
     * Checks if achievement has been completed.
     * @return true if achievement has been completed
     */
    @Override
    protected boolean isCompleted() {
        if (GameGlobals.ELAPSED_TIME <= 0 && masterOfChangePossible) { //&& gameWon) {
            return true;
        }
        
        if (GameGlobals.ELAPSED_TIME >= 120 && GameGlobals.SATISFACTION.getSatisfaction() >= 50) {
            masterOfChangePossible = false;
        }

        return false;
    }
}
