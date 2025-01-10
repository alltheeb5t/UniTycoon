package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.building.BuildingStats.BuildingType;
import com.vikingz.unitycoon.global.GameGlobals;

public class RosesLoseEvent extends Event {

    /**
     * The aftermath of the Roses event where the player loses.
     * Income from recreational buildings is reset, and a satisfaction penalty is applied.
     */
    public RosesLoseEvent() {

        setMessage("Your University has lost Roses!\n\nPeople are upset.\nMaybe next time...");

        setLeftRun(() -> {
            GameGlobals.MONEY.applyMultiplierToType(BuildingType.RECREATIONAL, (float) 1/2);
            GameGlobals.SATISFACTION.applyPenalty(10);
        });
    }
}
