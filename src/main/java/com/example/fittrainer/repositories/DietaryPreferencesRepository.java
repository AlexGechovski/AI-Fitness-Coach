package com.example.fittrainer.repositories;
import com.example.fittrainer.models.DietaryPreferences;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DietaryPreferencesRepository {
    private final JdbcTemplate jdbcTemplate;

    public DietaryPreferencesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DietaryPreferences create(DietaryPreferences dietaryPreferences) {
        String query = "INSERT INTO DietaryPreferences (PreferenceID, ProfileID, Vegetarian, Vegan, GlutenFree) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(query, dietaryPreferences.getPreferenceId(), dietaryPreferences.getProfileId(), dietaryPreferences.isVegetarian(), dietaryPreferences.isVegan(), dietaryPreferences.isGlutenFree());
        return dietaryPreferences;
    }

    public List<DietaryPreferences> findByProfileId(int profileId) {
        String query = "SELECT * FROM DietaryPreferences WHERE ProfileID = ?";
        return jdbcTemplate.query(query, (resultSet, rowNum) -> {
            DietaryPreferences dietaryPreferences = new DietaryPreferences();
            dietaryPreferences.setPreferenceId(resultSet.getInt("PreferenceID"));
            dietaryPreferences.setProfileId(resultSet.getInt("ProfileID"));
            dietaryPreferences.setVegetarian(resultSet.getBoolean("Vegetarian"));
            dietaryPreferences.setVegan(resultSet.getBoolean("Vegan"));
            dietaryPreferences.setGlutenFree(resultSet.getBoolean("GlutenFree"));
            return dietaryPreferences;
        }, profileId);
    }



    public DietaryPreferences update(DietaryPreferences dietaryPreferences) {
        String query = "UPDATE DietaryPreferences SET Vegetarian = ?, Vegan = ?, GlutenFree = ? WHERE ProfileID = ?";
        jdbcTemplate.update(query, dietaryPreferences.isVegetarian(), dietaryPreferences.isVegan(), dietaryPreferences.isGlutenFree(), dietaryPreferences.getProfileId());
        return dietaryPreferences;
    }

    public void deleteByProfileId(int profileId) {
        String query = "DELETE FROM DietaryPreferences WHERE ProfileID = ?";
        jdbcTemplate.update(query, profileId);
    }

    public DietaryPreferences findById(int preferenceId) {
        String query = "SELECT * FROM DietaryPreferences WHERE PreferenceID = ?";
        return jdbcTemplate.queryForObject(query, (resultSet, rowNum) -> {
            DietaryPreferences dietaryPreferences = new DietaryPreferences();
            dietaryPreferences.setPreferenceId(resultSet.getInt("PreferenceID"));
            dietaryPreferences.setProfileId(resultSet.getInt("ProfileID"));
            dietaryPreferences.setVegetarian(resultSet.getBoolean("Vegetarian"));
            dietaryPreferences.setVegan(resultSet.getBoolean("Vegan"));
            dietaryPreferences.setGlutenFree(resultSet.getBoolean("GlutenFree"));
            return dietaryPreferences;
        }, preferenceId);
    }

    public void deleteById(int preferenceId) {
        String query = "DELETE FROM DietaryPreferences WHERE PreferenceID = ?";
        jdbcTemplate.update(query, preferenceId);
    }
}

