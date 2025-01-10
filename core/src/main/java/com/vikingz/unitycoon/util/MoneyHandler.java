package com.vikingz.unitycoon.util;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vikingz.unitycoon.building.Building;
import com.vikingz.unitycoon.building.BuildingStats.BuildingType;
import com.vikingz.unitycoon.building.EarnSchedule;

public class MoneyHandler {
    private int balance;

    // Used to keep track of type-wide changes to the amount of money a building yields
    Map<BuildingType, Float> earningMultipliersByType = new HashMap<BuildingType,Float>();

    // Support limiting the amount of debt that player is allowed to accumulate
    public static final int MAX_DEBT = Integer.MAX_VALUE;

    // Future support for variable starting balance
    public static final int STARTING_BALANCE = 1000;

    /**
     * Constructor called at the start of the game and on reset
     */
    public MoneyHandler() {
        balance = STARTING_BALANCE;

        // Set all multipliers initially to 1
        EnumSet.allOf(BuildingType.class).forEach(buildingType -> {
            earningMultipliersByType.put(buildingType, 1f);
        });
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
     * buildings that provide a continuous revenue stream (e.g. food outlets).
     * Earns only half the normal amount of money if in debt.
     */
    public void earn(List<Building> buildings, EarnSchedule earnSchedule) {
        for (Building building : buildings){
            float multiplier = earningMultipliersByType.get(building.getBuildingType());

            if (balance < 0) { // Earnings half when in debt
                multiplier *= 0.5;
            }
            
            balance += multiplier * building.calculateProfitMade(earnSchedule);
        }
    }

    /**
     * Get the current total cash balance of the user. May be negative if in debt.
     * @return
     */
    public float getBalance() {
        return balance;
    }

    /**
     * Applies a new multiplier to specific building type (compounded on existing multiplier)
     * @param buildingType Type of building to apply multiplier to
     * @param multiplier Float value to multiply by
     * @return New total multiplier (not including how earnings half when in debt)
     */
    public float applyMultiplierToType(BuildingType buildingType, float multiplier) {
        float newMultiplier = earningMultipliersByType.get(buildingType) * multiplier;
        earningMultipliersByType.put(buildingType, newMultiplier);

        return newMultiplier;
    }
}
