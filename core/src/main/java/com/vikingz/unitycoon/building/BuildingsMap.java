package com.vikingz.unitycoon.building;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vikingz.unitycoon.achievements.IndecisiveAchievement;
import com.vikingz.unitycoon.building.buildings.AcademicBuilding;
import com.vikingz.unitycoon.building.buildings.AccommodationBuilding;
import com.vikingz.unitycoon.building.buildings.FoodBuilding;
import com.vikingz.unitycoon.building.buildings.RecreationalBuilding;
import com.vikingz.unitycoon.global.GameGlobals;
import com.vikingz.unitycoon.render.BackgroundRenderer;

/**
 * This new class is used to store and manage the placed buildings.
 * This has been added to help with organisation of code.
 */
public class BuildingsMap {

    final List<Building> placedBuildings;

    // Used by check collision routine to check collision with background
    final BackgroundRenderer backgroundRenderer;

    public BuildingsMap(BackgroundRenderer backgroundRenderer) {
        placedBuildings = new ArrayList<>();
        this.backgroundRenderer = backgroundRenderer;
    }

    public List<Building> getPlacedBuildings() {
        return placedBuildings;
    }

    /**
     * Returns a random building from the list of placed buildings.
     * This new method was added to help complete UR_EVENTS.
     * @return Building chosen.
     */
    public Building chooseRandomBuilding() {
        Random rand = new Random();
        int buildingIndex = rand.nextInt(0,placedBuildings.size());
        return placedBuildings.get(buildingIndex);
    }

    /**
     * Attempt to add a new building to the map. This method handles checking collision information 
     * and funds.
     * @param buildingInfo info of building to add.
     * @param buildingTexture texture of building to add.
     * @param x x-coordinate of position to add building to.
     * @param y y-coordinate of position to add building to.
     * @return List<Building> Empty if unsuccessful. Contains placed building otherwise.
     */
    public List<Building> attemptAddBuilding(BuildingInfo buildingInfo, TextureRegion buildingTexture, 
            float x, float y) {
        return attemptAddBuilding(buildingInfo, buildingTexture, x, y, false);
    }

    /**
     * Attempt to add a new building to the map. This method handles checking collision information
     * and funds.
     * This new function was added to allow buildings to be placed and pricing to be ignored for 
     * testing.
     * The original function was also refactored to allow new satisfaction and money handlers to be 
     * used. This helps with FR_SATISFACTION and FR_STUDENT_FINANCE.
     * @param buildingInfo info of building to add.
     * @param buildingTexture texture of building to add.
     * @param x x-coordinate of position to add building to.
     * @param y y-coordinate of position to add building to.
     * @param ignoreCost Used for testing to ignore any tests related to cost.
     * @return List<Building> Empty if unsuccessful. Contains placed building otherwise.
     */
    public List<Building> attemptAddBuilding(BuildingInfo buildingInfo, TextureRegion buildingTexture, 
            float x, float y, boolean ignoreCost) {
        List<Building> addedBuildings = new LinkedList<>();
        if (checkCollisions(x, y)) {
            // Check if the user has enough money to buy that building
            if (!ignoreCost && !BuildingStats.nextBuildingFree) {
                // Check that user is able to withdraw funds to build building
                if (!GameGlobals.MONEY.withdraw(buildingInfo.getBuildingCost())) {
                    return addedBuildings;  // Added buildings will simply be empty at this point
                }
            }

            // Ensures next building is not free.
            BuildingStats.nextBuildingFree = false;

            // Adds a building of the correct type to the list of buildings that are to be drawn 
            // to the screen.
            switch (buildingInfo.getBuildingType()) {
                case ACADEMIC:
                    addedBuildings.add(addPlacedBuilding(new AcademicBuilding(buildingTexture, x, y, 
                        buildingInfo, buildingInfo.getCoinsPerSecond())));
                    break;

                case ACCOMODATION:
                    addedBuildings.add(addPlacedBuilding(new AccommodationBuilding(buildingTexture, x, y, 
                        buildingInfo, buildingInfo.getCoinsPerSecond())));
                    break;

                case RECREATIONAL:
                    addedBuildings.add(addPlacedBuilding(new RecreationalBuilding(buildingTexture, x, y, 
                        buildingInfo, buildingInfo.getCoinsPerSecond())));
                    break;

                case FOOD:
                    addedBuildings.add(addPlacedBuilding(new FoodBuilding(buildingTexture, x, y, buildingInfo, 
                        buildingInfo.getCoinsPerSecond())));
                    break;

                default:
                    break;
            }
        }

        return addedBuildings;
    }

    /**
     * Updates stats to reflect building being built.
     * This new function was added to allow a delay between the user buying a building and that building
     * havingh affects on the game. This was to help with FR_BUILD_TIME.
     * @param building Building which has finished building.
     */
    public void builtBuilding(Building building) {
        GameGlobals.STUDENTS += building.getBuildingInfo().getNumberOfStudents();
        incrementBuildingsCount(building.getBuildingInfo().getBuildingType());
        GameGlobals.SATISFACTION.recalculateSatisfaction(getPlacedBuildings());
    }

    /**
     * Adds a new building to the list of placed buildings by y coordinate rendered in the correct order. 
     * This new method ensures buildings placed infront are displayed infront.
     */
    public Building addPlacedBuilding(Building newBuilding) {
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

        return newBuilding;
    }

