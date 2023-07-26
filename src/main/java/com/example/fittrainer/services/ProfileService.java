package com.example.fittrainer.services;

import com.example.fittrainer.config.JwtService;
import com.example.fittrainer.models.*;
import com.example.fittrainer.repositories.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserService userService;
    private final JwtService jwtService;
    private final DietaryPreferencesRepository dietaryPreferencesRepository;
    private final FoodAllergiesRepository foodAllergiesRepository;
    private final GoalsRepository goalsRepository;
    private final HealthConditionsRepository healthConditionsRepository;

    public ProfileService(ProfileRepository profileRepository, UserService userService, JwtService jwtService, DietaryPreferencesRepository dietaryPreferencesRepository, FoodAllergiesRepository foodAllergiesRepository, GoalsRepository goalsRepository, HealthConditionsRepository healthConditionsRepository) {
        this.profileRepository = profileRepository;
        this.userService = userService;
        this.jwtService = jwtService;
        this.dietaryPreferencesRepository = dietaryPreferencesRepository;
        this.foodAllergiesRepository = foodAllergiesRepository;
        this.goalsRepository = goalsRepository;
        this.healthConditionsRepository = healthConditionsRepository;
    }

    public Profile createProfile(Profile profile) {
        String username = jwtService.getCurrentUsername();
        User userDetails = userService.findByUsername(username);

        // Check if a profile already exists for the user
        if (profileRepository.existsByUserId(userDetails.getId())){
            throw new IllegalStateException("Profile already exists for the user");
        }

        profile.setUserId(Math.toIntExact(userDetails.getId()));

        return profileRepository.create(profile);
    }

    public Profile getProfileById(int profileId) {
        return profileRepository.findById(profileId);
    }

    public FullProfile updateProfile(String username, Profile updatedProfile) {
        String tokenUsername = jwtService.getCurrentUsername();
        User userDetails = userService.findByUsername(tokenUsername);

        Profile existingProfile = profileRepository.findByUserId(userDetails.getId());

        if (existingProfile == null) {
            throw new IllegalArgumentException("Profile not found with username: " + username);
        }

        // Update the relevant fields of the existing profile with the new data
        existingProfile.setAge(updatedProfile.getAge());
        existingProfile.setGender(updatedProfile.getGender());
        existingProfile.setHeight(updatedProfile.getHeight());
        existingProfile.setWeight(updatedProfile.getWeight());
        existingProfile.setBodyFatPercentage(updatedProfile.getBodyFatPercentage());
        existingProfile.setMaintainCalories(updatedProfile.getMaintainCalories());

        // Save the updated profile back to the repository
        profileRepository.update(existingProfile);
        return getFullProfileByUsername(username);
    }



    public void deleteProfileById(int profileId) {
        profileRepository.deleteById(profileId);
    }

    public Profile getProfileByUsername(String username) {
        String tokenUsername = jwtService.getCurrentUsername();
        User userDetails = userService.findByUsername(tokenUsername);
        return profileRepository.findByUserId(userDetails.getId());
    }

    public Profile getCurrentProfile() {
        String tokenUsername = jwtService.getCurrentUsername();
        User userDetails = userService.findByUsername(tokenUsername);
        return profileRepository.findByUserId(userDetails.getId());
    }

    public int getProfileIdByUsername(String username) {
        String tokenUsername = jwtService.getCurrentUsername();
        User userDetails = userService.findByUsername(tokenUsername);
        return profileRepository.findByUserId(userDetails.getId()).getProfileId();
    }

    public void deleteProfileByUsername(String username) {

        Profile profile = getProfileByUsername(username);

        profileRepository.deleteById(profile.getProfileId());
    }

    public FullProfile getFullProfileByUsername(String username) {

          String tokenUsername = jwtService.getCurrentUsername();
          User userDetails = userService.findByUsername(tokenUsername);
          Profile profile = profileRepository.findByUserId(userDetails.getId());

            if (profile == null) {
                throw new RuntimeException("Profile not found with username: " + username);
            }

            List<DietaryPreferences> dietaryPreferences = dietaryPreferencesRepository.findByProfileId(profile.getProfileId());
            List<FoodAllergy> foodAllergies = foodAllergiesRepository.findByProfileId(profile.getProfileId());
            List<Goals> goals = goalsRepository.findByProfileId(profile.getProfileId());
            List<HealthCondition> healthConditions = healthConditionsRepository.findByProfileId(profile.getProfileId());

            return new FullProfile(profile, dietaryPreferences, foodAllergies, goals, healthConditions);
     }
}

