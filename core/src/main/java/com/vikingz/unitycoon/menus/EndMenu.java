package com.vikingz.unitycoon.menus;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.vikingz.unitycoon.global.GameConfig;
import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This class is the menu that pops up at the end of the game.
 *
 * This menu also contains a quit button that sends the user back to the
 * main menu as well as a continue button that lets the user continue the game.
 */
public class EndMenu extends Window {

    //Message to be displayed at end of the game
    private final String Message = "";

    //skin used for window
    private final Skin skin;




    /**
     * Creates a new EndMenu
     * @param skin The skin used to style the popup
     * @param Message The message that will be shown on the popup
     */
    public EndMenu(Skin skin, String Message) {

        super("", skin);

        this.setSize(800, 400);
        this.setModal(true);
        this.setMovable(false);
        this.setResizable(false);

        this.skin = skin;
        this.setBackground(GameGlobals.backGroundDrawable);


        Label message = new Label(Message, skin);
        this.add(message).padLeft(-35).row();


        //used to display the current top stored satisfaction
        Label topSatisfaction = new Label("Top Satisfaction: " + GameConfig.getInstance().getTopSatisfaction(), skin);
        this.add(topSatisfaction).padBottom(20).row();
    }

    /**
     * Crates the buttons Left and Right,
     * sets the actions and text of each button
     * @param leftRun contains function to be run on click for left button
     * @param leftText contains text for the left button
     * @param rightRun contains function to be run on click for right button
     * @param rightText contains text for the right button
     */
    public void setupButtons(Runnable leftRun, String leftText, Runnable rightRun, String rightText){


        TextButton leftBtn = new TextButton(leftText, skin);
        TextButton rightBtn = new TextButton(rightText, skin);
        this.add(leftBtn).pad(10);
        this.add(rightBtn).pad(10);

        // Created for yes - no game events
        // The Popup needs to call back to parent object in someway

        leftBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                leftRun.run();
                //PopupMenu.this.remove();
            }
        });

        rightBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                rightRun.run();
                //PopupMenu.this.remove();
            }
        });
    }

    /**
     * Adds new Label to endMenu,
     * when a new highScore is reached
     */
    public void addNewHighScore() {
        Label congrats = new Label("You have reached a new high score of satisfaction", skin);
        this.row();
        this.add(congrats);

    }
}
