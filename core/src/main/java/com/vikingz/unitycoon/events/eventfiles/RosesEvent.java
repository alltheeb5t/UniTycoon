package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.building.BuildingStats;
import com.vikingz.unitycoon.global.GameGlobals;

import java.util.Random;

public class RosesEvent extends Event {

    public RosesEvent() {

        setMessage("Roses is about to start!\n\nSports teams eagerly await the event, and would like facilities to train in.\n\nBuilding these facilities will provide additional\nbenefits, including an increased chance to win!");

        setLeftRun(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                BuildingStats.setTypeIncomes(BuildingStats.BuildingType.RECREATIONAL, 2F);
                int eventTime = ((GameGlobals.ELAPSED_TIME / 100) * 100) + 1;
                // Prevents events happening at the same time
                for (int time : GameGlobals.EVENT.getEventTimes()) {
                    if (time == eventTime) {
                        eventTime++;
                    }
                }
                System.out.println(eventTime);
                Runnable eventRun = () -> {
                    System.out.println("FUCK IM WORKING WOOOO");
                    if (GameGlobals.RECREATIONAL_BUILDINGS_COUNT < 6) {
                        int chance = random.nextInt(3);
                        switch (chance) {
                            case 0:
                                gameScreen.event("RosesWinEvent");
                                break;
                            default:
                                gameScreen.event("RosesLoseEvent");
                        }
                    } else {
                        int chance = random.nextInt(2);
                        switch (chance) {
                            case 0:
                                gameScreen.event("RosesLoseEvent");
                                break;
                            default:
                                gameScreen.event("RosesWinEvent");
                        }
                    }
                };
                GameGlobals.EVENT.extendEventQueue(eventTime, eventRun);
            }
        });
    }
}
