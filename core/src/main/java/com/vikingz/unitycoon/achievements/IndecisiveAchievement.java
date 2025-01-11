package com.vikingz.unitycoon.achievements;

/**
 * This new class creates an indecisive achievement object.
 * It was implemented to complete FR_INDECISIVE_UNLOCK.
 */
public class IndecisiveAchievement extends Achievement {
    
    public static final String NAME = "Indecisive";
    public static final String DESCRIPTION = "Remove more than 20 buildings.";

    int removedBuildings;

    public IndecisiveAchievement() {
        super();
        removedBuildings = 0;
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
        removedBuildings = 0;
    }

    /**
     * Increases the removed buildings counter by one.
     */
    public void incrementRemovedBuildings() {
        removedBuildings++;
    }

    @Override
    public boolean isCompleted() {
        if (removedBuildings > 20) {
            return true;
        }

        return false;
    }
}
