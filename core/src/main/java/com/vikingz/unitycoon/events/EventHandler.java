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

    Map<Integer, Runnable> eventQueue;

    int positiveEvent;

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

        int randomChoice = random.nextInt(8);
        e = switch (randomChoice) {
            case 0 -> new StrikesEvent();
            case 1 -> new AwardEvent();
            case 2 -> new AlumniEvent();
            case 3 -> new BusChangeEvent();
            case 4 -> new FeeIncreaseEvent();
            case 5 -> new FireEvent();
            case 6 -> new FloodEvent();
            default -> new SponsorEvent();
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
            case "FireEvent" -> new FireEvent();
            case "AwardEvent" -> new AwardEvent();
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
}
