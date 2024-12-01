package com.vikingz.unitycoon.events;

import com.vikingz.unitycoon.events.eventfiles.*;

import java.util.Random;

public class EventManager {

    /**
     * Chooses a random event to run
     */
    public EventPopup randomEvent() {

        Random random = new Random();
        SuperEvent e;

        int randomChoice = random.nextInt(2);
        e = switch (randomChoice) {
            case 0 -> new TestEvent();
            case 1 -> new TestEvent2();
            default -> new TestEvent3();
        };

        if (e.noChoice) {
            return new EventPopup(e.skin, e.message);
        } else {
            return new EventPopup(e.skin, e.message, e.leftRun, e.leftText, e.rightRun, e.rightText);
        }
    }
}
