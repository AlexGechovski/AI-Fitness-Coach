package com.example.fittrainer.models;

import com.example.fittrainer.dtos.MessageDTO;

import java.sql.Timestamp;

public class Message {
    private Long messageId;
    private Long chatId;
    private String role;
    private String content;
    private Timestamp timestamp;

    // Constructors, getters, and setters
    // ...

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", chatId=" + chatId +
                ", role='" + role + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public Message() {
    }


    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public MessageDTO toMessageDTO() {
        return new MessageDTO(this.role, this.content);
    }
}

