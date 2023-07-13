package com.example.fittrainer.services;

import com.example.fittrainer.repositories.GoalsRepository;
import com.example.fittrainer.models.Goals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalsService {
    private final GoalsRepository goalsRepository;

    @Autowired
    public GoalsService(GoalsRepository goalsRepository) {
        this.goalsRepository = goalsRepository;
    }

    public Goals createGoal(Goals goal) {
        return goalsRepository.create(goal);
    }

    public List<Goals> findGoalsByProfileId(int profileId) {
        return goalsRepository.findByProfileId(profileId);
    }

    public Goals findGoalById(int goalId) {
        return goalsRepository.findById(goalId);
    }

    public Goals updateGoal(Goals goal) {
        return goalsRepository.update(goal);
    }

    public void deleteGoalById(int goalId) {
        goalsRepository.deleteById(goalId);
    }
}

