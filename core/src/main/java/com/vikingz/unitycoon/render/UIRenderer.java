package com.vikingz.unitycoon.render;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vikingz.unitycoon.global.GameGlobals;
import com.vikingz.unitycoon.menus.*;
import com.vikingz.unitycoon.screens.GameScreen;
import com.vikingz.unitycoon.screens.ScreenMultiplexer;
import com.vikingz.unitycoon.util.Leaderboard;

/**
 * This class renders all the UI elements to the Screen.
 * <p>
 * This enables us to control how the UI is draw and resized
 * differently from how the rest of the game is drawn.
 * <p>
 * This class essentially forms another layer on the screen that
 * renders all the UI elements on this layer as opposed to the
 * game layer.
 */
public class UIRenderer {

    private final Stage stage;
    private final Viewport viewport;
    private final SpriteBatch spriteBatch;

    private final BuildMenu buildMenu;
    private final StatsRenderer statsRenderer;

    // Popup Menus
    private final PauseMenu pauseMenu;
    private final EndMenu endOfTimerPopup;
    private final LeaderboardMenu leaderboardPopUp;

    private boolean displayingAchievement = false;
    private TextButton achievementLabel;

    private Texture statsBarTexture;

    GameScreen gameScreen;

    /**
     * Creates a new UIRenderer
     * @param skin Skin used to style content
     * @param buildingRenderer Building renderer
     */
    public UIRenderer(Skin skin, BuildingRenderer buildingRenderer){

        //viewport = new FillViewport(1824, 1026);
        viewport = new FitViewport(1824, 1026);
        spriteBatch = new SpriteBatch();
        //viewport = new ScreenViewport();
        stage = new Stage(viewport);


        statsRenderer = new StatsRenderer(skin);
        buildMenu = new BuildMenu(skin, buildingRenderer, stage);

        pauseMenu = new PauseMenu(skin);
        endOfTimerPopup = new EndMenu(skin, "End of Game");
        leaderboardPopUp = new LeaderboardMenu(skin, "");

        statsBarTexture = new Texture("png\\statsBar.png");

        // Set up achievements popup
        achievementLabel = new TextButton("", skin);
        achievementLabel.setWidth(1000);
        achievementLabel.setPosition((stage.getWidth() - achievementLabel.getWidth()) / 2, (stage.getHeight() - 100));
        achievementLabel.getLabel().setFontScale((float)0.4,(float)0.4);

        // Sets what the buttons do on the end of timer window
        Runnable rightBtn = ScreenMultiplexer::closeGame;
        Runnable leftBtn = () -> {
            leaderboardPopUp.setPosition((stage.getWidth() - leaderboardPopUp.getWidth()) / 2, (stage.getHeight() - leaderboardPopUp.getHeight()) / 2);
            stage.addActor(leaderboardPopUp);};

        endOfTimerPopup.setupButtons(leftBtn, "Leaderboard", rightBtn, "Menu");
        leaderboardPopUp.setupButton();
    }

    /**
     * When the game screen has decided the game has finished the game
     * will call this function which will show the end of game popup.
     */
    public void endGame(String title) {
        Leaderboard.loadLeaderboard();
        
        String message = "Final Satisfaction: " + GameGlobals.SATISFACTION.getSatisfaction() + "\n\n";
        message += GameGlobals.ACHIEVEMENTS.allAchievementsCompleted();
        endOfTimerPopup.setTitle(title);
        endOfTimerPopup.setMessage(message);
        endOfTimerPopup.setPosition((stage.getWidth() - endOfTimerPopup.getWidth()) / 2, (stage.getHeight() - endOfTimerPopup.getHeight()) / 2);
        stage.addActor(endOfTimerPopup);

        if (Leaderboard.isLeaderboardScore(GameGlobals.SATISFACTION.getSatisfaction())) {
            Leaderboard.addScoreToLeaderBoard(GameGlobals.SATISFACTION.getSatisfaction(), UsernameMenu.getUsername());
            Leaderboard.saveLeaderboard();
        }

        leaderboardPopUp.setMessage(Leaderboard.getLeaderboardValue());
        GameGlobals.ACHIEVEMENTS.saveAchievements();
    }

    /**
     * Creates the event and displays it
     */
    public void createEvent() {
        System.out.println("Event made");

        PopupMenu event = GameGlobals.EVENT.randomEvent().getPopup();
        stage.addActor(event);
        event.setPosition((stage.getWidth() - pauseMenu.getWidth()) / 2, (stage.getHeight() - pauseMenu.getHeight()) / 2);
        GameGlobals.TIME.setPaused(true);
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
            GameGlobals.TIME.setPaused(true);
        }
        else{
            pauseMenu.remove();
            GameGlobals.TIME.setPaused(false);
        }

    }

    /**
     * Calls all render functions in the renderers
     * @param delta
     */
    public void render(float delta){
        viewport.apply();

        // Draws stats bar
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        //Uses values defined when viewport is created
        spriteBatch.draw(statsBarTexture, 0, 983, 1824, 43);
        spriteBatch.end();

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

    /**
     * Displays achievements in the order they were completed.
     */
    public void displayAchievements() {

        //Creates a task to remove the event from the screen after 8s.
        Timer timer = new Timer(8000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                achievementLabel.remove();
                displayingAchievement = false;
            }
        });
        timer.setRepeats(false);

        if (GameGlobals.ACHIEVEMENTS.achievementsToDisplay.size() != 0 && !displayingAchievement) {
            achievementLabel.setText(GameGlobals.ACHIEVEMENTS.achievementsToDisplay.remove());
            stage.addActor(achievementLabel);
            displayingAchievement = true;
            timer.start();
        }
    }
}
