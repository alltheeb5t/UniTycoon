package com.vikingz.unitycoon.menus;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.vikingz.unitycoon.building.Building;
import com.vikingz.unitycoon.building.BuildingsMap;
import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This class creates a Menu that pops up when the user tries to go delete a buliding.
 * This new class was added after user-evaluation to improve the UI and better meet NFR_EASE_OF_USE.
 */
public class RemoveBuildingMenu extends Window {
    
    Skin skin;
    String message = "Are you sure you want to delete this building?";
    Label messageLabel;

    Table buttonsTbl;

    public RemoveBuildingMenu (Skin skin) {
        
        super("", skin);

        this.setSize(900, 300);
        this.setModal(true);
        this.setMovable(false);
        this.setResizable(false);

        this.setBackground(GameGlobals.backGroundDrawable);
        this.skin = skin;

        messageLabel = new Label(message, skin);
        messageLabel.setFontScale(2);
        this.add(messageLabel).row();

        buttonsTbl = new Table();
    }

    /**
     * Configures the pop-up.
     */
    public void setupPopUp(BuildingsMap campusBuildingsMap, Building buildingToRemove){

        this.clear();

        buttonsTbl.clear();

        TextButton leftBtn = new TextButton("No", skin);
        buttonsTbl.add(leftBtn).pad(5);

        leftBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                RemoveBuildingMenu.this.remove();
            }
        });

        TextButton rightBtn = new TextButton("Yes", skin);
        buttonsTbl.add(rightBtn).pad(5);

        rightBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Removes selected building
                campusBuildingsMap.attemptBuildingDelete(buildingToRemove);
                RemoveBuildingMenu.this.remove();
            }
        });

        this.add(messageLabel).row();
        this.add(buttonsTbl);
    }
}
