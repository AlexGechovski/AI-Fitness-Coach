package com.example.fittrainer.services;

import com.example.fittrainer.models.Goals;
import com.example.fittrainer.repositories.GoalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalsService {
    private final GoalsRepository goalsRepository;
    private final ProfileService profileService;

    @Autowired
    public GoalsService(GoalsRepository goalsRepository, ProfileService profileService) {
        this.goalsRepository = goalsRepository;
        this.profileService = profileService;
    }

    public Goals createGoal(String username, Goals goal) {
        int profileId = profileService.getProfileIdByUsername(username);
        goal.setProfileId(profileId);
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

    public List<Goals> getGoalsByUsername(String username) {
        return goalsRepository.findByProfileId(profileService.getProfileIdByUsername(username));
    }
}
