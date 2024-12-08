package com.vikingz.unitycoon.util;

import java.util.List;

import com.vikingz.unitycoon.building.Building;
import com.vikingz.unitycoon.building.BuildingStats;
import com.vikingz.unitycoon.building.buildings.FoodBuilding;
import com.vikingz.unitycoon.building.buildings.RecreationalBuilding;

public class MoneyHandler {
    private int balance;

    // Support limiting the amount of debt that player is allowed to accumulate
    public static final int MAX_DEBT = Integer.MAX_VALUE;

    // Future support for variable starting balance
    public static final int STARTING_BALANCE = 0;

    /**
     * Constructor called at the start of the game and on reset
     */
    public MoneyHandler() {
        balance = STARTING_BALANCE;
    }

    /**
     * Remove the specified amount from the current balance
     * @param amount
     * @return boolean: Was withdrawal successful?
     */
    public boolean withdraw(float amount) {
        if (balance - amount > -MAX_DEBT) {
            balance -= amount;
            return true;
        }

        return false;
    }

    /**
     * Add the specified amount to the current balance (as a one-off)
     * @param amount
     * @return boolean: Was the deposit successful?
     */
    public boolean deposit(float amount) {
        balance += amount;

        return true;
    }


    /**
     * Called periodically by the timer. Calculates and adds revenue from
     * buildings that provide a continuous revenue stream (e.g. food outlets)
     */
    public void earn(List<Building> buildings) {
        for (Building building : buildings){
            if(building.getBuildingType() == BuildingStats.BuildingType.FOOD){
                FoodBuilding foodBuilding = (FoodBuilding) building;
                balance += foodBuilding.calculateProfitMade();
            }

            if(building.getBuildingType() == BuildingStats.BuildingType.RECREATIONAL){
                RecreationalBuilding recreationalBuildingBuilding = (RecreationalBuilding) building;
                balance += recreationalBuildingBuilding.calculateProfitMade();
            }

        }
    }

    /**
     * Called at the start of every semester. Adds revenue from buildings that
     * provide revenue only at the end of semester (e.g. Accommodation)
     */
    public void earnSemesterly() {
        
    }

    /**
     * Get the current total cash balance of the user. May be negative if in debt.
     * @return
     */
    public float getBalance() {
        return balance;
    }
}
