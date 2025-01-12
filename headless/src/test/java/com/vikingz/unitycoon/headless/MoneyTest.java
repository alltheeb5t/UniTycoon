package com.vikingz.unitycoon.headless;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.vikingz.unitycoon.building.BuildingStats.BuildingType;
import com.vikingz.unitycoon.building.Building;
import com.vikingz.unitycoon.building.BuildingStats;
import com.vikingz.unitycoon.building.BuildingsMap;
import com.vikingz.unitycoon.building.EarnSchedule;
import com.vikingz.unitycoon.global.GameGlobals;
import com.vikingz.unitycoon.util.MoneyHandler;

public class MoneyTest extends TestSuper {

    @Test
    public void testBuildingReducesBalance() {
        BuildingsMap testMap = getTestMap();

        float initialBalance = GameGlobals.MONEY.getBalance();

        addBasicTestBuilding(testMap, BuildingType.ACADEMIC, 110, 10, true, false);
        assertEquals(initialBalance-Integer.valueOf(BuildingStats.buildingPriceDict.get(BuildingType.ACADEMIC)[0]), GameGlobals.MONEY.getBalance());
    }

    @Test
    public void testDepositWithdraw() {
        MoneyHandler testMoneyHandler = new MoneyHandler();

        float initialBalance = testMoneyHandler.getBalance();
        assertEquals(MoneyHandler.STARTING_BALANCE, initialBalance, "Confirm initial balance is allocated correctly");

        testMoneyHandler.deposit(4000);
        assertEquals(initialBalance+4000, testMoneyHandler.getBalance(), "Confirm that deposit works correctly");

        testMoneyHandler.withdraw(2300);
        assertEquals(initialBalance+1700, testMoneyHandler.getBalance(), "Confirm that withdrawal works");
    }

    @Test
    public void testEarnSemesterly() {
        BuildingsMap testMap = getTestMap();
        MoneyHandler testMoneyHandler = new MoneyHandler();

        Building testAcademic = addGetBasicTestBuilding(testMap, BuildingType.ACADEMIC, validCoords[0][0], validCoords[0][1], true, true).get(0);
        Building testAccommodation = addGetBasicTestBuilding(testMap, BuildingType.ACCOMODATION, validCoords[1][0], validCoords[1][1], true, true).get(0);

        float initialBalance = testMoneyHandler.getBalance();

        testMoneyHandler.earn(testMap.getPlacedBuildings(), EarnSchedule.SEMESTERLY);

        assertEquals(initialBalance + testAcademic.calculateProfitMade(EarnSchedule.SEMESTERLY) + testAccommodation.calculateProfitMade(EarnSchedule.SEMESTERLY),
                     testMoneyHandler.getBalance(), "The amount earned in a semester should be equal to earnings of placed buildings");

        float newBalance = testMoneyHandler.getBalance();

        testMoneyHandler.earn(testMap.getPlacedBuildings(), EarnSchedule.DAILY);

        assertEquals(newBalance, testMoneyHandler.getBalance(), "Accommodation and academic buildings shouldn't earn daily");

    }

    @Test
    public void testEarnDaily() {
        BuildingsMap testMap = getTestMap();
        MoneyHandler testMoneyHandler = new MoneyHandler();

        Building testRecreation = addGetBasicTestBuilding(testMap, BuildingType.RECREATIONAL, validCoords[0][0], validCoords[0][1], true, true).get(0);
        Building testFood = addGetBasicTestBuilding(testMap, BuildingType.FOOD, validCoords[1][0], validCoords[1][1], true, true).get(0);

        float initialBalance = testMoneyHandler.getBalance();

        testMoneyHandler.earn(testMap.getPlacedBuildings(), EarnSchedule.DAILY);

        assertEquals(initialBalance + testFood.calculateProfitMade(EarnSchedule.DAILY) + testRecreation.calculateProfitMade(EarnSchedule.DAILY),
                     testMoneyHandler.getBalance(), "The daily amount earned should be equal to earnings of placed buildings");

        float newBalance = testMoneyHandler.getBalance();

        testMoneyHandler.earn(testMap.getPlacedBuildings(), EarnSchedule.SEMESTERLY);

        assertEquals(newBalance, testMoneyHandler.getBalance(), "There should be no additional earnings at the end of s semester for Recreation and Food buildings");

    }

    @Test
    public void testEarningMultipliers() {
        BuildingsMap testMap = getTestMap();
        MoneyHandler testMoneyHandler = new MoneyHandler();

        Building testRecreation = addGetBasicTestBuilding(testMap, BuildingType.RECREATIONAL, validCoords[0][0], validCoords[0][1], true, true).get(0);
        Building testFood = addGetBasicTestBuilding(testMap, BuildingType.FOOD, validCoords[1][0], validCoords[1][1], true, true).get(0);

        float initialBalance = testMoneyHandler.getBalance();

        testMoneyHandler.applyMultiplierToType(BuildingType.RECREATIONAL, 3);

        testMoneyHandler.earn(testMap.getPlacedBuildings(), EarnSchedule.DAILY);

        assertEquals(initialBalance + testFood.calculateProfitMade(EarnSchedule.DAILY) + testRecreation.calculateProfitMade(EarnSchedule.DAILY)*3,
                     testMoneyHandler.getBalance(), "The daily amount earned should be only 1x for food but 3x for recreation");

        float newBalance = testMoneyHandler.getBalance();

        testMoneyHandler.applyMultiplierToType(BuildingType.RECREATIONAL, 2);
        testMoneyHandler.earn(testMap.getPlacedBuildings(), EarnSchedule.DAILY);

        assertEquals(newBalance + testFood.calculateProfitMade(EarnSchedule.DAILY) + testRecreation.calculateProfitMade(EarnSchedule.DAILY)*6,
                     testMoneyHandler.getBalance(), "Test that multipliers stack. The daily amount earned should be only 1x for food but 6x for recreation");
        
        testMoneyHandler.withdraw(testMoneyHandler.getBalance()+1000);

        testMoneyHandler.earn(testMap.getPlacedBuildings(), EarnSchedule.DAILY);

        assertEquals(-1000 + testFood.calculateProfitMade(EarnSchedule.DAILY)*0.5 + testRecreation.calculateProfitMade(EarnSchedule.DAILY)*3,
                     testMoneyHandler.getBalance(), "Test that earnings half when in debt. The daily amount earned should be 0.5x for food but 3x for recreation");

    }
}
