package com.example.fittrainer.dtos;

public class ExerciseDTO {
    private String name;
    private String sets;
    private String reps;
    private String duration;

    @Override
    public String toString() {
        return "Exercise{" +
                "name='" + name + '\'' +
                ", sets='" + sets + '\'' +
                ", reps='" + reps + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }

    public ExerciseDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
