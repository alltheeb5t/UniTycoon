package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.global.GameGlobals;

public class TestEvent extends Event {

    /**
     * Test event with 2 button
     */
    public TestEvent() {

        super.message = "This is a test message\nLeft: add 100000 students\nRight: add 100000 money";

        super.leftRun = new Runnable() {
            @Override
            public void run() {
                System.out.println("Left button pressed");
                GameGlobals.STUDENTS += 100000;
            }
        };
        super.leftText = "Left";

        super.rightRun = new Runnable() {
            @Override
            public void run() {
                System.out.println("Right button pressed");
                GameGlobals.MONEY.deposit(10000);
            }
        };
        super.rightText = "Right";

        noChoice = false;

    }
}
