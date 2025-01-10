package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.building.BuildingStats;
import com.vikingz.unitycoon.global.GameGlobals;

public class SponsorEvent extends Event {

    /**
     * A positive event where the next building the player chooses to place is free.
     */
    public SponsorEvent() {

        setMessage("You have received a sponsor!\n\nThe next building you place is free!");

        setLeftRun(() -> BuildingStats.nextBuildingFree = true);

        GameGlobals.EVENT.incrementPositiveEvent();
    }
}
