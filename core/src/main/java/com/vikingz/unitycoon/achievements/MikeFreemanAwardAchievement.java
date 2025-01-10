package com.vikingz.unitycoon.achievements;

import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This new class creates a mike freeman award achievement object.
 * It was implemented to complete FR_MFA_UNLOCK.
 */
public class MikeFreemanAwardAchievement extends Achievement {
    
    public static final String NAME = "Mike Freeman Award";
    public static final String DESCRIPTION = "Maintain 70% or higher satisfaction for more than 3 minutes.";
    static final boolean HIDDEN = true;

    int mikeFreemanAwardStartTime;

    public MikeFreemanAwardAchievement() {
        super();
        mikeFreemanAwardStartTime = 0;
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
        mikeFreemanAwardStartTime = 0;
    }

    @Override
    public boolean getHidden() {
        return HIDDEN;
    }

    @Override
    public boolean isCompleted() {
        if (mikeFreemanAwardStartTime - GameGlobals.TIME_REMAINING > 180) {
            return true;
        }

        if (GameGlobals.SATISFACTION.getSatisfaction() < 70) {
            mikeFreemanAwardStartTime = -1;
        }
        
        // Starts timer when satisfaction goes over 80%
        if (GameGlobals.SATISFACTION.getSatisfaction() >= 80 && mikeFreemanAwardStartTime == -1){
            mikeFreemanAwardStartTime = GameGlobals.TIME_REMAINING;
        }

        return false;
    }

    @Override
    public int getSatisfactionBonus() {
        return 5;
    }
}
