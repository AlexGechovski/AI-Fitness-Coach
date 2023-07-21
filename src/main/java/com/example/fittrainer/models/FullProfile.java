package com.example.fittrainer.models;

import java.util.List;

public class FullProfile {

    private int age;
    private String gender;
    private float height;
    private float weight;
    private float bodyFatPercentage;
    private int maintainCalories;
    private List<FoodAllergy> foodAllergies;
    private List<Goals> goals;
    private List<HealthCondition> healthConditions;
    private List<DietaryPreferences> dietaryPreferences;

    public FullProfile(Profile profile, List<DietaryPreferences> dietaryPreferences, List<FoodAllergy> foodAllergies,
                       List<Goals> goals, List<HealthCondition> healthConditions) {
        this.age = profile.getAge();
        this.gender = profile.getGender();
        this.height = profile.getHeight();
        this.weight = profile.getWeight();
        this.bodyFatPercentage = profile.getBodyFatPercentage();
        this.maintainCalories = profile.getMaintainCalories();
        this.foodAllergies = foodAllergies;
        this.goals = goals;
        this.healthConditions = healthConditions;
        this.dietaryPreferences = dietaryPreferences;
    }

    private String goalsToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Goals goal : goals) {
            sb.append(goal.toString());
            sb.append(", ");
        }
        if (!goals.isEmpty()) {
            sb.setLength(sb.length() - 2);  // Remove the last comma and space
        }
        sb.append("]");
        return sb.toString();
    }
    private String healthConditionsToSting() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (HealthCondition healthCondition : healthConditions) {
            sb.append(healthCondition.toString());
            sb.append(", ");
        }
        if (!healthConditions.isEmpty()) {
            sb.setLength(sb.length() - 2);  // Remove the last comma and space
        }
        sb.append("]");
        return sb.toString();
    }
    public String toString() {
        return "FullProfile{" +
                "age=" + age +
                ", gender='" + gender + '\'' +
                ", height=" + height +
                ", weight=" + weight +

                ", goals=" + goalsToString() +
                ", healthConditions=" + healthConditionsToSting() ;
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

    public List<FoodAllergy> getFoodAllergies() {
        return foodAllergies;
    }

    public void setFoodAllergies(List<FoodAllergy> foodAllergies) {
        this.foodAllergies = foodAllergies;
    }

    public List<Goals> getGoals() {
        return goals;
    }

    public void setGoals(List<Goals> goals) {
        this.goals = goals;
    }

    public List<HealthCondition> getHealthConditions() {
        return healthConditions;
    }

    public void setHealthConditions(List<HealthCondition> healthConditions) {
        this.healthConditions = healthConditions;
    }


}
