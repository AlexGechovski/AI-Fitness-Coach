package com.example.fittrainer.dtos;

import java.util.ArrayList;
import java.util.List;

public class ChatRequest {

    private String model;
    private List<MessageDTO> messages;


    public ChatRequest(String model, String prompt) {
        this.model = model;

        this.messages = new ArrayList<>();
        this.messages.add(new MessageDTO("user", prompt));
    }

    public String getModel() {
        return model;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    // getters and setters
}
