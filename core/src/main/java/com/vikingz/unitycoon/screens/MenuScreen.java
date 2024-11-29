package com.vikingz.unitycoon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.vikingz.unitycoon.events.eventfiles.SuperEvent;
import com.vikingz.unitycoon.events.eventfiles.TestEvent;

/**
 * This class represents the main menu of the game.
 *
 * The main menu is where the user begins from. This menu
 * contains multiple buttons that allow the user to begin the game.
 *
 * Inherits Screen, SuperScreen
 */
public class MenuScreen extends SuperScreen implements Screen {


    /**
     * Creates a new menu screen
     */
    public MenuScreen() {
        Gdx.input.setInputProcessor(stage);

        // Load a default skin

        // Create buttons
        TextButton playButton = new TextButton("Play", skin);
        TextButton settingsButton = new TextButton("Settings", skin);
        TextButton quitButton = new TextButton("Quit", skin);

        // Add listeners to buttons
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //SuperEvent myEvent = new TestEvent();
                //myEvent.leftRun();
                ScreenMultiplexer.switchScreens(ScreenMultiplexer.Screens.MAPSELECTION);
            };
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenMultiplexer.switchScreens(ScreenMultiplexer.Screens.SETTINGS);
            };
        });

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit(); // Quit the application
            };
        });

        // Create a table for layout
        Table table = new Table();
        table.setFillParent(true);  // Center table on stage
        table.center();

        Image texture = new Image(new Texture(Gdx.files.internal("gameLogo.png")));
        table.add(texture).pad(50);
        table.row();

        // Add buttons to table
        table.add(playButton).pad(10);
        table.row();
        table.add(settingsButton).pad(10);
        table.row();
        table.add(quitButton).pad(10);

        // Add the table to the stage
        stage.addActor(table);
    }

    @Override
    public void show() {
        // Called when this screen becomes the current screen for the game.
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(25/255f, 25/255f, 25/255f, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        // Draw the stage
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Update the stage's viewport when the screen size changes
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() {
        // This removes the bug where the user can still click the buttons from the game screen.
    }

    /**
     * disposes MenuScreen for garbage collection
     */
    @Override
    public void dispose() {
        // Dispose of assets when this screen is no longer used
        stage.dispose();
        skin.dispose();
    }
}
