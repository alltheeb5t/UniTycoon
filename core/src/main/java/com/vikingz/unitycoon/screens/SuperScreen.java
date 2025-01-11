package com.vikingz.unitycoon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.vikingz.unitycoon.global.GameSkins;

/**
 * This is an abstract class that contains all the components that all other screens use, and therefore 
 * by creating them in here we de-clutter all the other screen classes.
 * 
 * This class has been refactored slightly to make the code more readable, but it is largely unchanged.
 */
public abstract class SuperScreen {

    public Stage stage;

    public SpriteBatch batch;
    public Skin skin;

    /**
     * Defines a Super screen constructor that other buildings that inherit from this abstract class 
     * use as a base case.
     */
    public SuperScreen(){
        stage = new Stage(new ScreenViewport());

        batch = new SpriteBatch();
        GameSkins skinLoader = new GameSkins();
        skin = skinLoader.getDefaultSkin();

    }

    /**
     * Sets the input processor to this screen
     */
    public void takeInput(){
        Gdx.input.setInputProcessor(stage);
    }
}
