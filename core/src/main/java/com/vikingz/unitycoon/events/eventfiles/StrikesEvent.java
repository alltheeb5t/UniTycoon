package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.building.BuildingStats.BuildingType;
import com.vikingz.unitycoon.global.GameGlobals;

import java.util.Random;

public class StrikesEvent extends Event{

    /**
     * A negative event where the staff strike, freezing buildings from building.
     * The player can either pay the staff to end the strikes at the cost of increased building costs, and a slight satisfaction hit,
     * or they may attempt to wait the strikes out, either recalling this event or resolving the strikes.
     */
    public StrikesEvent() {

        GameGlobals.buildingAllowed = false;

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
            GameGlobals.MONEY.applyMultiplierToType(BuildingType.ACADEMIC, 1.5F);
            GameGlobals.MONEY.applyMultiplierToType(BuildingType.RECREATIONAL, 0.75F);
            GameGlobals.buildingAllowed = true;
        });

        GameGlobals.EVENT.incrementNegativeEvent();

        setNoChoice(false);
    }
}
