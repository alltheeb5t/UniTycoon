package com.vikingz.unitycoon.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.vikingz.unitycoon.building.Building;
import com.vikingz.unitycoon.building.BuildingInfo;
import com.vikingz.unitycoon.building.BuildingStats;
import com.vikingz.unitycoon.building.BuildingsMap;
import com.vikingz.unitycoon.global.GameGlobals;
import com.vikingz.unitycoon.util.GameSounds;

/**
 *  This class is in charge of drawing Buildings in the game.
 *
 * This class also does the collision calculations for buildings
 * which make sure that the user is unable to place buildings on top
 * of each other, as well as using right click to be able to remove the
 * buildings from the game.
 */
public class BuildingRenderer{

    //Used to draw buildings textures
    private final SpriteBatch batch;

    //X and Y values used to place buildings
    private float previewX, previewY;

    //If building is being placed by user
    private boolean isPreviewing;

    //Textures of Building, fire and construction
    private TextureRegion selectedTexture;
    private Texture constructingTexture = new Texture("png\\UnderConstruction.png");
    private Texture fireTexture = new Texture("png\\fire.png");


    //Current Building being placed information
    private BuildingInfo currentBuildingInfo = null;

    //GameRender used to get mouse position and background tiles
    private final GameRenderer gameRenderer;

    // Moved building placement handler to external helper class to aid testing
    private final BuildingsMap campusBuildingsMap;

    /**
     * Creates a new Building Renderer
     * @param gameRenderer Parent renderer {@code GameRenderer}
     */
    public BuildingRenderer(GameRenderer gameRenderer) {

        this.gameRenderer = gameRenderer;

        batch = new SpriteBatch();
        isPreviewing = false;
        selectedTexture = null;

        campusBuildingsMap = new BuildingsMap(gameRenderer.getBackgroundRenderer());
    }

    /**
     * Renders buildings
     * @param delta Time since last frame
     */
    public void render(float delta) {
        checkBuildings(delta);
    }

    /**
     * Checks if the user is currently adding or removing buildings
     * @param delta Time since last frame
     */
    private void checkBuildings(float delta){
        //Stops previewing building and background building being removed at once
        Boolean removedPreviewing = false;

        // Update preview position to follow the mouse cursor
        if (isPreviewing && selectedTexture != null) {
            // Stops previewing building if user right clicks
            if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)){
                System.out.println("RightClick");
                isPreviewing = false;
                selectedTexture = null;
                removedPreviewing = true;
            }

            // Makes sure that the mouse is in the center of the building texture
            Vector3 previewPoint = snapBuildingToGrid(Gdx.input.getX() - GameGlobals.SCREEN_BUILDING_SIZE / 2, Gdx.input.getY() + GameGlobals.SCREEN_BUILDING_SIZE / 2);

            previewX = previewPoint.x;
            previewY = previewPoint.y;
        }

        batch.begin();

        // Draw all placed textures
        for (Building building : campusBuildingsMap.getPlacedBuildings()) {
            batch.draw(building.getTexture(), building.getX(), building.getY());
            // Checks if building is under construction
            if (building.getConstructing()) {
                batch.draw(constructingTexture, building.getX(), 
                    building.getY(), GameGlobals.SCREEN_BUILDING_SIZE, (int) (GameGlobals.SCREEN_BUILDING_SIZE * 0.75));
                
                // Starts or stops timer if needed, doesn't place building if not currently building buildings.
                if (building.getEndConstructionTime() == -1) {
                    building.setEndConstructionTime(GameGlobals.ELAPSED_TIME - 10);
                }
                else if(building.getEndConstructionTime() >= GameGlobals.ELAPSED_TIME && GameGlobals.currentlyBuilding) {
                    building.setConstructing(false);
                    campusBuildingsMap.builtBuilding(building);
                }
    
                // Adds the passed time to the end construction time if not currently building buildings.
                if (!GameGlobals.currentlyBuilding) {
                    building.updateEndConstructionTime(delta);
                }
            }
            // Draws fire texture on building if on fire
            if(building.getOnFire()) {
                batch.draw(fireTexture, building.getX(), 
                    building.getY(), GameGlobals.SCREEN_BUILDING_SIZE, GameGlobals.SCREEN_BUILDING_SIZE);
            }
        }

        // Draw the preview texture if one is selected
        if (isPreviewing && selectedTexture != null) {
            batch.draw(selectedTexture, previewX, previewY);
        }

        batch.end();

        // Removes the building the user right clicks on
        if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT) && !removedPreviewing){
            System.out.println("RightClick");
            Vector3 translatedPoint = gameRenderer.translateCoords(Gdx.input.getX(), Gdx.input.getY());

            if(campusBuildingsMap.attemptBuildingDeleteAt(translatedPoint.x, translatedPoint.y).isEmpty()) {
                System.out.println("building was null: " + null);
            }
        }

        // Stops fire if the building is on fire
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && selectedTexture == null){
            Vector3 translatedPoint = gameRenderer.translateCoords(Gdx.input.getX(), Gdx.input.getY());
            Building currentBuilding = campusBuildingsMap.getBuildingAtPoint(translatedPoint.x, translatedPoint.y);
            if(currentBuilding != null) {
                currentBuilding.setOnFire(false);
            }
        }

        // Check for left mouse click to place the texture
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && selectedTexture != null) {

            if (!campusBuildingsMap.attemptAddBuilding(currentBuildingInfo, selectedTexture, previewX, previewY).isEmpty()) {
                // Plays the sound of a building being places
                GameSounds.playPlacedBuilding();

                // The building is no longer being placed
                isPreviewing = false;
                currentBuildingInfo = null;
                selectedTexture = null;
            }
            else {
                // If building is colliding with something
                System.err.println("Player Trying to place on a collision piece");
                GameSounds.playPlaceError();
            }
        }

    }

    /**
     * Selects a building by building ID
     * @param buildingType buildingType of the building that the user wants to place down
     * @param index int the index of where it is in the dictionary
     */
    public void selectBuilding(BuildingStats.BuildingType buildingType, int index){

        isPreviewing = true;
        BuildingInfo newBuilding = BuildingStats.getInfo(buildingType,index);
        selectedTexture = BuildingStats.getTextureOfBuilding(BuildingStats.BuildingDict.get(buildingType)[index]);
        if (selectedTexture == null){
            System.err.println("ERROR: Could not select building: " + BuildingStats.BuildingDict.get(buildingType)[index]);
        }
        currentBuildingInfo = newBuilding;
    }

    /**
     * Snaps the coordinates passed in to the grid
     * @param x X
     * @param y Y
     * @return Point new coordinates that occur on an intersection of the tiles in the background
     */
    private Vector3 snapBuildingToGrid(float x, float y){

        // 30 rows
        // 56 cols
        int gridSize = 32;
        Vector3 translatedPoint = gameRenderer.translateCoords(x, y);

        float newX = Math.round(translatedPoint.x / gridSize) * gridSize;
        float newY = Math.round(translatedPoint.y / gridSize) * gridSize;

        return new Vector3(newX, newY, 0);
    }

    /**
     * Updates the width and height when the window
     * is resized
     */
    public void resize() {
    }

    /**
     * disposes building being drawn for garbage collection
     */
    public void dispose(){
        batch.dispose();
    }

    // ─── Getters And Setters ─────────────────────────────────────────────

    /**
     * Return the Building Map object. Used by the GameScreen for satisfaction calculation
     * @return
     */
    public BuildingsMap getBuildingsMap() {
        return campusBuildingsMap;
    }


}
