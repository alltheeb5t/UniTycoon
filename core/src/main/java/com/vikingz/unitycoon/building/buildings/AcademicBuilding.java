package com.vikingz.unitycoon.building.buildings;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vikingz.unitycoon.building.Building;
import com.vikingz.unitycoon.building.BuildingInfo;
import com.vikingz.unitycoon.building.EarnSchedule;


/**
 * AcademicBuilding
 *
 * Represents the academic building in the game
 * Inherits Building
 *
 */
public class AcademicBuilding extends Building{

    public AcademicBuilding(TextureRegion texture, float x, float y, BuildingInfo buildingInfo, float earnAmount){
        super(texture, x, y, buildingInfo, earnAmount);
        earnSchedule = EarnSchedule.SEMESTERLY;
    }
}
