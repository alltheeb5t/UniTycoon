package com.vikingz.unitycoon.events.eventfiles;

public class TestEvent3 extends SuperEvent {

    //This event shouldn't ever fire and is only here for debug reasons
    public TestEvent3() {

        super.message = "This shouldn't be seen";

        super.leftRun = new Runnable() {
            @Override
            public void run() {
                System.out.println("Left button pressed");
            }
        };
        super.leftText = "Left";

        super.rightRun = new Runnable() {
            @Override
            public void run() {
                System.out.println("Right button pressed");
            }
        };
        super.rightText = "Right";

    }
}
