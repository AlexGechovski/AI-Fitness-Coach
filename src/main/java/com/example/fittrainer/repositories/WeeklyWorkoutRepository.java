package com.example.fittrainer.repositories;

import com.example.fittrainer.models.WeeklyWorkout;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WeeklyWorkoutRepository {
    private final JdbcTemplate jdbcTemplate;

    public WeeklyWorkoutRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public WeeklyWorkout save() {
        String query = "INSERT INTO WeeklyWorkout () VALUES ()";
        jdbcTemplate.update(query);

        // Retrieve the auto-generated ID of the inserted row
        long generatedId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        // Set the auto-generated ID to the WeeklyWorkout object
        WeeklyWorkout weeklyWorkout = new WeeklyWorkout();
        weeklyWorkout.setWeeklyWorkoutId(generatedId);
        return weeklyWorkout;
    }

    public WeeklyWorkout findById(long id) {
        String query = "SELECT * FROM WeeklyWorkout WHERE weekly_workout_id = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{id}, (rs, rowNum) -> {
            WeeklyWorkout weeklyWorkout = new WeeklyWorkout();
            weeklyWorkout.setWeeklyWorkoutId(rs.getLong("weekly_workout_id"));
            return weeklyWorkout;
        });
    }

    public void delete(long id) {
        String query = "DELETE FROM WeeklyWorkout WHERE weekly_workout_id = ?";
        jdbcTemplate.update(query, id);
    }

    // Other CRUD operations and custom query methods
}

