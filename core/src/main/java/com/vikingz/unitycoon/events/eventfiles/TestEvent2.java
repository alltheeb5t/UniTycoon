package com.vikingz.unitycoon.events.eventfiles;

public class TestEvent2 extends SuperEvent {

    public TestEvent2() {

        super.message = "This is a test2 message";

        super.leftRun = new Runnable() {
            @Override
            public void run() {
                System.out.println("Left2 button pressed");
            }
        };
        super.leftText = "Left2";

        super.rightRun = new Runnable() {
            @Override
            public void run() {
                System.out.println("Right2 button pressed");
            }
        };
        super.rightText = "Right2";

    }
}
