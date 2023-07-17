package com.example.fittrainer.dtos;

import java.util.ArrayList;
import java.util.List;

public class ChatRequest {

    private String model;
    private List<Message> messages;


    public ChatRequest(String model, String prompt) {
        this.model = model;

        this.messages = new ArrayList<>();
        this.messages.add(new Message("user", prompt));
    }

    public String getModel() {
        return model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    // getters and setters
}
