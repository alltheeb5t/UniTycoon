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
import com.vikingz.unitycoon.global.GameGlobals;
import com.vikingz.unitycoon.util.Achievements;

/**
 * This class creates a Menu that pops up when the user chooses to view the achievements.
 */
public class AchievementsMenu extends Window{

    public static final String[] ACHIEVEMENT_TITLES = {"Bankruptcy", "Bare Minimum", "Busy Campus",
        "Clean Slate", "Indecisive", "Is This A University", "Lucky","Master Of Change", 
        "Mike Freeman Award", "Priorities", "Saviour", "Unlucky"};

    public static final String[] ACHIEVEMENT_DESCRIPTIONS = {"Balance drops below 0.", 
        "Place exactly 1 of each building type.", "Place more than 40 buildings.",
        "Place 10 or more buildings and remove them all.", "Remove more than 20 buildings.", 
        "Have twice as many reacreation as study buildings after 20 buildings.", 
        "Get 3 positive events in one game.", 
        "Remain under 50% satisfaction for the first 3 minutes and then win the game.", 
        "Maintain 80% or higher satisfaction for more than 3 minutes.", 
        "Have twice as many study as reactreation buildings after 20 buildings.", 
        "Save a burning building.", "Get 3 negative events in one game."};

    public static final int NUM_OF_ACHIEVEMENTS = 12;

    // Skin for the popup
    private final Skin skin;

    // Table to display achievements
    private Table table;


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
        for (int i = 0; i < NUM_OF_ACHIEVEMENTS; i++) {
            String achievementTitle = ACHIEVEMENT_TITLES[i];
            String achievementDescription = ACHIEVEMENT_DESCRIPTIONS[i];
            if((i == 7 && !Achievements.getUsernameAchievements()[7])  
                    || (i == 8 && !Achievements.getUsernameAchievements()[8])) { // Skip Hidden achievements
                continue;
            }
            Label achievementTitleLabel = new Label(achievementTitle, skin);
            table.add(achievementTitleLabel).uniformX().pad(10);

            Label achievementDesriptionLabel = new Label(achievementDescription, skin);
            table.add(achievementDesriptionLabel).uniformX().align(Align.left);

            Label achievementCompletedLabel = new Label("no", skin);
            if (Achievements.getUsernameAchievements()[i]) {
                achievementCompletedLabel.setText("yes");
            }
            table.add(achievementCompletedLabel).uniformX().pad(10);
            table.row();
        }

        //Add hidden achievements to the end
        for (int i = 7; i <= 8; i++) {
            if((!Achievements.getUsernameAchievements()[i])) {
                Label achievementTitleLabel = new Label("?????", skin);
                table.add(achievementTitleLabel).uniformX().pad(10);

                Label achievementDesriptionLabel = new Label("?????", skin);
                table.add(achievementDesriptionLabel).uniformX().align(Align.left);

                Label achievementCompletedLabel = new Label("no", skin);
                if (Achievements.getUsernameAchievements()[i]) {
                    achievementCompletedLabel.setText("yes");
                }
                table.add(achievementCompletedLabel).uniformX().pad(10);
                table.row();
            }
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

