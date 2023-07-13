package com.example.fittrainer.models;

public class Profile {
    private int profileId;
    private int userId;
    private int age;
    private String gender;
    private float height;
    private float weight;
    private float bodyFatPercentage;
    private int maintainCalories;

    // Getters and Setters

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getBodyFatPercentage() {
        return bodyFatPercentage;
    }

    public void setBodyFatPercentage(float bodyFatPercentage) {
        this.bodyFatPercentage = bodyFatPercentage;
    }

    public int getMaintainCalories() {
        return maintainCalories;
    }

    public void setMaintainCalories(int maintainCalories) {
        this.maintainCalories = maintainCalories;
    }

}

