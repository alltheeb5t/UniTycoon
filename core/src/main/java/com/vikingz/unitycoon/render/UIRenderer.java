package com.vikingz.unitycoon.render;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vikingz.unitycoon.global.GameConfig;
import com.vikingz.unitycoon.global.GameGlobals;
import com.vikingz.unitycoon.menus.*;
import com.vikingz.unitycoon.screens.GameScreen;
import com.vikingz.unitycoon.screens.ScreenMultiplexer;
import com.vikingz.unitycoon.util.Leaderboard;

/**
 * This class renders all the UI elements to the Screen.
 * 
 * This enables us to control how the UI is draw and resized differently from how the rest of 
 * the game is drawn.
 * 
 * This class essentially forms another layer on the screen that renders all the UI elements on 
 * this layer as opposed to the game layer.
 * 
 * This class has been refactored to change the appearance of the UI to complete NFR_EASE_OF_USE.
 * It also completes UR_ACHIEVEMENTS and UR_LEADERBOARD.
 */
public class UIRenderer {

    final Stage stage;
    final Viewport viewport;
    final SpriteBatch spriteBatch;

    final BuildMenu buildMenu;
    final StatsRenderer statsRenderer;

    // Popup Menus
    final PauseMenu pauseMenu;
    final EndMenu endOfTimerPopup;
    final LeaderboardMenu leaderboardPopUp;

    boolean displayingAchievement = false;
    TextButton achievementLabel;

    Texture statsBarTexture;
    ImageButton pauseBtn;

    GameScreen gameScreen;

    /**
     * Creates a new UIRenderer
     * @param skin Skin used to style content
     * @param buildingRenderer Building renderer
     */
    public UIRenderer(Skin skin, BuildingRenderer buildingRenderer){

        viewport = new FitViewport(GameConfig.getInstance().getWindowWidth(), GameConfig.getInstance().getWindowHeight());
        spriteBatch = new SpriteBatch();
        stage = new Stage(viewport);

        //Set pause button
        Table table = new Table();
        table.setFillParent(true);
        table.right().top();
        Texture pauseTexture = new Texture("png\\pause.png");
        Texture pauseHoverTexture = new Texture("png\\pauseHover.png");
        pauseBtn = new ImageButton(new ImageButton.ImageButtonStyle());
        pauseBtn.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(pauseTexture));
        pauseBtn.getStyle().imageOver = new TextureRegionDrawable(new TextureRegion(pauseHoverTexture));
        table.add(pauseBtn).size(43).padRight(5);
        stage.addActor(table);
        pauseBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pause(GameGlobals.TIME.isPaused());
            }
        });

        statsRenderer = new StatsRenderer(skin);
        buildMenu = new BuildMenu(skin, buildingRenderer, stage);

        pauseMenu = new PauseMenu(skin, stage);
        endOfTimerPopup = new EndMenu(skin, "End of Game");
        leaderboardPopUp = new LeaderboardMenu(skin, "");

        statsBarTexture = new Texture("png\\statsBar.png");

        // Set up achievements popup
        achievementLabel = new TextButton("", skin);
        achievementLabel.setWidth(1000);
        achievementLabel.setPosition((stage.getWidth() - achievementLabel.getWidth()) / 2,  
            (stage.getHeight() - 100));
        achievementLabel.getLabel().setFontScale((float)0.4,(float)0.4);

        // Sets what the buttons do on the end of timer window
        Runnable rightBtn = ScreenMultiplexer::closeGame;
        Runnable leftBtn = () -> {
            leaderboardPopUp.setPosition((stage.getWidth() - leaderboardPopUp.getWidth()) / 2, 
                (stage.getHeight() - leaderboardPopUp.getHeight()) / 2);
            stage.addActor(leaderboardPopUp);};

        endOfTimerPopup.setupButtons(leftBtn, "Leaderboard", rightBtn, "Menu");
        leaderboardPopUp.setupButton();
        
        //Allows building pop-ups to be added to the ui stage
        buildingRenderer.setUIStage(stage);
    }

    /**
     * When the game has finished the game will call this function which will show the end of game popup.
     */
    public void endGame(String title) {
        Leaderboard.loadLeaderboard();

        String message = "Final Satisfaction: " + GameGlobals.SATISFACTION.getSatisfaction() + "\n\n";
        message += GameGlobals.ACHIEVEMENTS.allAchievementsCompleted();
        endOfTimerPopup.setTitle(title);
        endOfTimerPopup.setMessage(message);
        endOfTimerPopup.setPosition((stage.getWidth() - endOfTimerPopup.getWidth()) / 2, 
            (stage.getHeight() - endOfTimerPopup.getHeight()) / 2);
        stage.addActor(endOfTimerPopup);

        if (Leaderboard.isLeaderboardScore(GameGlobals.SATISFACTION.getSatisfaction())) {
            Leaderboard.addScoreToLeaderBoard(GameGlobals.SATISFACTION.getSatisfaction(), 
                UsernameMenu.getUsername());
            Leaderboard.saveLeaderboard();
        }

        leaderboardPopUp.setMessage(Leaderboard.getLeaderboardValue(), Leaderboard.getLeaderboardPos());
        GameGlobals.ACHIEVEMENTS.saveAchievements();
    }

    /**
     * Creates the event and displays it
     */
    public void createEvent() {

        PopupMenu event = GameGlobals.EVENT.randomEvent().getPopup();
        stage.addActor(event);
        event.setPosition((stage.getWidth() - event.getWidth()) / 2, 
            (stage.getHeight() - event.getHeight()) / 2);
        GameGlobals.TIME.setPaused(true);
    }

    /**
     * Creates a specific event and displays it
     */
    public void createEvent(String eventName) {

        PopupMenu event = GameGlobals.EVENT.setEvent(eventName).getPopup();
        stage.addActor(event);
        event.setPosition((stage.getWidth() - event.getWidth()) / 2, 
            (stage.getHeight() - event.getHeight()) / 2);
        GameGlobals.TIME.setPaused(true);
    }

    /**
     * Pauses the game displays the pause menu
     * @param isPaused boolean of if the game is paused
     */
    public void pause(boolean isPaused) {

        if(!pauseMenu.hasParent()){
            stage.addActor(pauseMenu);
            pauseMenu.setPosition((stage.getWidth() - pauseMenu.getWidth()) / 2, 
                (stage.getHeight() - pauseMenu.getHeight()) / 2);
            GameGlobals.TIME.setPaused(true);
        }
        else{
            pauseMenu.remove();
            GameGlobals.TIME.setPaused(false);
        }

    }

    /**
     * Calls all render functions in the renderers.
     * @param delta
     */
    public void render(float delta){
        // Draws stats bar
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        // Uses values defined when viewport is created
        spriteBatch.draw(statsBarTexture, 0, 
            GameConfig.getInstance().getWindowHeight() * 24/25, GameConfig.getInstance().getWindowWidth(), 
            GameConfig.getInstance().getWindowHeight() * 1/24);
        spriteBatch.end();

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

    /**
     * Displays achievements in the order they were completed.
     */
    public void displayAchievements() {

        //Creates a task to remove the achievement from the screen after 8s.
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
