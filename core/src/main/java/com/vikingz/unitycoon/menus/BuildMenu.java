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
import com.vikingz.unitycoon.global.GameConfig;
import com.vikingz.unitycoon.global.GameGlobals;
import com.vikingz.unitycoon.render.BuildingRenderer;

import static com.vikingz.unitycoon.building.BuildingStats.BuildingCoinDict;
import static com.vikingz.unitycoon.building.BuildingStats.BuildingType.*;


/**
 * This class is what creates the build menu in the game.
 *
 * It contains a single constructor that takes a Skin, BuildingRenderer
 * and a Stage as parameters to create the Building Menu.
 *
 * This class also creates the 4 buttons at the bottom of the game screen
 *  by which the build menu is accessed
 */
public class BuildMenu{

    //renders buildings and handles placing them
    private final BuildingRenderer buildingRenderer;

    //the stage where the menu will be added to
    private final Stage stage;

    //The skin of the window
    private final Skin skin;

    //Determines if there is already an active window open
    private boolean windowActive = false;

    //Allows BuildMenu to be placed in the center
    private int width = GameConfig.getInstance().getWindowWidth();
    private int height = GameConfig.getInstance().getWindowHeight();

    //Current displayed in game Menu
    private Window currentMenu;

    //Selects which building of BuildingType,
    //should be displayed currently
    private int index = 0;

    // Determines if the user has ever been in debt
    private boolean inDebtBefore;

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

        inDebtBefore = false;

        //Texture atlas of building menu bar
        Texture textureAtlas = new Texture(Gdx.files.internal("textureAtlases/buildMenuButtonsAtlas.png")); // Load your 64x64 PNG

        //Sets the pixel size of tiles used for build menu bar
        int atlasTileSize = 64;
        TextureRegion btn1Texture = new TextureRegion(textureAtlas, 0, 0, atlasTileSize, atlasTileSize);
        TextureRegion btn2Texture = new TextureRegion(textureAtlas, atlasTileSize, 0, atlasTileSize, atlasTileSize);
        TextureRegion btn3Texture = new TextureRegion(textureAtlas, atlasTileSize * 2, 0, atlasTileSize, atlasTileSize);
        TextureRegion btn4Texture = new TextureRegion(textureAtlas, atlasTileSize * 3, 0, atlasTileSize, atlasTileSize);

        TextureRegion btn1Texture_hover = new TextureRegion(textureAtlas, 0, atlasTileSize, atlasTileSize, atlasTileSize);
        TextureRegion btn2Texture_hover = new TextureRegion(textureAtlas, atlasTileSize, atlasTileSize, atlasTileSize, atlasTileSize);
        TextureRegion btn3Texture_hover = new TextureRegion(textureAtlas, atlasTileSize * 2, atlasTileSize, atlasTileSize, atlasTileSize);
        TextureRegion btn4Texture_hover = new TextureRegion(textureAtlas, atlasTileSize * 3, atlasTileSize, atlasTileSize, atlasTileSize);

        // Create ImageButtons
        ImageButton btn1 = new ImageButton(new ImageButton.ImageButtonStyle());
        btn1.getStyle().imageUp = new TextureRegionDrawable(btn1Texture);
        btn1.getStyle().imageOver = new TextureRegionDrawable(btn1Texture_hover);

