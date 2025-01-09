package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.building.BuildingStats;
import com.vikingz.unitycoon.global.GameGlobals;

public class RosesLoseEvent extends Event {

    public RosesLoseEvent() {

        setMessage("Your University has lost Roses!\n\nPeople are upset.\nMaybe next time...");

        setLeftRun(() -> {
            BuildingStats.setTypeIncomes(BuildingStats.BuildingType.RECREATIONAL, (float) 1/2);
            GameGlobals.SATISFACTION.applyPenalty(10);
        });
    }
}
