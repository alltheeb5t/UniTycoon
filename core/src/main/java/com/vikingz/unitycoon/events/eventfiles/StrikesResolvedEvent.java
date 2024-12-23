package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.building.BuildingStats;
import com.vikingz.unitycoon.global.GameGlobals;

public class StrikesResolvedEvent extends Event {

    public StrikesResolvedEvent() {

        setMessage("The strikes seem to have cleared themselves up!\n\nYou can now build again!");

        setLeftRun(() -> GameGlobals.currentlyBuilding = true);
    }
}
