package com.example.fittrainer.models;

public class UserWorkoutDay {
    private int userWorkoutDayId;
    private int profileId;
    private int dayId;
    private int workoutId;

    private long workoutDayId;

    // Constructor, getters, and setters


    public long getWorkoutDayId() {
        return workoutDayId;
    }

    public void setWorkoutDayId(long workoutDayId) {
        this.workoutDayId = workoutDayId;
    }

    public int getUserWorkoutDayId() {
        return userWorkoutDayId;
    }

    public void setUserWorkoutDayId(int userWorkoutDayId) {
        this.userWorkoutDayId = userWorkoutDayId;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public int getDayId() {
        return dayId;
    }

    public void setDayId(int dayId) {
        this.dayId = dayId;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public String getDay() {
        switch (dayId) {
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
                return "Saturday";
            case 7:
                return "Sunday";
            default:
                return "Invalid day";
        }
    }


}
