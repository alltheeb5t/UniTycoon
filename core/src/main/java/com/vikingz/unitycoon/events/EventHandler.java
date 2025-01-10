package com.vikingz.unitycoon.events;

import com.vikingz.unitycoon.events.eventfiles.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * This new class manages the random events that occur during the game.
 * It was implemented to complete UR_EVENTS, FR_EVENT_RESULT, FR_EVENT_DISPLAY, FR_EVENT_CHOICE.
 */
public class EventHandler {

    public int[] getEventTimes() {
        return eventTimes;
    }

    int[] eventTimes;

    public Map<Integer, Runnable> getEventQueue() {
        return eventQueue;
    }

    //Used to add scheduled events to run at specific times
    Map<Integer, Runnable> eventQueue;

    //Used to track how many positive events have run for achievements
    int positiveEvent;

    //Used to track how many negative events have run for achievements
    int negativeEvent;

    /**
     * Manages the in game events, adds functionality for creating events
     */
    public EventHandler() {
        Random random = new Random();

        eventTimes = new int[3];

        eventQueue = new HashMap<>();

        //Creates 1 event per in game year.
        eventTimes[0] = random.nextInt(201, 285);
        eventTimes[1] = random.nextInt(101, 199);
        eventTimes[2] = random.nextInt(15, 99);
    }

    /**
     * Chooses a random event to run and sends it to the correct constructor
     */
    public EventPopup randomEvent() {

        Event e;
        Random random = new Random();

        int randomChoice = random.nextInt(9);
        e = switch (randomChoice) {
            case 0 -> new AlumniEvent();
            case 1 -> new AwardEvent();
            case 2 -> new BusChangeEvent();
            case 3 -> new FeeIncreaseEvent();
            case 4 -> new FireEvent();
            case 5 -> new FloodEvent();
            case 6 -> new RosesEvent();
            case 7 -> new SponsorEvent();
            default -> new StrikesEvent();
        };

        if (e.noChoice) {
            return new EventPopup(e.skin, e.message, e.leftRun);
        } else {
            return new EventPopup(e.skin, e.message, e.leftRun, e.leftText, e.rightRun, e.rightText);
        }
    }

    /**
     * Used to call specific events, mostly to created event chains
     * @param eventName a string containing the name of the event
     */
    public EventPopup setEvent(String eventName) {

        Event e;

        e = switch (eventName) {
            case "StrikesEvent" -> new StrikesEvent();
            case "StrikesResolvedEvent" -> new StrikesResolvedEvent();
            case "RosesWinEvent" -> new RosesWinEvent();
            case "RosesLoseEvent" -> new RosesLoseEvent();
            default -> throw new IllegalArgumentException("Unexpected value: " + eventName);
        };

        if (e.noChoice) {
            return new EventPopup(e.skin, e.message, e.leftRun);
        } else {
            return new EventPopup(e.skin, e.message, e.leftRun, e.leftText, e.rightRun, e.rightText);
        }
    }

    /**
     * Allows events to run over a duration of time.
     * @param time the time the event should last.
     * @param event the event.
     */
    public void extendEventQueue(int time, Runnable event) {
        eventQueue.put(time, event);
    }

    /**
     * Removes an event from the queue once it has been completed. 
     */ 
    public void reduceEventQueue(int time) {
        eventQueue.remove(time);
    }

    public void incrementPositiveEvent() {
        positiveEvent++;
    }

    public void incrementNegativeEvent() {
        negativeEvent++;
    }

    public int getPositiveEvent() {
        return positiveEvent;
    }

    public int getNegativeEvent() {
        return negativeEvent;
    }
}
