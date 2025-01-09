package com.vikingz.unitycoon.achievements;

/**
 * This class contains creates an achievement object.
 */
public class Achievement {

    public boolean achieved = false;
    public boolean usernameAchieved = false;

    public String getName() {
        return "";
    }

    public String getDescription() {
        return "";
    }

    public boolean getHidden() {
        return false;
    }

    /**
     * Resets the achievement.
     */
    public void reset() {}

    /**
     * Checks if the achievement has been completed
     * @return true if completed else false
     */
    public boolean isCompleted() {
        return false;
    }

    /**
     * Gets the bonus value, if the achievement givees a bonus.
     * @return
     */
    public int getSatisfactionBonus() {
        return 0;
    }
}
