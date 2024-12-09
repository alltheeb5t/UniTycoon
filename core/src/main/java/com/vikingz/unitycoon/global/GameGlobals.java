package com.vikingz.unitycoon.global;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.vikingz.unitycoon.achievements.AchievementsHandler;
import com.vikingz.unitycoon.util.MoneyHandler;
import com.vikingz.unitycoon.util.SatisfactionHandler;

import static com.badlogic.gdx.math.MathUtils.random;


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
    public static int ELAPSED_TIME = 0;
    public static AchievementsHandler ACHIEVEMENTS = new AchievementsHandler();
    public static MoneyHandler MONEY = new MoneyHandler();

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

    // Generates random events for the game
    public static int firstSemEvent;
    public static int secondSemEvent;
    public static int thirdSemEvent;


    /**
     * Resets the game globals to the original values
     * @param time The amount of time in minutes the game should last for
     */
    public static void resetGlobals(int time){
        ELAPSED_TIME = time;
        ACADEMIC_BUILDINGS_COUNT = 0;
        ACCOMODATION_BUILDINGS_COUNT = 0;
        RECREATIONAL_BUILDINGS_COUNT = 0;
        FOOD_BUILDINGS_COUNT = 0;
        SATISFACTION = new SatisfactionHandler();
        STUDENTS = 0;
        MONEY = new MoneyHandler();
        firstSemEvent = random.nextInt(201, 285);
        secondSemEvent = random.nextInt(101, 199);
        thirdSemEvent = random.nextInt(15, 99);
        System.out.println(firstSemEvent);
        System.out.println(secondSemEvent);
        System.out.println(thirdSemEvent);
    }

}
