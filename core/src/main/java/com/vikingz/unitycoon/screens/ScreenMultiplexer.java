package com.vikingz.unitycoon.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.vikingz.unitycoon.global.GameConfig;


/**
 * This class allows us to change screens anywhere within the project.
 *
 * On init(Game newGame) this class instantiates all the classes so that they are ready in memory and
 * waiting to be switched to.
 *
 * This class also contains the enum Screens which is what is used to differentiate between the different
 * screens available so that when the user wants to change screen all they have to do is call
 * ScreenMultiplexer.switchScreens(Screens screen), which is much easier than having to keep track at 
 * which point each screen is instantiated.
 *
 * This class also enables us to access the settings screen from any screen in the game as when the settings 
 * screen is displayed, this class keeps track from which screen it came from so that the back button on the 
 * settings screen will always send the user back to the screen they just came from.
 * 
 * This class has been refactored slightly to make the code more readable, however it is largely unchanged.
 */
public class ScreenMultiplexer {

    /**
     * This enum contains all the screens available in the program
     */
    public enum Screens{
        GAME,
        MENU,
        SETTINGS,
        MAPSELECTION,
    }

    // The main Game that allows us to control input
    static Game game;

    // All the screens available
    public static GameScreen gameScreen;
    public static MenuScreen menuScreen;
    public static SettingsScreen settingsScreen;
    public static MapSelectorScreen mapSelectorScreen;


    /**
     * Initializes the Screen Multiplexer
     * @param newGame Game
     */
    public static void init(Game newGame){
        game = newGame;

        menuScreen = new MenuScreen();
        settingsScreen = new SettingsScreen();
        mapSelectorScreen = new MapSelectorScreen();
    }

    /**
     * Sets up everything to run a game instance, the changes to the game screen
     * @param map Map that will be loaded in
     */
    public static void runGame(String map){
        int startHeight = Gdx.graphics.getHeight();
        int startWidth = Gdx.graphics.getWidth();
        boolean fullScreen = Gdx.graphics.isFullscreen();
        boolean firstTickNeed = false;
        if (Gdx.graphics.getHeight() != GameConfig.getInstance().getWindowHeight() && Gdx.graphics.getHeight() != GameConfig.getInstance().getWindowHeight()) {
            Gdx.graphics.setWindowedMode(GameConfig.getInstance().getWindowWidth(), GameConfig.getInstance().getWindowHeight());
            firstTickNeed = true;
        }

        //Creates new GameScreen
        gameScreen = new GameScreen(map);

        //Sets previousValues
        gameScreen.startHeight = startHeight;
        gameScreen.startWidth = startWidth;
        gameScreen.fullScreen = fullScreen;
        gameScreen.FirstTick = firstTickNeed;

        //Switches to gameScreen
        gameScreen.takeInput();
        game.setScreen(gameScreen);
        settingsScreen.setGameScreen(gameScreen);
    }

    /**
     * Changes the screen to the settings screen
     * @param prevScreen The screen that the settings screen was invoked from so that when the user 
     *                   presses the back button, they will be taken back to where they came from.
     */
    public static void openSettings(Screens prevScreen){
        settingsScreen.setPrevScreen(prevScreen);
        game.setScreen(settingsScreen);
        settingsScreen.takeInput();
    }

    /**
     * Stops the game and takes the user back to the main menu
     */
    public static void closeGame(){
        gameScreen.dispose();
        openMenu();
    }

    /**
     * Opens the main menu
     */
    public static void openMenu(){
        menuScreen.dispose();
        menuScreen = new MenuScreen();
        game.setScreen(menuScreen);
    }

    /**
     * Switches screens
     * @param screen The screen you want to switch to
     */
    public static void switchScreens(Screens screen){

        switch (screen) {
            case GAME -> {
                game.setScreen(gameScreen);
                gameScreen.takeInput();
            }
            case SETTINGS -> {
                game.setScreen(settingsScreen);
                settingsScreen.takeInput();
            }
            case MENU -> {
                game.setScreen(menuScreen);
                menuScreen.takeInput();
            }
            case MAPSELECTION -> {
                game.setScreen(mapSelectorScreen);
                mapSelectorScreen.takeInput();
            }
            default -> {
            }
        }
    }
}
