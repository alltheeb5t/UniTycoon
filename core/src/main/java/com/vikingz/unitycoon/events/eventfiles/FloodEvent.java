package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This is a new class which creates a flood event object.
 * It was implemented to complete UR_EVENTS.
 */
public class FloodEvent extends Event {

    public FloodEvent() {

        setMessage("Your University has flooded!\n\nOne of your buildings has been destroyed.");

        setLeftRun(() -> buildingsMap.attemptBuildingDelete(buildingsMap.chooseRandomBuilding()));

        GameGlobals.EVENT.incrementNegativeEvent();
    }
}
