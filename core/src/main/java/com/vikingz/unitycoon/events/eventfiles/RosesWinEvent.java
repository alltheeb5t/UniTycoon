package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.building.BuildingStats;
import com.vikingz.unitycoon.global.GameGlobals;

public class RosesWinEvent extends Event {

    public RosesWinEvent() {

        setMessage("Your University has won Roses!\n\nPeople are very happy!");

        setLeftRun(() -> {
            BuildingStats.setTypeIncomes(BuildingStats.BuildingType.RECREATIONAL, ((float) 1/2));
            GameGlobals.SATISFACTION.addBonus(20);
        });
    }
}
