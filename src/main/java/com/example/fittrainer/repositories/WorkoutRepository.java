package com.example.fittrainer.repositories;

import com.example.fittrainer.models.Workout;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class WorkoutRepository {
    private final JdbcTemplate jdbcTemplate;

    public WorkoutRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Workout findById(int workoutId) {
        String query = "SELECT * FROM Workouts WHERE workout_id = ?";
        return jdbcTemplate.queryForObject(query, (resultSet, rowNum) -> {
            Workout workout = new Workout();
            workout.setWorkoutId(resultSet.getInt("workout_id"));
            workout.setName(resultSet.getString("name"));
            return workout;
        }, workoutId);
    }

    public List<Workout> findAll() {
        String query = "SELECT * FROM Workouts";
        return jdbcTemplate.query(query, (resultSet, rowNum) -> {
            Workout workout = new Workout();
            workout.setWorkoutId(resultSet.getInt("workout_id"));
            workout.setName(resultSet.getString("name"));
            return workout;
        });
    }

    // Add other methods as needed for CRUD operations

//    // For example, to create a new workout:
//    public Workout create(Workout workout) {
//        String query = "INSERT INTO Workouts (name) VALUES (?)";
//        jdbcTemplate.update(query, workout.getName());
//        // Set the generated workout_id if needed
//        workout.setWorkoutId(getLastInsertedId());
//        return workout;
//    }

    // For example, to update an existing workout:
    public Workout update(Workout workout) {
        String query = "UPDATE Workouts SET name = ? WHERE workout_id = ?";
        jdbcTemplate.update(query, workout.getName(), workout.getWorkoutId());
        return workout;
    }

    // For example, to delete a workout:
    public void delete(int workoutId) {
        String query = "DELETE FROM Workouts WHERE workout_id = ?";
        jdbcTemplate.update(query, workoutId);
    }

    // Helper method to get the last inserted ID
    private int getLastInsertedId() {
        String query = "SELECT LAST_INSERT_ID()";
        return jdbcTemplate.queryForObject(query, Integer.class);
    }

//flag
    public Workout save(Workout newWorkout) {
        String query = "INSERT INTO Workouts (name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, newWorkout.getName());
            return statement;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            int workoutId = keyHolder.getKey().intValue();
            newWorkout.setWorkoutId(workoutId);
            return newWorkout;
        } else {
            throw new RuntimeException("Failed to save the workout.");
        }
    }

}
