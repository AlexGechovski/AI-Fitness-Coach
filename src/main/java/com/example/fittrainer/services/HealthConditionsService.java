package com.example.fittrainer.services;

import com.example.fittrainer.models.HealthCondition;
import com.example.fittrainer.repositories.HealthConditionsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthConditionsService {
    private final HealthConditionsRepository healthConditionsRepository;

    @Autowired
    public HealthConditionsService(HealthConditionsRepository healthConditionsRepository) {
        this.healthConditionsRepository = healthConditionsRepository;
    }

    public HealthCondition createHealthCondition(HealthCondition healthConditions) {
        return healthConditionsRepository.create(healthConditions);
    }

    public List<HealthCondition> findHealthConditionsByProfileId(int profileId) {
        return healthConditionsRepository.findByProfileId(profileId);
    }

    public HealthCondition findHealthConditionById(int conditionId) {
        return healthConditionsRepository.findById(conditionId);
    }

    public HealthCondition updateHealthCondition(HealthCondition healthConditions) {
        return healthConditionsRepository.update(healthConditions);
    }

    public void deleteHealthConditionById(int conditionId) {
        healthConditionsRepository.deleteById(conditionId);
    }
}

