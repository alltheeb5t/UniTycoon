package com.vikingz.unitycoon.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.vikingz.unitycoon.building.Building;
import com.vikingz.unitycoon.building.BuildingStats;
import com.vikingz.unitycoon.building.buildings.FoodBuilding;
import com.vikingz.unitycoon.building.buildings.RecreationalBuilding;
import com.vikingz.unitycoon.global.GameConfigManager;
import com.vikingz.unitycoon.global.GameGlobals;
import com.vikingz.unitycoon.render.GameRenderer;
import com.vikingz.unitycoon.render.UIRenderer;

/**
 * This is the main game class from which the game is run.
 *
 * This game instantiates the 2 renderers which are the GameRenderer
 * and the UIRenderer, as well as contains the game loop that control how the game
 * runs.
 *
 * The game loop contains a section where everything in that section is updated
 * every second which is where all of our game stats are updated.
 *
 * Inherits Screen, SuperScreen
 */
public class GameScreen extends SuperScreen implements Screen {

    //Determines if the game had been loaded from fullScreen
    public boolean fullScreen;

    //determines if the game is paused
    private boolean isPaused;

    // Counter variables
    private float elapsedTime;

    // Renderers
    GameRenderer gameRenderer;
    UIRenderer uiRenderer;

    //Used to fix incorrect initial Renderer size
    public int startWidth;
    public int startHeight;

    //Determines if first tick of game has passed
    public boolean FirstTick;

    //Determines if end game has been already called
    public boolean endedAlready;


    /**
     * Creates a new Game Screen
     * @param mapName The name of the map that will be used
     */
    public GameScreen(String mapName){
        super();

        this.isPaused = false;
        endedAlready = false;
        gameRenderer = new GameRenderer(mapName);
        uiRenderer = new UIRenderer(skin, gameRenderer.getBuildingRenderer(), this);
        elapsedTime = 0;
        //5 minutes
        GameGlobals.resetGlobals(300);
    }


    @Override
    public void show() {
        // Initialize game objects here

    }

    /**
     * Contains the game loop, renders game all game content from this loop
     * @param delta Time since last frame
     */
    @Override
    public void render(float delta) {
        // Clear screen
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            pause();
        }

        // Testing events key
        if(Gdx.input.isKeyJustPressed(Input.Keys.SEMICOLON)){
            event();
        }

        if(!isPaused){

            elapsedTime += delta; // delta is the time elapsed since the last frame
            if (elapsedTime >= 1) { // Increment counter every second

                // Calculate Game Stats
                GameGlobals.ELAPSED_TIME--;

                for (Building building : gameRenderer.getBuildingRenderer().getBuildingsMap().getPlacedBuildings()){
                    GameGlobals.SATISFACTION += building.calculateSatisfaction(GameGlobals.STUDENTS);

                    if(building.getBuildingType() == BuildingStats.BuildingType.FOOD){
                        FoodBuilding foodBuilding = (FoodBuilding) building;
                        GameGlobals.BALANCE += foodBuilding.calculateProfitMade();
                    }

                    if(building.getBuildingType() == BuildingStats.BuildingType.RECREATIONAL){
                        RecreationalBuilding foodBuilding = (RecreationalBuilding) building;
                        GameGlobals.BALANCE += foodBuilding.calculateProfitMade();
                    }

                }
                elapsedTime = 0; // Reset elapsed time
            }


        }

        if(GameGlobals.ELAPSED_TIME <= 0 && !endedAlready){
            endedAlready = true;
            endGame();
        }


        // Draw
        batch.begin();
        gameRenderer.render(delta);
        uiRenderer.render(delta);
        batch.end();

        //resizes to previous starting resolution
        if (FirstTick){
            if (fullScreen){
                GameConfigManager.setFullScreen();
            }
            else {
                Gdx.graphics.setWindowedMode(startWidth, startHeight);
            }
            FirstTick = false;
        }
    }


    /**
     * Checks if window has been resized
     */
    @Override
    public void resize(int width, int height) {
        uiRenderer.resize(width, height);
        gameRenderer.resize(width, height);


    }

    /**
     * Pauses the game and calls the UI renderer to display the
     * pause menu UI
     */
    @Override
    public void pause() {
        uiRenderer.pause(isPaused);

    }

    /**
     * Creates an event and calls the UI renderer to display it
     */
    public void event() {
        uiRenderer.createEvent();
    }

    /**
     * This is called when the game finishes, ie when the timer runs out
     */
    private void endGame(){
        isPaused = true;
        uiRenderer.endGame();

    }

    @Override
    public void resume() { }

    @Override
    public void hide() { }


    /**
     * disposes Renderers being drawn for garbage collection
     */
    @Override
    public void dispose() {
        batch.dispose();
        gameRenderer.dispose();
        uiRenderer.dispose();
    }

    /**
     * Sets ui Renderer to having input control
     */
    @Override
    public void takeInput() {
        uiRenderer.takeInput();
    }

    /**
     * Sets the game to be paused
     * @param isPaused boolean of if the game is paused
     */
    public void setPaused(boolean isPaused){
        this.isPaused = isPaused;
    }


}
