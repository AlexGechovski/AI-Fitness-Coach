package com.example.fittrainer.models;

public class Goals {
    private int goalId;
    private int profileId;
    private String goalDescription;
    private float targetWeight;
    private float targetBodyFatPercentage;
    private float targetCaloricIntake;

    // Getters and Setters


    public int getGoalId() {
        return goalId;
    }

    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getGoalDescription() {
        return goalDescription;
    }

    public void setGoalDescription(String goalDescription) {
        this.goalDescription = goalDescription;
    }

    public float getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(float targetWeight) {
        this.targetWeight = targetWeight;
    }

    public float getTargetBodyFatPercentage() {
        return targetBodyFatPercentage;
    }

    public void setTargetBodyFatPercentage(float targetBodyFatPercentage) {
        this.targetBodyFatPercentage = targetBodyFatPercentage;
    }

    public float getTargetCaloricIntake() {
        return targetCaloricIntake;
    }

    public void setTargetCaloricIntake(float targetCaloricIntake) {
        this.targetCaloricIntake = targetCaloricIntake;
    }
}

