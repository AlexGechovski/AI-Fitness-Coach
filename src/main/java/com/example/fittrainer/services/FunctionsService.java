package com.example.fittrainer.services;

import com.example.fittrainer.dtos.FunctionDTO;
import com.example.fittrainer.dtos.ParameterSchema;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FunctionsService {

    public FunctionDTO getWeatherFunction(){
        FunctionDTO function = new FunctionDTO();
        function.setName("get_current_weather");
        function.setDescription("Get the current weather in a given location");

// Create a parameter schema
        ParameterSchema parameterSchema = new ParameterSchema();
        parameterSchema.setType("object");

// Create a map to hold the properties of the parameter
        Map<String, Object> properties = new HashMap<>();

// Create a property for "location" with its type and description
        Map<String, String> locationProperty = new HashMap<>();
        locationProperty.put("type", "string");
        locationProperty.put("description", "The city and state, e.g. San Francisco, CA");
        properties.put("location", locationProperty);

// Create a property for "unit" with its type and description
        Map<String, String> unitProperty = new HashMap<>();
        unitProperty.put("type", "string");
        unitProperty.put("string", "celsius");
        unitProperty.put("description", "The unit of temperature, e.g. celsius or fahrenheit");
        properties.put("unit", unitProperty);

        parameterSchema.setProperties(properties);

// Set the required properties
        String[] requiredProperties = {"location"}; // Add more if needed
        parameterSchema.setRequired(requiredProperties);

// Set the parameter schema for the function
        function.setParameters(parameterSchema);
        return function;
    }


}
