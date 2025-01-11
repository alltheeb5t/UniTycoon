package com.vikingz.unitycoon.menus;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This class creates a Menu that pops up when the user chooses to play the game.
 * It asks the user to enter a username.
 */
public class UsernameMenu extends Window{

    private String message = "Usernames should have no puntcuation, no spaces and no more than 12 characters.";
    private Label messageLabel;
    private static String username = "";

    // Text field for entering username.
    TextField usernameField;

    // Skin for the popup
    private final Skin skin;


    /**
     * Creates a new leaderboard menu.
     * @param skin Skin for the menu.
     * @param message Message to be displayed in the popup.
     */
    public UsernameMenu(Skin skin) {

        super("", skin);

        this.setSize(1000, 300);
        this.setModal(true);
        this.setMovable(false);
        this.setResizable(false);

        this.skin = skin;
        this.setBackground(GameGlobals.backGroundDrawable);

        Label title = new Label("Enter Username:", skin);
        this.add(title).padTop(1).row();

        // Adds the username input field
        usernameField = new TextField("", skin);
        this.add(usernameField).pad(10).width(300).height(50).row();

        messageLabel = new Label(message, skin);
        this.add(messageLabel).padBottom(10).row();
        
    }

    /**
     * Configures the button that appears on the popup.
     */
    public void setupButton(){

        TextButton leftBtn = new TextButton("Ok", skin);
        this.add(leftBtn).pad(10);

        leftBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setUsername();
                UsernameMenu.this.remove();
            }
        });
    }

    /**
     * Gets the entered username and ensures that it is in the correct format 
     * (no punctuation, no spaces, less than 12 characters).
     * @return The value of the username with no spaces or punctuation or guest 
     *         if the username if blank.
     */
    private void formatUsername() {
        String finalUsername = "";

        // Format username.
        for (Character c : username.toCharArray()) {
            if(Character.isLetterOrDigit(c)) {
                finalUsername += c;
            }
            if(finalUsername.length() >= 12) {
                break;
            }
        }

        // Check username is not empty.
        if (finalUsername == "") {
            finalUsername = "Guest";
        }
        
        username = finalUsername;
    }


    public static String getUsername() {
        return username;
    }

    private void setUsername() {
        username = usernameField.getText();
        formatUsername();
        GameGlobals.ACHIEVEMENTS.setUsername(UsernameMenu.getUsername());
        GameGlobals.ACHIEVEMENTS.loadAchievements();
    }
}
