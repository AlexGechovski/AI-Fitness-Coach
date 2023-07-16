package com.example.fittrainer.repositories;

import com.example.fittrainer.models.Exercise;
import com.example.fittrainer.models.WorkoutExercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WorkoutExerciseRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WorkoutExerciseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<WorkoutExercise> findByWorkoutId(int workoutId) {
        String query = "SELECT * FROM WorkoutExercises WHERE workout_id = ?";
        return jdbcTemplate.query(query, (resultSet, rowNum) -> {
            WorkoutExercise workoutExercise = new WorkoutExercise();
            workoutExercise.setWorkoutId(resultSet.getInt("workout_id"));
            workoutExercise.setExerciseId(resultSet.getInt("exercise_id"));
            workoutExercise.setSequence(resultSet.getInt("sequence"));
            return workoutExercise;
        }, workoutId);
    }


    public void save(WorkoutExercise workoutExercise) {
        String query = "INSERT INTO WorkoutExercises (workout_id, exercise_id, sequence) VALUES (?, ?, ?)";
        jdbcTemplate.update(query, workoutExercise.getWorkoutId(), workoutExercise.getExerciseId(),
                workoutExercise.getSequence());
    }

    public void deleteByWorkoutId(int workoutId) {
        String query = "DELETE FROM WorkoutExercises WHERE workout_id = ?";
        jdbcTemplate.update(query, workoutId);
    }

    public List<Exercise> getExercisesByWorkoutId(int workoutId) {
        String query = "SELECT e.* FROM Exercises e " +
                "JOIN WorkoutExercises we ON e.exercise_id = we.exercise_id " +
                "WHERE we.workout_id = ?";
        return jdbcTemplate.query(query, (resultSet, rowNum) -> {
            Exercise exercise = new Exercise();
            exercise.setExerciseId(resultSet.getInt("exercise_id"));
            exercise.setName(resultSet.getString("name"));
            exercise.setSets(resultSet.getInt("sets"));
            exercise.setReps(resultSet.getInt("reps"));
            exercise.setDuration(resultSet.getString("duration"));
            return exercise;
        }, workoutId);
    }

    public void saveWorkoutExercise(int workoutId, int exerciseId) {
        String query = "INSERT INTO WorkoutExercises (workout_id, exercise_id, sequence) VALUES (?, ?, ?)";
        jdbcTemplate.update(query, workoutId, exerciseId, 0);
    }
}
