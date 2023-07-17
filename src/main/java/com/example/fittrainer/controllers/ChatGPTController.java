package com.example.fittrainer.controllers;


import com.example.fittrainer.services.ChatGPTService;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatGPTController {

    private final ChatGPTService chatGPTService;

    public ChatGPTController(ChatGPTService chatGPTService) {
        this.chatGPTService = chatGPTService;
    }

    @PostMapping("/chat-gpt")
    public ResponseEntity<String> chatGPT() {
        String response = chatGPTService.getAnswerToQuestion("message");
        return ResponseEntity.ok(response);
    }


}
