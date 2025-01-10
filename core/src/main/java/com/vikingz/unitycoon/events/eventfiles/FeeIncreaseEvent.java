package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.building.BuildingStats.BuildingType;
import com.vikingz.unitycoon.global.GameGlobals;

public class FeeIncreaseEvent extends Event{

    public FeeIncreaseEvent() {

        setMessage("Inflation has hit your Uni!\n\nIncome from Accommodation has increased,\nbut people aren't happy...");

        setLeftRun(() -> {
            GameGlobals.MONEY.applyMultiplierToType(BuildingType.ACCOMODATION, 1.25F);
            GameGlobals.SATISFACTION.applyPenalty(10);
        });
    }
}
