package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.global.GameGlobals;

public class TestEvent2 extends SuperEvent {

    /**
     * Test event with one close button
     * @param gameScreen Game screen
     */
    public TestEvent2() {

        super.message = "This is a test2 message\nYou lost 200000 money";

        super.leftRun = new Runnable() {
            @Override
            public void run() {
                System.out.println("Close button pressed");
                if (GameGlobals.BALANCE >= 200000) {
                    GameGlobals.BALANCE -= 200000;
                } else {
                    GameGlobals.BALANCE = 0;
                }
            }
        };

    }
}
