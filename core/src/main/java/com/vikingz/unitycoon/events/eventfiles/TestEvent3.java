package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.screens.GameScreen;

public class TestEvent3 extends SuperEvent {

    /** This event shouldn't ever fire and is only here for debug reasons
     * @param gameScreen Game screen
     */
    public TestEvent3() {

        super.message = "This shouldn't be seen";
    }
}
