package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.global.GameGlobals;
import com.vikingz.unitycoon.screens.GameScreen;

public class TestEvent extends SuperEvent {

    /**
     * Test event with 2 button
     * @param gameScreen Game screen
     */
    public TestEvent(GameScreen gameScreen) {

        super(gameScreen);

        super.message = "This is a test message\nLeft: add 100000 students\nRight: add 100000 money";

        super.leftRun = new Runnable() {
            @Override
            public void run() {
                System.out.println("Left button pressed");
                GameGlobals.STUDENTS += 100000;
                gameScreen.setPaused(false);
            }
        };
        super.leftText = "Left";

        super.rightRun = new Runnable() {
            @Override
            public void run() {
                System.out.println("Right button pressed");
                GameGlobals.BALANCE += 100000;
                gameScreen.setPaused(false);
            }
        };
        super.rightText = "Right";

        noChoice = false;

    }
}
