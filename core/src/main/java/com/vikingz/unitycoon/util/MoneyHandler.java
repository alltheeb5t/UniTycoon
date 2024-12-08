package com.vikingz.unitycoon.util;

public class MoneyHandler {

    /**
     * Remove the specified amount from the current balance
     * @param amount
     * @return boolean: Was withdrawal successful?
     */
    public boolean withdraw(float amount) {
        return true;
    }

    /**
     * Add the specified amount to the current balance (as a one-off)
     * @param amount
     * @return boolean: Was the deposit successful?
     */
    public boolean deposit(float amount) {
        return true;
    }


    /**
     * Called periodically by the timer. Calculates and adds revenue from
     * buildings that provide a continuous revenue stream (e.g. food outlets)
     */
    public void earn() {

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
        return 0;
    }
}
