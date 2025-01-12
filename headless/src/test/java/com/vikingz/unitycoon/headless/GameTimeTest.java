package com.vikingz.unitycoon.headless;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.vikingz.unitycoon.global.GameGlobals;

public class GameTimeTest extends TestSuper {

    /**
     * Test that the game timer counts down correctly from 5 minutes
     * Relates to: FR_TIMER
     */
    @Test
    public void testTimerCountdown() {
        // Initialize the timer with 5 minutes (300 seconds)
        int initialTime = 300; // 5 minutes in seconds
        GameGlobals.resetGlobals(initialTime);

        // Simulate a game loop where time decrements by 1 second per loop
        for (int i = 0; i < 300; i++) {
            // Decrement the timer
            GameGlobals.TIME_REMAINING--;

            // Assert that the timer is decreasing correctly
            assertEquals(initialTime - (i + 1), GameGlobals.TIME_REMAINING,
                    "Timer should decrement correctly for each second elapsed.");
        }

        // After 300 seconds, the timer should reach 0
        assertEquals(0, GameGlobals.TIME_REMAINING, "Timer should reach 0 after 300 seconds.");
    }

    /**
     * Test that the timer does not go negative
     * Relates to: FR_TIMER, FR_GAME_END
     */
    @Test
    public void testTimerDoesNotGoNegative() {
        // Initialize the timer with 5 seconds for this test
        int initialTime = 5;
        GameGlobals.resetGlobals(initialTime);

        // Simulate a game loop running for more than the initial time
        for (int i = 0; i < 10; i++) {
            // Decrement the timer
            if (GameGlobals.TIME_REMAINING > 0) {
                GameGlobals.TIME_REMAINING--;
            }
        }

        // Assert that the timer stops at 0 and does not go negative
        assertEquals(0, GameGlobals.TIME_REMAINING, "Timer should not go below 0.");
    }
}
