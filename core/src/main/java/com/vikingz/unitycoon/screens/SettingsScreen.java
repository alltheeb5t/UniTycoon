package com.vikingz.unitycoon.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.vikingz.unitycoon.global.GameConfigManager;
import com.vikingz.unitycoon.screens.ScreenMultiplexer.Screens;
import com.vikingz.unitycoon.util.GameMusic;
import com.vikingz.unitycoon.util.GameSounds;

/**
 * This screen represents the settings screen in the game
 *
 * It contains multiple buttons and slider which edit different game settings.
 *
 * Inherits Screen, SuperScreen
 * 
 * This class has been refactored slightly to make the code more readable and improve UI.
 */
public class SettingsScreen extends SuperScreen implements Screen {

    // Components on the settings screen
    final Label resolutionLabel;
    String resolutionString;
    String musicVolume;
    String soundVolume;

    //Music and Sounds Components
    final Slider SoundVolumeSlider;
    final Label SoundVolumeLabel;
    final Slider MusicVolumeSlider;
    final Label MusicVolumeLabel;

    //Stores the previous screen before settings
    ScreenMultiplexer.Screens previousScreen;

    //Button that fullscreen game
    final TextButton fullscreenButton;
    //Button that makes the game window
    final TextButton windowButton;

    GameScreen gameScreen;

    boolean changedAudioSliders;

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

        changedAudioSliders = false;

        // Back button to return to MenuScreen
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                goBack();
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

        // Create layout table
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(resolutionLabel);
        table.row();

        // Add elements to the table
        table.add(SoundVolumeLabel).uniformX().pad(10);
        table.row();

        table.add(SoundVolumeSlider).fillX().uniformX().pad(10);
        table.row();

        table.add(MusicVolumeLabel).uniformX().pad(10);
        table.row();

        table.add(MusicVolumeSlider).fillX().uniformX().pad(10);
        table.row();

        Table buttonsRows = new Table();
        buttonsRows.add(fullscreenButton).width(425).pad(10);
        buttonsRows.add(windowButton).width(425).pad(10);
        table.add(buttonsRows).pad(10).row(); //Centers buttons on same row

        table.add(backButton).width(425).pad(10);

        // Add table to stage
        stage.addActor(table);
    }

    /**
     * Switches screens back to the screen the user access the settings from
     */
    public void goBack(){
        if (previousScreen.name().equals("GAME")) {
            ScreenMultiplexer.switchScreens(previousScreen);
            setPrevScreen(Screens.MENU); 
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
        audioChanged();

        GameSounds.setVolume(SoundVolumeSlider.getValue());
        GameMusic.setVolume(MusicVolumeSlider.getValue());

        SoundVolumeLabel.setText(soundVolume);
        MusicVolumeLabel.setText(musicVolume);
        resolutionLabel.setText(resolutionString);

        stage.act(delta);
        stage.draw();
    }

    /**
     * Determines if either audio slider has been moved and saves if it has.
     * This new method was added to clear the user interface and avoid confusion with the save
     * button following the user evaluation.
     */
    private void audioChanged() {
        if (SoundVolumeSlider.isDragging() || MusicVolumeSlider.isDragging()) {
            changedAudioSliders = true;
        }
        else if (changedAudioSliders) {
            GameConfigManager.saveGameConfig();
            changedAudioSliders = false;
        }
    }

    /**
     * Changes SettingScreen to new resolution, and updates resolutionText
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
