package com.vikingz.unitycoon.events;

import com.vikingz.unitycoon.events.eventfiles.SuperEvent;
import com.vikingz.unitycoon.events.eventfiles.TestEvent;

public class EventManager {

    /**
     * Chooses a random event to run
     */
    public EventPopup randomEvent() {

        SuperEvent e = new TestEvent();

        return new EventPopup(e.skin, e.message, e.leftRun, e.leftText, e.rightRun, e.rightText);
    }
}
