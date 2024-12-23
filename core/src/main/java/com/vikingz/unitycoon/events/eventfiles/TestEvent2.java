package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.global.GameGlobals;

public class TestEvent2 extends Event {

    /**
     * Test event with one close button
     */
    public TestEvent2() {

        super.message = "This is a test2 message\nYou lost 200000 money";

        super.leftRun = new Runnable() {
            @Override
            public void run() {
                System.out.println("Close button pressed");
                if (GameGlobals.MONEY.getBalance() >= 200000) {
                    GameGlobals.MONEY.withdraw(200000);
                } else {
                    GameGlobals.MONEY.withdraw(GameGlobals.MONEY.getBalance());
                }
            }
        };

    }
}
