package com.example.fittrainer.repositories;

import com.example.fittrainer.models.FoodAllergy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FoodAllergiesRepository {
    private final JdbcTemplate jdbcTemplate;

    public FoodAllergiesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public FoodAllergy create(FoodAllergy foodAllergy) {
        String query = "INSERT INTO FoodAllergies (AllergyID, ProfileID, AllergyName) VALUES (?, ?, ?)";
        jdbcTemplate.update(query, foodAllergy.getAllergyId(), foodAllergy.getProfileId(), foodAllergy.getAllergyName());
        return foodAllergy;
    }

    public List<FoodAllergy> findByProfileId(int profileId) {
        String query = "SELECT * FROM FoodAllergies WHERE ProfileID = ?";
        return jdbcTemplate.query(query, (resultSet, rowNum) -> {
            FoodAllergy foodAllergy = new FoodAllergy();
            foodAllergy.setAllergyId(resultSet.getInt("AllergyID"));
            foodAllergy.setProfileId(resultSet.getInt("ProfileID"));
            foodAllergy.setAllergyName(resultSet.getString("AllergyName"));
            return foodAllergy;
        }, profileId);
    }

    public FoodAllergy findById(int allergyId) {
        String query = "SELECT * FROM FoodAllergies WHERE AllergyID = ?";
        return jdbcTemplate.queryForObject(query, (resultSet, rowNum) -> {
            FoodAllergy foodAllergy = new FoodAllergy();
            foodAllergy.setAllergyId(resultSet.getInt("AllergyID"));
            foodAllergy.setProfileId(resultSet.getInt("ProfileID"));
            foodAllergy.setAllergyName(resultSet.getString("AllergyName"));
            return foodAllergy;
        }, allergyId);
    }

    public FoodAllergy update(FoodAllergy foodAllergy) {
        String query = "UPDATE FoodAllergies SET AllergyName = ? WHERE AllergyID = ?";
        jdbcTemplate.update(query, foodAllergy.getAllergyName(), foodAllergy.getAllergyId());
        return foodAllergy;
    }

    public void deleteById(int allergyId) {
        String query = "DELETE FROM FoodAllergies WHERE AllergyID = ?";
        jdbcTemplate.update(query, allergyId);
    }
}

