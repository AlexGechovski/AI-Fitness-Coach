package com.example.fittrainer.controllers;

import com.example.fittrainer.models.*;
import com.example.fittrainer.services.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
@Api(tags = "Profiles")
public class ProfileController {
    private final ProfileService profileService;
    private final FoodAllergiesService foodAllergyService;
    private final GoalsService goalsService;
    private final HealthConditionsService healthConditionsService;
    private final DietaryPreferencesService dietaryPreferencesService;

    @Autowired
    public ProfileController(ProfileService profileService, FoodAllergiesService foodAllergiesService,
                             GoalsService goalsService, HealthConditionsService healthConditionsService, DietaryPreferencesService dietaryPreferencesService) {
        this.profileService = profileService;
        this.foodAllergyService = foodAllergiesService;
        this.goalsService = goalsService;
        this.healthConditionsService = healthConditionsService;
        this.dietaryPreferencesService = dietaryPreferencesService;
    }

    // Create a new profile
    @PostMapping
    @ApiOperation("Create a new profile")
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) {
        Profile createdProfile = profileService.createProfile(profile);
        return ResponseEntity.ok(createdProfile);
    }

//    // Get profile details by username
//    @GetMapping("/{username}")
//    @ApiOperation("Get profile details by username")
//    public ResponseEntity<Profile> getProfileByUsername(@PathVariable String username) {
//        Profile profile = profileService.getProfileByUsername(username);
//        return ResponseEntity.ok(profile);
//    }
    @GetMapping("/{username}")
    @ApiOperation("Get profile details by username")
    public ResponseEntity<FullProfile> getProfileByUsername(@PathVariable String username) {
        FullProfile profile = profileService.getFullProfileByUsername(username);
        return ResponseEntity.ok(profile);
    }

    // Update profile details by username
    @PutMapping("/{username}")
    @ApiOperation("Update profile details by username")
    public ResponseEntity<Profile> updateProfile(@PathVariable String username, @RequestBody Profile profile) {
        Profile updatedProfile = profileService.updateProfile(username, profile);
        return ResponseEntity.ok(updatedProfile);
    }

    // Delete a profile by username
    @DeleteMapping("/{username}")
    @ApiOperation("Delete a profile by username")
    public ResponseEntity<Void> deleteProfile(@PathVariable String username,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        if (!username.equals(userDetails.getUsername())) {
            throw new RuntimeException("You are not authorized to delete this profile.");
        }

        profileService.deleteProfileByUsername(username);
        return ResponseEntity.noContent().build();
    }

    // Create a food allergy for a profile
    // Create a food allergy for a profile
    @PostMapping("/{username}/food-allergies")
    @ApiOperation("Create a food allergy for a profile")
    public ResponseEntity<FoodAllergy> createFoodAllergy(@PathVariable String username,
                                                         @RequestBody FoodAllergy foodAllergy) {
        FoodAllergy createdAllergy = foodAllergyService.createFoodAllergy(username, foodAllergy);
        return ResponseEntity.ok(createdAllergy);
    }

    // Get food allergies for a profile
    @GetMapping("/{username}/food-allergies")
    @ApiOperation("Get food allergies for a profile")
    public ResponseEntity<List<FoodAllergy>> getFoodAllergies(@PathVariable String username) {
        List<FoodAllergy> allergies = foodAllergyService.getFoodAllergiesByUsername(username);
        return ResponseEntity.ok(allergies);
    }

    // Update a specific food allergy for a profile
    @PutMapping("/{username}/food-allergies/{allergyId}")
    @ApiOperation("Update a specific food allergy for a profile")
    public ResponseEntity<FoodAllergy> updateFoodAllergy(@PathVariable String username, @PathVariable int allergyId,
                                                         @RequestBody FoodAllergy foodAllergy) {
        foodAllergy.setAllergyId(allergyId);
        FoodAllergy updatedAllergy = foodAllergyService.updateFoodAllergy(foodAllergy);
        return ResponseEntity.ok(updatedAllergy);
    }

    // Delete a specific food allergy for a profile
    @DeleteMapping("/{username}/food-allergies/{allergyId}")
    @ApiOperation("Delete a specific food allergy for a profile")
    public ResponseEntity<Void> deleteFoodAllergy(@PathVariable String username, @PathVariable int allergyId) {

        foodAllergyService.deleteFoodAllergyById(allergyId);
        return ResponseEntity.noContent().build();
    }


    // Create goals for a profile
    @PostMapping("/{username}/goals")
    public ResponseEntity<Goals> createGoals(@PathVariable String username, @RequestBody Goals goals) {
        Goals createdGoals = goalsService.createGoal(username, goals);
        return ResponseEntity.ok(createdGoals);
    }

    // Get goals for a profile
    @GetMapping("/{username}/goals")
    @ApiOperation("Get goals for a profile")
    public ResponseEntity<List<Goals>> getGoals(@PathVariable String username) {
        List<Goals> goals = goalsService.getGoalsByUsername(username);
        return ResponseEntity.ok(goals);
    }

    // Update a specific goal for a profile
    @PutMapping("/{username}/goals/{goalId}")
    @ApiOperation("Update a specific goal for a profile")
    public ResponseEntity<Goals> updateGoal(@PathVariable String username, @PathVariable int goalId,
                                            @RequestBody Goals goals) {
        goals.setGoalId(goalId);
        Goals updatedGoal = goalsService.updateGoal(goals);
        return ResponseEntity.ok(updatedGoal);
    }

    // Delete a specific goal for a profile
    @DeleteMapping("/{username}/goals/{goalId}")
    @ApiOperation("Delete a specific goal for a profile")
    public ResponseEntity<Void> deleteGoal(@PathVariable String username, @PathVariable int goalId) {
        goalsService.deleteGoalById(goalId);
        return ResponseEntity.noContent().build();
    }

    // Create a health condition for a profile
    @PostMapping("/{username}/health-conditions")
    @ApiOperation("Create a health condition for a profile")
    public ResponseEntity<HealthCondition> createHealthCondition(@PathVariable String username,
                                                                  @RequestBody HealthCondition healthCondition) {
        HealthCondition createdCondition = healthConditionsService.createHealthCondition(username, healthCondition);
        return ResponseEntity.ok(createdCondition);
    }

    // Get health conditions for a profile
    @GetMapping("/{username}/health-conditions")
    @ApiOperation("Get health conditions for a profile")
    public ResponseEntity<List<HealthCondition>> getHealthConditions(@PathVariable String username) {
        List<HealthCondition> conditions = healthConditionsService.getHealthConditionsByUsername(username);
        return ResponseEntity.ok(conditions);
    }

    // Update a specific health condition for a profile
    @PutMapping("/{username}/health-conditions/{conditionId}")
    @ApiOperation("Update a specific health condition for a profile")
    public ResponseEntity<HealthCondition> updateHealthCondition(@PathVariable String username,
                                                                  @PathVariable int conditionId,
                                                                  @RequestBody HealthCondition healthCondition) {
        healthCondition.setConditionId(conditionId);
        HealthCondition updatedCondition = healthConditionsService.updateHealthCondition(healthCondition);
        return ResponseEntity.ok(updatedCondition);
    }

    // Delete a specific health condition for a profile
    @DeleteMapping("/{username}/health-conditions/{conditionId}")
    @ApiOperation("Delete a specific health condition for a profile")
    public ResponseEntity<Void> deleteHealthCondition(@PathVariable String username, @PathVariable int conditionId) {

        healthConditionsService.deleteHealthConditionById(conditionId);
        return ResponseEntity.noContent().build();
    }


    // Create a dietary preference for a profile
    @PostMapping("/{username}/dietary-preferences")
    @ApiOperation("Create a dietary preference for a profile")
    public ResponseEntity<DietaryPreferences> createDietaryPreference(@PathVariable String username,
                                                                      @RequestBody DietaryPreferences dietaryPreferences) {
        DietaryPreferences createdPreference = dietaryPreferencesService.createDietaryPreference(username, dietaryPreferences);
        return ResponseEntity.ok(createdPreference);
    }

    // Get dietary preferences for a profile
    @GetMapping("/{username}/dietary-preferences")
    @ApiOperation("Get dietary preferences for a profile")
    public ResponseEntity<List<DietaryPreferences>> getDietaryPreferences(@PathVariable String username) {
        List<DietaryPreferences> preferences = dietaryPreferencesService.getDietaryPreferencesByUsername(username);
        return ResponseEntity.ok(preferences);
    }

    // Update a specific dietary preference for a profile
    @PutMapping("/{username}/dietary-preferences/{preferenceId}")
    @ApiOperation("Update a specific dietary preference for a profile")
    public ResponseEntity<DietaryPreferences> updateDietaryPreference(@PathVariable String username,
                                                                      @PathVariable int preferenceId,
                                                                      @RequestBody DietaryPreferences dietaryPreferences) {
        dietaryPreferences.setPreferenceId(preferenceId);
        DietaryPreferences updatedPreference = dietaryPreferencesService.updateDietaryPreference(username, dietaryPreferences);
        return ResponseEntity.ok(updatedPreference);
    }

    // Delete a specific dietary preference for a profile
    @DeleteMapping("/{username}/dietary-preferences/{preferenceId}")
    @ApiOperation("Delete a specific dietary preference for a profile")
    public ResponseEntity<Void> deleteDietaryPreference(@PathVariable String username, @PathVariable int preferenceId) {

        dietaryPreferencesService.deleteDietaryPreference(preferenceId);
        return ResponseEntity.noContent().build();
    }


}
