package com.example.fittrainer.services;

import com.example.fittrainer.dtos.ChatDTO;
import com.example.fittrainer.models.Chat;
import com.example.fittrainer.models.Profile;
import com.example.fittrainer.repositories.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private ProfileService profileService;

    public ChatService(ChatRepository chatRepository, ProfileService profileService) {
        this.chatRepository = chatRepository;
        this.profileService = profileService;
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

    // Other service methods as needed
}

