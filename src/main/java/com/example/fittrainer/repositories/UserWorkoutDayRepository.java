package com.example.fittrainer.repositories;
import com.example.fittrainer.models.UserWorkoutDay;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserWorkoutDayRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserWorkoutDayRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    public List<UserWorkoutDay> getUserWorkoutDaysByUsername(String username) {
//        String query = "SELECT * FROM UserWorkoutDay WHERE profile_id = (SELECT ProfileID FROM Profile WHERE Username = ?)";
//        return jdbcTemplate.query(query, (resultSet, rowNum) -> {
//            UserWorkoutDay userWorkoutDay = new UserWorkoutDay();
//            userWorkoutDay.setUserWorkoutDayId(resultSet.getInt("user_workout_day_id"));
//            // Set other properties of UserWorkoutDay
//            userWorkoutDay.setProfileId(resultSet.getInt("profile_id"));
//            userWorkoutDay.setDayId(resultSet.getInt("day_id"));
//            userWorkoutDay.setWorkoutId(resultSet.getInt("workout_id"));
//            return userWorkoutDay;
//        }, username);
//    }

    public List<UserWorkoutDay> getUserWorkoutDaysByProfileId(int profileId) {
        String query = "SELECT * FROM UserWorkoutDay WHERE profile_id = ?";
        return jdbcTemplate.query(query, (resultSet, rowNum) -> {
            UserWorkoutDay userWorkoutDay = new UserWorkoutDay();
            userWorkoutDay.setUserWorkoutDayId(resultSet.getInt("user_workout_day_id"));
            // Set other properties of UserWorkoutDay
            userWorkoutDay.setProfileId(resultSet.getInt("profile_id"));
            userWorkoutDay.setDayId(resultSet.getInt("day_id"));
            userWorkoutDay.setWorkoutId(resultSet.getInt("workout_id"));
            return userWorkoutDay;
        }, profileId);
    }

    public UserWorkoutDay save(UserWorkoutDay newUserWorkoutDay) {
        String query = "INSERT INTO UserWorkoutDay (profile_id, day_id, workout_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, newUserWorkoutDay.getProfileId());
            statement.setInt(2, newUserWorkoutDay.getDayId());
            statement.setInt(3, newUserWorkoutDay.getWorkoutId());
            return statement;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            int userWorkoutDayId = keyHolder.getKey().intValue();
            newUserWorkoutDay.setUserWorkoutDayId(userWorkoutDayId);
            return newUserWorkoutDay;
        } else {
            throw new RuntimeException("Failed to save the user workout day.");
        }
    }

    public void deleteAll(List<UserWorkoutDay> userWorkoutDays) {
    }

    public void delete(UserWorkoutDay userWorkoutDay) {
        String query = "DELETE FROM UserWorkoutDay WHERE user_workout_day_id = ?";
        jdbcTemplate.update(query, userWorkoutDay.getUserWorkoutDayId());
    }


    // Add other custom methods if needed
}

