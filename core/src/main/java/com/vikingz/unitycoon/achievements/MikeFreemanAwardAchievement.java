package com.vikingz.unitycoon.achievements;

import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This class creates a mike freeman award achievement object.
 */
public class MikeFreemanAwardAchievement extends Achievements {

    private int mikeFreemanAwardStartTime;
    
    private final String NAME = "Mike Freeman Award";
    private final String DESCRIPTION = "Maintain 80% or higher satisfaction for more than 3 minutes.";
    private final boolean HIDDEN = true;

    public MikeFreemanAwardAchievement() {
        mikeFreemanAwardStartTime = 0;
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
        if (mikeFreemanAwardStartTime - GameGlobals.ELAPSED_TIME > 180) {
            return true;
        }

        if (GameGlobals.SATISFACTION.getSatisfaction() < 80) {
            mikeFreemanAwardStartTime = -1;
        }
        
        // Starts timer when satisfaction goes over 80%
        if (GameGlobals.SATISFACTION.getSatisfaction() >= 80 && mikeFreemanAwardStartTime == -1){
            mikeFreemanAwardStartTime = GameGlobals.ELAPSED_TIME;
        }

        return false;
    }

    @Override
    protected int getSatisfactionBonus() {
        return 5;
    }
}
