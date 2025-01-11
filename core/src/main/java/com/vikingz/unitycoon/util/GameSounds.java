package com.vikingz.unitycoon.util;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.vikingz.unitycoon.global.GameConfig;

/**
 * This class loads and plays all the sounds effects for the entire game.
 * 
 * This class has been refactored slightly to make the code more readable, however it is largely unchanged.
 */
public class GameSounds {

    // Load the sounds fx
    static final Sound placeBuilding1 = Gdx.audio.newSound(Gdx.files.internal("audio/place_1.ogg"));
    static final Sound placeBuilding2 = Gdx.audio.newSound(Gdx.files.internal("audio/place_2.ogg"));
    static final Sound placeBuilding3 = Gdx.audio.newSound(Gdx.files.internal("audio/place_3.ogg"));

    static final Sound placeError1 = Gdx.audio.newSound(Gdx.files.internal("audio/place_error_1.ogg"));
    static final Sound placeError2 = Gdx.audio.newSound(Gdx.files.internal("audio/place_error_2.ogg"));

    //Sets the volume of the GameSounds to be played
    public static float volume = GameConfig.getInstance().SoundVolumeValue;

    /**
     * Plays the placed building sound
     */
    public static void playPlacedBuilding(){
        int randNum = new Random().nextInt(1, 4);
        switch (randNum) {
            case 1 -> placeBuilding1.play(volume);
            case 2 -> placeBuilding2.play(volume);
            case 3 -> placeBuilding3.play(volume);
            default -> {
            }
        }
    }

    /**
     * Plays the error sounds when the user tries placing a building somewhere illegal
     */
    public static void playPlaceError(){
        int randNum = new Random().nextInt(1, 3);

        switch (randNum) {
            case 1 -> placeError1.play(volume);
            case 2 -> placeError2.play(volume);
            default -> {
            }
        }
    }

    /**
     * Gets the volume
     * @return Float Volume level
     */
    public static float getVolume() {
        return volume;
    }

    /**
     * Sets the volume level
     * @param volume New volume level
     */
    public static void setVolume(float volume) {
        GameSounds.volume = volume;
        GameConfig.getInstance().SoundVolumeValue = volume;
    }
}
