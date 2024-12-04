package com.vikingz.unitycoon.building.buildings;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vikingz.unitycoon.building.Building;
import com.vikingz.unitycoon.building.BuildingInfo;
import com.vikingz.unitycoon.util.Point;
import com.vikingz.unitycoon.util.StatsCalculator;


/**
 * FoodBuilding
 *
 * Represents the food building class in the game
 * Inherits Building
 */
public class FoodBuilding extends Building{

    private final float coinsPerSecond;

    /**
     * Creates new food building
     * @param texture Texture
     * @param x X
     * @param y Y
     * @param buildingInfo Building Info
     * @param coinsPerSecond Amount of coins this building make per second
     */
    public FoodBuilding(TextureRegion texture, float x, float y, BuildingInfo buildingInfo, float coinsPerSecond){
        super(texture, x, y, buildingInfo);
        this.coinsPerSecond = coinsPerSecond;
    }

    /**
     * Creates new food building
     * @param texture Texture
     * @param p Point p
     * @param buildingInfo Building Info
     * @param coinsPerSecond Amount of coins this building make per second
     */
    public FoodBuilding(TextureRegion texture, Point p, BuildingInfo buildingInfo, float coinsPerSecond){
        super(texture, p, buildingInfo);
        this.coinsPerSecond = coinsPerSecond;
    }

    /**
     * Calculates profit of this building
     * @return float coins made
     */
    public float calculateProfitMade(){
        // No profits if not built yet
        if (getConstructing()) {
            return 0;
        }
        return StatsCalculator.calculateProfitMade(this.coinsPerSecond);
    }
}
