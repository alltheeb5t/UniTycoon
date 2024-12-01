package com.vikingz.unitycoon.events.eventfiles;

public class TestEvent extends SuperEvent {

    public TestEvent() {

        super.message = "This is a test message";

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
