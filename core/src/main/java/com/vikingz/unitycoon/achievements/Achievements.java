package com.vikingz.unitycoon.achievements;

/**
 * This class contains creates an achievement object.
 */
public class Achievements {

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
     * Checks if the achievement has been completed
     * @return true if completed else false
     */
    protected boolean isCompleted() {
        return false;
    }

    /**
     * Gets the bonus value, if the achievement givees a bonus.
     * @return
     */
    protected int getSatisfactionBonus() {
        return 0;
    }
}
