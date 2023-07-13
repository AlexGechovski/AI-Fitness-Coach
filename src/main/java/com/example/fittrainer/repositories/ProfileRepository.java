package com.example.fittrainer.repositories;

import com.example.fittrainer.models.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProfileRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Profile create(Profile profile) {
        String query = "INSERT INTO Profile (ProfileID, UserID, Age, Gender, Height, Weight, BodyFatPercentage, MaintainCalories) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(query, profile.getProfileId(), profile.getUserId(), profile.getAge(), profile.getGender(), profile.getHeight(), profile.getWeight(), profile.getBodyFatPercentage(), profile.getMaintainCalories());
        return profile;
    }

    public Profile findById(int profileId) {
        String query = "SELECT * FROM Profile WHERE ProfileID = ?";
        return jdbcTemplate.queryForObject(query, (resultSet, rowNum) -> {
            Profile profile = new Profile();
            profile.setProfileId(resultSet.getInt("ProfileID"));
            profile.setUserId(resultSet.getInt("UserID"));
            profile.setAge(resultSet.getInt("Age"));
            profile.setGender(resultSet.getString("Gender"));
            profile.setHeight(resultSet.getFloat("Height"));
            profile.setWeight(resultSet.getFloat("Weight"));
            profile.setBodyFatPercentage(resultSet.getFloat("BodyFatPercentage"));
            profile.setMaintainCalories(resultSet.getInt("MaintainCalories"));
            return profile;
        }, profileId);
    }

    public Profile findByUserId(Long userId) {
        try {
            String query = "SELECT * FROM Profile WHERE UserID = ?";
            return jdbcTemplate.queryForObject(query, (resultSet, rowNum) -> {
                Profile profile = new Profile();
                profile.setProfileId(resultSet.getInt("ProfileID"));
                profile.setUserId(resultSet.getInt("UserID"));
                profile.setAge(resultSet.getInt("Age"));
                profile.setGender(resultSet.getString("Gender"));
                profile.setHeight(resultSet.getFloat("Height"));
                profile.setWeight(resultSet.getFloat("Weight"));
                profile.setBodyFatPercentage(resultSet.getFloat("BodyFatPercentage"));
                profile.setMaintainCalories(resultSet.getInt("MaintainCalories"));
                return profile;
            }, userId);
        } catch (DataAccessException e) {
            return null;
        }
    }


    public Profile update(Profile profile) {
        String query = "UPDATE Profile SET Age = ?, Gender = ?, Height = ?, Weight = ?, BodyFatPercentage = ?, MaintainCalories = ? WHERE ProfileID = ?";
        jdbcTemplate.update(query, profile.getAge(), profile.getGender(), profile.getHeight(), profile.getWeight(), profile.getBodyFatPercentage(), profile.getMaintainCalories(), profile.getProfileId());
        return profile;
    }

    public void deleteById(int profileId) {
        String query = "DELETE FROM Profile WHERE ProfileID = ?";
        jdbcTemplate.update(query, profileId);
    }

//    public Profile findByUsername(String username) {
//        String query = "SELECT * FROM Profile WHERE Username = ?";
//        return jdbcTemplate.queryForObject(query, (resultSet, rowNum) -> {
//            Profile profile = new Profile();
//            profile.setProfileId(resultSet.getInt("ProfileID"));
//            profile.setUserId(resultSet.getInt("UserID"));
//            profile.setAge(resultSet.getInt("Age"));
//            profile.setGender(resultSet.getString("Gender"));
//            profile.setHeight(resultSet.getFloat("Height"));
//            profile.setWeight(resultSet.getFloat("Weight"));
//            profile.setBodyFatPercentage(resultSet.getFloat("BodyFatPercentage"));
//            profile.setMaintainCalories(resultSet.getInt("MaintainCalories"));
//            return profile;
//        }, username);
//    }

    public void deleteByUsername(String username) {
        String query = "DELETE FROM Profile WHERE Username = ?";
        jdbcTemplate.update(query, username);
    }

    public boolean existsByUserId(Long userId) {
        String query = "SELECT COUNT(*) FROM Profile WHERE UserID = ?";
        int count = jdbcTemplate.queryForObject(query, Integer.class, userId);
        return count > 0;
    }

}
