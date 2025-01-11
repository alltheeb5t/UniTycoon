package com.vikingz.unitycoon.building;

import com.vikingz.unitycoon.building.BuildingStats.BuildingType;

/**
 * This class essentially serves as a struct to pass data around regarding buildings.
 * When the user presses a button in the menu to buy a building, the data for that building.
 * is passed around a type BuildingInfo.
 * 
 * This class has been refactored to help with code readability.
 */
public class BuildingInfo {

    String  buildingID;
    BuildingType buildingType;
    float buildingCost;
    int numberOfStudents;
    float coinsPerSecond;

    public BuildingInfo(String  buildingID,  BuildingStats.BuildingType buildingType, float buildingCost, 
            int numberOfStudents, int coinsPerSecond){
        this.buildingID = buildingID;
        this.buildingType = buildingType;
        this.buildingCost = buildingCost;
        this.numberOfStudents = numberOfStudents;
        this.coinsPerSecond = coinsPerSecond;
    }

    public String getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(String buildingID) {
        this.buildingID = buildingID;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }

    public float getBuildingCost() {
        return buildingCost;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public float getCoinsPerSecond() {
        return coinsPerSecond;
    }
}
