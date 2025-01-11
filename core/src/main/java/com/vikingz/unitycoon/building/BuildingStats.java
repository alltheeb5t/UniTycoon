package com.vikingz.unitycoon.building;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;
import java.util.Dictionary;

/**
 * This class contains all the static data, or data loaded from a file.
 * This class has been refactored to improve code readability.
 */
public class BuildingStats {

    /**
     * Enum of types of buildings available
     */
    public enum BuildingType {
        NONE,
        ACADEMIC,
        ACCOMODATION,
        RECREATIONAL,
        FOOD
    }

    /**
     * Dictionaries of all the buildings in files:
     * buildingNameDict contains the full names of every Building, lookup using BuildingType
     * buildingPriceDict contains the price of every building, lookup using BuildingType
     * buildingStudentDict contains the student increase of every building, lookup using BuildingType
     * buildingCoinDict contains the coins per second of every building, lookup using BuildingType
     * buildingDict contains the ShortHandNames of every building, lookup using BuildingType
     * buildingTextureMap contains the positions of every building's Texture, lookup using String of 
     *     the buildings ShortHandName
     */

    //Loaded from buildingInfo.json
    public static Dictionary<BuildingType, String[]> buildingNameDict;
    public static Dictionary<BuildingStats.BuildingType, String[]> buildingPriceDict;
    public static Dictionary<BuildingStats.BuildingType, String[]> buildingStudentDict;
    public static Dictionary<BuildingStats.BuildingType, String[]> buildingCoinDict;
    public static Dictionary<BuildingStats.BuildingType, String[]> buildingDict;
    public static ArrayList<String> BuildingIDs;

    //Loaded from TextureAtlasMap.json
    public static Dictionary<String, int[]> buildingTextureMap;

    //Textures information
    public static String textureAtlasLocation;
    public static int atlasBuildingSize = 128;

    //Allows 1 building to be built for free
    public static boolean nextBuildingFree = false;

    /**
     * Uses the params to lookup and convert values into a new BuildingInfo Object using the 
     * lookup dictionaries.
     * @param buildingType contains type of building from BuildingStats
     * @param index int contains the index of which building is being selected from Dictionary
     * @return BuildingInfo
     */
    public static BuildingInfo getInfo(BuildingStats.BuildingType buildingType, int index){

        int price, student, coins;

        //Price
        try {price = Integer.parseInt(buildingPriceDict.get(buildingType)[index]);}
        catch (Exception e){price = 100;}

        //Student
        try {student = Integer.parseInt(buildingStudentDict.get(buildingType)[index]);}
        catch (Exception e) {student = 0;}

        //Coins
        try {coins = Integer.parseInt(buildingCoinDict.get(buildingType)[index]);}
        catch (Exception e) {coins = 0;}


        try {
            return new BuildingInfo(buildingDict.get(buildingType)[index],
                buildingType,
                price,
                student,
                coins);
        }
        catch (Exception e){
            return null;
        }
    }

    /**
     * Creates the Texture Image for the Building to be drawn with using the lookup dictionary.
     * @param id String name of the building
     * @return TextureRegion
     */
    public static TextureRegion getTextureOfBuilding(String id){
        Texture textureAtlas = new Texture(Gdx.files.internal(textureAtlasLocation)); // Load the PNG
        try {
            return new TextureRegion(textureAtlas, atlasBuildingSize * buildingTextureMap.get(id)[0],
                atlasBuildingSize * buildingTextureMap.get(id)[1],
                atlasBuildingSize, atlasBuildingSize);
        }
        catch (Exception e){
            return null;
        }
    }

    /**
     * Returns a drawable Texture region, used for building ui.
     * @param id Selects which building is being used from the building StringID
     * @return TextureRegionDrawable
     */
    public static TextureRegionDrawable getTextureDrawableOfBuilding(String id) {
        return new TextureRegionDrawable(BuildingStats.getTextureOfBuilding(id));
    }
}
