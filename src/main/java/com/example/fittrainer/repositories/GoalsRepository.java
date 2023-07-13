package com.example.fittrainer.repositories;

import com.example.fittrainer.models.Goals;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GoalsRepository {
    private final JdbcTemplate jdbcTemplate;

    public GoalsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Goals create(Goals goals) {
        String query = "INSERT INTO Goals (GoalID, ProfileID, GoalDescription, TargetWeight, TargetBodyFatPercentage, TargetCaloricIntake) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(query, goals.getGoalId(), goals.getProfileId(), goals.getGoalDescription(), goals.getTargetWeight(), goals.getTargetBodyFatPercentage(), goals.getTargetCaloricIntake());
        return goals;
    }

    public List<Goals> findByProfileId(int profileId) {
        String query = "SELECT * FROM Goals WHERE ProfileID = ?";
        return jdbcTemplate.query(query, (resultSet, rowNum) -> {
            Goals goals = new Goals();
            goals.setGoalId(resultSet.getInt("GoalID"));
            goals.setProfileId(resultSet.getInt("ProfileID"));
            goals.setGoalDescription(resultSet.getString("GoalDescription"));
            goals.setTargetWeight(resultSet.getFloat("TargetWeight"));
            goals.setTargetBodyFatPercentage(resultSet.getFloat("TargetBodyFatPercentage"));
            goals.setTargetCaloricIntake(resultSet.getFloat("TargetCaloricIntake"));
            return goals;
        }, profileId);
    }

    public Goals findById(int goalId) {
        String query = "SELECT * FROM Goals WHERE GoalID = ?";
        return jdbcTemplate.queryForObject(query, (resultSet, rowNum) -> {
            Goals goals = new Goals();
            goals.setGoalId(resultSet.getInt("GoalID"));
            goals.setProfileId(resultSet.getInt("ProfileID"));
            goals.setGoalDescription(resultSet.getString("GoalDescription"));
            goals.setTargetWeight(resultSet.getFloat("TargetWeight"));
            goals.setTargetBodyFatPercentage(resultSet.getFloat("TargetBodyFatPercentage"));
            goals.setTargetCaloricIntake(resultSet.getFloat("TargetCaloricIntake"));
            return goals;
        }, goalId);
    }

    public Goals update(Goals goals) {
        String query = "UPDATE Goals SET GoalDescription = ?, TargetWeight = ?, TargetBodyFatPercentage = ?, TargetCaloricIntake = ? WHERE GoalID = ?";
        jdbcTemplate.update(query, goals.getGoalDescription(), goals.getTargetWeight(), goals.getTargetBodyFatPercentage(), goals.getTargetCaloricIntake(), goals.getGoalId());
        return goals;
    }

    public void deleteById(int goalId) {
        String query = "DELETE FROM Goals WHERE GoalID = ?";
        jdbcTemplate.update(query, goalId);
    }
}
