package com.example.fittrainer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "Public endpoint, accessible to all users";
    }

    @GetMapping("/secured")
    public String securedEndpoint() {
        return "Secured endpoint, accessible only to authenticated users";
    }

    @GetMapping("/admin")
    public String adminEndpoint() {
        return "Admin endpoint, accessible only to users with admin role";
    }

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TestController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/db-connection")
    public String testDbConnection() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("SELECT 1");
            return "Database connection successful!";
        } catch (SQLException e) {
            return "Failed to establish database connection: " + e.getMessage();
        }
    }
}
