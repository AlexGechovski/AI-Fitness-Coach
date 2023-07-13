package com.example.fittrainer.repositories;

import com.example.fittrainer.models.HealthCondition;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HealthConditionsRepository {
    private final JdbcTemplate jdbcTemplate;

    public HealthConditionsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public HealthCondition create(HealthCondition healthCondition) {
        String query = "INSERT INTO HealthConditions (ConditionID, ProfileID, ConditionDescription) VALUES (?, ?, ?)";
        jdbcTemplate.update(query, healthCondition.getConditionId(), healthCondition.getProfileId(), healthCondition.getConditionDescription());
        return healthCondition;
    }

    public List<HealthCondition> findByProfileId(int profileId) {
        String query = "SELECT * FROM HealthConditions WHERE ProfileID = ?";
        return jdbcTemplate.query(query, (resultSet, rowNum) -> {
            HealthCondition healthCondition = new HealthCondition();
            healthCondition.setConditionId(resultSet.getInt("ConditionID"));
            healthCondition.setProfileId(resultSet.getInt("ProfileID"));
            healthCondition.setConditionDescription(resultSet.getString("ConditionDescription"));
            return healthCondition;
        }, profileId);
    }

    public HealthCondition findById(int conditionId) {
        String query = "SELECT * FROM HealthConditions WHERE ConditionID = ?";
        return jdbcTemplate.queryForObject(query, (resultSet, rowNum) -> {
            HealthCondition healthCondition = new HealthCondition();
            healthCondition.setConditionId(resultSet.getInt("ConditionID"));
            healthCondition.setProfileId(resultSet.getInt("ProfileID"));
            healthCondition.setConditionDescription(resultSet.getString("ConditionDescription"));
            return healthCondition;
        }, conditionId);
    }

    public HealthCondition update(HealthCondition healthCondition) {
        String query = "UPDATE HealthConditions SET ConditionDescription = ? WHERE ConditionID = ?";
        jdbcTemplate.update(query, healthCondition.getConditionDescription(), healthCondition.getConditionId());
        return healthCondition;
    }

    public void deleteById(int conditionId) {
        String query = "DELETE FROM HealthConditions WHERE ConditionID = ?";
        jdbcTemplate.update(query, conditionId);
    }
}

