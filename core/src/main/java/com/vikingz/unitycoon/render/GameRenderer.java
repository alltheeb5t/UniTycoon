package com.vikingz.unitycoon.render;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vikingz.unitycoon.global.GameConfig;

/**
 * This class contains all the renderers that render the game.
 *
 * This class contains the renderers that draw the background as well as the buildings. Using this class 
 * enables us to have a separate viewport that controls how the game is rendered as well as what happens 
 * when the game window is resized, as we wanted the map and the buildings to resize differently from the 
 * UI, which is what the {@code UIRenderer} is used for.
 * 
 * This class has been refactored to make the code more readable.
 */
public class GameRenderer {

    // Viewport stuff
    final Stage stage;
    final Camera camera;
    final Viewport viewport;

    final BackgroundRenderer backgroundRenderer;
    final BuildingRenderer buildingRenderer;

    /**
     * Creates and new Game Renderer
     * @param mapName Name of the map to be drawn as the background
     */
    public GameRenderer(String mapName, Skin skin){

        // Creates and camera and set up the viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.getInstance().getWindowWidth(), GameConfig.getInstance().getWindowHeight());
        
        stage = new Stage(viewport);
        backgroundRenderer = new BackgroundRenderer(mapName);
        buildingRenderer = new BuildingRenderer(this, skin);
    }

    /**
     * Draws the game contents to the screen
     * @param delta Time since last frame
     */
    public void render(float delta){
        viewport.apply();
        stage.getViewport().apply();
        camera.update();
        backgroundRenderer.render(delta);
        buildingRenderer.render(delta);
    }

    /**
     * Translates screen coordinates to game canvas coordinates.
     * @param p Point on the screen
     * @return Point on the game canvas
     */
    public Vector3 translateCoords(float x, float y){
        Vector3 vec3 = new Vector3(x, y, 0);
        Vector3 vec3Translated = viewport.unproject(vec3);
        return vec3Translated;
    }

    /**
     * Translates screen width to canvas width.
     * @param width Width
     * @return float Translated width
     */
    public float translateX(float width){
        Vector3 vec3 = new Vector3(width, 0, 0);
        Vector3 vec3Translated = viewport.unproject(vec3);
        return vec3Translated.x;
    }

    /**
     * Translates screen height to canvas height.
     * @param height Height
     * @return float Translated height
     */
    public float translateY(float height){
        Vector3 vec3 = new Vector3(0, height, 0);
        Vector3 vec3Translated = viewport.unproject(vec3);
        return vec3Translated.y;
    }

    /**
     * Updates renderers when the window is resized.
     * @param width New width
     * @param height New height
     */
    public void resize(int width, int height){
        viewport.update(width, height);
    }

    public BuildingRenderer getBuildingRenderer(){
        return buildingRenderer;
    }

    public BackgroundRenderer getBackgroundRenderer() {
        return backgroundRenderer;
    }

    /**
     * Disposes all renderers being drawn for garbage collection.
     */
    public void dispose(){
        stage.dispose();
        backgroundRenderer.dispose();
        buildingRenderer.dispose();
    }
}
