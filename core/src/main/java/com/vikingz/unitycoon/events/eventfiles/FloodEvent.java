package com.vikingz.unitycoon.events.eventfiles;

public class FloodEvent extends Event {

    public FloodEvent() {

        setMessage("Your University has flooded!\n\nOne of your buildings has been destroyed.");

        setLeftRun(() -> buildingsMap.attemptBuildingDelete(buildingsMap.chooseRandomBuilding()));
    }
}
