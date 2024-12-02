package com.vikingz.unitycoon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.vikingz.unitycoon.menus.AchievementsMenu;
import com.vikingz.unitycoon.menus.UsernameMenu;

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
        TextButton achievementsButton = new TextButton("Achievements",skin);
        TextButton settingsButton = new TextButton("Settings", skin);
        TextButton quitButton = new TextButton("Quit", skin);

        AchievementsMenu achievementsMenu = new AchievementsMenu(skin);
        
        // Add listeners to buttons
        playButton.addListener(e -> {
            if (!playButton.isPressed()) return false;
            ScreenMultiplexer.switchScreens(ScreenMultiplexer.Screens.MAPSELECTION);
            return true;
        });

        achievementsButton.addListener(e -> {
            if (!achievementsButton.isPressed()) return false;
                achievementsMenu.update();
                achievementsMenu.setPosition((stage.getWidth() - achievementsMenu.getWidth()) / 2, (stage.getHeight() - achievementsMenu.getHeight()) / 2);
                stage.addActor(achievementsMenu);
            return true;
        });

        settingsButton.addListener(e -> {
            if (!settingsButton.isPressed()) return false;
            ScreenMultiplexer.switchScreens(ScreenMultiplexer.Screens.SETTINGS);
            return true;
        });

        quitButton.addListener(e -> {
            if (!quitButton.isPressed()) return false;
            Gdx.app.exit(); // Quit the application
            return true;
        });

        // Create a table for layout
        Table table = new Table();
        table.setFillParent(true);  // Center table on stage
        table.center();

        Image texture = new Image(new Texture(Gdx.files.internal("gameLogo.png")));
        table.add(texture).pad(50);
        table.row();

        // Add buttons to table
        table.add(playButton).width(425).pad(10);
        table.row();
        table.add(achievementsButton).width(425).pad(10);
        table.row();
        table.add(settingsButton).width(425).pad(10);
        table.row();
        table.add(quitButton).width(425).pad(10);

        // Add the table to the stage
        stage.addActor(table);

        // Opens a username screen if it hasn't already been entered
        if (UsernameMenu.getUsername() == "") {
            UsernameMenu usernamePopUp = new UsernameMenu(skin);
            usernamePopUp.setPosition((stage.getWidth() - usernamePopUp.getWidth()) / 2, (stage.getHeight() - usernamePopUp.getHeight()) / 2);
            usernamePopUp.setupButton();
            stage.addActor(usernamePopUp);
        }
    }

    @Override
    public void show() {
        // Called when this screen becomes the current screen for the game.
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(25/255f, 25/255f, 25/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
