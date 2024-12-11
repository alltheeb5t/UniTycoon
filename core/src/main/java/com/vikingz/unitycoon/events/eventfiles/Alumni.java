package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.global.GameGlobals;

public class Alumni extends SuperEvent {

    public Alumni() {

        super.message = "Alumni have decided to donate to the university!\n\nYou have received 1,000,000!";

        super.leftRun = new Runnable() {
            @Override
            public void run() {
                GameGlobals.MONEY.deposit(1000000);
            }
        };
    }
}
