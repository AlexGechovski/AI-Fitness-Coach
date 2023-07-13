package com.example.fittrainer.services;

import com.example.fittrainer.config.JwtService;
import com.example.fittrainer.models.Profile;
import com.example.fittrainer.models.User;
import com.example.fittrainer.repositories.ProfileRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserService userService;
    private final JwtService jwtService;

    public ProfileService(ProfileRepository profileRepository, UserService userService, JwtService jwtService) {
        this.profileRepository = profileRepository;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    public Profile createProfile(Profile profile) {
        String username = jwtService.getCurrentUsername();
        User userDetails = userService.findByUsername(username);

        // Check if a profile already exists for the user
        if (profileRepository.existsByUserId(userDetails.getId())){
            throw new IllegalStateException("Profile already exists for the user");
        }

        profile.setUserId(Math.toIntExact(userDetails.getId()));

        return profileRepository.create(profile);
    }

    public Profile getProfileById(int profileId) {
        return profileRepository.findById(profileId);
    }

    public Profile updateProfile(String username, Profile updatedProfile) {
        String tokenUsername = jwtService.getCurrentUsername();
        User userDetails = userService.findByUsername(tokenUsername);

        Profile existingProfile = profileRepository.findByUserId(userDetails.getId());

        if (existingProfile == null) {
            throw new IllegalArgumentException("Profile not found with username: " + username);
        }

        // Update the relevant fields of the existing profile with the new data
        existingProfile.setAge(updatedProfile.getAge());
        existingProfile.setGender(updatedProfile.getGender());
        existingProfile.setHeight(updatedProfile.getHeight());
        existingProfile.setWeight(updatedProfile.getWeight());
        existingProfile.setBodyFatPercentage(updatedProfile.getBodyFatPercentage());
        existingProfile.setMaintainCalories(updatedProfile.getMaintainCalories());

        // Save the updated profile back to the repository
        return profileRepository.update(existingProfile);
    }



    public void deleteProfileById(int profileId) {
        profileRepository.deleteById(profileId);
    }

    public Profile getProfileByUsername(String username) {
        String tokenUsername = jwtService.getCurrentUsername();
        User userDetails = userService.findByUsername(tokenUsername);
        return profileRepository.findByUserId(userDetails.getId());
    }

    public void deleteProfileByUsername(String username) {

        Profile profile = getProfileByUsername(username);

        profileRepository.deleteById(profile.getProfileId());
    }
}

