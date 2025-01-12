package com.vikingz.unitycoon.headless;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.vikingz.unitycoon.util.TimeHandler;

public class TimerTest extends TestSuper {

    /**
     * Test that if seconds can correctly convert to minute
     */
    @Test
    public void testSecondsTOMinSecs() {
        TimeHandler.Time  time1 = TimeHandler.secondsToMinSecs(125);
         // Asserts that test if 125 seconds is converted correctly
        assertEquals(2, time1.mins);
        assertEquals(5, time1.secs);
        assertEquals("02:05", time1.toString());

        TimeHandler.Time time2 = TimeHandler.secondsToMinSecs(59);
        // Asserts that test if 59 seconds is converted correctly
        assertEquals(0, time2.mins);
        assertEquals(59, time2.secs);
        assertEquals("00:59", time2.toString());

        TimeHandler.Time time3 = TimeHandler.secondsToMinSecs(300);
        // Asserts that test if 300 seconds is converted correctly
        assertEquals(5, time3.mins);
        assertEquals(0, time3.secs);
        assertEquals("05:00", time3.toString());
    }

    /**
     * Test that if Semester and Summer is in order
     */
    @Test
    public void testInGameTime() {
        // Asserts that test if Year 1 is in the right order
        assertEquals("Year 1, Summer", TimeHandler.inGameTime(290));
        assertEquals("Year 1, Semester 1", TimeHandler.inGameTime(260));
        assertEquals("Year 1, Semester 2", TimeHandler.inGameTime(220) );

        // Asserts that test if Year 2 is in the right order
        assertEquals("Year 2, Summer", TimeHandler.inGameTime(190));
        assertEquals("Year 2, Semester 1", TimeHandler.inGameTime(160) );
        assertEquals("Year 2, Semester 2", TimeHandler.inGameTime(140) );

        // Asserts that test if Year 3 is in the right order
        assertEquals("Year 3, Summer", TimeHandler.inGameTime(90));
        assertEquals("Year 3, Semester 1", TimeHandler.inGameTime(60));
        assertEquals("Year 3, Semester 2", TimeHandler.inGameTime(20));
    }

}



