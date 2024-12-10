package com.vikingz.unitycoon.building.buildings;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vikingz.unitycoon.building.Building;
import com.vikingz.unitycoon.building.BuildingInfo;

/**
 * RecreationalBuilding
 *
 * Represents the recreational building class in the game
 * Inherits Building
 */
public class RecreationalBuilding extends Building{

    public RecreationalBuilding(TextureRegion texture, float x, float y, BuildingInfo buildingInfo, float earnAmount){
        super(texture, x, y, buildingInfo, earnAmount);
    }

}
