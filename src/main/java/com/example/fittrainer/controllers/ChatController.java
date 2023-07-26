package com.example.fittrainer.controllers;

import com.example.fittrainer.dtos.ChatDTO;
import com.example.fittrainer.dtos.MessageDTO;
import com.example.fittrainer.models.Chat;
import com.example.fittrainer.models.Message;
import com.example.fittrainer.models.Profile;
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
    //Post request to add a message to a chat by chatId
    @PostMapping("/{chatId}")
    public ResponseEntity<MessageDTO> addMessageToChat(@PathVariable Long chatId, @RequestBody Message message) {
        MessageDTO updatedChat = chatService.addMessageToChat(chatId, message);
        return ResponseEntity.ok(updatedChat);
    }

    //Create a new chat with a message
    @PostMapping
    public ResponseEntity<ChatDTO> createChatWithWelcomeMessage() {
        ChatDTO chat = chatService.createChatWithInitialMessage();
        return ResponseEntity.ok(chat);
    }
    //Delete chat by chatId
    @DeleteMapping("/{chatId}")
    public ResponseEntity<String> deleteChatByChatId(@PathVariable Long chatId) {
        chatService.deleteChatByChatId(chatId);
        return ResponseEntity.ok("Chat with id " + chatId + " was deleted");
    }

    //

    // Other API endpoints for update, delete, and other operations can be added here

    // Exception handling for specific exceptions can also be added as needed
}
