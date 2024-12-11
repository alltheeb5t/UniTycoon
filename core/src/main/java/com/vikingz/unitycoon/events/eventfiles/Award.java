package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.global.GameGlobals;

public class Award extends SuperEvent {

    public Award() {

        super.message = "Your University has won a prestigious award!";

        super.leftRun = new Runnable() {
            @Override
            public void run() {
            }
        };
    }
}
