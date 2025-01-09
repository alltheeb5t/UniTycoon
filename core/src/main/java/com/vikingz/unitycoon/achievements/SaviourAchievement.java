package com.vikingz.unitycoon.achievements;

/**
 * This class creates a saviour achievement object.
 */
public class SaviourAchievement extends Achievement {
    
    private final String NAME = "Saviour";
    private final String DESCRIPTION = "Save a burning building.";

    public SaviourAchievement() {
        achieved = false;
    }

    @Override
    public void reset() {
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
     * Checks if achievement has been completed.
     * @return true if achievement has been completed
     */
    @Override
    public boolean isCompleted() {
        //if(savedBurningBuilding) {
        //    return true;
        //}
        return false;
    }

    @Override
    public int getSatisfactionBonus() {
        return 5;
    }
}
