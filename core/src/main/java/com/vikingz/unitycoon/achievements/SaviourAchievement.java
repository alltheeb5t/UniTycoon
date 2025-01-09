package com.vikingz.unitycoon.achievements;

/**
 * This class creates a saviour achievement object.
 */
public class SaviourAchievement extends Achievement {

    boolean savedBurningBuilding;
    
    private final String NAME = "Saviour";
    private final String DESCRIPTION = "Save a burning building.";

    public SaviourAchievement() {
        savedBurningBuilding = false;
        achieved = false;
    }

    @Override
    public void reset() {
        savedBurningBuilding = false;
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

    public void burningBuildingSaved() {
        savedBurningBuilding = true;
    }

    /**
     * Checks if achievement has been completed.
     * @return true if achievement has been completed
     */
    @Override
    public boolean isCompleted() {
        return savedBurningBuilding;
    }

    @Override
    public int getSatisfactionBonus() {
        return 5;
    }
}
