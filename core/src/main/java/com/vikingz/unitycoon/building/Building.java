package com.vikingz.unitycoon.building;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vikingz.unitycoon.global.GameGlobals;


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
    private float endConstructionTime;
    private boolean onFire;


    // Building functional properties
    private BuildingStats.BuildingType buildingType;
    private BuildingInfo buildingInfo;
    private float earnAmount;

    protected EarnSchedule earnSchedule = EarnSchedule.DAILY;


    /**
     * Creates a new Building
     * @param texture Texture
     * @param x X
     * @param y Y
     * @param buildingInfo Building Info
     */
    public Building(TextureRegion texture, float x, float y, BuildingInfo buildingInfo, float earnAmount){
        this.x = x;
        this.y = y;
        this.width = GameGlobals.SCREEN_BUILDING_SIZE;
        this.height = GameGlobals.SCREEN_BUILDING_SIZE;
        this.texture = texture;
        this.constructing = true;
        this.endConstructionTime = -1;
        this.buildingType = buildingInfo.getBuildingType();
        this.buildingInfo = buildingInfo;
        this.earnAmount = earnAmount;
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

    public float getEndConstructionTime() {
        return endConstructionTime;
    }

    public void setEndConstructionTime(int endConstructionTime) {
        this.endConstructionTime = endConstructionTime;
    }

    /**
     * Lowers the endConstructionTime by the given amount.
     */
    public void updateEndConstructionTime(float extraTime) {
        this.endConstructionTime -= extraTime;
    }

    public boolean getOnFire() {
        return onFire;
    }

    public void setOnFire(boolean onFire) {
        this.onFire = onFire;
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

    /**
     * Calculate the amount of earnings this building has made for the given period.
     * Daily will actually be called roughly every 3.5 in-game days
     * @param earnSchedule Will be set to either DAILY or SEMESTERLY depending on where the earn method is called from
     * @return The building's defined earnAmount or 0 if not relevant to this building's EARN schedule
     */
    public float calculateProfitMade(EarnSchedule earnSchedule) {
        if (getConstructing()) {
            return 0;
        }

        if (earnSchedule == this.earnSchedule) {
            return earnAmount;
        } else {
            return 0;
        }
        
    }
}
