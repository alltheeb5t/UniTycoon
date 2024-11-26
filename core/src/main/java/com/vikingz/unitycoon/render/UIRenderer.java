package com.vikingz.unitycoon.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vikingz.unitycoon.global.GameConfig;
import com.vikingz.unitycoon.global.GameConfigManager;
import com.vikingz.unitycoon.global.GameGlobals;
import com.vikingz.unitycoon.menus.BuildMenu;
import com.vikingz.unitycoon.menus.EndMenu;
import com.vikingz.unitycoon.menus.PauseMenu;
import com.vikingz.unitycoon.screens.GameScreen;
import com.vikingz.unitycoon.screens.ScreenMultiplexer;

/**
 * This class renders all the UI elements to the Screen.
 *
 * This enables us to control how the UI is draw and resized
 * differently from how the rest of the game is drawn.
 *
 * This class essentially forms another layer on the screen that
 * renders all the UI elements on this layer as opposed to the
 * game layer.
 */
public class UIRenderer {

    private final Stage stage;
    private final Viewport viewport;


    private final BuildMenu buildMenu;
    private final StatsRenderer statsRenderer;

    // Popup Menus
    private final PauseMenu pauseMenu;
    private final EndMenu endOfTimerPopup;

    private boolean newHighScore = true;
    GameScreen gameScreen;

    /**
     * Creates a new UIRenderer
     * @param skin Skin used to style content
     * @param buildingRenderer Building renderer
     * @param gameScreen Game screen
     */
    public UIRenderer(Skin skin, BuildingRenderer buildingRenderer, GameScreen gameScreen){

        this.gameScreen = gameScreen;

        //viewport = new FillViewport(1824, 1026);
        viewport = new FitViewport(1824, 1026);
        //viewport = new ScreenViewport();
        stage = new Stage(viewport);


        statsRenderer = new StatsRenderer(skin);
        buildMenu = new BuildMenu(skin, buildingRenderer, stage);

        pauseMenu = new PauseMenu(skin);
        endOfTimerPopup = new EndMenu(skin, "End of Game");

        // Sets what the buttons do on the end of timer window
        Runnable leftBtn = ScreenMultiplexer::closeGame;
        Runnable rightBtn = () -> {};

        endOfTimerPopup.setupButtons(leftBtn, "Quit", rightBtn, "Leaderboard");

    }

    /**
     * When the game screen has decided the game has finished the game
     * will call this function which will show the end of game popup
     * @param newScore boolean of if new high score has been met
     */
    public void endGame(boolean newScore){

        endOfTimerPopup.setPosition((stage.getWidth() - endOfTimerPopup.getWidth()) / 2, (stage.getHeight() - endOfTimerPopup.getHeight()) / 2);
        if (newScore && newHighScore){
            endOfTimerPopup.addNewHighScore();
            GameConfig.getInstance().setTopSatisfaction(GameGlobals.SATISFACTION);
            GameConfigManager.saveGameConfig();
            newHighScore = false;
        }
        stage.addActor(endOfTimerPopup);

    }

    /**
     * Pauses the game displays the pause menu
     * @param isPaused boolean of if the game is paused
     */
    public void pause(boolean isPaused) {
        System.out.println("Pressed ESC");

        if(!pauseMenu.hasParent()){
            stage.addActor(pauseMenu);
            pauseMenu.setPosition((stage.getWidth() - pauseMenu.getWidth()) / 2, (stage.getHeight() - pauseMenu.getHeight()) / 2);
            gameScreen.setPaused(true);
        }
        else{
            pauseMenu.remove();
            gameScreen.setPaused(false);
        }

    }

    /**
     * Calls all render functions in the renderers
     * @param delta
     */
    public void render(float delta){
        viewport.apply();
        statsRenderer.render(delta);
        buildMenu.render(delta);

    }


    /**
     * Resizes UI content when the window is resized
     * @param width New width
     * @param height New height
     */
    public void resize(int width, int height){
        viewport.update(width, height);
        stage.getViewport().update(width, height, true);
        buildMenu.resize(width, height);
        statsRenderer.resize(width, height);

    }

    /**
     * Sets the input process to this class when called
     */
    public void takeInput(){
        Gdx.input.setInputProcessor(stage);

    }

    /**
     * Disposes of content in this screen
     */
    public void dispose(){
        stage.dispose();
    }

}
