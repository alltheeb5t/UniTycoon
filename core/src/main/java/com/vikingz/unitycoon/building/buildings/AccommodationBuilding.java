package com.vikingz.unitycoon.building.buildings;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vikingz.unitycoon.building.Building;
import com.vikingz.unitycoon.building.BuildingInfo;
import com.vikingz.unitycoon.building.EarnSchedule;

/**
 * Accomodation building class
 *
 * Represents the Accomodation building in the game
 * Inherits Building
 *
 */
public class AccommodationBuilding extends Building{

    public AccommodationBuilding(TextureRegion texture, float x, float y, BuildingInfo buildingInfo, float earnAmount){
        super(texture, x, y, buildingInfo, earnAmount);
        earnSchedule = EarnSchedule.SEMESTERLY;
    }

}
