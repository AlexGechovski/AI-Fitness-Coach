package com.example.fittrainer.dtos;

import java.util.List;

public class MatchDTO {
    private String id;
    private double score;
    private List<String> values;

    public MatchDTO() {
    }

    // Constructor
    public MatchDTO(String id, double score, List<String> values) {
        this.id = id;
        this.score = score;
        this.values = values;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
