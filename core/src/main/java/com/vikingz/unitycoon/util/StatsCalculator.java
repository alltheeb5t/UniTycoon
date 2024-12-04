package com.vikingz.unitycoon.util;

import java.util.List;
import com.vikingz.unitycoon.building.Building;
import com.vikingz.unitycoon.building.BuildingStats.BuildingType;
import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This class is used to calculate different statistics such as
 * satisfaction and balance.
 */
public class StatsCalculator {

    private static int satisfactionModifier = 0;
    
    /**
     * Calculates satisfaction 
     * @param placedBuildings List of placed buildings on the map
     * @return Int Amount of satisfaction gained
     */
    public static void calculateSatisfaction(List<Building> placedBuildings){
        int satisfaction;
        satisfaction = calculateMaxSatisfaction();
        satisfaction -= calculateBuildingProportionLoss();
        //satisfaction -= calculateProximityLoss(placedBuildings);

        satisfaction += satisfactionModifier;
        if (satisfaction < 0) {
            satisfaction = 0;
        }
        else if (satisfaction > 100) {
            satisfaction = 100;
        }

        GameGlobals.SATISFACTION = satisfaction;
    }

    /**
     * Calculates a loss to add to satisfaction if the accomodation buildings
     * are not near enough to one of each other building. 
     * @return Loss to add to satisfaction
     */
    private static int calculateProximityLoss(List<Building> placedBuildings) {
        int proximityLoss = 0;

        for (Building accomodationBuilding : placedBuildings) {
            boolean nearAcademic = false;
            boolean nearFood = false;
            boolean nearRecreation = false;

            if (accomodationBuilding.getBuildingType() == BuildingType.ACCOMODATION) {
                for (Building otherBuilding : placedBuildings) {
                    // Checks if the other type of building is within 4 buildings distance
                    if (otherBuilding.getBuildingType() == BuildingType.ACADEMIC && !nearAcademic) {
                        if (getDistance(accomodationBuilding, otherBuilding) < 4 * GameGlobals.SCREEN_BUILDING_SIZE) {
                            nearAcademic = true;
                        }
                        continue;
                    }
                    else if (otherBuilding.getBuildingType() == BuildingType.FOOD && !nearFood) {
                        if (getDistance(accomodationBuilding, otherBuilding) < 4 * GameGlobals.SCREEN_BUILDING_SIZE) {
                            nearFood = true;
                        }
                        continue;
                    }
                    else if (otherBuilding.getBuildingType() == BuildingType.RECREATIONAL && !nearRecreation) {
                        if (getDistance(accomodationBuilding, otherBuilding) < 4 * GameGlobals.SCREEN_BUILDING_SIZE) {
                            nearRecreation = true;
                        }
                        continue;
                    }
                }
            }

            // Adds 1 to proximity for each building that isn't close enough
            if(!nearAcademic) {proximityLoss++;}
            if(!nearFood) {proximityLoss++;}
            if(!nearRecreation) {proximityLoss++;}
        }

        return proximityLoss;
    }

    /**
     * Gets the distance between any 2 buildings defined by the taxicab metric:
     * Distance = |xcoord1 - xcoord2| + |ycoord1 - ycoord2|
     * @param accBuilding accomodation building
     * @param otherBuilding another building
     * @return the distance value
     */
    private static int getDistance(Building accBuilding, Building otherBuilding) {
        int distance = (int) (Math.abs(accBuilding.getX() - otherBuilding.getX()) 
            + Math.abs(Math.abs(accBuilding.getY() - otherBuilding.getY())));
        
        return distance;
    }

    /**
     * Calculates a loss to add to satisfaction if the number of
     * non-accomodation buildings is much higher than the number of
     * accomodation buildings. 
     * @return Loss to add to satisfaction
     */
    private static int calculateBuildingProportionLoss() {
        int buildingProportionLoss = 0;

        // Checks if recreational buildings is in proportion with accomodation buildings.
        if (GameGlobals.RECREATIONAL_BUILDINGS_COUNT >= 4 // There should be no more than 1 per every 6 and never more than 4.
                || (GameGlobals.RECREATIONAL_BUILDINGS_COUNT > Math.ceil((GameGlobals.ACCOMODATION_BUILDINGS_COUNT + 1) / 6.0))) {
            buildingProportionLoss += 10; 
        }
        // Checks if food buildings is in proportion with accomodation buildings.
        if (GameGlobals.FOOD_BUILDINGS_COUNT >= 6 // There should be no more than 1 per every 4 and never more than 6.
                || (GameGlobals.FOOD_BUILDINGS_COUNT > Math.ceil((GameGlobals.ACCOMODATION_BUILDINGS_COUNT + 1)  / 4.0))) {
            buildingProportionLoss += 10;
        }
        // Checks if the recreational buildings is in proportion with accomodation buildings.
        if (GameGlobals.ACADEMIC_BUILDINGS_COUNT >= 12 // There should be no more than 1 per every 2 and never more than 12.
                || (GameGlobals.ACADEMIC_BUILDINGS_COUNT > Math.ceil((GameGlobals.ACCOMODATION_BUILDINGS_COUNT + 1) / 2.0))){
            buildingProportionLoss += 10;
        }

        return buildingProportionLoss;
    }

    /**
     * Resets the satisfactionModifier to 0.
     */
    public static void resetSatisfactionModifier() {
        satisfactionModifier = 0;
    }

    /**
     * Increases the satisfaction by a given amount. Stores that amount so that
     * it is added everytime satisfaction is calculated.
     * @param satisfactionBonus
     */
    public static void increaseSatisfaction(int satisfactionBonus) {
        satisfactionModifier += satisfactionBonus;
        GameGlobals.SATISFACTION += satisfactionBonus;
    }

    /**
     * Decreases the satisfaction by a given amount. Stores that amount so that
     * it is removed everytime satisfaction is calculated.
     * @param satisfactionBonus
     */
    public static void decreaseSatisfaction(int satisfactionChange) {
        satisfactionModifier -= satisfactionChange;
        GameGlobals.SATISFACTION -= satisfactionChange;
    }

    /**
     * Calculates the maximum satisfaction the user can have based on 
     * the number of placed buildings.
     * @return the max satisfaction
     */
    private static int calculateMaxSatisfaction() {
        int maxSatisfaction = 0;
        int numBuildings = GameGlobals.ACADEMIC_BUILDINGS_COUNT + GameGlobals.ACCOMODATION_BUILDINGS_COUNT
            + GameGlobals.FOOD_BUILDINGS_COUNT + GameGlobals.RECREATIONAL_BUILDINGS_COUNT;

        if (numBuildings <= 24) {
            maxSatisfaction = Math.round((10 / 3) * numBuildings) + 10;
        }
        else if (numBuildings <= 36) {
            maxSatisfaction = 90;
        }
        else {
            maxSatisfaction = 90 - Math.round((10 / 3) * (numBuildings - 36));
        }

        return maxSatisfaction;
    }

    /**
     * Calculates the profit made per second
     * @param coinsPerSecond The ammout of coins a building makes per second 
     * @return Float The ammout of coins made per second
     */
    public static float calculateProfitMade(float coinsPerSecond){
        return coinsPerSecond;
    }

}
