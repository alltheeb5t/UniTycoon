package com.vikingz.unitycoon.util;

import java.util.List;

import com.vikingz.unitycoon.building.Building;
import com.vikingz.unitycoon.building.EarnSchedule;

public class MoneyHandler {
    private int balance;

    // Determines if the user has ever been in debt
    private boolean inDebtBefore;

    // Support limiting the amount of debt that player is allowed to accumulate
    public static final int MAX_DEBT = Integer.MAX_VALUE;

    // Future support for variable starting balance
    public static final int STARTING_BALANCE = 1000;

    /**
     * Constructor called at the start of the game and on reset
     */
    public MoneyHandler() {
        balance = STARTING_BALANCE;
        inDebtBefore = false;
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
            if (balance >= 0) {
                balance += building.calculateProfitMade(earnSchedule);
            }
            else {
                balance += 0.5 * building.calculateProfitMade(earnSchedule);
            }
        }
    }

    /**
     * Get the current total cash balance of the user. May be negative if in debt.
     * @return
     */
    public float getBalance() {
        return balance;
    }

    public boolean getInDebtBefore() {
        return inDebtBefore;
    }

    public void setInDebtBefore(boolean inDebtBefore) {
        this.inDebtBefore = inDebtBefore;
    }
}
