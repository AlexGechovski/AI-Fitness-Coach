package com.example.fittrainer.dtos;
public class MessageDTO {

    private String role;
    private String content;

    public MessageDTO() {
    }

    public MessageDTO(String user, String prompt) {
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

