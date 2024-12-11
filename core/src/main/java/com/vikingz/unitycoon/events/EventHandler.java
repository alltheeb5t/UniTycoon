package com.vikingz.unitycoon.events;

import com.vikingz.unitycoon.events.eventfiles.*;
import com.vikingz.unitycoon.screens.GameScreen;

import java.util.Arrays;
import java.util.Random;

public class EventHandler {

    // Generates random events for the game
    int firstYearEvent;
    int secondYearEvent;
    int thirdYearEvent;

    public int[] getEventTimes() {
        return eventTimes;
    }

    int[] eventTimes;

    /**
     * Manages the in game events, adds functionality for creating events
     */
    public EventHandler() {
        Random random = new Random();

        eventTimes = new int[3];

        eventTimes[0] = random.nextInt(201, 285);
        eventTimes[1] = random.nextInt(101, 199);
        eventTimes[2] = random.nextInt(15, 99);

        System.out.println(Arrays.toString(eventTimes));
    }

    /**
     * Chooses a random event to run and sends it to the correct constructor
     */
    public EventPopup randomEvent() {

        SuperEvent e;
        Random random = new Random();

        int randomChoice = random.nextInt(3);
        e = switch (randomChoice) {
            case 0 -> new Alumni();
            case 1 -> new Award();
            default -> new TestEvent();
        };

        if (e.noChoice) {
            return new EventPopup(e.skin, e.message, e.leftRun);
        } else {
            return new EventPopup(e.skin, e.message, e.leftRun, e.leftText, e.rightRun, e.rightText);
        }
    }
}
