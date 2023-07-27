package com.example.fittrainer.services;

import com.example.fittrainer.models.User;
import com.example.fittrainer.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void save(User user) {
        user.setRegistrationDate(LocalDate.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public void delete(Long id) {
        userRepository.delete(id);
    }

    public User findById(int userId) {
        return userRepository.findById(userId);
    }

    // Other service methods
}

