package com.vikingz.unitycoon.events.eventfiles;

import com.vikingz.unitycoon.global.GameGlobals;

public class AwardEvent extends Event {

    public AwardEvent() {

        setMessage("Your University has won a prestigious award!");

        GameGlobals.EVENT.incrementPositiveEvent();
    }
}
