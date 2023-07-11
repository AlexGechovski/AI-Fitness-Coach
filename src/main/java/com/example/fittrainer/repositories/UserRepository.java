package com.example.fittrainer.repositories;

import com.example.fittrainer.enums.UserRole;
import com.example.fittrainer.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{username}, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
            user.setRole(UserRole.valueOf(rs.getString("role")));
            return user;
        });
    }

    public User findByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{email}, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
            user.setRole(UserRole.valueOf(rs.getString("role")));
            return user;
        });
    }

    public void save(User user) {
        String query = "INSERT INTO users (username, password, email, registration_date, role) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(query,
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRegistrationDate(),
                user.getRole().name());
    }

    public void update(User user) {
        String query = "UPDATE users SET username = ?, password = ?, email = ?, registration_date = ?, role = ? WHERE id = ?";
        jdbcTemplate.update(query,
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRegistrationDate(),
                user.getRole().name(),
                user.getId());
    }

    public void delete(Long id) {
        String query = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    public User getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{username}, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
            user.setRole(UserRole.valueOf(rs.getString("role")));
            return user;
        });
    }



    // Other CRUD operations and custom query methods
}

