package com.vikingz.unitycoon.achievements;

/**
 * This class creates an indecisive achievement object.
 */
public class IndecisiveAchievement extends Achievements {

    private static int removedBuildings;
    
    private final String NAME = "Indecisive";
    private final String DESCRIPTION = "Remove more than 20 buildings.";

    public IndecisiveAchievement() {
        removedBuildings = 0;
        achieved = false;
    }

    @Override
    public void reset() {
        removedBuildings = 0;
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

    /**
     * Increases the removed buildings counter by one.
     */
    public static void incrementRemovedBuildings() {
        removedBuildings++;
    }

    /**
     * Checks if achievement has been completed.
     * @return true if achievement has been completed
     */
    @Override
    protected boolean isCompleted() {
        if (removedBuildings > 20) {
            return true;
        }

        return false;
    }
}
