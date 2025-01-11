package com.vikingz.unitycoon.building;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vikingz.unitycoon.global.GameGlobals;

/**
 * Building
 *
 * Abstract class that represents all of the buildings in the game.
 * 
 * This class has been refactored to help with code readability.
 */
public abstract class Building {

    // Building drawing properties
    TextureRegion texture;
    float x;
    float y;
    float width;
    float height;
    boolean constructing;    
    float endConstructionTime;
    boolean onFire;

    // Building functional properties
    BuildingStats.BuildingType buildingType;
    BuildingInfo buildingInfo;
    float earnAmount;

    // Protected so can be overridden by building subclasses
    protected EarnSchedule earnSchedule = EarnSchedule.DAILY;

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

    public BuildingInfo getBuildingInfo() {
        return buildingInfo;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public void setTexture(TextureRegion textureBuilding) {
        this.texture = textureBuilding;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

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

    public boolean getOnFire() {
        return onFire;
    }

    public void setOnFire(boolean onFire) {
        this.onFire = onFire;
    }

    public BuildingStats.BuildingType getBuildingType() {
        return buildingType;
    }

    /**
     * Lowers endConstructionTime by the given amount (extends constructing time by given amount).
     * This is new method added to complete FR_BUILD_TIME.
     * @param extraTime amount of time to extends constructing time by
     */
    public void updateEndConstructionTime(float extraTime) {
        this.endConstructionTime -= extraTime;
    }

    /**
     * Displayed in the format:
     *    x: 0 y: 0 width: 1 height: 1 type: NONE
     * @return the building's attributes as a string
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
     * Daily will actually be called roughly every 3.5 in-game days.
     * This is a new method added to complete UR_FINANCE and FR_STUDENT_FINANCE
     * @param earnSchedule Set to either DAILY or SEMESTERLY depending on where the earn method is called.
     * @return The building's defined earnAmount or 0 if not relevant to this building's EARN schedule.
     */
    public float calculateProfitMade(EarnSchedule earnSchedule) {
        if (getConstructing()) {
            return 0;
        }

        if (earnSchedule == this.earnSchedule) {
            return earnAmount;
        } 
        else {
            return 0;
        }
    }
}
