package com.vikingz.unitycoon.headless;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.validateMockitoUsage;

import java.util.Timer;
import java.util.TimerTask;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.vikingz.unitycoon.util.TimeUtil;

public class TimerTest {
    @BeforeEach
    public void setup() {
        System.out.println("Starting LibGDX Headless");
        Gdx.gl = Gdx.gl20 = mock(GL20.class);
        HeadlessLauncher.main(new String[0]);
        
        System.out.println("Started Headless Mode");
    }
    @Test
    void testSecondsTOMinSecs(){
        TimeUtil.Time  time1 = TimeUtil.secondsToMinSecs(125);
        assertEquals(2, time1.mins);
        assertEquals(5, time1.secs);
        assertEquals("02:05", time1.toString());

        TimeUtil.Time time2 = TimeUtil.secondsToMinSecs(59);
        assertEquals(0, time2.mins);
        assertEquals(59, time2.secs);
        assertEquals("00:59", time2.toString());

        TimeUtil.Time time3 = TimeUtil.secondsToMinSecs(300);
        assertEquals(5, time3.mins);
        assertEquals(0, time3.secs);
        assertEquals("05:00", time3.toString());
    }

    @Test
    void testInGameTime(){
        assertEquals("Year 1, Summer", TimeUtil.inGameTime(290));
        assertEquals("Year 1, Semester 1", TimeUtil.inGameTime(260));
        assertEquals("Year 1, Semester 2", TimeUtil.inGameTime(220) );

        assertEquals("Year 2, Summer", TimeUtil.inGameTime(190));
        assertEquals("Year 2, Semester 1", TimeUtil.inGameTime(160) );
        assertEquals("Year 2, Semester 2", TimeUtil.inGameTime(140) );

        assertEquals("Year 3, Summer", TimeUtil.inGameTime(90));
        assertEquals("Year 3, Semester 1", TimeUtil.inGameTime(60));
        assertEquals("Year 3, Semester 2", TimeUtil.inGameTime(20));
    }

    @Test
    void testTicking (){
        

    }

        

}