        ImageButton btn2 = new ImageButton(new ImageButton.ImageButtonStyle());
        btn2.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(btn2Texture));
        btn2.getStyle().imageOver = new TextureRegionDrawable(new TextureRegion(btn2Texture_hover));

        ImageButton btn3 = new ImageButton(new ImageButton.ImageButtonStyle());
        btn3.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(btn3Texture));
        btn3.getStyle().imageOver = new TextureRegionDrawable(new TextureRegion(btn3Texture_hover));

        ImageButton btn4 = new ImageButton(new ImageButton.ImageButtonStyle());
        btn4.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(btn4Texture));
        btn4.getStyle().imageOver = new TextureRegionDrawable(new TextureRegion(btn4Texture_hover));


        // Table for layout
        Table table = new Table();
        table.setFillParent(true);
        table.bottom().center();
        table.bottom();

        // Add buttons to table
        table.add(btn1).pad(10);
        table.add(btn2).pad(10);
        table.add(btn3).pad(10);
        table.add(btn4).pad(10);

        // Add table to stage
        stage.addActor(table);

        // Set up click listeners for buttons
        btn1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentMenu != null) { currentMenu.remove(); }
                showMenu(ACADEMIC);
            }
        });

        btn2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentMenu != null) { currentMenu.remove(); }

                showMenu(BuildingStats.BuildingType.ACCOMODATION);
            }
        });

        btn3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentMenu != null) { currentMenu.remove(); }

                showMenu(RECREATIONAL);
            }
        });

        btn4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentMenu != null) { currentMenu.remove(); }

                showMenu(FOOD);
            }
        });


    }


    /**
     * Creates a new window and sets up all of the contents of the window
     * so that when the user presses one of the buttons at the bottom of the game
     * screen the corresponding menu is shown.
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
        Label buildingNameLabel = new Label(BuildingStats.BuildingNameDict.get(buildingType)[0], skin);
        window.add((Actor) null);
        window.add(buildingNameLabel);
        window.row().padTop(10);

        //Image Of Building
        window.add((Actor) null);
        Image buildingImage = new Image(BuildingStats.getTextureOfBuilding( BuildingStats.BuildingDict.get(buildingType)[0]));
        window.add(buildingImage);
        window.row().padTop(20);

        //Student Label
        window.add((Actor) null);
        Label buildingStudent = new Label("Student Space: " + BuildingStats.BuildingStudentDict.get(buildingType)[0],skin);
        window.add(buildingStudent).expandX();
        window.row();

        //Coins Label
        window.add((Actor) null);
        Label buildingCoins = new Label("Coins Per Second: " + BuildingCoinDict.get(buildingType)[0],skin);
        window.add(buildingCoins).expandX();
        window.row();

        //Price Label
        window.add((Actor) null);
        Label buildingPrice = new Label("Price: " + BuildingStats.BuildingPriceDict.get(buildingType)[0],skin);
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
                    SetLabelText(buildingNameLabel, buildingType, buildingPrice, buildingStudent, buildingCoins, buildingImage);
                }
                catch (ArrayIndexOutOfBoundsException e){
                    index = BuildingStats.BuildingNameDict.get(buildingType).length-1;
                    SetLabelText(buildingNameLabel, buildingType, buildingPrice, buildingStudent, buildingCoins, buildingImage);
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
                if(!inDebtBefore && (GameGlobals.BALANCE - Integer.valueOf(BuildingStats.BuildingPriceDict.get(buildingType)[index]) < 0)) {
                    inDebtBefore = true;
                    DebtMenu debtPopUp = new DebtMenu(skin);
                    debtPopUp.setPosition((stage.getWidth() - debtPopUp.getWidth()) / 2, (stage.getHeight() - debtPopUp.getHeight()) / 2);
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
                    SetLabelText(buildingNameLabel, buildingType, buildingPrice, buildingStudent, buildingCoins, buildingImage);
                }
                catch (ArrayIndexOutOfBoundsException e){
                    index = 0;
                    SetLabelText(buildingNameLabel, buildingType, buildingPrice, buildingStudent, buildingCoins, buildingImage);
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
        window.setPosition(this.width / 2f - (MENU_WINDOW_WIDTH / 2), this.height / 2f - (MENU_WINDOW_HEIGHT / 2));

        // Add window to the stage
        stage.addActor(window);

    }

    /**
     * Sets the text of each label to current index
     * @param buildingNameLabel Name Of building
     * @param buildingType Type of Building used, for dictionary lookup
     * @param buildingPrice Price of building
     * @param buildingSatisfaction Satisfaction rating of building
     * @param buildingStudent Student space of building
     * @param buildingCoins coin generated per second by building
     * @param buildingImage Image of building being, used for preview
     */
    private void SetLabelText(Label buildingNameLabel, BuildingStats.BuildingType buildingType, Label buildingPrice, Label buildingStudent, Label buildingCoins, Image buildingImage) {
        buildingNameLabel.setText(BuildingStats.BuildingNameDict.get(buildingType)[index]);
        buildingPrice.setText("Price: " + BuildingStats.BuildingPriceDict.get(buildingType)[index]);
        buildingStudent.setText("Student Space: " + BuildingStats.BuildingStudentDict.get(buildingType)[index]);
        buildingCoins.setText("Coins Per Second: " + BuildingCoinDict.get(buildingType)[index]);
        buildingImage.setDrawable(BuildingStats.getTextureDrawableOfBuilding(BuildingStats.BuildingDict.get(buildingType)[index]));
    }

    /**
     * returns if the window is currently open
     * @return windowActive boolean
     */
    public boolean isWindowActive() {
        return windowActive;
    }


    /**
     * Sets the windowActive, used when the menu is opened or closed,
     * to prevent two windows being opened at same time.
     * @param windowActive boolean
     */
    public void setWindowActive(boolean windowActive) {
        this.windowActive = windowActive;
    }

    /**
     * BuildingMenu render actors objects
     * @param delta
     */
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    /**
     * Called when the window resizes
     * @param width New width
     * @param height New height
     */
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        this.width = width;
        this.height = height;
    }

    /**
     * Disposes of the build menu
     */
    public void dispose() {
        stage.dispose();

    }
}
