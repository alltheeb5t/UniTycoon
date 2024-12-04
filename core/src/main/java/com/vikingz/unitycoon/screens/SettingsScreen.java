package com.vikingz.unitycoon.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.vikingz.unitycoon.global.GameConfigManager;
import com.vikingz.unitycoon.util.GameMusic;
import com.vikingz.unitycoon.util.GameSounds;

/**
 * This screen represents the settings screen in the game
 *
 * It contains multiple buttons and slider which edit different game settings
 *
 * Inherits Screen, SuperScreen
 */
public class SettingsScreen extends SuperScreen implements Screen {


    // Components on the settings screen
    private final Label resolutionLabel;
    private String resolutionString;
    private String musicVolume;
    private String soundVolume;

    //Music and Sounds Components
    private final Slider SoundVolumeSlider;
    private final Label SoundVolumeLabel;
    private final Slider MusicVolumeSlider;
    private final Label MusicVolumeLabel;

    //Stores the previous screen before settings
    private ScreenMultiplexer.Screens previousScreen;

    //Button that fullscreen game
    private final TextButton fullscreenButton;
    //Button that makes the game window
    private final TextButton windowButton;
    //Saves the configuration of GameConfig
    private final TextButton saveGameConfigButton;

    private GameScreen gameScreen;


    /**
     * Creates a new settings screen
     */
    public SettingsScreen() {
        super();
        resolutionString = GameConfigManager.CurrentWindowSize();

        this.previousScreen = ScreenMultiplexer.Screens.MENU;
        this.resolutionLabel = new Label(GameConfigManager.CurrentWindowSize(), skin);

        // Create Sound volume slider
        SoundVolumeSlider = new Slider(0, 1, 0.1f, false, skin); // Min: 0, Max: 100, Step: 1
        SoundVolumeSlider.setValue(GameSounds.getVolume());
        SoundVolumeLabel = new Label(soundVolume, skin);
        this.soundVolume = "Sound Volume: " + SoundVolumeSlider.getValue();

        // Create Music volume slider
        MusicVolumeSlider = new Slider(0, 1, 0.1f, false, skin); // Min: 0, Max: 100, Step: 1
        MusicVolumeSlider.setValue(GameMusic.getVolume());
        MusicVolumeLabel = new Label(soundVolume, skin);
        this.musicVolume = "Music Volume: " + MusicVolumeSlider.getValue();

        // Adds event listeners to buttons

        // Back button to return to MenuScreen
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                goBack();
            }
        });

        TextButton quitButton = new TextButton("Quit", skin);
        quitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit(); // Quit the application
            }
        });

        fullscreenButton = new TextButton("Fullscreen",skin);
        fullscreenButton.addListener(e -> {
            if (fullscreenButton.isPressed()){
                GameConfigManager.setFullScreen();
                if(gameScreen != null) { gameScreen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); }

            }
            return true;
        });

        windowButton = new TextButton("Window Mode",skin);
        windowButton.addListener(e -> {
            if (windowButton.isPressed()){
                GameConfigManager.setWindowScreen();
            }
            return true;
        });

        saveGameConfigButton = new TextButton("Save",skin);
        saveGameConfigButton.addListener(e -> {
            if (saveGameConfigButton.isPressed()){
                GameConfigManager.saveGameConfig();
            }
            return true;
        });

        // Create layout table
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        table.add((Actor) null);
        table.add(resolutionLabel);
        table.row();

        // Add elements to the table
        table.add((Actor) null);
        table.add(SoundVolumeLabel).uniformX().pad(10);
        table.row();

        table.add((Actor) null);
        table.add(SoundVolumeSlider).fillX().uniformX().pad(10);
        table.row();

        table.add((Actor) null);
        table.add(MusicVolumeLabel).uniformX().pad(10);
        table.row();

        table.add((Actor) null);
        table.add(MusicVolumeSlider).fillX().uniformX().pad(10);
        table.row();

        table.add(fullscreenButton).fillX().uniformX().pad(10);
        table.add(saveGameConfigButton).fillX().pad(10);
        table.add(windowButton).fillX().uniformX().pad(10);
        table.row();


        table.add((Actor) null);
        table.add(backButton).fillX().uniformX().pad(10);

        table.row();
        table.add((Actor) null);

        table.add(quitButton).fillX().pad(10);
        table.row();

        table.row();


        // Add table to stage
        stage.addActor(table);
    }

    /**
     * Switches screens back to the screen the user access the settings from
     */
    public void goBack(){
        System.out.println(previousScreen.name());
        if (previousScreen.name().equals("GAME")) {
            ScreenMultiplexer.switchScreens(previousScreen);
        }
        else {
            ScreenMultiplexer.openMenu();
        }
    }

    @Override
    public void show() { }

    /**
     * Draws the components of the settings screen
     * @param delta Time since last frame
     */
    @Override
    public void render(float delta) {
        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //back button
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            goBack();
        }

        soundVolume = "Sound Volume: " + Math.round(SoundVolumeSlider.getValue() * 10);
        musicVolume = "Music Volume: " + Math.round(MusicVolumeSlider.getValue() * 10);

        GameSounds.setVolume(SoundVolumeSlider.getValue());
        GameMusic.setVolume(MusicVolumeSlider.getValue());

        SoundVolumeLabel.setText(soundVolume);
        MusicVolumeLabel.setText(musicVolume);
        resolutionLabel.setText(resolutionString);

        stage.act(delta);
        stage.draw();
    }

    /**
     * Changes SettingScreen to new resolution,
     * and updates resolutionText
     * @param width int resolution
     * @param height int resolution
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        resolutionString = "Resolution: " + width + "x" + height;
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    /**
     * Disposes SettingsScreen for garbage collection
     */
    @Override
    public void dispose() {

        stage.dispose();
        skin.dispose();
    }

    /**
     * Sets the previous screen
     * @param prevScreen Previous screen
     */
    public void setPrevScreen(ScreenMultiplexer.Screens prevScreen){
        this.previousScreen = prevScreen;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

}
