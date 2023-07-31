package com.example.fittrainer.controllers;


import com.example.fittrainer.dtos.ChatGptRequestDTO;
import com.example.fittrainer.dtos.ChatGptResponseDTO;
import com.example.fittrainer.dtos.EmbeddingRequestDTO;
import com.example.fittrainer.dtos.EmbeddingResponseDTO;
import com.example.fittrainer.services.ChatGPTService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatGPTController {

    private final ChatGPTService chatGPTService;

    public ChatGPTController(ChatGPTService chatGPTService) {
        this.chatGPTService = chatGPTService;
    }

//    @PostMapping("/chat-gpt")
//    public ResponseEntity<ChatGptResponseDTO> chatGPT(@RequestBody String message) {
//        ChatGptResponseDTO response = chatGPTService.getAnswerToQuestionDTO(message);
//        return ResponseEntity.ok(response);
//    }

    @PostMapping("/chat-gpt")
    public ResponseEntity<ChatGptResponseDTO> getChatGptResponse(@RequestBody ChatGptRequestDTO requestDTO) {
        try {
            ChatGptResponseDTO responseDTO = chatGPTService.getChatGptResponse(requestDTO);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/embeddings")
    public ResponseEntity<EmbeddingResponseDTO> getEmbeddings(@RequestBody EmbeddingRequestDTO requestDTO) {
        try {
            EmbeddingResponseDTO responseDTO = chatGPTService.getEmbedding(requestDTO);
            System.out.println(responseDTO);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}