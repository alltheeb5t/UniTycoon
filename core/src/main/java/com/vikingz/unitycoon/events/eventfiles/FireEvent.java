package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.building.Building;
import com.vikingz.unitycoon.global.GameGlobals;

public class FireEvent extends Event {

    /**
     * A negative event where a building sets on fire, and the player is required to put it out, otherwise it will break.
     */
    public FireEvent() {

        Building burningBuilding = buildingsMap.chooseRandomBuilding();

        if (burningBuilding != null) { // It is possible that this event may be fired before user has placed any buildings
            int eventTime = GameGlobals.TIME_REMAINING - 2;
            burningBuilding.setOnFire(true);

            setMessage("A building on campus is on fire!\n\nClick on it to put it out, or leave it for the insurance money!");

            Runnable eventRun = () -> {
                if (burningBuilding.getOnFire()) {
                    buildingsMap.attemptBuildingDelete(burningBuilding);
                }
            };

            GameGlobals.EVENT.extendEventQueue(eventTime, eventRun);

        } else {
            setMessage("You escaped!\n\nA fire broke out but there was no buildings to burn.");
        }

        GameGlobals.EVENT.incrementNegativeEvent();

    }
}
