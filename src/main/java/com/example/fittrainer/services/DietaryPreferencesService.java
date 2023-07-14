package com.example.fittrainer.services;

import com.example.fittrainer.models.DietaryPreferences;
import com.example.fittrainer.repositories.DietaryPreferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DietaryPreferencesService {
    private final DietaryPreferencesRepository dietaryPreferencesRepository;
    private final ProfileService profileService;

    @Autowired
    public DietaryPreferencesService(DietaryPreferencesRepository dietaryPreferencesRepository, ProfileService profileService) {
        this.dietaryPreferencesRepository = dietaryPreferencesRepository;
        this.profileService = profileService;
    }

    public DietaryPreferences createDietaryPreference(String username ,DietaryPreferences dietaryPreferences) {
        dietaryPreferences.setProfileId(profileService.getProfileIdByUsername(username));
        return dietaryPreferencesRepository.create(dietaryPreferences);
    }

    public List<DietaryPreferences> findDietaryPreferencesByProfileId(int profileId) {
        return dietaryPreferencesRepository.findByProfileId(profileId);
    }

    public DietaryPreferences findDietaryPreferenceById(int preferenceId) {
        return dietaryPreferencesRepository.findById(preferenceId);
    }

    public DietaryPreferences updateDietaryPreference(String username, DietaryPreferences updatedDietaryPreferences) {
        // Get the existing dietary preferences from the repository

        int id = profileService.getProfileIdByUsername(username);
        DietaryPreferences existingDietaryPreferences = dietaryPreferencesRepository.findById(id);

        if (existingDietaryPreferences == null) {
            throw new IllegalArgumentException("Dietary Preferences not found with ID: " + id);
        }

        // Update the relevant fields of the existing dietary preferences with the new data
        existingDietaryPreferences.setVegetarian(updatedDietaryPreferences.isVegetarian());
        existingDietaryPreferences.setVegan(updatedDietaryPreferences.isVegan());
        existingDietaryPreferences.setGlutenFree(updatedDietaryPreferences.isGlutenFree());

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

    public List<DietaryPreferences> getDietaryPreferencesByUsername(String username) {
        return dietaryPreferencesRepository.findByProfileId(profileService.getProfileIdByUsername(username));
    }

    public void deleteDietaryPreference(int preferenceId) {
        dietaryPreferencesRepository.deleteById(preferenceId);

    }
}
