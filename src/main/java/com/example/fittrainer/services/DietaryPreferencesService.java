package com.example.fittrainer.services;

import com.example.fittrainer.models.DietaryPreferences;
import com.example.fittrainer.repositories.DietaryPreferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DietaryPreferencesService {
    private final DietaryPreferencesRepository dietaryPreferencesRepository;

    @Autowired
    public DietaryPreferencesService(DietaryPreferencesRepository dietaryPreferencesRepository) {
        this.dietaryPreferencesRepository = dietaryPreferencesRepository;
    }

    public DietaryPreferences createDietaryPreference(DietaryPreferences dietaryPreferences) {
        return dietaryPreferencesRepository.create(dietaryPreferences);
    }

    public List<DietaryPreferences> findDietaryPreferencesByProfileId(int profileId) {
        return dietaryPreferencesRepository.findByProfileId(profileId);
    }

    public DietaryPreferences findDietaryPreferenceById(int preferenceId) {
        return dietaryPreferencesRepository.findById(preferenceId);
    }

    public DietaryPreferences updateDietaryPreference(int id, DietaryPreferences updatedDietaryPreferences) {
        DietaryPreferences existingDietaryPreferences = dietaryPreferencesRepository.findById(id);

        if (existingDietaryPreferences == null) {
            throw new IllegalArgumentException("Dietary Preferences not found with ID: " + id);
        }

        // Update the relevant fields of the existing dietary preferences with the new data
        existingDietaryPreferences.setVegetarian(updatedDietaryPreferences.isVegetarian());
        existingDietaryPreferences.setVegan(updatedDietaryPreferences.isVegan());
        existingDietaryPreferences.setGlutenFree(updatedDietaryPreferences.isGlutenFree());
        existingDietaryPreferences.setFoodAllergies(updatedDietaryPreferences.getFoodAllergies());

        // Save the updated dietary preferences back to the repository
        return dietaryPreferencesRepository.update(existingDietaryPreferences);
    }



    public void deleteDietaryPreferenceById(int preferenceId) {
        dietaryPreferencesRepository.deleteById(preferenceId);
    }

    public DietaryPreferences createDietaryPreferences(int id, DietaryPreferences dietaryPreferences) {
        dietaryPreferences.setProfileId(id);
        return dietaryPreferencesRepository.create(dietaryPreferences);
    }

}
