package com.vikingz.unitycoon.global;

import com.badlogic.gdx.Gdx;

import java.io.Serializable;

/**
 * This class contains all of the config for the game, things such as width, height, sounds volume etc.
 * This class is also used by the GameConfig Manager to save the current config so that when the user 
 * reloads the game their settings are still present.
 * 
 * This is also a singleton class which means we instanciate it once and then that instance can be 
 * fetched statically by calling GameConfig.getInstance() and then from this instance we can retrieve 
 * settings. We have chosen to do this because by not making it static we are able to implement 
 * Serializable which lets us save the GameConfig class as is without any other logic.
 * 
 * This class has been refactored slightly to make the code more readable, however it is largely unchanged.
 */
public class GameConfig implements Serializable{

    // Constants for width and height MUST BE PUBLIC TO BE SERIALIZE
    public int windowWidth;
    public int windowHeight;
    public boolean skipMenus;
    public float SoundVolumeValue;
    public float MusicVolumeValue;
    static boolean VSync = false;
    public float guiSize = 1;

    // The single instance of GameConfig (eager initialization)
    static GameConfig INSTANCE = new GameConfig(
        1792, 1008, false, 1f,1f); // Default values

    // Private constructor to prevent instantiation from outside
    private GameConfig(int width, int height, boolean skipMenus, float SoundVolumeValue, float MusicVolumeValue) {
        this.windowWidth = width;
        this.windowHeight = height;
        this.skipMenus = skipMenus;
        this.SoundVolumeValue = SoundVolumeValue;
        this.MusicVolumeValue = MusicVolumeValue;
    }

    // Sets VSync mode for game on or off
    public static boolean setVSync(boolean enable){
        VSync = enable;
        Gdx.graphics.setVSync(enable);
        return VSync;
    }
    public static boolean getVSync(){
        return VSync;
    }

    public void setInstance(GameConfig conf){
        INSTANCE = conf;
    }


    // Method to get the single instance of GameConfig
    public static GameConfig getInstance() {
        return INSTANCE;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public boolean isSkipMenus() {
        return skipMenus;
    }

    public float getGuiSize() {
        return guiSize;
    }

    public void setGuiSize(float guiSize) {
        this.guiSize = guiSize;
    }
}




