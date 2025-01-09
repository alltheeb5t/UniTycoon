package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.global.GameGlobals;

public class AlumniEvent extends Event {

    public AlumniEvent() {

        setMessage("Alumni have decided to donate to the university!\n\nYou have received 1,000,000!");

        setLeftRun(() -> GameGlobals.MONEY.deposit(1000));

        GameGlobals.EVENT.incrementPositiveEvent();
    }
}
