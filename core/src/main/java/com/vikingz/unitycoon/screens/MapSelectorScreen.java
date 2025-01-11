package com.vikingz.unitycoon.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This class represents the screen where the user chooses the map they want to play.
 *
 * This class contains a bunch of buttons that allow the user to cycle through the different maps available 
 * as well as go back to the main menu or play game.
 *
 * Inherits Screen, SuperScreen
 * 
 * This class has been refactored slightly to make the code more readable and improve UI.
 */
public class MapSelectorScreen extends SuperScreen implements Screen {

    //Text that shows which map is currently selected
    final TextField mapText;

    //List of map Image that are used to set the preview image
    final TextureRegionDrawable[] mapArray;

    //Current map Selected
    private int mapSelection = 1;

    /**
     * Creates a new Map selector screen
     */
    public MapSelectorScreen() {
        super();

        //Loads maps from global config
        mapArray = GameGlobals.mapArray;

        TextButton goBack = new TextButton("Go Back",skin);
        TextButton startGame = new TextButton("Start Game",skin);
        TextButton nextMap = new TextButton("->",skin);
        TextButton previousMap = new TextButton("<-",skin);

        //Go back Button
        goBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenMultiplexer.switchScreens(ScreenMultiplexer.Screens.MENU);
            };
        });


        Image mapImage = new Image(GameGlobals.map1Texture);

        //Selects the next map
        nextMap.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mapSelection++;
                if (mapSelection > 3) {
                    mapSelection = 1;
                }
                mapImage.setDrawable(mapArray[mapSelection-1]);
                return true;
            }
        });


        //Selects the previous map
        previousMap.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mapSelection--;
                if (mapSelection < 1){
                    mapSelection = mapArray.length;
                }
                mapImage.setDrawable(mapArray[mapSelection-1]);
                return true;
            }
        });


        mapText = new TextField("map".concat(Integer.toString(mapSelection)),skin);

        //Starts the game
        startGame.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenMultiplexer.runGame(mapText.getText().toLowerCase());
            };
        });

        // Create table for layout
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        table.add((Actor) null);
        table.add(mapImage).width(300).height(350).pad(10);

        table.row();

        table.add(previousMap).pad(10);
        table.add(mapText).pad(10);
        table.add(nextMap).pad(10);
        table.row();
        table.row();

        table.add(goBack).pad(10);
        table.add((Actor) null);
        table.add(startGame).pad(10);

        // Add the table to the stage
        stage.addActor(table);
    }

    @Override
    public void show() {}

    /**
     * Renders the stage to the screen
     * @param delta Time since last frame
     */
    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(25/255f, 25/255f, 25/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapText.setText("Map".concat(Integer.toString(mapSelection)));

        stage.act(delta);
        stage.draw();
    }


    /**
     * Updates the viewport when the window is resized
     * @param width New width
     * @param height New height
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    /**
     * Disposes MapScreen for garbage collection.
     */
    @Override
    public void dispose() {
        // Dispose of resources
        stage.dispose();
        skin.dispose();
    }
}
