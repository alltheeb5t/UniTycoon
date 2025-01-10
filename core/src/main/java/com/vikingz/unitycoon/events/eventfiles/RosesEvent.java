package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.building.BuildingStats;
import com.vikingz.unitycoon.global.GameGlobals;

import java.util.Random;

/**
 * This is a new class which creates a roses event object.
 * It was implemented to complete UR_EVENTS.
 */
public class RosesEvent extends Event {

    public RosesEvent() {

        setMessage("Roses is about to start!\n\nSports teams eagerly await the event, and would like "
            + "facilities to train in.\n\nBuilding these facilities will provide additional\nbenefits, "
            + "including an increased chance to win!");

        GameGlobals.EVENT.incrementPositiveEvent();

        setLeftRun(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                BuildingStats.setTypeIncomes(BuildingStats.BuildingType.RECREATIONAL, 2F);
                int eventTime = ((GameGlobals.TIME_REMAINING / 100) * 100) + 1;
                // Prevents events happening at the same time
                for (int time : GameGlobals.EVENT.getEventTimes()) {
                    if (time == eventTime) {
                        eventTime++;
                    }
                }
                Runnable eventRun = () -> {
                    if (GameGlobals.RECREATIONAL_BUILDINGS_COUNT < 6) {
                        int chance = random.nextInt(3);
                        if (chance == 0) {
                            gameScreen.event("RosesWinEvent");
                        } else {
                            gameScreen.event("RosesLoseEvent");
                        }
                    } else {
                        int chance = random.nextInt(2);
                        if (chance == 0) {
                            gameScreen.event("RosesLoseEvent");
                        } else {
                            gameScreen.event("RosesWinEvent");
                        }
                    }
                };
                GameGlobals.EVENT.extendEventQueue(eventTime, eventRun);
            }
        });
    }
}
