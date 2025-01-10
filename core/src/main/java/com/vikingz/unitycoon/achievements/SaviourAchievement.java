package com.vikingz.unitycoon.achievements;

/**
 * This new class creates a saviour achievement object.
 * It was implemented to complete FR_SAVIOUR_UNLOCK.
 */
public class SaviourAchievement extends Achievement {
    
    public static final String NAME = "Saviour";
    public static final String DESCRIPTION = "Save a burning building.";

    boolean savedBurningBuilding;

    public SaviourAchievement() {
        super();
        savedBurningBuilding = false;
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
        savedBurningBuilding = false;
    }

    public void burningBuildingSaved() {
        savedBurningBuilding = true;
    }

    @Override
    public boolean isCompleted() {
        return savedBurningBuilding;
    }

    @Override
    public int getSatisfactionBonus() {
        return 5;
    }
}
