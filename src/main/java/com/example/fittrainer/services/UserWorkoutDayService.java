package com.example.fittrainer.services;

import com.example.fittrainer.dtos.ExerciseDTO;
import com.example.fittrainer.dtos.UserWeeklyWorkoutDTO;
import com.example.fittrainer.models.*;
import com.example.fittrainer.repositories.ExerciseRepository;
import com.example.fittrainer.repositories.UserWorkoutDayRepository;
import com.example.fittrainer.repositories.WorkoutExerciseRepository;
import com.example.fittrainer.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserWorkoutDayService {

    private final UserWorkoutDayRepository userWorkoutDayRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final WorkoutRepository workoutRepository;

    private final ProfileService profileService;
    private final ExerciseRepository exerciseRepository;
    private final ChatGPTService chatGPTService;

    @Autowired
    public UserWorkoutDayService(UserWorkoutDayRepository userWorkoutDayRepository,
                                 WorkoutExerciseRepository workoutExerciseRepository, WorkoutRepository workoutRepository, ProfileService profileService, ExerciseRepository exerciseRepository, ChatGPTService chatGPTService) {
        this.userWorkoutDayRepository = userWorkoutDayRepository;
        this.workoutExerciseRepository = workoutExerciseRepository;
        this.workoutRepository = workoutRepository;
        this.profileService = profileService;
        this.exerciseRepository = exerciseRepository;
        this.chatGPTService = chatGPTService;
    }

    public List<UserWeeklyWorkoutDTO> getUserWeeklyWorkout(String username) {
        Profile user = profileService.getProfileByUsername(username);
        List<UserWorkoutDay> userWorkoutDays = userWorkoutDayRepository.getUserWorkoutDaysByProfileId(user.getProfileId());

        List<UserWeeklyWorkoutDTO> weeklyWorkouts = new ArrayList<>();

        for (UserWorkoutDay userWorkoutDay : userWorkoutDays) {
            UserWeeklyWorkoutDTO weeklyWorkoutDTO = new UserWeeklyWorkoutDTO();
            weeklyWorkoutDTO.setDay(userWorkoutDay.getDay());
            Workout workout = workoutRepository.findById(userWorkoutDay.getWorkoutId());
            weeklyWorkoutDTO.setWorkout(workout.getName());
            List<ExerciseDTO> exerciseDTOs = convertToExerciseDTOs(workoutExerciseRepository.getExercisesByWorkoutId(userWorkoutDay.getWorkoutId()));
            weeklyWorkoutDTO.setExercises(exerciseDTOs);
            weeklyWorkouts.add(weeklyWorkoutDTO);
        }

        return weeklyWorkouts;
    }

    private List<ExerciseDTO> convertToExerciseDTOs(List<Exercise> exercises) {
        List<ExerciseDTO> exerciseDTOs = new ArrayList<>();
        for (Exercise exercise : exercises) {
            ExerciseDTO exerciseDTO = new ExerciseDTO();
            exerciseDTO.setName(exercise.getName());
            exerciseDTO.setSets(exercise.getSets());
            exerciseDTO.setReps(exercise.getReps());
            exerciseDTO.setDuration(exercise.getDuration());
            // Set other properties as needed
            exerciseDTOs.add(exerciseDTO);
        }
        return exerciseDTOs;
    }

    public UserWeeklyWorkoutDTO createUserWorkoutDay(String username, UserWeeklyWorkoutDTO userWorkoutDay) {
        // Retrieve the profile ID based on the username
        Profile user = profileService.getProfileByUsername(username);

        // Create a new Workout object
        Workout newWorkout = new Workout();
        newWorkout.setName(userWorkoutDay.getWorkout());

        // Save the new Workout in the repository
        Workout createdWorkout = workoutRepository.save(newWorkout);

        // Create a new UserWorkoutDay object
        UserWorkoutDay newUserWorkoutDay = new UserWorkoutDay();
        newUserWorkoutDay.setProfileId(user.getProfileId());
        newUserWorkoutDay.setDayId(userWorkoutDay.getDayId());
        newUserWorkoutDay.setWorkoutId(createdWorkout.getWorkoutId());

        // Save the new UserWorkoutDay in the repository
        UserWorkoutDay createdUserWorkoutDay = userWorkoutDayRepository.save(newUserWorkoutDay);

        // Create a list of Exercise objects
        List<Exercise> exercises = new ArrayList<>();
        for (ExerciseDTO exerciseDTO : userWorkoutDay.getExercises()) {
            Exercise exercise = new Exercise();
            exercise.setName(exerciseDTO.getName());
            exercise.setSets(exerciseDTO.getSets());
            exercise.setReps(exerciseDTO.getReps());
            exercise.setDuration(exerciseDTO.getDuration());
            exercises.add(exercise);
        }

        // Save the exercises in the repository and associate them with the workout
        for (Exercise exercise : exercises) {
            Exercise createdExercise = exerciseRepository.save(exercise);
            workoutExerciseRepository.saveWorkoutExercise(createdWorkout.getWorkoutId(), createdExercise.getExerciseId());
        }

        // Map the created UserWorkoutDay and Workout objects to UserWeeklyWorkoutDTO
        UserWeeklyWorkoutDTO createdWorkoutDTO = new UserWeeklyWorkoutDTO();
        createdWorkoutDTO.setDay(createdUserWorkoutDay.getDay());
        createdWorkoutDTO.setWorkout(createdWorkout.getName());
        createdWorkoutDTO.setExercises(userWorkoutDay.getExercises());

        return createdWorkoutDTO;
    }


    public List<UserWeeklyWorkoutDTO> createUserWorkoutDays(String username, List<UserWeeklyWorkoutDTO> userWorkoutDays) {
        List<UserWeeklyWorkoutDTO> createdWorkoutDays = new ArrayList<>();

        for (UserWeeklyWorkoutDTO userWorkoutDay : userWorkoutDays) {
            UserWeeklyWorkoutDTO createdWorkoutDay = createUserWorkoutDay(username, userWorkoutDay);
            createdWorkoutDays.add(createdWorkoutDay);
        }

        return createdWorkoutDays;
    }

    public void deleteUserWorkoutDay(String username) {
        // Retrieve the profile ID based on the username
        Profile user = profileService.getProfileByUsername(username);

        // Check if the user workout day exists
        List<UserWorkoutDay> userWorkoutDays = userWorkoutDayRepository.getUserWorkoutDaysByProfileId(user.getProfileId());


        // Delete the user workout day
        for (UserWorkoutDay userWorkoutDay : userWorkoutDays) {
            userWorkoutDayRepository.delete(userWorkoutDay);
        }
    }

    public List<UserWeeklyWorkoutDTO> generateUserWorkoutDays(String username, List<UserWeeklyWorkoutDTO> userWorkoutDays) {
        // Retrieve the profile ID based on the username
        FullProfile user = profileService.getFullProfileByUsername(username);

        String exampleWeekWorkout = "[{\"day\":\"Monday\",\"workout\":\"UpperBody\",\"exercises\":[{\"name\":\"PushUps\",\"sets\":3,\"reps\":15,\"duration\":null},{\"name\":\"BicepCurls\",\"sets\":3,\"reps\":12,\"duration\":null}],\"dayId\":1},{\"day\":\"Tuesday\",\"workout\":\"LowerBody\",\"exercises\":[{\"name\":\"Squats\",\"sets\":3,\"reps\":15,\"duration\":null},{\"name\":\"Lunges\",\"sets\":3,\"reps\":12,\"duration\":null}],\"dayId\":2},{\"day\":\"Wednesday\",\"workout\":\"RestDay\",\"exercises\":[],\"dayId\":3},{\"day\":\"Thursday\",\"workout\":\"Core\",\"exercises\":[{\"name\":\"Planks\",\"sets\":3,\"reps\":null,\"duration\":\"1min\"},{\"name\":\"RussianTwists\",\"sets\":3,\"reps\":20,\"duration\":null}],\"dayId\":4},{\"day\":\"Friday\",\"workout\":\"UpperBody\",\"exercises\":[{\"name\":\"TricepDips\",\"sets\":3,\"reps\":15,\"duration\":null},{\"name\":\"PullUps\",\"sets\":3,\"reps\":10,\"duration\":null}],\"dayId\":5},{\"day\":\"Saturday\",\"workout\":\"RestDay\",\"exercises\":[],\"dayId\":6},{\"day\":\"Sunday\",\"workout\":\"Cardio\",\"exercises\":[{\"name\":\"Running\",\"sets\":null,\"reps\":null,\"duration\":\"30min\"}],\"dayId\":7}]";
        String structure = "[{\"day\":\"Monday\",\"workout\":\"..\",\"exercises\":[{\"name\":\"..\",\"sets\":\"..\",\"reps\":\"..\"},{\"name\":\"..\",\"sets\":\"..\",\"reps\":\"..\"}],\"dayId\":1},{\"day\":\"Tuesday\",\"workout\":\"..\",\"exercises\":[{\"name\":\"..\",\"sets\":\"..\",\"reps\":\"..\"},{\"name\":\"..\",\"sets\":\"..\",\"reps\":\"..\"}],\"dayId\":2},{\"day\":\"Wednesday\",\"workout\":\"..\",\"exercises\":[],\"dayId\":3},{\"day\":\"Thursday\",\"workout\":\"..\",\"exercises\":[{\"name\":\"..\",\"sets\":\"..\",\"duration\":\"..\"},{\"name\":\"..\",\"sets\":\"..\",\"reps\":\"..\"}],\"dayId\":4},{\"day\":\"Friday\",\"workout\":\"..\",\"exercises\":[{\"name\":\"..\",\"sets\":\"..\",\"reps\":\"..\"},{\"name\":\"..\",\"sets\":\"..\",\"reps\":\"..\"}],\"dayId\":5},{\"day\":\"Saturday\",\"workout\":\"..\",\"exercises\":[],\"dayId\":6},{\"day\":\"Sunday\",\"workout\":\"..\",\"exercises\":[{\"name\":\"..\",\"duration\":\"..\"}],\"dayId\":7}]";
//        String prompt = "Based on my profile and my goals " +
//                user.toString() +
//                " Generate a fitness program. Return the answer as this JSON object." +
//                exampleWeekWorkout;

        String prompt = "Consider the following characteristics and Goals for the user: \n" +
                user.toString() +
                "\nGenerate a workout program. Put it in the following JSON structure. Don't add keys that don't exist\n" +
                structure
                ;
        System.out.println(prompt);

        String answer = chatGPTService.getAnswerToQuestion(prompt);
        System.out.println(answer);


//        List<UserWeeklyWorkoutDTO> createdWorkoutDays = new ArrayList<>();
//
//        for (UserWeeklyWorkoutDTO userWorkoutDay : userWorkoutDays) {
//            UserWeeklyWorkoutDTO createdWorkoutDay = createUserWorkoutDay(username, userWorkoutDay);
//            createdWorkoutDays.add(createdWorkoutDay);
//        }
//
//        return createdWorkoutDays;
        return  userWorkoutDays;
    }
}
