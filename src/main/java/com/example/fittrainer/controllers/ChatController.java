package com.example.fittrainer.controllers;

import com.example.fittrainer.dtos.ChatDTO;
import com.example.fittrainer.models.Chat;
import com.example.fittrainer.services.ChatService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chats")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<Long> saveChatWithMessages(@RequestBody Chat chat) {
        Long chatId = chatService.saveChatWithMessages(chat);
        return ResponseEntity.ok(chatId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Chat>> getAllChatsWithMessages() {
        List<Chat> chats = chatService.getAllChatsWithMessages();
        return ResponseEntity.ok(chats);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<ChatDTO>> getChatsByProfileUsername(@PathVariable String username) {
        List<ChatDTO> chats = chatService.getChatsByProfileUsername(username);
        return ResponseEntity.ok(chats);
    }

    // Other API endpoints for update, delete, and other operations can be added here

    // Exception handling for specific exceptions can also be added as needed
}
