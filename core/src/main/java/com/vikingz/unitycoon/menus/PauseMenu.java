package com.vikingz.unitycoon.menus;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.vikingz.unitycoon.global.GameGlobals;
import com.vikingz.unitycoon.screens.ScreenMultiplexer;

/**
 *  This class represents a PauseMenu in the game.
 *
 * This is the menu that appears if the user pressed the  button
 * during the game.
 *
 * This menu contains a settings button which sends the user to the settings
 * screen from which they can edit the settings.
 *
 * To close the pause menu, the user has to press the esc button again.
 */
public class PauseMenu extends Window {

    /**
     * Creates a new pause menu
     * This menu is shown when the user pauses the game / presses
     *  the esc button during the game.
     * @param skin Contains the skin pack to be used with menu
     */
    public PauseMenu(Skin skin, Stage stage) {

        super("", skin);

        this.setSize(1200, 500);
        this.setModal(true);
        this.setMovable(false);
        this.setResizable(false);

        Label message = new Label("\t\tGame Paused\nControls: right click removes buildings.", skin);
        message.setFontScale(3);
        this.add(message).padBottom(20).row();
        this.setBackground(GameGlobals.backGroundDrawable);

        //Displays buttons
        Table buttons = new Table();
        TextButton achievementsBtn = new TextButton("Achievements", skin);
        TextButton continueBtn = new TextButton("Continue", skin);
        TextButton settingsBtn = new TextButton("Settings", skin);
        TextButton quitBtn = new TextButton("Main Menu", skin);
        buttons.add(continueBtn).width(425).pad(10);
        buttons.add(achievementsBtn).width(425).pad(10);
        buttons.row();
        buttons.add(settingsBtn).width(425).pad(10);
        buttons.add(quitBtn).width(425).pad(10);
        this.add(buttons);

        AchievementsMenu achievementsMenu = new AchievementsMenu(skin);
        
        continueBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameGlobals.TIME.setPaused(false);
                PauseMenu.this.remove();
            }
        });

        achievementsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                achievementsMenu.update();
                achievementsMenu.setPosition((stage.getWidth() - achievementsMenu.getWidth()) / 2, (stage.getHeight() - achievementsMenu.getHeight()) / 2);
                stage.addActor(achievementsMenu);
            };
         });

        settingsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {ScreenMultiplexer.openSettings(ScreenMultiplexer.Screens.GAME);}
        });

        quitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenMultiplexer.closeGame();
            }
        });
    }
}
