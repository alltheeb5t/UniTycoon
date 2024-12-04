package com.vikingz.unitycoon.render;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.vikingz.unitycoon.global.GameGlobals;
import com.vikingz.unitycoon.util.TimeUtil;

/**
 * This class is used for drawing game stats to the screen.
 *
 * This class contains all the labels that are on the
 * top right of the screen that display the users balance,
 * satisfaction etc.
 */
public class StatsRenderer {

    //Used to render UI
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final Stage stage;

    //Used to resize UI renderer to new screen size
    float width;
    float height;

    // Labels
    String balStr;
    Label balance;

    String studentsStr;
    Label students;

    String satisStr;
    Label satisfaction;

    String accomBuildingsStr;
    Label accomBuildings;

    String academBuildingsStr;
    Label academBuildings;

    String recBuildingsStr;
    Label recBuildings;

    String foodBuildingsStr;
    Label foodBuildings;

    String timerStr;
    Label timer;

    String inGameTimerStr;
    Label inGameTimer;

    //Stores all labels
    List<Label> labels;

    /**
     * Creates a new stats renderer
     * @param skin Skin that determines the style of the text
     */
    public StatsRenderer(Skin skin) {

        batch = new SpriteBatch();
        stage = new Stage();
        font = new BitmapFont();
        font.getData().setScale(1.5f);
        labels = new ArrayList<>();

        // Label strings
        balStr = "Balance";
        studentsStr = "Students";
        satisStr = "satisfaction";
        accomBuildingsStr = "Accodomation";
        academBuildingsStr = "Academic";
        recBuildingsStr = "Recreational";
        foodBuildingsStr = "Food";
        timerStr = "Timer: ";
        inGameTimerStr = "";

        // Creating labels
        balance = new Label(balStr, skin);
        students = new Label(studentsStr, skin);
        satisfaction = new Label(satisStr, skin);
        accomBuildings = new Label(accomBuildingsStr, skin);
        academBuildings = new Label(academBuildingsStr, skin);
        recBuildings = new Label(recBuildingsStr, skin);
        foodBuildings = new Label(foodBuildingsStr, skin);
        timer = new Label(timerStr, skin);
        inGameTimer = new Label(inGameTimerStr, skin);

        // Adding labels to a list
        labels.add(balance);
        labels.add(students);
        labels.add(satisfaction);
        labels.add(accomBuildings);
        labels.add(academBuildings);
        labels.add(recBuildings);
        labels.add(foodBuildings);
        labels.add(timer);
        labels.add(inGameTimer);

        for(Label lbl: labels){
            lbl.setColor(Color.BLACK);
            lbl.setFontScale(1.5f);
        }

        int padding = 3;

        // Create layout table
        Table table = new Table();
        table.setFillParent(true);

        table.top();
        table.left();

        // Adds the labels to the table
        table.add(balance).pad(padding).align(Align.left);
        table.row();
        table.add(students).pad(padding).align(Align.left);
        table.row();
        table.add(satisfaction).pad(padding).align(Align.left);
        table.row();
        table.add(accomBuildings).pad(padding).align(Align.left);
        table.row();
        table.add(academBuildings).pad(padding).align(Align.left);
        table.row();
        table.add(recBuildings).pad(padding).align(Align.left);
        table.row();
        table.add(foodBuildings).pad(padding).align(Align.left);
        table.row();
        table.add(timer).pad(padding).align(Align.left);
        table.row();
        table.add(inGameTimer).pad(padding).align(Align.left);

        stage.addActor(table);

    }


    /**
     * Draws the labels to the screen
     * @param delta Time since last frame
     */
    public void render(float delta) {

        batch.begin();

        // Update the label contents each frame
        balStr = "Balance: " + GameGlobals.BALANCE;
        studentsStr = "Students: " + GameGlobals.STUDENTS;
        satisStr = "Satisfaction: " + GameGlobals.SATISFACTION + "%";
        accomBuildingsStr = "Accomodation: " + GameGlobals.ACCOMODATION_BUILDINGS_COUNT;
        academBuildingsStr = "Academic: " + GameGlobals.ACADEMIC_BUILDINGS_COUNT;
        recBuildingsStr = "Recreational: " + GameGlobals.RECREATIONAL_BUILDINGS_COUNT;
        foodBuildingsStr = "Food: " + GameGlobals.FOOD_BUILDINGS_COUNT;

        TimeUtil.Time timerAmount = TimeUtil.secondsToMinSecs(GameGlobals.ELAPSED_TIME);
        timerStr = "Timer: " + timerAmount;

        inGameTimerStr = TimeUtil.inGameTime(GameGlobals.ELAPSED_TIME);

        // Sets the new string to the corresponding label
        balance.setText(balStr);
        students.setText(studentsStr);
        satisfaction.setText(satisStr);
        accomBuildings.setText(accomBuildingsStr);
        academBuildings.setText(academBuildingsStr);
        recBuildings.setText(recBuildingsStr);
        foodBuildings.setText(foodBuildingsStr);
        timer.setText(timerStr);
        inGameTimer.setText(inGameTimerStr);

        stage.act(delta);
        stage.draw();
        batch.end();
    }

    /**
     * Sets current width and height to the new values when the window is resized
     * @param width New width
     * @param height New height
     */
    public void resize(float width, float height){
        this.width = width;
        this.height = height;
    }

    /**
     * disposes stats being drawn for garbage collection
     */
    public void dispose(){
        stage.dispose();
        batch.dispose();
        font.dispose();
    }


}
