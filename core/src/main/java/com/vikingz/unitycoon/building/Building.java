package com.vikingz.unitycoon.building;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vikingz.unitycoon.global.GameGlobals;
import com.vikingz.unitycoon.util.Point;
import com.vikingz.unitycoon.util.StatsCalculator;


/**
 * Building
 *
 * Abstract class that represents all of the buildings in the game.
 */
public abstract class Building {

    // Building drawing properties
    private TextureRegion texture;
    private float x;
    private float y;
    private float width;
    private float height;
    private boolean constructing;    
    private int endConstructionTime;


    // Building functional properties
    private BuildingStats.BuildingType buildingType;
    private float satisfactionMultiplier;
    private BuildingInfo buildingInfo;


    /**
     * Creates a new Building
     * @param texture Texture
     * @param x X
     * @param y Y
     * @param buildingInfo Building Info
     */
    public Building(TextureRegion texture, float x, float y, BuildingInfo buildingInfo){
        this.x = x;
        this.y = y;
        this.width = GameGlobals.SCREEN_BUILDING_SIZE;
        this.height = GameGlobals.SCREEN_BUILDING_SIZE;
        this.texture = texture;
        this.constructing = true;
        this.endConstructionTime = -1;
        this.buildingType = buildingInfo.getBuildingType();
        this.satisfactionMultiplier = buildingInfo.getSatisfactionMultiplier();
        this.buildingInfo = buildingInfo;
    }


    /**
     * Creates a new Building
     * @param texture Texture
     * @param p Point p
     * @param buildingInfo Building Info
     */
    public Building(TextureRegion texture, Point p, BuildingInfo buildingInfo){
        this.x = p.getX();
        this.y = p.getY();
        this.width = GameGlobals.SCREEN_BUILDING_SIZE;
        this.height = GameGlobals.SCREEN_BUILDING_SIZE;
        this.texture = texture;
        this.constructing = true;
        this.endConstructionTime = -1;
        this.buildingType = buildingInfo.getBuildingType();
        this.satisfactionMultiplier = buildingInfo.getSatisfactionMultiplier();
        this.buildingInfo = buildingInfo;
    }



    /**
     * Calculates satisfaction logic
     * @param numberOfStudents Number of students
     * @return Amount of satisfaction
     */
    public int calculateSatisfaction(int numberOfStudents){
        // No satisfaction multiplier if not built yet
        if (constructing) {
            return 0;
        }
        return StatsCalculator.calculateSatisfaction(numberOfStudents, this.satisfactionMultiplier);
    }


    // Getters and Setters


    /**
     * gets the building information of the building
     * @return buildingInfo Building Info
     */
    public BuildingInfo getBuildingInfo() {
        return buildingInfo;
    }

    /**
     * returns the float of the building width in pixels
     * @return width float of building
     */
    public float getWidth() {
        return width;
    }


    /**
     * Sets the pixel width of the building
     * @param width float
     */
    public void setWidth(float width) {
        this.width = width;
    }


    /**
     * returns the float of the building height in pixels
     * @return height float of building
     */
    public float getHeight() {
        return height;
    }

    /**
     * Sets the pixel height of the building
     * @param height float
     */
    public void setHeight(float height) {
        this.height = height;
    }


    /**
     * Returns the current texture of the building,
     * used for seeing a preview of a building and rendering
     * @return TextureRegion Image of the building
     */
    public TextureRegion getTexture() {
        return texture;
    }

    /**
     * Sets the Texture of the building to passed param
     * @param textureBuilding
     */
    public void setTexture(TextureRegion textureBuilding) {
        this.texture = textureBuilding;
    }

    /**
     * gets the current middle point of the building X coordinate
     * @return x float
     */
    public float getX() {
        return x;
    }

    /**
     * Sets the x coordinate of the building to param float
     * @param x
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * gets the current middle point of the building Y coordinate
     * @return y float
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the y coordinate of the building to param float
     * @param y
     */
    public void setY(float y) {
        this.y = y;
    }

    public boolean getConstructing() {
        return constructing;
    }

    public void setConstructing(boolean constructing) {
        this.constructing = constructing;
    }

    public int getEndConstructionTime() {
        return endConstructionTime;
    }

    public void setEndConstructionTime(int endConstructionTime) {
        this.endConstructionTime = endConstructionTime;
    }

    /**
     * gets the buildingType ENUM of the building
     * @return BuildingStats.BuildingType ENUM value
     */
    public BuildingStats.BuildingType getBuildingType() {
        return buildingType;
    }


    /**
     * output the class to a string
     * @return str String
     */
    public String toString(){
        String str = "";

        str += "x: " + this.x;
        str += " y: " + this.y;
        str += " width: " + this.width;
        str += " height: " + this.height;
        str += " type: " + this.buildingType;


        return str;

    }



}
