package com.vikingz.unitycoon.render;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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


    // Labels and images
    String balStr;
    Label balance;
    Image balImg;

    String studentsStr;
    Label students;
    Image studentsImg;

    String satisStr;
    Label satisfaction;
    Image satisImg;

    String accomBuildingsStr;
    Label accomBuildings;
    Image accomImg;

    String academBuildingsStr;
    Label academBuildings;
    Image academImg;

    String recBuildingsStr;
    Label recBuildings;
    Image recImg;

    String foodBuildingsStr;
    Label foodBuildings;
    Image foodImg;

    String timerStr;
    Label timer;
    Image timerImg;

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
        balStr = "";
        studentsStr = "";
        satisStr = "";
        accomBuildingsStr = "";
        academBuildingsStr = "";
        recBuildingsStr = "";
        foodBuildingsStr = "";
        timerStr = "";
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
            lbl.setColor(Color.WHITE);
            lbl.setFontScale(1.5f);
        }

        //Texture atlas of building menu bar
        Texture textureAtlas = new Texture(Gdx.files.internal("textureAtlases/buildMenuButtonsAtlas.png")); // Load your 64x64 PNG

        //Sets the pixel size of tiles used for build menu bar
        int atlasTileSize = 64;
        //Sets all the images
        academImg = new Image(new TextureRegion(textureAtlas, 0, 0, atlasTileSize, atlasTileSize));
        accomImg = new Image(new TextureRegion(textureAtlas, atlasTileSize, 0, atlasTileSize, atlasTileSize));
        recImg = new Image(new TextureRegion(textureAtlas, atlasTileSize * 2, 0, atlasTileSize, atlasTileSize));
        foodImg = new Image(new TextureRegion(textureAtlas, atlasTileSize * 3, 0, atlasTileSize, atlasTileSize));
        balImg = new Image(new Texture("png\\moneySymbol.png"));
        satisImg = new Image(new Texture("png\\satisfactionSymbol.png"));
        timerImg = new Image(new Texture("png\\timeSymbol.png"));
        studentsImg = new Image(new Texture("png\\studentNumSymbol.png"));

        int padding = 3;
        int groupSpacing = 50;
        int spacing = 330;

        // Create layout table
        Table table = new Table();
        table.setFillParent(true);

        table.top();
        table.left();

        // Adds the labels to the table
        table.add(balImg).pad(padding).size(30);
        table.add(balance).pad(padding).spaceRight(groupSpacing);
        table.add(studentsImg).pad(padding).size(30);
        table.add(students).pad(padding).spaceRight(groupSpacing);
        table.add(satisImg).pad(padding).size(30);
        table.add(satisfaction).pad(padding).spaceRight(spacing);
        table.add(accomImg).pad(padding).size(30);
        table.add(accomBuildings).pad(padding).spaceRight(groupSpacing);
        table.add(academImg).pad(padding).size(30);
        table.add(academBuildings).pad(padding).spaceRight(groupSpacing);
        table.add(recImg).pad(padding).size(30);
        table.add(recBuildings).pad(padding).spaceRight(groupSpacing);
        table.add(foodImg).pad(padding).size(30);
        table.add(foodBuildings).pad(padding).spaceRight(spacing);
        table.add(timerImg).pad(padding).size(30);
        table.add(timer).pad(padding).spaceRight(groupSpacing);
        table.add(inGameTimer).pad(padding);

        stage.addActor(table);
    }


    /**
     * Draws the labels to the screen
     * @param delta Time since last frame
     */
    public void render(float delta) {

        batch.begin();

        // Update the label contents each frame
        if (GameGlobals.BALANCE < 0) {balance.setColor(Color.RED);} //Sets the balance colour to red if in debt
        balStr = "" + GameGlobals.BALANCE;
        studentsStr = "" + GameGlobals.STUDENTS;
        satisStr = "" + GameGlobals.SATISFACTION;
        accomBuildingsStr = "" + GameGlobals.ACCOMODATION_BUILDINGS_COUNT;
        academBuildingsStr = "" + GameGlobals.ACADEMIC_BUILDINGS_COUNT;
        recBuildingsStr = "" + GameGlobals.RECREATIONAL_BUILDINGS_COUNT;
        foodBuildingsStr = "" + GameGlobals.FOOD_BUILDINGS_COUNT;

        TimeUtil.Time timerAmount = TimeUtil.secondsToMinSecs(GameGlobals.ELAPSED_TIME);
        timerStr = "" + timerAmount;

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
