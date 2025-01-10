package com.vikingz.unitycoon.events.eventfiles;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.vikingz.unitycoon.building.BuildingsMap;
import com.vikingz.unitycoon.global.GameGlobals;
import com.vikingz.unitycoon.global.GameSkins;
import com.vikingz.unitycoon.screens.GameScreen;
import com.vikingz.unitycoon.screens.ScreenMultiplexer;

/**
 * This is an abstract class that contains all the components
 * that all other events use, and therefore by creating them in here
 * we de-clutter all the other event classes.
 */
public abstract class Event {

    public GameScreen gameScreen;
    public BuildingsMap buildingsMap;

    public Skin skin;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setLeftRun(Runnable leftRun) {
        this.leftRun = leftRun;
    }

    public void setLeftText(String leftText) {
        this.leftText = leftText;
    }

    public void setRightRun(Runnable rightRun) {
        this.rightRun = rightRun;
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
    }

    public void setNoChoice(boolean noChoice) {
        this.noChoice = noChoice;
    }

    public String message;

    public Runnable leftRun;
    public String leftText;

    public Runnable rightRun;
    public String rightText;

    public boolean noChoice;

    /**
     * Defines the SuperEvent constructor, which other events use for the base cases.
     */
    public Event() {

        gameScreen = ScreenMultiplexer.gameScreen;
        buildingsMap = GameGlobals.BUILDINGS_MAP;

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
