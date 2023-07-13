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
    private final FoodAllergiesService foodAllergiesService;
    private final GoalsService goalsService;
    private final HealthConditionsService healthConditionsService;

    @Autowired
    public ProfileController(ProfileService profileService, FoodAllergiesService foodAllergiesService,
                             GoalsService goalsService, HealthConditionsService healthConditionsService) {
        this.profileService = profileService;
        this.foodAllergiesService = foodAllergiesService;
        this.goalsService = goalsService;
        this.healthConditionsService = healthConditionsService;
    }

    // Create a new profile
    @PostMapping
    @ApiOperation("Create a new profile")
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) {
        Profile createdProfile = profileService.createProfile(profile);
        return ResponseEntity.ok(createdProfile);
    }

    // Get profile details by username
    @GetMapping("/{username}")
    @ApiOperation("Get profile details by username")
    public ResponseEntity<Profile> getProfileByUsername(@PathVariable String username) {
        Profile profile = profileService.getProfileByUsername(username);
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

//    // Create a food allergy for a profile
//    @PostMapping("/{username}/food-allergies")
//    @ApiOperation("Create a food allergy for a profile")
//    public ResponseEntity<FoodAllergies> createFoodAllergy(@PathVariable String username,
//                                                           @RequestBody FoodAllergies foodAllergy) {
//        FoodAllergies createdAllergy = foodAllergiesService.createFoodAllergy(username, foodAllergy);
//        return ResponseEntity.ok(createdAllergy);
//    }
//
//    // Get food allergies for a profile
//    @GetMapping("/{username}/food-allergies")
//    @ApiOperation("Get food allergies for a profile")
//    public ResponseEntity<List<FoodAllergies>> getFoodAllergies(@PathVariable String username) {
//        List<FoodAllergies> allergies = foodAllergiesService.getFoodAllergiesByUsername(username);
//        return ResponseEntity.ok(allergies);
//    }
//
//    // Update a specific food allergy for a profile
//    @PutMapping("/{username}/food-allergies/{allergyId}")
//    @ApiOperation("Update a specific food allergy for a profile")
//    public ResponseEntity<FoodAllergies> updateFoodAllergy(@PathVariable String username, @PathVariable int allergyId,
//                                                           @RequestBody FoodAllergies foodAllergy) {
//        foodAllergy.setPreferenceId(allergyId);
//        FoodAllergies updatedAllergy = foodAllergiesService.updateFoodAllergy(foodAllergy);
//        return ResponseEntity.ok(updatedAllergy);
//    }
//
//    // Delete a specific food allergy for a profile
//    @DeleteMapping("/{username}/food-allergies/{allergyId}")
//    @ApiOperation("Delete a specific food allergy for a profile")
//    public ResponseEntity<Void> deleteFoodAllergy(@PathVariable String username, @PathVariable int allergyId,
//                                                  @AuthenticationPrincipal UserDetails userDetails) {
//        if (!username.equals(userDetails.getUsername())) {
//            throw new UnauthorizedException("You are not authorized to delete this food allergy.");
//        }
//
//        foodAllergiesService.deleteFoodAllergyById(allergyId);
//        return ResponseEntity.noContent().build();
//    }
//
//    // Create goals for a profile
//    @PostMapping("/{username}/goals")
//    @ApiOperation("Create goals for a profile")
//    public ResponseEntity<Goals> createGoals(@PathVariable String username, @RequestBody Goals goals) {
//        Goals createdGoals = goalsService.createGoals(username, goals);
//        return ResponseEntity.ok(createdGoals);
//    }
//
//    // Get goals for a profile
//    @GetMapping("/{username}/goals")
//    @ApiOperation("Get goals for a profile")
//    public ResponseEntity<List<Goals>> getGoals(@PathVariable String username) {
//        List<Goals> goals = goalsService.getGoalsByUsername(username);
//        return ResponseEntity.ok(goals);
//    }
//
//    // Update a specific goal for a profile
//    @PutMapping("/{username}/goals/{goalId}")
//    @ApiOperation("Update a specific goal for a profile")
//    public ResponseEntity<Goals> updateGoal(@PathVariable String username, @PathVariable int goalId,
//                                            @RequestBody Goals goals) {
//        goals.setGoalId(goalId);
//        Goals updatedGoal = goalsService.updateGoal(goals);
//        return ResponseEntity.ok(updatedGoal);
//    }
//
//    // Delete a specific goal for a profile
//    @DeleteMapping("/{username}/goals/{goalId}")
//    @ApiOperation("Delete a specific goal for a profile")
//    public ResponseEntity<Void> deleteGoal(@PathVariable String username, @PathVariable int goalId,
//                                           @AuthenticationPrincipal UserDetails userDetails) {
//        if (!username.equals(userDetails.getUsername())) {
//            throw new UnauthorizedException("You are not authorized to delete this goal.");
//        }
//
//        goalsService.deleteGoalById(goalId);
//        return ResponseEntity.noContent().build();
//    }
//
//    // Create a health condition for a profile
//    @PostMapping("/{username}/health-conditions")
//    @ApiOperation("Create a health condition for a profile")
//    public ResponseEntity<HealthConditions> createHealthCondition(@PathVariable String username,
//                                                                  @RequestBody HealthConditions healthCondition) {
//        HealthConditions createdCondition = healthConditionsService.createHealthCondition(username, healthCondition);
//        return ResponseEntity.ok(createdCondition);
//    }
//
//    // Get health conditions for a profile
//    @GetMapping("/{username}/health-conditions")
//    @ApiOperation("Get health conditions for a profile")
//    public ResponseEntity<List<HealthConditions>> getHealthConditions(@PathVariable String username) {
//        List<HealthConditions> conditions = healthConditionsService.getHealthConditionsByUsername(username);
//        return ResponseEntity.ok(conditions);
//    }
//
//    // Update a specific health condition for a profile
//    @PutMapping("/{username}/health-conditions/{conditionId}")
//    @ApiOperation("Update a specific health condition for a profile")
//    public ResponseEntity<HealthConditions> updateHealthCondition(@PathVariable String username,
//                                                                  @PathVariable int conditionId,
//                                                                  @RequestBody HealthConditions healthCondition) {
//        healthCondition.setConditionId(conditionId);
//        HealthConditions updatedCondition = healthConditionsService.updateHealthCondition(healthCondition);
//        return ResponseEntity.ok(updatedCondition);
//    }
//
//    // Delete a specific health condition for a profile
//    @DeleteMapping("/{username}/health-conditions/{conditionId}")
//    @ApiOperation("Delete a specific health condition for a profile")
//    public ResponseEntity<Void> deleteHealthCondition(@PathVariable String username, @PathVariable int conditionId,
//                                                      @AuthenticationPrincipal UserDetails userDetails) {
//        if (!username.equals(userDetails.getUsername())) {
//            throw new UnauthorizedException("You are not authorized to delete this health condition.");
//        }
//
//        healthConditionsService.deleteHealthConditionById(conditionId);
//        return ResponseEntity.noContent().build();
//    }
}
