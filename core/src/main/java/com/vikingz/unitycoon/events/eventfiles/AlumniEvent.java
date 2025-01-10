package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This is a new class which creates an alumni event object.
 * It was implemented to complete UR_EVENTS.
 */
public class AlumniEvent extends Event {

    public AlumniEvent() {

        setMessage("Alumni have decided to donate to the university!\n\nYou have received 1,000k!");

        setLeftRun(() -> GameGlobals.MONEY.deposit(1000));

        GameGlobals.EVENT.incrementPositiveEvent();
    }
}
