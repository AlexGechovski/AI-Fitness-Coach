package com.example.fittrainer.dtos;
public class Message {

    private String role;
    private String content;

    public Message(String user, String prompt) {
        this.role = user;
        this.content = prompt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // constructor, getters and setters
}

