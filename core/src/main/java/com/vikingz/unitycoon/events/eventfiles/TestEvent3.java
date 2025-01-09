package com.vikingz.unitycoon.events.eventfiles;

public class TestEvent3 extends Event {

    /** This event shouldn't ever fire and is only here for debug reasons
     */
    public TestEvent3() {

        super.message = "This shouldn't be seen";
    }
}
