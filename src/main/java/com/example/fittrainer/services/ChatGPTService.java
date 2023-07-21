package com.example.fittrainer.services;
import com.example.fittrainer.dtos.ChatGptRequestDTO;
import com.example.fittrainer.dtos.ChatGptResponseDTO;
import com.example.fittrainer.dtos.ChatRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class ChatGPTService {


    @Value("${openai.api.url}")
    private  String apiUrl;

    @Value("${openai.api.key}")
    private String apiKey;

    public ChatGptResponseDTO getAnswerToQuestionDTO(String prompt) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        ChatRequest request = new ChatRequest("gpt-3.5-turbo", prompt);

        HttpEntity<ChatRequest> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        JSONObject responseBody = new JSONObject(responseEntity.getBody());

        // Create a new ChatGptResponseDTO to hold the response data
        ChatGptResponseDTO responseDto = new ChatGptResponseDTO();

        // Set the id, object, created, and model from the response
        responseDto.setId(responseBody.getString("id"));
        responseDto.setObject(responseBody.getString("object"));
        responseDto.setCreated(responseBody.getLong("created"));
        responseDto.setModel(responseBody.getString("model"));

        // Get the 'choices' array from the response
        JSONArray choicesArray = responseBody.getJSONArray("choices");

        if (choicesArray.length() > 0) {
            // Get the first choice object
            JSONObject firstChoice = choicesArray.getJSONObject(0);

            // Create a new Choice object and set the index and finish_reason from the first choice
            ChatGptResponseDTO.Choice choice = new ChatGptResponseDTO.Choice();
            choice.setIndex(firstChoice.getInt("index"));
            choice.setFinish_reason(firstChoice.getString("finish_reason"));

            // Get the 'message' object from the first choice
            JSONObject messageObject = firstChoice.getJSONObject("message");

            // Create a new Message object and set the role and content from the messageObject
            ChatGptResponseDTO.Message message = new ChatGptResponseDTO.Message();
            message.setRole(messageObject.getString("role"));
            message.setContent(messageObject.getString("content"));

            // Set the message object in the choice
            choice.setMessage(message);

            // Add the choice to the responseDto
            responseDto.setChoices(Collections.singletonList(choice));
        }

        // Get the 'usage' object from the response
        JSONObject usageObject = responseBody.getJSONObject("usage");

        // Create a new Usage object and set the token counts
        ChatGptResponseDTO.Usage usage = new ChatGptResponseDTO.Usage();
        usage.setPrompt_tokens(usageObject.getInt("prompt_tokens"));
        usage.setCompletion_tokens(usageObject.getInt("completion_tokens"));
        usage.setTotal_tokens(usageObject.getInt("total_tokens"));

        // Set the usage object in the responseDto
        responseDto.setUsage(usage);

        // Return the populated ChatGptResponseDTO
        return responseDto;
    }


    public String getAnswerToQuestion(String prompt) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        ChatRequest request = new ChatRequest("gpt-3.5-turbo", prompt);

        HttpEntity<ChatRequest> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        JSONObject responseBody = new JSONObject(responseEntity.getBody());

        // Get the 'choices' array from the response
        JSONArray choicesArray = responseBody.getJSONArray("choices");

        if (choicesArray.length() > 0) {
            // Get the first choice object
            JSONObject firstChoice = choicesArray.getJSONObject(0);

            // Get the 'message' object from the first choice
            JSONObject messageObject = firstChoice.getJSONObject("message");

            // Extract the content of the message
            String content = messageObject.getString("content");

            // Get the 'usage' object from the response
            JSONObject usageObject = responseBody.getJSONObject("usage");

            // Extract the token counts
            int promptTokens = usageObject.getInt("prompt_tokens");
            int completionTokens = usageObject.getInt("completion_tokens");
            int totalTokens = usageObject.getInt("total_tokens");

            // Print the token counts
            System.out.println("Prompt Tokens: " + promptTokens);
            System.out.println("Completion Tokens: " + completionTokens);
            System.out.println("Total Tokens: " + totalTokens);

            return content;
        }

        return "No response";
    }

    public String findJson(String prompt){
        int startIndex = prompt.indexOf("[");
        int endIndex = prompt.lastIndexOf("]");

        if (startIndex != -1 && endIndex != -1) {
            String json = prompt.substring(startIndex, endIndex + 1);
            return json;
        }
        return null;
    }

    public ChatGptResponseDTO getChatGptResponse(ChatGptRequestDTO requestDTO) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<ChatGptRequestDTO> requestEntity = new HttpEntity<>(requestDTO, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ChatGptResponseDTO> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                ChatGptResponseDTO.class
        );

        return responseEntity.getBody();
    }
}

