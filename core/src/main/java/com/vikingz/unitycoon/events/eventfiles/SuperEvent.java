package com.vikingz.unitycoon.events.eventfiles;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.vikingz.unitycoon.global.GameSkins;

/**
 * This is an abstract class that contains all the components
 * that all other events use, and therefore by creating them in here
 * we de-clutter all the other event classes.
 */
public abstract class SuperEvent {

    public Skin skin;
    public String message;

    public Runnable leftRun;
    public String leftText;

    public Runnable rightRun;
    public String rightText;

    public boolean noChoice;


    /**
     * Defines the SuperEvent constructor, which other events use for the base cases.
     * @param gameScreen Game screen
     */
    public SuperEvent() {

        GameSkins skinLoader = new GameSkins();
        skin = skinLoader.getDefaultSkin();

        message = "";

        leftRun = new Runnable() {
            @Override
            public void run() {
            }
        };
        leftText = "";

        rightRun = new Runnable() {
            @Override
            public void run() {
            }
        };
        rightText = "";

        noChoice = true;
    }
}
