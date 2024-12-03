package com.vikingz.unitycoon.building.buildings;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vikingz.unitycoon.building.Building;
import com.vikingz.unitycoon.building.BuildingInfo;
import com.vikingz.unitycoon.util.Point;
import com.vikingz.unitycoon.util.StatsCalculator;

/**
 * RecreationalBuilding
 *
 * Represents the recreational building class in the game
 * Inherits Building
 */
public class RecreationalBuilding extends Building{

    private final float coinsPerSecond;

    /**
     * Creates a new Recreational Building
     * @param texture Texture
     * @param x X
     * @param y Y
     * @param buildingInfo Building Info
     * @param coinsPerSecond The amount of coins this building makes per second
     */
    public RecreationalBuilding(TextureRegion texture, float x, float y, BuildingInfo buildingInfo, float coinsPerSecond){
        super(texture, x, y, buildingInfo);
        this.coinsPerSecond = coinsPerSecond;
    }
    /**
     * Creates a new Recreational Building
     * @param texture Texture
     * @param p Point p
     * @param buildingInfo Building Info
     * @param coinsPerSecond The amount of coins this building makes per second
     */
    public RecreationalBuilding(TextureRegion texture, Point p, BuildingInfo buildingInfo, float coinsPerSecond){
        super(texture, p, buildingInfo);
        this.coinsPerSecond = coinsPerSecond;
    }

    /**
     * Calculates profit made
     * @return float of profit made
     */
    public float calculateProfitMade(){
        // No profits if not built yet
        if (getConstructing()) {
            return 0;
        }
        return StatsCalculator.calculateProfitMade(this.coinsPerSecond);
    }

}
