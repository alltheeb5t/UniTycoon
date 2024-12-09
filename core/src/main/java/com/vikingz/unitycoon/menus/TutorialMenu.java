package com.vikingz.unitycoon.menus;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This class creates a Menu that pops up with the game rules.
 */
public class TutorialMenu extends Window  {

    private String title = "How To Play";
    private Label titleLabel;
    
    private String message = "Build the campus of your dreams and keep your students happy "
        + "\nby buying and placing a range of facilities from the menu.\n\nControls: "
        + "\nPause Game - esc key \n\nTry to reach 100% satisfaction before the end of semester" 
        + " 2 in year 3. \nSatisfaction is influenced by building amounts and proximities, and debts."
        + "\n\nPlease use the built in full screen method in the settings page.\n\nGood luck!";
    private Label messageLabel;

    public TutorialMenu (Skin skin) {
        
        super("", skin);

        this.setSize(700, 500);
        this.setModal(true);
        this.setMovable(false);
        this.setResizable(false);

        this.setBackground(GameGlobals.backGroundDrawable);

        titleLabel = new Label(title, skin);
        titleLabel.setFontScale(3);
        this.add(titleLabel).padTop(1).row();

        messageLabel = new Label(message, skin);
        this.add(messageLabel).padBottom(10).row();
    }

    /**
     * Configures the button that appears on the popup.
     */
    public void setupButton(Skin skin){

        TextButton leftBtn = new TextButton("Close", skin);
        this.add(leftBtn).pad(10);

        leftBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TutorialMenu.this.remove();
            }
        });
    }
}
