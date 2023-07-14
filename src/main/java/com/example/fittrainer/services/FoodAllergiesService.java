package com.example.fittrainer.services;

import com.example.fittrainer.models.FoodAllergy;
import com.example.fittrainer.repositories.FoodAllergiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.IllegalFormatCodePointException;
import java.util.List;

@Service
public class FoodAllergiesService {
    private final FoodAllergiesRepository foodAllergiesRepository;
    private final ProfileService profileService;

    @Autowired
    public FoodAllergiesService(FoodAllergiesRepository foodAllergiesRepository, ProfileService profileService) {
        this.foodAllergiesRepository = foodAllergiesRepository;

        this.profileService = profileService;
    }

    public FoodAllergy createFoodAllergy(String username, FoodAllergy foodAllergy) {
        if(foodAllergy.getAllergyName() == null) {
            throw new IllegalArgumentException("Allergy name cannot be null");
        }
        foodAllergy.setProfileId(profileService.getProfileIdByUsername(username));
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

    public List<FoodAllergy> getFoodAllergiesByUsername(String username) {
        return foodAllergiesRepository.findByProfileId(profileService.getProfileIdByUsername(username));
    }
}
