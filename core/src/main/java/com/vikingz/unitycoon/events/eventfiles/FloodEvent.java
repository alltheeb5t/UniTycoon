package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This is a new class which creates a flood event object.
 * It was implemented to complete UR_EVENTS.
 */
public class FloodEvent extends Event {

    /**
     * A negative event where a building on campus floods, breaking it.
     */
    public FloodEvent() {

        if (!buildingsMap.getPlacedBuildings().isEmpty()) {
            setMessage("Your University has flooded!\n\nOne of your buildings has been destroyed. Fortunately, you had insurance.");

            setLeftRun(() -> buildingsMap.attemptBuildingDelete(buildingsMap.chooseRandomBuilding()));

        } else {
            setMessage("Your campus has flooded!\n\nThe grass will recover don't worry.");
        }

        GameGlobals.EVENT.incrementNegativeEvent();
    }
}
