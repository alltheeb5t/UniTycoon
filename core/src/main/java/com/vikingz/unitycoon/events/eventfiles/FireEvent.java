package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.building.Building;
import com.vikingz.unitycoon.global.GameGlobals;

public class FireEvent extends Event {

    public FireEvent() {

        Building burningBuilding = buildingsMap.chooseRandomBuilding();
        int eventTime = GameGlobals.ELAPSED_TIME - 2;
        burningBuilding.setOnFire(true);

        setMessage("A building on campus is on fire!\n\nClick on it to put it out, or leave it for the insurance money!");

        Runnable eventRun = () -> {
            if (burningBuilding.getOnFire()) {
                buildingsMap.attemptBuildingDelete(burningBuilding);
            }
        };

        GameGlobals.EVENT.extendEventQueue(eventTime, eventRun);
    }
}
