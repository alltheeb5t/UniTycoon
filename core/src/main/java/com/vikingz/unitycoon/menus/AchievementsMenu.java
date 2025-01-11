package com.vikingz.unitycoon.menus;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.vikingz.unitycoon.achievements.Achievement;
import com.vikingz.unitycoon.global.GameGlobals;

/**
 * This class creates a Menu that pops up when the user chooses to view the achievements.
 * 
 * This is a new class is used to complete FR_ACHIVEMENT_MENU.
 */
public class AchievementsMenu extends Window{

    // Skin for the popup
    final Skin skin;

    // Table to display achievements
    Table table;

    /**
     * Creates a new achievement menu.
     * @param skin Skin for the menu.
     */
    public AchievementsMenu(Skin skin) {

        super("", skin);

        this.setSize(1600, 825);
        this.setModal(true);
        this.setMovable(false);
        this.setResizable(false);

        this.skin = skin;
        this.setBackground(GameGlobals.backGroundDrawable);

        table = new Table();
        this.addActor(table);
    }

    /**
     * Formats all the information to be put on the menu
     */
    private void addToTable() {
        table = new Table();
        table.setFillParent(true); 
        table.center();

        table.add((Actor) null); //Centres title
        Label title = new Label("Achievements", skin);
        title.setFontScale(3);
        table.add(title).pad(5);
        table.row();

        //Adds headings
        Label titleLabel = new Label("Title", skin);
        titleLabel.setFontScale(2);
        table.add(titleLabel).uniformX().pad(10);

        Label desriptionLabel = new Label("Description", skin);
        desriptionLabel.setFontScale(2);
        table.add(desriptionLabel).uniformX();

        Label completedLabel = new Label("Completed", skin);
        completedLabel.setFontScale(2);
        table.add(completedLabel).uniformX().pad(10);
        table.row();

        //Adds row for each achievement
        Achievement[] achievements = GameGlobals.ACHIEVEMENTS.getAchievements();
        int numHiddenAchievements = 0;
        for (int i = 0; i < achievements.length; i++) {
            String achievementTitle = achievements[i].getName();
            String achievementDescription = achievements[i].getDescription();
            if((achievements[i].getHidden() && !achievements[i].usernameAchieved)) { // Skip Hidden achievements
                numHiddenAchievements++;
                continue;
            }
            Label achievementTitleLabel = new Label(achievementTitle, skin);
            table.add(achievementTitleLabel).uniformX().pad(10);

            Label achievementDesriptionLabel = new Label(achievementDescription, skin);
            table.add(achievementDesriptionLabel).uniformX().align(Align.left);

            Label achievementCompletedLabel = new Label("no", skin);
            if (GameGlobals.ACHIEVEMENTS.getAchievements()[i].usernameAchieved) {
                achievementCompletedLabel.setText("yes");
            }
            table.add(achievementCompletedLabel).uniformX().pad(10);
            table.row();
        }

        //Add hidden achievements to the end
        for (int i = 0; i < numHiddenAchievements; i++) {
            Label achievementTitleLabel = new Label("?????", skin);
            table.add(achievementTitleLabel).uniformX().pad(10);

            Label achievementDesriptionLabel = new Label("?????", skin);
            table.add(achievementDesriptionLabel).uniformX().align(Align.left);

            Label achievementCompletedLabel = new Label("no", skin);
            table.add(achievementCompletedLabel).uniformX().pad(10);
            table.row();
        }

        //Adds button
        table.add((Actor) null);
        TextButton leftBtn = new TextButton("Close", skin);
        table.add(leftBtn).pad(10);

        leftBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AchievementsMenu.this.remove();
            }
        });
        
        this.addActor(table);
    }

    /** 
     * Adds table to display.
     */
    public void update() {
        table.remove();
        addToTable();
    }
}

