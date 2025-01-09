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
import com.vikingz.unitycoon.util.TimeHandler;

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

    Table statsBarTbl;

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
        academImg = new Image(new TextureRegion(textureAtlas, (int) (atlasTileSize * 0.5), atlasTileSize, atlasTileSize, atlasTileSize));
        accomImg = new Image(new TextureRegion(textureAtlas, (int) (atlasTileSize * 2.5), atlasTileSize, atlasTileSize, atlasTileSize));
        recImg = new Image(new TextureRegion(textureAtlas, (int) (atlasTileSize * 4.5), atlasTileSize, atlasTileSize, atlasTileSize));
        foodImg = new Image(new TextureRegion(textureAtlas, (int) (atlasTileSize * 6.5), atlasTileSize, atlasTileSize, atlasTileSize));
        balImg = new Image(new Texture("png\\moneySymbol.png"));
        satisImg = new Image(new Texture("png\\satisfactionSymbol.png"));
        timerImg = new Image(new Texture("png\\timeSymbol.png"));
        studentsImg = new Image(new Texture("png\\studentNumSymbol.png"));

        addStatsBar();
    }


    private void addStatsBar() {

        int padding = 5;
        int groupSpacing = 30;

        // Create layout table
        statsBarTbl = new Table();
        statsBarTbl.setFillParent(true);

        statsBarTbl.top();
        statsBarTbl.left();

        // Adds the labels and images to the stats bar(table)
        // Groups together game stats
        Table gameStatsTbl = new Table();
        // Groups each stat to it's image
        Table studentsTbl = new Table();
        studentsTbl.add(studentsImg).pad(padding).size(30);
        studentsTbl.add(students).pad(padding);
        gameStatsTbl.add(studentsTbl).spaceRight(groupSpacing).expandX().uniformX();
        Table satisTbl = new Table();
        satisTbl.add(satisImg).pad(padding).size(30);
        satisTbl.add(satisfaction).pad(padding);
        gameStatsTbl.add(satisTbl).spaceRight(groupSpacing).expandX().uniformX();
        Table balanceTbl = new Table();
        balanceTbl.add(balImg).pad(padding).size(30);
        balanceTbl.add(balance).pad(padding);
        gameStatsTbl.add(balanceTbl).expandX().uniformX();
        statsBarTbl.add(gameStatsTbl).uniformX().expandX().left();

        // Groups together bulding stats
        Table buildingTbl = new Table();
        Table accomTbl = new Table();
        accomTbl.add(accomImg).pad(padding).size(30);
        accomTbl.add(accomBuildings).pad(padding);
        buildingTbl.add(accomTbl).spaceRight(groupSpacing).expandX().uniformX();
        Table academTbl = new Table();
        academTbl.add(academImg).pad(padding).size(30);
        academTbl.add(academBuildings).pad(padding);
        buildingTbl.add(academTbl).spaceRight(groupSpacing).expandX().uniformX();
        Table recTbl = new Table();
        recTbl.add(recImg).pad(padding).size(30);
        recTbl.add(recBuildings).pad(padding);
        buildingTbl.add(recTbl).spaceRight(groupSpacing).expandX().uniformX();
        Table foodTbl = new Table();
        foodTbl.add(foodImg).pad(padding).size(30);
        foodTbl.add(foodBuildings).pad(padding);
        buildingTbl.add(foodTbl).expandX().uniformX();
        statsBarTbl.add(buildingTbl).uniformX().expandX();

        // Groups together timer stats
        Table timerTbl = new Table();
        Table timeCountdownTbl = new Table();
        timeCountdownTbl.add(timerImg).pad(padding).size(30);
        timeCountdownTbl.add(timer).pad(padding);
        timerTbl.add(timeCountdownTbl).expandX().uniformX();
        timerTbl.add(inGameTimer).pad(padding).expandX().uniformX();
        statsBarTbl.add(timerTbl).expandX().uniformX().right();
        statsBarTbl.padRight(50); //Adds gap at the end for pause button

        stage.addActor(statsBarTbl);
    }

    /**
     * Draws the labels to the screen
     * @param delta Time since last frame
     */
    public void render(float delta) {

        batch.begin();

        // Update the label contents each frame
        if (GameGlobals.MONEY.getBalance() < 0) {balance.setColor(Color.RED);} //Sets the balance colour to red if in debt
        else {balance.setColor(Color.WHITE);}
        balStr = "" + (int)(GameGlobals.MONEY.getBalance()) + "k";
        studentsStr = "" + GameGlobals.STUDENTS;
        satisStr = "" + GameGlobals.SATISFACTION.getSatisfaction() + "%";
        accomBuildingsStr = "" + GameGlobals.ACCOMODATION_BUILDINGS_COUNT;
        academBuildingsStr = "" + GameGlobals.ACADEMIC_BUILDINGS_COUNT;
        recBuildingsStr = "" + GameGlobals.RECREATIONAL_BUILDINGS_COUNT;
        foodBuildingsStr = "" + GameGlobals.FOOD_BUILDINGS_COUNT;

        TimeHandler.Time timerAmount = TimeHandler.secondsToMinSecs(GameGlobals.TIME_REMAINING);
        timerStr = "Timer: " + timerAmount;

        inGameTimerStr = TimeHandler.inGameTime(GameGlobals.TIME_REMAINING);

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
