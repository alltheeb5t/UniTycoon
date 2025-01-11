package com.vikingz.unitycoon.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.vikingz.unitycoon.building.BuildingStats;
import com.vikingz.unitycoon.building.BuildingStats.BuildingType;
import com.vikingz.unitycoon.global.GameConfig;
import com.vikingz.unitycoon.global.GameGlobals;
import com.vikingz.unitycoon.render.BuildingRenderer;

import static com.vikingz.unitycoon.building.BuildingStats.buildingCoinDict;
import static com.vikingz.unitycoon.building.BuildingStats.BuildingType.*;


/**
 * This class is what creates the build menu in the game.
 *
 * It contains a single constructor that takes a Skin, BuildingRenderer
 * and a Stage as parameters to create the Building Menu.
 *
 * This class also creates the 4 buttons at the bottom of the game screen
 *  by which the build menu is accessed.
 * 
 * This class has been refactored to update the UI.
 */
public class BuildMenu{

    final BuildingRenderer buildingRenderer;
    final Stage stage;
    final Skin skin;

    boolean windowActive = false;
    int width = GameConfig.getInstance().getWindowWidth();
    int height = GameConfig.getInstance().getWindowHeight();

    Window currentMenu;

    //Selects which building of BuildingType should be displayed currently.
    int index = 0;

    // Determines if the user has ever seen the inDebtMenu
    boolean seenDebtMenu;

    /**
     * Creates a new BuildMenu
     * @param skin SKin of the buttons on the menu
     * @param buildingRenderer  BuildingRenderer instance that renders the buildings in the game
     * @param stage The stage on which the menu is drawn
     */
    public BuildMenu(Skin skin, BuildingRenderer buildingRenderer, Stage stage) {

        this.stage = stage;
        this.buildingRenderer =  buildingRenderer;
        this.skin = skin;

        //Texture atlas of building menu bar
        Texture textureAtlas = new Texture(Gdx.files.internal("textureAtlases/buildMenuButtonsAtlas.png"));

        //Sets the pixel size of tiles used for build menu bar
        int atlasTileSize = 128;
        TextureRegion btn1Texture = new TextureRegion(textureAtlas, 0, 0, atlasTileSize, atlasTileSize);
        TextureRegion btn2Texture = new TextureRegion(textureAtlas, atlasTileSize, 0, atlasTileSize, 
            atlasTileSize);
        TextureRegion btn3Texture = new TextureRegion(textureAtlas, atlasTileSize * 2, 0, atlasTileSize, 
            atlasTileSize);
        TextureRegion btn4Texture = new TextureRegion(textureAtlas, atlasTileSize * 3, 0, atlasTileSize, 
            atlasTileSize);

        TextureRegion btn1Texture_hover = new TextureRegion(textureAtlas, 0, atlasTileSize, atlasTileSize, 
            atlasTileSize);
        TextureRegion btn2Texture_hover = new TextureRegion(textureAtlas, atlasTileSize, atlasTileSize, 
            atlasTileSize, atlasTileSize);
        TextureRegion btn3Texture_hover = new TextureRegion(textureAtlas, atlasTileSize*2, atlasTileSize, 
            atlasTileSize, atlasTileSize);
        TextureRegion btn4Texture_hover = new TextureRegion(textureAtlas, atlasTileSize*3, atlasTileSize, 
            atlasTileSize, atlasTileSize);

        // Create ImageButtons
        ImageButton studyBtn = new ImageButton(new ImageButton.ImageButtonStyle());
        studyBtn.getStyle().imageUp = new TextureRegionDrawable(btn1Texture);
        studyBtn.getStyle().imageOver = new TextureRegionDrawable(btn1Texture_hover);

        ImageButton accommBtn = new ImageButton(new ImageButton.ImageButtonStyle());
        accommBtn.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(btn2Texture));
        accommBtn.getStyle().imageOver = new TextureRegionDrawable(new TextureRegion(btn2Texture_hover));

