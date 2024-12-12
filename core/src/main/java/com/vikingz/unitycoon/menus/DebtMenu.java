package com.vikingz.unitycoon.menus;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.vikingz.unitycoon.building.BuildingStats.BuildingType;
import com.vikingz.unitycoon.global.GameGlobals;
import com.vikingz.unitycoon.render.BuildingRenderer;

/**
 * This class creates a Menu that pops up when the user tries to go into debt the first time.
 */
public class DebtMenu extends Window {
    
    private String message = "Are you sure you want to go into debt?";
    private Label messageLabel;

    public DebtMenu (Skin skin) {
        
        super("", skin);

        this.setSize(800, 300);
        this.setModal(true);
        this.setMovable(false);
        this.setResizable(false);

        this.setBackground(GameGlobals.backGroundDrawable);

        messageLabel = new Label(message, skin);
        messageLabel.setFontScale(2);
        this.add(messageLabel).row();
    }

    /**
     * Configures the button that appears on the popup.
     */
    public void setupButton(Skin skin, BuildingRenderer buildingRenderer, Window window, BuildingType buildingType, int index){

        Table table = new Table();

        TextButton leftBtn = new TextButton("No", skin);
        table.add(leftBtn).pad(5);

        leftBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                DebtMenu.this.remove();
            }
        });

        TextButton rightBtn = new TextButton("Yes", skin);
        table.add(rightBtn).pad(5);

        rightBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buildingRenderer.selectBuilding(buildingType,index);
                window.remove();
                DebtMenu.this.remove();
            }
        });

        this.add(table);
    }
}
