package com.example.fittrainer.services;

import com.example.fittrainer.dtos.*;
import com.example.fittrainer.models.Message;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FunctionsService {

    private final ChatGPTService chatGPTService;

    public FunctionsService(ChatGPTService chatGPTService) {
        this.chatGPTService = chatGPTService;
    }

    public Message executeFunction(ChatGptResponseDTO responseDTO, ChatGptRequestDTO chatGptRequestDTO) {
        String functionName = responseDTO.getChoices().get(0).getMessage().getFunction_call().getName();
        String arguments = responseDTO.getChoices().get(0).getMessage().getFunction_call().getArguments();
        String response;

        switch (functionName){
            case "get_current_weather":
                response = callWeatherFunction(responseDTO.getChoices().get(0).getMessage().getFunction_call());
                break;
            default:
                throw new RuntimeException("Function not found");
        }

        FunctionMessageDTO functionMessageDTO = new FunctionMessageDTO();
        functionMessageDTO.setRole("function");
        functionMessageDTO.setName(functionName);
        functionMessageDTO.setContent(response);
        chatGptRequestDTO.getMessages().add(functionMessageDTO);

        ChatGptResponseDTO responseFunctionDTO = chatGPTService.getChatGptResponse(chatGptRequestDTO);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Serialize the chatGptRequestDTO object to JSON string
            String jsonString = objectMapper.writeValueAsString(responseFunctionDTO);

            // Print the JSON string
            System.out.println(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<ChatGptResponseDTO.Choice> answers= responseFunctionDTO.getChoices();
        Message message1 = new Message();
        message1.setRole(answers.get(0).getMessage().getRole());
        message1.setContent(answers.get(0).getMessage().getContent());


        return message1;
    }

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

    private String getCurrentWeather(String location) {
        // Implement your logic here to fetch the weather for the given location
        // You can use external APIs or any other data source to get the weather information
        // Return the weather information as a string
        return "The weather in " + location + " is sunny and 22 degrees Celsius.";
    }

    private String callWeatherFunction(FunctionCall functionCall) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode argumentsJson = objectMapper.readTree(functionCall.getArguments());
            String location = argumentsJson.get("location").asText();

            // Call the getCurrentWeather function with the location argument
            String weatherResponse = getCurrentWeather(location);



            System.out.println(weatherResponse);
            return weatherResponse;
        } catch (Exception e) {
            throw new RuntimeException("Error parsing arguments");
        }
    }
}
