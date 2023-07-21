package com.example.fittrainer.repositories;

import com.example.fittrainer.models.Exercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ExerciseRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ExerciseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Exercise save(Exercise newExercise) {
        String query = "INSERT INTO Exercises (name, sets, reps, duration) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, newExercise.getName());
            statement.setString(2, newExercise.getSets());
            statement.setString(3, newExercise.getReps());
            statement.setString(4, newExercise.getDuration());
            return statement;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            int exerciseId = keyHolder.getKey().intValue();
            newExercise.setExerciseId(exerciseId);
            return newExercise;
        } else {
            throw new RuntimeException("Failed to save the exercise.");
        }
    }

    public List<Exercise> getAllExercises() {
        String query = "SELECT * FROM Exercises";
        return jdbcTemplate.query(query, (resultSet, rowNum) -> {
            Exercise exercise = new Exercise();
            exercise.setExerciseId(resultSet.getInt("exercise_id"));
            exercise.setName(resultSet.getString("name"));
            exercise.setSets(resultSet.getString("sets"));
            exercise.setReps(resultSet.getString("reps"));
            exercise.setDuration(resultSet.getString("duration"));
            return exercise;
        });
    }

    public Exercise getExerciseById(int exerciseId) {
        String query = "SELECT * FROM Exercises WHERE exercise_id = ?";
        return jdbcTemplate.queryForObject(query, (resultSet, rowNum) -> {
            Exercise exercise = new Exercise();
            exercise.setExerciseId(resultSet.getInt("exercise_id"));
            exercise.setName(resultSet.getString("name"));
            exercise.setSets(resultSet.getString("sets"));
            exercise.setReps(resultSet.getString("reps"));
            exercise.setDuration(resultSet.getString("duration"));
            return exercise;
        }, exerciseId);
    }

    public void deleteExerciseById(int exerciseId) {
        String query = "DELETE FROM Exercises WHERE exercise_id = ?";
        jdbcTemplate.update(query, exerciseId);
    }
}

