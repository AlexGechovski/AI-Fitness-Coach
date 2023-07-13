package com.example.fittrainer.models;

import java.util.List;

public class DietaryPreferences {
    private int preferenceId;
    private int profileId;
    private boolean vegetarian;
    private boolean vegan;
    private boolean glutenFree;
    private List<String> foodAllergies;

    public int getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(int preferenceId) {
        this.preferenceId = preferenceId;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public boolean isGlutenFree() {
        return glutenFree;
    }

    public void setGlutenFree(boolean glutenFree) {
        this.glutenFree = glutenFree;
    }

    public List<String> getFoodAllergies() {
        return foodAllergies;
    }

    public void setFoodAllergies(List<String> foodAllergies) {
        this.foodAllergies = foodAllergies;
    }

    // Getters and Setters
}

