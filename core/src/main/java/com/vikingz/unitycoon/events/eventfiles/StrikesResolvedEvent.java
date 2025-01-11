package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This is a new class which creates a strikes resolved event object.
 * It was implemented to complete UR_EVENTS.
 */
public class StrikesResolvedEvent extends Event {

    public StrikesResolvedEvent() {

        setMessage("The strikes seem to have cleared themselves up!\n\nYou can now build again!");

        setLeftRun(() -> GameGlobals.buildingAllowed = true);
    }
}
