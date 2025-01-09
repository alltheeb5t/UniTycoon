package com.vikingz.unitycoon;

import com.badlogic.gdx.Game;
import com.vikingz.unitycoon.global.GameConfig;
import com.vikingz.unitycoon.global.GameConfigManager;
import com.vikingz.unitycoon.screens.ScreenMultiplexer;
import com.vikingz.unitycoon.util.GameMusic;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */

/**
 * Main class
 *
 * Entry point of the game
 */
public class Main extends Game {
    @Override
    public void create() {
        GameConfigManager.loadGameConfig();

        new GameMusic().init();
        GameMusic.play();
        ScreenMultiplexer.init(this);

        // If SKIP_MENUS is enabled in GameConfig, the game will load straight into the game.
        if(GameConfig.getInstance().isSkipMenus()){
            ScreenMultiplexer.switchScreens(ScreenMultiplexer.Screens.MENU);
        }
        else{
            ScreenMultiplexer.switchScreens(ScreenMultiplexer.Screens.MENU);
        }
    }
}
