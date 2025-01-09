package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.building.BuildingStats;
import com.vikingz.unitycoon.global.GameGlobals;

import java.util.Random;

public class StrikesEvent extends Event{

    public StrikesEvent() {

        GameGlobals.currentlyBuilding = false;

        setMessage("Your staff have gone on strike over pay disputes!\n\nYou can either increase their pay and stop the strikes\nor hope it goes away on its own.\n\nWhile strikes are ongoing, you cannot\nbuild any more buildings and your\nsatisfaction will fall.");

        setLeftText("Ignore");

        setLeftRun(() -> {
            Random random = new Random();
            GameGlobals.SATISFACTION.applyPenalty(5);
            int eventTime = GameGlobals.TIME_REMAINING - 10;
            // Prevents events happening at the same time
            for (int time : GameGlobals.EVENT.getEventTimes()) {
                if (time == eventTime) {
                    eventTime++;
                }
            }
            Runnable eventRun = () -> {
                int chance = random.nextInt(3);
                switch(chance) {
                    case 0:
                        gameScreen.event("StrikesResolvedEvent");
                        break;
                    default:
                        gameScreen.event("StrikesEvent");
                }
            };
            GameGlobals.EVENT.extendEventQueue(eventTime, eventRun);
        });

        setRightText("Pay More");

        setRightRun(() -> {
            GameGlobals.SATISFACTION.applyPenalty(5);
            BuildingStats.setTypeIncomes(BuildingStats.BuildingType.ACADEMIC, 1.5F);
            BuildingStats.setTypeIncomes(BuildingStats.BuildingType.RECREATIONAL, 0.75F);
            GameGlobals.currentlyBuilding = true;
        });

        GameGlobals.EVENT.incrementNegativeEvent();

        setNoChoice(false);
    }
}
