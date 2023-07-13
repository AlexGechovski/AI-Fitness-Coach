package com.example.fittrainer.services;

import com.example.fittrainer.models.FoodAllergy;
import com.example.fittrainer.repositories.FoodAllergiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodAllergiesService {
    private final FoodAllergiesRepository foodAllergiesRepository;

    @Autowired
    public FoodAllergiesService(FoodAllergiesRepository foodAllergiesRepository) {
        this.foodAllergiesRepository = foodAllergiesRepository;
    }

    public FoodAllergy createFoodAllergy(FoodAllergy foodAllergy) {
        return foodAllergiesRepository.create(foodAllergy);
    }

    public List<FoodAllergy> findFoodAllergiesByProfileId(int profileId) {
        return foodAllergiesRepository.findByProfileId(profileId);
    }

    public FoodAllergy findFoodAllergyById(int allergyId) {
        return foodAllergiesRepository.findById(allergyId);
    }

    public FoodAllergy updateFoodAllergy(FoodAllergy foodAllergy) {
        return foodAllergiesRepository.update(foodAllergy);
    }

    public void deleteFoodAllergyById(int allergyId) {
        foodAllergiesRepository.deleteById(allergyId);
    }
}
