package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This is a new class which creates an award event object.
 * It was implemented to complete UR_EVENTS.
 */
public class AwardEvent extends Event {

    public AwardEvent() {

        setMessage("Your University has won a prestigious award!");

        GameGlobals.EVENT.incrementPositiveEvent();
    }
}
