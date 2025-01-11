package com.vikingz.unitycoon.events;

import com.badlogic.gdx.utils.Null;
import com.vikingz.unitycoon.events.eventfiles.*;
import com.vikingz.unitycoon.global.GameGlobals;

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

        //Prevents the game from crashing due to events trying to interact with none existent buildings
        if (GameGlobals.BUILDINGS_MAP.getPlacedBuildings().isEmpty()) {
            int randomChoice = random.nextInt(7);
            e = switch (randomChoice) {
                case 0 -> new AlumniEvent();
                case 1 -> new AwardEvent();
                case 2 -> new BusChangeEvent();
                case 3 -> new FeeIncreaseEvent();
                case 4 -> new RosesEvent();
                case 5 -> new SponsorEvent();
                default -> new StrikesEvent();
            };
        }
        else {
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
        }

        //Generates the correct format depending on if the event has two options or no choice
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
    public EventPopup setEvent(String eventName){

        Event e;

        e = switch (eventName) {
            case "StrikesEvent" -> new StrikesEvent();
            case "StrikesResolvedEvent" -> new StrikesResolvedEvent();
            case "RosesWinEvent" -> new RosesWinEvent();
            case "RosesLoseEvent" -> new RosesLoseEvent();
            default -> null;
        };

        if (e == null) {
            throw new NullPointerException("The event specified does not exist.");
        }

        if (e.noChoice) {
            return new EventPopup(e.skin, e.message, e.leftRun);
        } else {
            return new EventPopup(e.skin, e.message, e.leftRun, e.leftText, e.rightRun, e.rightText);
        }
    }

    /**
     * Extends the queue of events to be run, given a specified time to execute the event
     * @param time the time that the event should fire
     * @param event the event that should be fired compiled into a runnable
     */
    public void extendEventQueue(int time, Runnable event) {

        eventQueue.put(time, event);
    }

    /**
     * Removes an event from the queue after it is executed at a specific time
     * @param time the time that the event was executed
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

    public void resetPositiveEvent() {
        positiveEvent = 0;
    }

    public void resetNegativeEvent() {
        negativeEvent = 0;
    }
}
