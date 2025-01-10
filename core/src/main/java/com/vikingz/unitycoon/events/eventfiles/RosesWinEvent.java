package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.building.BuildingStats;
import com.vikingz.unitycoon.global.GameGlobals;

public class RosesWinEvent extends Event {

    /**
     * The aftermath of the Roses event where the player wins.
     * Income from recreational buildings is reset, and a satisfaction bonus is applied.
     */
    public RosesWinEvent() {

        setMessage("Your University has won Roses!\n\nPeople are very happy!");

        setLeftRun(() -> {
            GameGlobals.MONEY.applyMultiplierToType(BuildingStats.BuildingType.RECREATIONAL, (float) 1/2);
            GameGlobals.SATISFACTION.addBonus(20);
        });
    }
}
