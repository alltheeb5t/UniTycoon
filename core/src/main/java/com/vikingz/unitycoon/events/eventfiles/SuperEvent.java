package com.vikingz.unitycoon.events.eventfiles;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.vikingz.unitycoon.global.GameSkins;
import com.vikingz.unitycoon.screens.GameScreen;

public abstract class SuperEvent {

    public Skin skin;
    public String message;

    public Runnable leftRun;
    public String leftText;

    public Runnable rightRun;
    public String rightText;

    public boolean noChoice;

    public GameScreen gameScreen;

    public SuperEvent(GameScreen gameScreen) {

        this.gameScreen = gameScreen;

        GameSkins skinLoader = new GameSkins();
        skin = skinLoader.getDefaultSkin();

        message = "";

        leftRun = new Runnable() {
            @Override
            public void run() {
                gameScreen.setPaused(false);
            }
        };
        leftText = "";

        rightRun = new Runnable() {
            @Override
            public void run() {
                gameScreen.setPaused(false);
            }
        };
        rightText = "";

        noChoice = true;
    }
}
