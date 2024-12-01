package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.global.GameGlobals;
import com.vikingz.unitycoon.screens.GameScreen;

public class TestEvent2 extends SuperEvent {

    public TestEvent2(GameScreen gameScreen) {

        super(gameScreen);

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
                gameScreen.setPaused(false);
            }
        };

    }
}
