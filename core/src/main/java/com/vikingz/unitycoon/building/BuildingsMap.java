package com.vikingz.unitycoon.building;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vikingz.unitycoon.achievements.IndecisiveAchievement;
import com.vikingz.unitycoon.building.buildings.AcademicBuilding;
import com.vikingz.unitycoon.building.buildings.AccommodationBuilding;
import com.vikingz.unitycoon.building.buildings.FoodBuilding;
import com.vikingz.unitycoon.building.buildings.RecreationalBuilding;
import com.vikingz.unitycoon.global.GameGlobals;
import com.vikingz.unitycoon.render.BackgroundRenderer;
import com.vikingz.unitycoon.util.Point;

public class BuildingsMap {
    // List of all buildings placed and needs rendering
    private final List<Building> placedBuildings;

    // Used by check collision routine to check collision with background
    private final BackgroundRenderer backgroundRenderer;

    public BuildingsMap(BackgroundRenderer backgroundRenderer) {
        placedBuildings = new ArrayList<>();
        this.backgroundRenderer = backgroundRenderer;
    }

    // ─── Storage Of Placed Buildings ─────────────────────────────────────

    public List<Building> getPlacedBuildings() {
        return placedBuildings;
    }

    /**
     * Attempt to add a new building to the map. This method handles checking collision information and funds
     * 
     * @param buildingInfo
     * @param buildingTexture
     * @param x
     * @param y
     * @return true if placement was successful. false otherwise
     */
    public boolean attemptAddBuilding(BuildingInfo buildingInfo, TextureRegion buildingTexture, float x, float y) {
        return attemptAddBuilding(buildingInfo, buildingTexture, x, y, false);
    }

    /**
     * Attempt to add a new building to the map. This method handles checking collision information and funds
     * 
     * @param buildingInfo
     * @param buildingTexture
     * @param x
     * @param y
     * @param ignoreCost Used for testing to ignore any tests related to cost
     * @return true if placement was successful. false otherwise
     */
    public boolean attemptAddBuilding(BuildingInfo buildingInfo, TextureRegion buildingTexture, float x, float y, boolean ignoreCost) {
        if (checkCollisions(x, y)) {
            // Check if the user has enough money to buy that building
            if (!ignoreCost) {
               float balanceAfterPurchase = GameGlobals.BALANCE - buildingInfo.getBuildingCost();
                if (balanceAfterPurchase < 0) {
                    return false;
                } 
            }
            
            // Adds a building of the correct type to the list of buildings that are
            // to be drawn to the screen.
            switch (buildingInfo.getBuildingType()) {
                case ACADEMIC:
                    addPlacedBuilding(new AcademicBuilding(buildingTexture, new Point(x, y), buildingInfo));
                    break;

                case ACCOMODATION:
                    addPlacedBuilding(new AccommodationBuilding(buildingTexture, new Point(x, y), buildingInfo, buildingInfo.getNumberOfStudents()));
                    break;

                case RECREATIONAL:
                    addPlacedBuilding(new RecreationalBuilding(buildingTexture, new Point(x, y), buildingInfo, buildingInfo.getCoinsPerSecond()));
                    break;

                case FOOD:
                    addPlacedBuilding(new FoodBuilding(buildingTexture, new Point(x, y),buildingInfo, buildingInfo.getCoinsPerSecond()));
                    break;

                case NONE:
                    System.out.println("This shouldn't have happened hmm");

                default:
                    break;
            }

            //Updates stats
            GameGlobals.BALANCE -= buildingInfo.getBuildingCost();

            return true;
        }

        return false;
    }

    /**
     * Updates stats to reflect building being built
     * @param building Building which has finished building
     */
    public void builtBuilding(Building building) {
        GameGlobals.STUDENTS += building.getBuildingInfo().getNumberOfStudents();
        incrementBuildingsCount(building.getBuildingInfo().getBuildingType());
    }

    /**
     * Adds a new building to the list of placed buildings by y coordinate
     * rendered in the correct order. Buildings infront are displayed infront.
     */
    public void addPlacedBuilding(Building newBuilding) {
        boolean isadded = false;
        for (int i = 0; i < placedBuildings.size(); i++) {
            if (placedBuildings.get(i).getY() < newBuilding.getY()) {
                placedBuildings.add(i, newBuilding);
                isadded = true;
                break;
            }
        }
        if (!isadded) {
            placedBuildings.add(newBuilding);
        }
    }

    /**
     * Attempt to remove a building from the list of placed buildings.
     * @param toRemove Building object to remove
     * @return true or false depending on if removal was successful
     */
    public Boolean attemptBuildingDelete(Building toRemove) {
        if (toRemove != null) {
            BuildingInfo buildingInfo = toRemove.getBuildingInfo();
            placedBuildings.remove(toRemove);
            GameGlobals.BALANCE += Math.round(buildingInfo.getBuildingCost()*0.75f);
            if (!toRemove.getConstructing()) {
                GameGlobals.STUDENTS -= buildingInfo.getNumberOfStudents();
                decrementBuildingsCount(buildingInfo.getBuildingType());
            }
            return true;
        }

        return false;
    }

