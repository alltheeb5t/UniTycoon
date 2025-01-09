package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.global.GameGlobals;

public class FloodEvent extends Event {

    public FloodEvent() {

        setMessage("Your University has flooded!\n\nOne of your buildings has been destroyed.");

        setLeftRun(() -> buildingsMap.attemptBuildingDelete(buildingsMap.chooseRandomBuilding()));

        GameGlobals.EVENT.incrementNegativeEvent();
    }
}
