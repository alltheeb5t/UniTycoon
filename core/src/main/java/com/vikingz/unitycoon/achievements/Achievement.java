package com.vikingz.unitycoon.achievements;

/**
 * This new abstract class contains the template for an achievement object. 
 * It was implemented to complete UR_ACHIEVEMENTS.
 */
public abstract class Achievement {

    public boolean achieved = false;
    public boolean usernameAchieved = false;

    public Achievement() {
        achieved = false;
    }

    public String getName() {
        return "";
    }

    public String getDescription() {
        return "";
    }

    /**
     * @return true if the achievement is a hidden achievement.
     */
    public boolean getHidden() {
        return false;
    }

    /**
     * Resets the achievement.
     */
    public void reset() {
        achieved = false;
    }

    /**
     * Checks if the achievement has been completed.
     * @return true if the achievement has been completed.
     */
    public boolean isCompleted() {
        return false;
    }

    /**
     * Gets the bonus value, if the achievement gives a bonus.
     * @return the bonus value.
     */
    public int getSatisfactionBonus() {
        return 0;
    }
}
