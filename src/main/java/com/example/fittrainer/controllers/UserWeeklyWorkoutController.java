package com.example.fittrainer.controllers;

import com.example.fittrainer.dtos.UserWeeklyWorkoutDTO;
import com.example.fittrainer.services.UserWorkoutDayService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/{username}/weekly-workout")
public class UserWeeklyWorkoutController {

    private final UserWorkoutDayService userWorkoutDayService;

    @Autowired
    public UserWeeklyWorkoutController(UserWorkoutDayService userWorkoutDayService) {
        this.userWorkoutDayService = userWorkoutDayService;
    }

    @GetMapping
    @ApiOperation("Get the weekly workout and exercises for a user")
    public ResponseEntity<List<UserWeeklyWorkoutDTO>> getUserWeeklyWorkout(@PathVariable String username) {
        List<UserWeeklyWorkoutDTO> weeklyWorkouts = userWorkoutDayService.getUserWeeklyWorkout(username);
        return ResponseEntity.ok(weeklyWorkouts);
    }

//    @GetMapping("/{userWorkoutDayId}")
//    @ApiOperation("Get a specific user workout day by ID")
//    public ResponseEntity<UserWeeklyWorkoutDTO> getUserWorkoutDayById(@PathVariable String username,
//                                                                      @PathVariable Long userWorkoutDayId) {
//        UserWeeklyWorkoutDTO userWorkoutDay = userWorkoutDayService.getUserWorkoutDayById(username, userWorkoutDayId);
//        return ResponseEntity.ok(userWorkoutDay);
//    }

//    @PostMapping
//    @ApiOperation("Create a new user workout day")
//    public ResponseEntity<UserWeeklyWorkoutDTO> createUserWorkoutDay(@PathVariable String username,
//                                                                     @RequestBody UserWeeklyWorkoutDTO userWorkoutDay) {
//        UserWeeklyWorkoutDTO createdWorkoutDay = userWorkoutDayService.createUserWorkoutDay(username, userWorkoutDay);
//        return ResponseEntity.ok(createdWorkoutDay);
//    }
    @PostMapping
    @ApiOperation("Create new user workout days")
    public ResponseEntity<List<UserWeeklyWorkoutDTO>> createUserWorkoutDays(@PathVariable String username,
                                                                            @RequestBody List<UserWeeklyWorkoutDTO> userWorkoutDays) {
        List<UserWeeklyWorkoutDTO> createdWorkoutDays = userWorkoutDayService.createUserWorkoutDays(username, userWorkoutDays);
        return ResponseEntity.ok(createdWorkoutDays);
    }
    @PostMapping("/generate")
    @ApiOperation("Generate user workout based on user's profile")
    public ResponseEntity<List<UserWeeklyWorkoutDTO>> generateUserWorkoutDays(@PathVariable String username,
                                                                            @RequestBody List<UserWeeklyWorkoutDTO> userWorkoutDays) {
        List<UserWeeklyWorkoutDTO> createdWorkoutDays = userWorkoutDayService.generateUserWorkoutDays(username, userWorkoutDays);
        return ResponseEntity.ok(createdWorkoutDays);
    }


//
//    @PutMapping("/{userWorkoutDayId}")
//    @ApiOperation("Update an existing user workout day")
//    public ResponseEntity<UserWeeklyWorkoutDTO> updateUserWorkoutDay(@PathVariable String username,
//                                                                     @PathVariable Long userWorkoutDayId,
//                                                                     @RequestBody UserWeeklyWorkoutDTO updatedWorkoutDay) {
//        UserWeeklyWorkoutDTO updatedUserWorkoutDay = userWorkoutDayService.updateUserWorkoutDay(username, userWorkoutDayId, updatedWorkoutDay);
//        return ResponseEntity.ok(updatedUserWorkoutDay);
//    }
//
    @DeleteMapping
    @ApiOperation("Delete a user workout day")
    public ResponseEntity<Void> deleteUserWorkoutDay(@PathVariable String username) {
        userWorkoutDayService.deleteUserWorkoutDay(username);
        return ResponseEntity.noContent().build();
    }




}
