package com.example.fittrainer.services;
import com.example.fittrainer.dtos.ChatGptRequestDTO;
import com.example.fittrainer.dtos.ChatGptResponseDTO;
import com.example.fittrainer.dtos.ChatRequest;
import com.example.fittrainer.dtos.MessageDTO;
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

