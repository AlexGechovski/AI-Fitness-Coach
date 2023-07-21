package com.example.fittrainer.models;

public class HealthCondition {
    private int conditionId;
    private int profileId;
    private String conditionDescription;

    // Getters and Setters

    @Override
    public String toString() {
        return "HealthCondition{" +
                "conditionDescription='" + conditionDescription + '\'' +
                '}';
    }

    public int getConditionId() {
        return conditionId;
    }

    public void setConditionId(int conditionId) {
        this.conditionId = conditionId;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getConditionDescription() {
        return conditionDescription;
    }

    public void setConditionDescription(String conditionDescription) {
        this.conditionDescription = conditionDescription;
    }
}