        ImageButton recBtn = new ImageButton(new ImageButton.ImageButtonStyle());
        recBtn.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(btn3Texture));
        recBtn.getStyle().imageOver = new TextureRegionDrawable(new TextureRegion(btn3Texture_hover));

        ImageButton foodBtn = new ImageButton(new ImageButton.ImageButtonStyle());
        foodBtn.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(btn4Texture));
        foodBtn.getStyle().imageOver = new TextureRegionDrawable(new TextureRegion(btn4Texture_hover));

        // Table for layout
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.bottom();

        // Add buttons to table
        table.add(studyBtn).pad(10);
        table.add(accommBtn).pad(10);
        table.add(recBtn).pad(10);
        table.add(foodBtn).pad(10);

        // Add table to stage
        stage.addActor(table);

        // Set up click listeners for buttons
        studyBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (buildingRenderer.getOpenMenu()) {
                    if(currentMenu != null) { currentMenu.remove(); }
                    showMenu(ACADEMIC);
                }
                buildingRenderer.setOpenMenu(true);
            }
        });

        accommBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (buildingRenderer.getOpenMenu()) {
                    if(currentMenu != null) { currentMenu.remove(); }
                    showMenu(ACCOMODATION);
                }
                buildingRenderer.setOpenMenu(true);
            }
        });

        recBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (buildingRenderer.getOpenMenu()) {
                    if(currentMenu != null) { currentMenu.remove(); }
                    showMenu(RECREATIONAL);
                }
                buildingRenderer.setOpenMenu(true);
            }
        });

        foodBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (buildingRenderer.getOpenMenu()) {
                    if(currentMenu != null) { currentMenu.remove(); }
                    showMenu(FOOD);
                }
                buildingRenderer.setOpenMenu(true);
            }
        });
        seenDebtMenu = false;
    }

    /**
     * Creates a new window and sets up all of the contents of the window so that when the user 
     * presses one of the buttons at the bottom of the game screen the corresponding menu is shown.
     * This method has been refactored to add a debt popup for UI.
     * @param buildingType contains Type of building from BuildingStats
     */
    private void showMenu(BuildingStats.BuildingType buildingType) {
        // Create a window (menu)
        index = 0;

        Window window = new Window("Build Menu", skin);
        window.getTitleTable().padTop(25).padLeft(437);
        this.currentMenu = window;
        window.setMovable(false);
        window.setBackground(GameGlobals.backGroundDrawable);

        //Building name Label
        Label buildingNameLabel = new Label(BuildingStats.buildingNameDict.get(buildingType)[0], skin);
        window.add((Actor) null);
        window.add(buildingNameLabel);
        window.row().padTop(10);

        //Image Of Building
        window.add((Actor) null);
        Image buildingImage = new Image(BuildingStats.getTextureOfBuilding( BuildingStats.buildingDict.get(
            buildingType)[0]));
        window.add(buildingImage);
        window.row().padTop(20);

        //Student Label
        window.add((Actor) null);
        Label buildingStudent = new Label("Student Space: " + BuildingStats.buildingStudentDict.get(
            buildingType)[0],skin);
        window.add(buildingStudent).expandX();
        window.row();

        //Coins Label
        window.add((Actor) null);
        Label buildingCoins;
        // Sets label to semester if the building changes money every semester and second otherwise
        if (buildingType == BuildingType.ACADEMIC || buildingType == BuildingType.ACCOMODATION) {
            buildingCoins = new Label("Coins Per Semester: " + buildingCoinDict.get(buildingType)[0] + "k",
                skin);
        }
        else {
            buildingCoins = new Label("Coins Per Second: " + buildingCoinDict.get(buildingType)[0] + "k",
                skin);
        }
        window.add(buildingCoins).expandX();
        window.row();

        //Price Label
        window.add((Actor) null);
        Label buildingPrice;
        if (BuildingStats.nextBuildingFree) {
            buildingPrice = new Label("Price: FREE", skin);
        }
        else {
            buildingPrice= new Label("Price: " + BuildingStats.buildingPriceDict.get(buildingType)[0] + "k",skin);
        }
        window.add(buildingPrice);

        window.row().padTop(20);



        //Back Building Button
        TextButton backButton = new TextButton("Back", skin);
        backButton.setSize(100, 30); // Set size for the back button
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    index--;
                    SetLabelText(buildingNameLabel, buildingType, buildingPrice, buildingStudent, 
                        buildingCoins, buildingImage);
                }
                catch (ArrayIndexOutOfBoundsException e){
                    index = BuildingStats.buildingNameDict.get(buildingType).length-1;
                    SetLabelText(buildingNameLabel, buildingType, buildingPrice, buildingStudent, 
                        buildingCoins, buildingImage);
                }
            }
        });
        window.add(backButton).padLeft(50);

        // Create the Buy Button
        TextButton buyButton = new TextButton("Buy", skin);
        buyButton.setSize(100, 30);
        buyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Creates an going into debt pop-up if the user doesn't have enough money for the building.
                // Only shows the first time
                if(!seenDebtMenu && (GameGlobals.MONEY.getBalance() - Integer.valueOf(
                        BuildingStats.buildingPriceDict.get(buildingType)[index]) < 0)) {
                    seenDebtMenu = true;
                    DebtMenu debtPopUp = new DebtMenu(skin);
                    debtPopUp.setPosition((stage.getWidth() - debtPopUp.getWidth()) / 2, 
                        (stage.getHeight() - debtPopUp.getHeight()) / 2);
                    debtPopUp.setupButton(skin, buildingRenderer, window, buildingType, index);
                    stage.addActor(debtPopUp);
                }
                else {
                    buildingRenderer.selectBuilding(buildingType,index);
                    window.remove();
                }
            }
        });
        window.add(buyButton);


        //Next Building Button
        TextButton nextButton = new TextButton("Next", skin);
        nextButton.setSize(100, 30); // Set size for the next button
        nextButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    index++;
                    SetLabelText(buildingNameLabel, buildingType, buildingPrice, buildingStudent, 
                        buildingCoins, buildingImage);
                }
                catch (ArrayIndexOutOfBoundsException e){
                    index = 0;
                    SetLabelText(buildingNameLabel, buildingType, buildingPrice, buildingStudent, 
                        buildingCoins, buildingImage);
                }
            }
        });
        window.add(nextButton).padRight(50);


        // Create the close button
        TextButton closeButton = new TextButton("Close", skin);
        closeButton.setSize(100, 30); // Set size for the close button

        closeButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                setWindowActive(false);
                window.remove();  // Remove window from the stage
            }
        });

        // Add close button to the window
        window.row().padTop(10); // Add a row before adding the close button
        window.add((Actor) null);
        window.add(closeButton);

        // Set size and position of the window
        //Size of the window
        int MENU_WINDOW_WIDTH = 1000;
        int MENU_WINDOW_HEIGHT = 800;
        window.setSize(MENU_WINDOW_WIDTH, MENU_WINDOW_HEIGHT);
        window.setPosition(this.width / 2f - (MENU_WINDOW_WIDTH / 2), this.height / 2f - 
            (MENU_WINDOW_HEIGHT / 2));

        // Add window to the stage
        stage.addActor(window);

    }

    /**
     * Sets the text of each label to current index
     * This method has been refactored to allow free buildings for UR_EVENTS.
     * @param buildingNameLabel Name Of building
     * @param buildingType Type of Building used, for dictionary lookup
     * @param buildingPrice Price of building
     * @param buildingStudent Student space of building
     * @param buildingCoins coin generated per second by building
     * @param buildingImage Image of building being, used for preview
     */
    private void SetLabelText(Label buildingNameLabel, BuildingStats.BuildingType buildingType, 
        Label buildingPrice, Label buildingStudent, Label buildingCoins, Image buildingImage) {
        buildingNameLabel.setText(BuildingStats.buildingNameDict.get(buildingType)[index]);
        if (BuildingStats.nextBuildingFree) {
            buildingPrice.setText("Price: FREE");
        }
        else {
            buildingPrice.setText("Price: " + BuildingStats.buildingPriceDict.get(buildingType)[index] 
                + "k");
        }
        buildingStudent.setText("Student Space: " + BuildingStats.buildingStudentDict.get(
                buildingType)[index]);
        if (buildingType == BuildingType.ACADEMIC || buildingType == BuildingType.ACCOMODATION) {
            buildingCoins.setText("Coins Per Semester: " + buildingCoinDict.get(buildingType)[index] + "k");
        }
        else {
            buildingCoins.setText("Coins Per Second: " + buildingCoinDict.get(buildingType)[index] + "k");
        }
        buildingImage.setDrawable(BuildingStats.getTextureDrawableOfBuilding(BuildingStats.buildingDict.get(
                buildingType)[index]));
    }

    /**
     * Returns if the window is currently open.
     * @return windowActive boolean
     */
    public boolean isWindowActive() {
        return windowActive;
    }

    /**
     * Sets the windowActive, used when the menu is opened or closed, to prevent two windows being 
     * opened at same time.
     * @param windowActive boolean
     */
    public void setWindowActive(boolean windowActive) {
        this.windowActive = windowActive;
    }

    /**
     * BuildingMenu render actors objects.
     * @param delta
     */
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    /**
     * Called when the window resizes.
     * @param width New width
     * @param height New height
     */
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        this.width = width;
        this.height = height;
    }

    /**
     * Disposes of the build menu.
     */
    public void dispose() {
        stage.dispose();
    }
}
