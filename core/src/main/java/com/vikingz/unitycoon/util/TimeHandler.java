package com.vikingz.unitycoon.util;

/**
 * This class contains functions that we use to format time.
 *
 * Contains an inner class {@code Time} simply just to serve as
 * a return type once the formatting is done
 */
public class TimeHandler {

    public boolean isPaused() {
        return isPaused;
    }

    //determines if the game is paused
    private boolean isPaused;

    /**
     * This class contains three attributes secs, mins and hrs.
     *
     * Only serves as a return type
     *
     */
    public static class Time{

        public int secs;
        public int mins;
        public int hrs;

        public Time(int secs, int mins, int hrs){
            this.secs = secs;
            this.mins = mins;
            this.hrs = hrs;
        }

        public Time(){
            this.secs = 0;
            this.mins = 0;
            this.hrs = 0;
        }

        /**
         * Returns the time in a min:second format where each value will always
         * be padded to 2 digits
         */
        public String toString(){
            return String.format("%02d", mins) + ":" + String.format("%02d", secs);
        }

    }

    /**
     * Format time from seconds to minutes and seconds
     * @param secs Time in seconds
     * @return Time in minutes and seconds
     */
    public static Time secondsToMinSecs(int secs){

        int m = secs/ 60;
        int s = secs % 60;

        return new Time(s, m, 0);
    }

    /**
     * Calculates the in game time which totals 3 years.
     * Each year has two 40s semesters and a 20s summer.
     * @param secs Time in seconds
     * @return In game time as string written "Year #, Semester #"
     */
    public static String inGameTime(int secs) {
        final int SECONDS_PER_YEAR = 100;
        final int SECONDS_PER_SUMMER = 20;
        final int SECONDS_PER_SEMESTER = 40;

        int timePassed = 300 - secs;
        int year = 0;
        String semester = "";

        year = (timePassed / SECONDS_PER_YEAR) + 1;

        //Seconds passed in the current year.
        int annualELapsedSeconds = timePassed - (year - 1) * SECONDS_PER_YEAR;

        //Calulates part of the year.
        if (annualELapsedSeconds < SECONDS_PER_SUMMER) {
            semester = "Summer";
        }
        else if (annualELapsedSeconds < SECONDS_PER_SUMMER + SECONDS_PER_SEMESTER) {
            semester = "Semester 1";
        }
        else {
            semester = "Semester 2";
        }

        return "Year " + String.valueOf(year) + ", " + semester;
    }

    /**
     * Sets the game to be paused
     * @param isPaused boolean of if the game is paused
     */
    public void setPaused(boolean isPaused){
        this.isPaused = isPaused;
    }

}

