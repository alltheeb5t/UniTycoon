package com.vikingz.unitycoon.events;

import com.vikingz.unitycoon.events.eventfiles.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

        eventTimes[0] = random.nextInt(201, 285);
        //eventTimes[0] = 285;
        eventTimes[1] = random.nextInt(101, 199);
        eventTimes[2] = random.nextInt(15, 99);

        System.out.println(Arrays.toString(eventTimes));
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
            default -> new TestEvent();
        };

        if (e.noChoice) {
            return new EventPopup(e.skin, e.message, e.leftRun);
        } else {
            return new EventPopup(e.skin, e.message, e.leftRun, e.leftText, e.rightRun, e.rightText);
        }
    }

    public void extendEventQueue(int time, Runnable event) {

        eventQueue.put(time, event);
    }

    public void reduceEventQueue(int time) {

        eventQueue.remove(time);
    }

    public void incrementPositiveEvent() {
        positiveEvent++;
    }

    public void incrementNegativeEvent() {
        negativeEvent++;
    }

    public int getPositiveEvents() {
        return positiveEvent;
    }

    public int getNegativeEvents() {
        return negativeEvent;
    }
}