    /**
     * Attempt to delete a building from the map at the specified coordinates.
     * It is important coordinates have been translated into game coordinates.
     * @param gameX
     * @param gameY
     * @return Boolean whether or not placement was successful
     */
    public Boolean attemptBuildingDeleteAt(float gameX, float gameY) {
        Building toRemove = getBuildingAtPoint(gameX, gameY);

        return attemptBuildingDelete(toRemove);
    }

    // ─── Helper Methods ──────────────────────────────────────────────────

    /**
     * Gets the building at the point paused in
     * @param mouseX Mouse X
     * @param mouseY Mouse Y
     * @return Building that was at the coords
     */
    private Building getBuildingAtPoint(float x, float y){

        for (Building building: this.placedBuildings) {

            float bx = building.getX();
            float by = building.getY();
            
            if(  (x > bx && x < (bx + building.getWidth())) &&
                 (y > by && y < (by + building.getHeight())) ){
                    return building;
            }
        }
        return null;
    }

    /**
     * Checks whether the user is trying to place a building on another building or not on grass
     * @param x X
     * @param y Y
     * @return false if there exists a building at the spot the user is trying to place the building at
     *          or if non grass is present in the buildings spot
     */
    private boolean checkCollisions(float x, float y){
        //Checks building exists in spot
        float RoundedX = Math.round(x);
        float RoundedY = Math.round(y);

        System.out.println(x + ":X | Y:" + y);
        
        if (!checkCollisionBuildings(RoundedX, RoundedY)) { return false; }

        if (!checkCollisionBackground(RoundedX, RoundedY)) { return false; }

        return true;
    }

    /**
     * Checks placement is permitted based purely on other buildings
     * @param roundedX
     * @param roundedY
     * @return true if placement allowed, false otherwise
     */
    private boolean checkCollisionBuildings(float roundedX, float roundedY) {
        for (Building building: this.placedBuildings) {
            if (
                (roundedX > (building.getX() - GameGlobals.SCREEN_BUILDING_SIZE) && roundedX < (building.getX() + GameGlobals.SCREEN_BUILDING_SIZE)) &&
                    (roundedY > (building.getY() - GameGlobals.SCREEN_BUILDING_SIZE/1.75) && roundedY < (building.getY() + GameGlobals.SCREEN_BUILDING_SIZE/1.75))
            ) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks whether a placement is allowed based on the background texture
     * @param roundedX
     * @param roundedY
     * @return true if placement is allowed, false otherwise
     */
    private boolean checkCollisionBackground(float roundedX, float roundedY) {
        String hold = backgroundRenderer.getMap();

        //CheckTiles on the ground are grassBlocks
        int yIndexLow = Math.round(((roundedY-64)/32)) + 3;
        int xIndexLow = Math.round((roundedX-64)/32) + 2;
        int lengthTiles = hold.split("\n").length;
        char[][] TileSet = new char[3][4]; //Only check collision for base of building(3/4 of the way up)
        for (int yCord=0;yCord<3;yCord++){
            for (int xCord=0;xCord<4;xCord++){
                try {
                    TileSet[yCord][xCord] = hold.split("\n")[lengthTiles - (yIndexLow + yCord)].charAt(xIndexLow + xCord);
                }catch (Exception ignored){

                }
            }
        }

        for (char[] itemI: TileSet) {
            for (char itemJ: itemI) {
                if (itemJ != backgroundRenderer.getGRASS() && itemJ != backgroundRenderer.getGRASS2()) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Increments the counter on the screen for the
     * corresponding building that has been placed down
     * @param type Type of the building that has been added
     */
    private void incrementBuildingsCount(BuildingStats.BuildingType type){

        switch (type) {
            case ACADEMIC -> GameGlobals.ACADEMIC_BUILDINGS_COUNT++;
            case ACCOMODATION -> GameGlobals.ACCOMODATION_BUILDINGS_COUNT++;
            case RECREATIONAL -> GameGlobals.RECREATIONAL_BUILDINGS_COUNT++;
            case FOOD -> GameGlobals.FOOD_BUILDINGS_COUNT++;
            default -> System.out.println("Building type doesnt exist!");
        }

    }

    /**
     * Increments the counter on the screen for the
     * corresponding building that has been placed down
     * @param type Type of the building that has been added
     */
    private void decrementBuildingsCount(BuildingStats.BuildingType type){

        switch (type) {
            case ACADEMIC -> GameGlobals.ACADEMIC_BUILDINGS_COUNT--;
            case ACCOMODATION -> GameGlobals.ACCOMODATION_BUILDINGS_COUNT--;
            case RECREATIONAL -> GameGlobals.RECREATIONAL_BUILDINGS_COUNT--;
            case FOOD -> GameGlobals.FOOD_BUILDINGS_COUNT--;
            default -> System.out.println("Building type doesnt exist!");
        }

        IndecisiveAchievement.incrementRemovedBuildings();
    }
}
