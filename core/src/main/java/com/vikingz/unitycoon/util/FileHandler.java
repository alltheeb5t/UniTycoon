package com.vikingz.unitycoon.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import com.google.gson.Gson;
import com.vikingz.unitycoon.building.BuildingStats;

import java.util.*;

import static com.vikingz.unitycoon.building.BuildingStats.BuildingType.*;


/**
 * This class handles loading in files
 *
 * Main use is to load in the map file
 * and loading building information
 */
public class FileHandler {

    /**
     * Loads in the map from a file
     * @param fileName Name of the file to load in
     * @return String The map 
     */
    public static String loadMap(String fileName){
        String mapData = "";
        FileHandle fileHandle = Gdx.files.internal("maps/" + fileName + ".txt");
        //FileHandle fileHandle = Gdx.files.internal("maps/map1.txt");

        // Check if the file exists
        if (fileHandle.exists()) {
            // Read the content as a string
            mapData = fileHandle.readString();

        } else {
            System.out.println("File not found!: " + fileHandle.toString());
        }

        return mapData;
    }



    /**
     * Loads Building information maps,
     * into static Dictionaries in BuildingStats
     * @param fileName String Name of the file to load
     * @param textureFileName String Name of the texture file
     */
    public static void loadBuildings(String fileName, String textureFileName)  {
        FileHandle fileHandle = Gdx.files.internal("config/" + fileName + ".json");
        FileHandle textureFileHandle = Gdx.files.internal("config/" + textureFileName + ".json");
        if (fileHandle.exists() && textureFileHandle.exists()) {

            //JSON HANDLE
            String fileRead = fileHandle.readString();
            String textureFileRead = textureFileHandle.readString();
            String[] arrayDict = fileRead.split("\n");
            Gson gson = new Gson();
            if (arrayDict.length != 5){
                System.out.println("FILE CORRUPTION DETECTED");
            }

            //Name
            BuildingParse nameParser = gson.fromJson(arrayDict[0],BuildingParse.class);

            BuildingStats.BuildingNameDict = new Hashtable<BuildingStats.BuildingType, String[]>(){{
                put(ACADEMIC, nameParser.ACADEMIC);
                put(ACCOMODATION, nameParser.ACCOMODATION);
                put(RECREATIONAL, nameParser.RECREATIONAL);
                put(FOOD, nameParser.FOOD);
                put(NONE, nameParser.NONE);
            }};

            //Price
            BuildingParse priceParser = gson.fromJson(arrayDict[1],BuildingParse.class);
            BuildingStats.BuildingPriceDict = new Hashtable<BuildingStats.BuildingType, String[]>(){{
                put(ACADEMIC, priceParser.ACADEMIC);
                put(ACCOMODATION, priceParser.ACCOMODATION);
                put(RECREATIONAL, priceParser.RECREATIONAL);
                put(FOOD, priceParser.FOOD);
                put(NONE, priceParser.NONE);
            }};

            //Student
            BuildingParse studentParser = gson.fromJson(arrayDict[2],BuildingParse.class);
            BuildingStats.BuildingStudentDict = new Hashtable<BuildingStats.BuildingType, String[]>(){{
                put(ACADEMIC, studentParser.ACADEMIC);
                put(ACCOMODATION, studentParser.ACCOMODATION);
                put(RECREATIONAL, studentParser.RECREATIONAL);
                put(FOOD, studentParser.FOOD);
                put(NONE, studentParser.NONE);
            }};

            //Coins
            BuildingParse coinParser = gson.fromJson(arrayDict[3],BuildingParse.class);
            BuildingStats.BuildingCoinDict = new Hashtable<BuildingStats.BuildingType, String[]>(){{
                put(ACADEMIC, coinParser.ACADEMIC);
                put(ACCOMODATION, coinParser.ACCOMODATION);
                put(RECREATIONAL, coinParser.RECREATIONAL);
                put(FOOD, coinParser.FOOD);
                put(NONE, coinParser.NONE);
            }};

            //IDs
            BuildingParse idParser = gson.fromJson(arrayDict[4],BuildingParse.class);
            BuildingStats.BuildingDict = new Hashtable<BuildingStats.BuildingType, String[]>(){{
                put(ACADEMIC, idParser.ACADEMIC);
                put(ACCOMODATION, idParser.ACCOMODATION);
                put(RECREATIONAL, idParser.RECREATIONAL);
                put(FOOD, idParser.FOOD);
                put(NONE, idParser.NONE);
            }};

            //passing child elements from types
            Enumeration<String[]> BuildingIDsIterator = BuildingStats.BuildingDict.elements();
            BuildingStats.BuildingIDs = new ArrayList<String>();
            while (BuildingIDsIterator.hasMoreElements()){
                for (String item :BuildingIDsIterator.nextElement()) {
                    if (item != null){
                        BuildingStats.BuildingIDs.add(item);
                    }
                }
            }


            //Textures
            BuildingStats.BuildingTextureMap = new Hashtable<String, int[]>();
            TextureParse textureParse = gson.fromJson(textureFileRead,TextureParse.class);
            BuildingStats.textureAtlasLocation = textureParse.textureAtlasLocation;
            BuildingStats.atlasBuildingSize = textureParse.atlasBuildingSize;
            for (int i=0;i<textureParse.buildings.size();i++){
                int[] convertValue = new int[]{Integer.parseInt(textureParse.buildingPos.get(i).split(",")[0]),
                                                Integer.parseInt(textureParse.buildingPos.get(i).split(",")[1]),
                };
                BuildingStats.BuildingTextureMap.put(textureParse.buildings.get(i),convertValue);
            }


            System.out.println("Successfully Loaded Buildings");

        } else {
            System.err.println("File not found!: " + fileHandle.toString());
        }
    }


}

/**
 * Object parser that has public variable
 * to allow map json to dictionary.
 */
class BuildingParse {
    public String ACADEMIC[];
    public String ACCOMODATION[];
    public String RECREATIONAL[];
    public String FOOD[];
    public String NONE[];
}

class TextureParse {
    public String textureAtlasLocation ="textureAtlases/buildingsAtlas.png";
    public int atlasBuildingSize = 128;
    ArrayList<String> buildings;
    ArrayList<String> buildingPos;

}



