package com.vikingz.unitycoon.events;

import com.vikingz.unitycoon.events.eventfiles.*;
import com.vikingz.unitycoon.screens.GameScreen;

import java.util.Random;

public class EventManager {

    GameScreen gameScreen;

    public EventManager(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }
    /**
     * Chooses a random event to run
     */
    public EventPopup randomEvent() {

        Random random = new Random();
        SuperEvent e;

        int randomChoice = random.nextInt(2);
        e = switch (randomChoice) {
            case 0 -> new TestEvent(gameScreen);
            case 1 -> new TestEvent2(gameScreen);
            default -> new TestEvent3(gameScreen);
        };

        if (e.noChoice) {
            return new EventPopup(e.skin, e.message, e.leftRun);
        } else {
            return new EventPopup(e.skin, e.message, e.leftRun, e.leftText, e.rightRun, e.rightText);
        }
    }
}
