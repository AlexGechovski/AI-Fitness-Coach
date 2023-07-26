package com.example.fittrainer.services;

import com.example.fittrainer.dtos.*;
import com.example.fittrainer.models.Chat;
import com.example.fittrainer.models.Message;
import com.example.fittrainer.models.Profile;
import com.example.fittrainer.repositories.ChatRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    private final FunctionsService functionsService;
    @Value("${openai.model}")
    private String model;
    private final ChatRepository chatRepository;
    private final ChatGPTService chatGPTService;
    private final ProfileService profileService;

    public ChatService(ChatRepository chatRepository, ProfileService profileService, ChatGPTService chatGPTService, FunctionsService functionsService) {
        this.chatRepository = chatRepository;
        this.profileService = profileService;
        this.chatGPTService = chatGPTService;
        this.functionsService = functionsService;
    }

    public Long saveChatWithMessages(Chat chat) {
        return chatRepository.saveChat(chat);
    }

    public List<Chat> getAllChatsWithMessages() {
        return chatRepository.getAllChats();
    }

    public List<ChatDTO> getChatsByProfileUsername(String username) {
        Profile user = profileService.getProfileByUsername(username);
        return chatRepository.getChatsByProfileId(user.getProfileId());
    }

    public MessageDTO addMessageToChat(Long chatId, Message message) {
        chatRepository.saveMessage(chatId, message);
        ChatGptRequestDTO chatGptRequestDTO = ChatGptRequestDTO.fromChatDTO(chatRepository.getChatByChatId(chatId));

// Create and set the function details
        List<FunctionDTO> functionsList = new ArrayList<>();
        FunctionDTO function = functionsService.getWeatherFunction();
        functionsList.add(function);

// Set the functions list in the ChatGptRequestDTO
        chatGptRequestDTO.setFunctions(functionsList);

// Rest of the code remains the same...

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Serialize the chatGptRequestDTO object to JSON string
            String jsonString = objectMapper.writeValueAsString(chatGptRequestDTO);

            // Print the JSON string
            System.out.println(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }


        ChatGptResponseDTO responseDTO = chatGPTService.getChatGptResponse(chatGptRequestDTO);

        try {
            // Serialize the chatGptRequestDTO object to JSON string
            String jsonString = objectMapper.writeValueAsString(responseDTO);

            // Print the JSON string
            System.out.println(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }


        List<ChatGptResponseDTO.Choice> answers= responseDTO.getChoices();
        Message message1 = new Message();
        message1.setRole(answers.get(0).getMessage().getRole());
        message1.setContent(answers.get(0).getMessage().getContent());
        message1.setChatId(chatId);
        chatRepository.saveMessage(chatId, message1);
        return answers.get(0).getMessage().toMessageDTO();
    }

    public ChatDTO createChatWithInitialMessage() {
        Profile user = profileService.getCurrentProfile();
        ChatDTO chat = chatRepository.createChatWithInitialMessage("How can I help with your fitness journey?", user.getProfileId(), model);
        return chat;
    }

    public void deleteChatByChatId(Long chatId) {
        chatRepository.deleteChatByChatId(chatId);
    }

    // Other service methods as needed


}

