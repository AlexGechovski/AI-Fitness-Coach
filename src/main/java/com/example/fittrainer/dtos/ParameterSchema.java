package com.example.fittrainer.dtos;


import java.util.Map;

public class ParameterSchema {
    private String type;
    private Map<String, Object> properties;
    private String[] required;

    public ParameterSchema() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public String[] getRequired() {
        return required;
    }

    public void setRequired(String[] required) {
        this.required = required;
    }
}