    /**
     * Attempt to remove a building from the list of placed buildings.
     * This method was refactored to allow new satisfaction and money handlers to be used. 
     * This helped to complete FR_SATISFACTION and FR_STUDENT_FINANCE.
     * @param toRemove Building object to remove
     * @return List<Building>. Empty if unsuccessful, otherwise contains the removed building
     */
    public List<Building> attemptBuildingDelete(Building toRemove) {
        List<Building> removed = new LinkedList<>();
        if (toRemove != null) {
            BuildingInfo buildingInfo = toRemove.getBuildingInfo();
            placedBuildings.remove(toRemove);
            GameGlobals.MONEY.deposit(Math.round(buildingInfo.getBuildingCost()*0.75f));
            if (!toRemove.getConstructing()) {
                GameGlobals.STUDENTS -= buildingInfo.getNumberOfStudents();
                decrementBuildingsCount(buildingInfo.getBuildingType());
            }
            removed.add(toRemove);
            GameGlobals.SATISFACTION.recalculateSatisfaction(getPlacedBuildings());
        }

        return removed;
    }

    /**
     * Attempt to delete a building from the map at the specified coordinates.
     * It is important coordinates have been translated into game coordinates.
     * @param gameX
     * @param gameY
     * @return List<Building>. Empty if uncessful, otherwise contains the removed buidling.
     */
    public List<Building> attemptBuildingDeleteAt(float gameX, float gameY) {
        Building toRemove = getBuildingAtPoint(gameX, gameY);
        return attemptBuildingDelete(toRemove);
    }

    /**
     * Gets the building at the point paused in.
     * @param x Mouse X
     * @param y Mouse Y
     * @return Building that was at the coords
     */
    public Building getBuildingAtPoint(float x, float y){
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
     * Checks whether the user is trying to place a building on another building or not on grass.
     * @param x X
     * @param y Y
     * @return false if there exists a building at the spot the user is trying to place the building
     *          at or if non grass is present in the buildings spot.
     */
    private boolean checkCollisions(float x, float y){
        //Checks building exists in spot
        float RoundedX = Math.round(x);
        float RoundedY = Math.round(y);

        if (!checkCollisionBuildings(RoundedX, RoundedY)) { return false; }

        if (!checkCollisionBackground(RoundedX, RoundedY)) { return false; }

        return true;
    }

    /**
     * Checks placement is permitted based purely on other buildings.
     * This method was refactored to prevent buildings colliding unnecessarily with each other.
     * @param roundedX
     * @param roundedY
     * @return true if placement allowed, false otherwise
     */
    private boolean checkCollisionBuildings(float roundedX, float roundedY) {
        for (Building building: this.placedBuildings) {
             //Only check collision for base of building(3/4 of the way up)
            if ((roundedX > (building.getX() - GameGlobals.SCREEN_BUILDING_SIZE) && 
                    roundedX < (building.getX() + GameGlobals.SCREEN_BUILDING_SIZE)) &&
                    (roundedY > (building.getY() - GameGlobals.SCREEN_BUILDING_SIZE * 3/4) &&  
                    roundedY < (building.getY() + GameGlobals.SCREEN_BUILDING_SIZE * 3/4))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks whether a placement is allowed based on the background texture.
     * This method was refactored to prevent buildings colliding unnecessarily with obstacles.
     * @param roundedX
     * @param roundedY
     * @return true if placement is allowed, false otherwise.
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
                    TileSet[yCord][xCord] = hold.split("\n")[lengthTiles - (yIndexLow + yCord)].charAt(
                        xIndexLow + xCord);
                }catch (Exception ignored){}
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
     * Increments the counter on the screen for the corresponding building that has been placed down.
     * @param type Type of the building that has been added
     */
    private void incrementBuildingsCount(BuildingStats.BuildingType type){

        switch (type) {
                case ACADEMIC -> GameGlobals.ACADEMIC_BUILDINGS_COUNT++;
                case ACCOMODATION -> GameGlobals.ACCOMODATION_BUILDINGS_COUNT++;
                case RECREATIONAL -> GameGlobals.RECREATIONAL_BUILDINGS_COUNT++;
                case FOOD -> GameGlobals.FOOD_BUILDINGS_COUNT++;
                default -> throw new IllegalArgumentException("Unexpected value: " + type);
        }
    }

    /**
     * Increments the counter on the screen for the corresponding building that has been placed down.
     * @param type Type of the building that has been added
     */
    private void decrementBuildingsCount(BuildingStats.BuildingType type){

        switch (type) {
            case ACADEMIC -> GameGlobals.ACADEMIC_BUILDINGS_COUNT--;
            case ACCOMODATION -> GameGlobals.ACCOMODATION_BUILDINGS_COUNT--;
            case RECREATIONAL -> GameGlobals.RECREATIONAL_BUILDINGS_COUNT--;
            case FOOD -> GameGlobals.FOOD_BUILDINGS_COUNT--;
            default -> throw new IllegalArgumentException("Unexpected value: " + type);
        }

        IndecisiveAchievement indecisiveAchievement = (IndecisiveAchievement)( 
            GameGlobals.ACHIEVEMENTS.getAchievement(IndecisiveAchievement.NAME));
        indecisiveAchievement.incrementRemovedBuildings();
    }
}
