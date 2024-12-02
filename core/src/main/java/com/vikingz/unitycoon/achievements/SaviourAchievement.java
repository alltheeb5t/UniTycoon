package com.vikingz.unitycoon.achievements;

public class SaviourAchievement extends Achievements {
    
    private final String NAME = "Saviour";
    private final String DESCRIPTION = "Save a burning building.";

    public SaviourAchievement() {
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
    protected boolean isCompleted() {
        //if(savedBurningBuilding) {
        //    return true;
        //}
        return false;
    }

    @Override
    protected int getSatisfactionBonus() {
        return 5;
    }
}
