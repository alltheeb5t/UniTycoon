package com.vikingz.unitycoon.global;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.vikingz.unitycoon.achievements.AchievementsHandler;
import com.vikingz.unitycoon.building.BuildingsMap;
import com.vikingz.unitycoon.screens.GameScreen;
import com.vikingz.unitycoon.screens.ScreenMultiplexer;
import com.vikingz.unitycoon.util.MoneyHandler;
import com.vikingz.unitycoon.util.SatisfactionHandler;
import com.vikingz.unitycoon.events.EventHandler;
import com.vikingz.unitycoon.util.TimeHandler;


/**
 * This class is used to store all the constants and global values
 * that need to be accessed from different classes.
 *
 * This class only provides public static attributes and public
 * static methods.
 */
public class GameGlobals {

    //Static stats of the current game
    public static SatisfactionHandler SATISFACTION = new SatisfactionHandler();
    public static int STUDENTS = 0;
    public static int ACADEMIC_BUILDINGS_COUNT = 0;
    public static int ACCOMODATION_BUILDINGS_COUNT = 0;
    public static int RECREATIONAL_BUILDINGS_COUNT = 0;
    public static int FOOD_BUILDINGS_COUNT = 0;
    public static int TIME_REMAINING = 0;
    public static BuildingsMap BUILDINGSMAP; // Instantiated within GameScreen when it is instantiated
    public static AchievementsHandler ACHIEVEMENTS = new AchievementsHandler();
    public static MoneyHandler MONEY = new MoneyHandler();
    public static EventHandler EVENT = new EventHandler();
    public static TimeHandler TIME = new TimeHandler();
    public static boolean gameWon = false;
    public static boolean buildingAllowed = true; //Stops buildings from being built when false.

    //Size of the building SCREEN_BUILDING_SIZExSCREEN_BUILDING_SIZE
    public static final int SCREEN_BUILDING_SIZE = 128;

    // Load map textures
    public static final Texture map1Texture = new Texture(Gdx.files.internal("png/map1Texture.png"));
    public static final Texture map2Texture = new Texture(Gdx.files.internal("png/map2Texture.png"));
    public static final Texture map3Texture = new Texture(Gdx.files.internal("png/map3Texture.png"));

    public static final TextureRegionDrawable backGroundDrawable = new TextureRegionDrawable(new Texture("png/background.png"));

    //Loads map as a drawable to allow changing Image Actors to different images
    public static final TextureRegionDrawable map1Draw = new TextureRegionDrawable(map1Texture);
    public static final TextureRegionDrawable map2Draw = new TextureRegionDrawable(map2Texture);
    public static final TextureRegionDrawable map3Draw = new TextureRegionDrawable(map3Texture);
    public static final TextureRegionDrawable[] mapArray = new TextureRegionDrawable[]{map1Draw, map2Draw, map3Draw};


    /**
     * Resets the game globals to the original values
     * @param time The amount of time in minutes the game should last for
     */
    public static void resetGlobals(int time){
        TIME_REMAINING = time;
        ACADEMIC_BUILDINGS_COUNT = 0;
        ACCOMODATION_BUILDINGS_COUNT = 0;
        RECREATIONAL_BUILDINGS_COUNT = 0;
        FOOD_BUILDINGS_COUNT = 0;
        SATISFACTION = new SatisfactionHandler();
        STUDENTS = 0;
        MONEY = new MoneyHandler();
        EVENT = new EventHandler();
        gameWon = false;
        ACHIEVEMENTS.resetAllAchievements();
        buildingAllowed = true;
    }

}
