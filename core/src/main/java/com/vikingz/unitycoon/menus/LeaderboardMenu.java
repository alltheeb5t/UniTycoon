package com.vikingz.unitycoon.menus;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This class creates a Menu that pops up when the user chooses to 
 * view the leaderboard at the end of the game.
 */
public class LeaderboardMenu extends Window{

    private Label messageLabel;

    // Skin for the popup
    private final Skin skin;


    /**
     * Creates a new leaderboard menu.
     * @param skin Skin for the menu.
     * @param message Message to be displayed in the popup.
     */
    public LeaderboardMenu(Skin skin, String message) {

        super("", skin);

        this.setSize(700, 500);
        this.setModal(true);
        this.setMovable(false);
        this.setResizable(false);

        this.skin = skin;
        this.setBackground(GameGlobals.backGroundDrawable);

        Label title = new Label("Leaderboard", skin);
        title.setFontScale(3);
        this.add(title).padTop(1).row();

        messageLabel = new Label(message, skin);
        messageLabel.setFontScale(2);
        this.add(messageLabel).padBottom(10).row();
        
    }

    /**
     * Configures the button that appears on the popup.
     */
    public void setupButton(){

        TextButton leftBtn = new TextButton("Close", skin);
        this.add(leftBtn).pad(10);

        leftBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LeaderboardMenu.this.remove();
            }
        });
    }
    
    /**
     * Formats and sets the leaderboard.
     * @param message the leaderboard
     * @param pos the position of the player
     */
    public void setMessage(String message, int pos) {
        String output = "";
        for (int i = 1; i <= message.split("\n").length; i++) {
            if(message.split("\n")[i-1] != "") {
                output +=  i + ". " + message.split("\n")[i-1];
            }
            if(i == pos) {
                output +=  "(this game)";
            }
            output += "\n";
        }
        messageLabel.setText(output);
    }
}
