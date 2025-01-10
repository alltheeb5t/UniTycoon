package com.vikingz.unitycoon.menus;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This is a generic PopupMenu class that can create user defined
 * popups. This class is crucial for the implementation of random events
 * in the game during later development.
 * <p>
 * The user can assign everything in the popup, all the text displayed
 * as well as the runnable that run when the 2 buttons are pressed.
 */
public class PopupMenu extends Window {

    // Skin for the popup
    private final Skin skin;

    /**
     * Creates a new Popup menu
     * @param skin Skin for the menu
     * @param Message Message to be displayed in the popup
     */
    public PopupMenu(Skin skin, String Message) {

        super("", skin);

        this.setSize(700, 400);
        this.setModal(true);
        this.setMovable(false);
        this.setResizable(false);

        this.skin = skin;
        this.setBackground(GameGlobals.backGroundDrawable);


        Label message = new Label(Message, skin);
        this.add(message).colspan(2).padBottom(20).row();

        GameGlobals.TIME.setPaused(true);

    }

    /**
     * Configures the 2 buttons that appear on the popup
     * @param leftRun Runnable that will be run if the left button is pressed
     * @param leftText The text written on the left button
     * @param rightRun Runnable that will be run if the right button is pressed
     * @param rightText The text written on the right button
     */
    public void setupButtons(Runnable leftRun, String leftText, Runnable rightRun, String rightText){


        TextButton leftBtn = new TextButton(leftText, skin);
        TextButton rightBtn = new TextButton(rightText, skin);

        this.add(leftBtn).pad(10);
        this.add(rightBtn).pad(10);

        // Created for yes - no game events

        leftBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                leftRun.run();
                PopupMenu.this.remove();
                GameGlobals.TIME.setPaused(false);
            }
        });

        rightBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                rightRun.run();
                PopupMenu.this.remove();
                GameGlobals.TIME.setPaused(false);
            }
        });
    }


    /**
     * Configures a close button for the popup
     */
    public void setupClose(Runnable runnable){

        TextButton closeButton = new TextButton("Close", skin);

        this.add(closeButton).colspan(2);

        // Created for game events with no choices

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                runnable.run();
                PopupMenu.this.remove();
                GameGlobals.TIME.setPaused(false);
            }
        });
    }
}
