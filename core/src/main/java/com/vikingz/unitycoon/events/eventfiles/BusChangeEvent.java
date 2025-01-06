package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.global.GameGlobals;

public class BusChangeEvent extends Event {

    public BusChangeEvent() {

        setMessage("The bus routes have changed!\n\nPeople are confused, and are pretty upset...");

        setLeftRun(() -> GameGlobals.SATISFACTION.applyPenalty(10));

        GameGlobals.EVENT.incrementNegativeEvent();
    }
}
