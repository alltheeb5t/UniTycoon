package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.global.GameGlobals;

public class FloodEvent extends Event {

    public FloodEvent() {

        if (buildingsMap.getPlacedBuildings().size() > 0) {
            setMessage("Your University has flooded!\n\nOne of your buildings has been destroyed. Fortunately, you had insurance.");

            setLeftRun(() -> buildingsMap.attemptBuildingDelete(buildingsMap.chooseRandomBuilding()));

        } else {
            setMessage("Your campus has flooded!\n\nThe grass will recover don't worry.");
        }
        
        GameGlobals.EVENT.incrementNegativeEvent();
    }
}
