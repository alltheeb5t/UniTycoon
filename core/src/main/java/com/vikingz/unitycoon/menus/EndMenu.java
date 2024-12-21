package com.vikingz.unitycoon.menus;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This class is the menu that pops up at the end of the game.
 *
 * This menu also contains a quit button that sends the user back to the
 * main menu as well as a continue button that lets the user continue the game.
 */
public class EndMenu extends Window {

    private Label titleLabel;
    private Label messageLabel;

    //skin used for window
    private final Skin skin;

    /**
     * Creates a new EndMenu
     * @param skin The skin used to style the popup
     * @param Message The message that will be shown on the popup
     */
    public EndMenu(Skin skin, String Message) {

        super("", skin);

        this.setSize(1000, 600);
        this.setModal(true);
        this.setMovable(false);
        this.setResizable(false);

        this.skin = skin;
        this.setBackground(GameGlobals.backGroundDrawable);

        titleLabel = new Label("", skin);
        titleLabel.setFontScale(3);
        this.add(titleLabel).row();

        messageLabel = new Label(Message, skin);
        messageLabel.setFontScale(2);
        this.add(messageLabel).align(Align.left).row();
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
        Table table = new Table();

        table.add(leftBtn).pad(10);
        table.add(rightBtn).pad(10);
        this.add(table);

        // Created for yes - no game events
        // The Popup needs to call back to parent object in someway

        leftBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                leftRun.run();
            }
        });

        rightBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                rightRun.run();
            }
        });
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }
}
