package com.example.fittrainer.dtos;

import java.util.Map;

public class FunctionDTO {
    private String name;
    private String description;
    private ParameterSchema parameters; // Add this property


    public void setParameters(ParameterSchema parameters) {
        this.parameters = parameters;
    }

    public ParameterSchema getParameters() {
        return parameters;
    }

    public FunctionDTO() {
    }

    @Override
    public String toString() {
        return "FunctionDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", parameters=" + parameters +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
