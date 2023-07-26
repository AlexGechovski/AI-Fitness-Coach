package com.example.fittrainer.dtos;

import java.util.ArrayList;
import java.util.List;

public class ChatGptRequestDTO {
    private String model;
    private List<MessageDTO> messages;

    private List<FunctionDTO> functions;

    public List<FunctionDTO> getFunctions() {
        return functions;
    }

    public void setFunctions(List<FunctionDTO> functions) {
        this.functions = functions;
    }

    public ChatGptRequestDTO() {
    }

    public static ChatGptRequestDTO fromChatDTO(ChatDTO chatDTO) {
        ChatGptRequestDTO chatGptRequestDTO = new ChatGptRequestDTO();
        chatGptRequestDTO.setModel(chatDTO.getModel());

        List<MessageDTO> messages = new ArrayList<>();
        for (MessageDTO messageDTO : chatDTO.getMessages()) {
            MessageDTO message = new MessageDTO();
            message.setRole(messageDTO.getRole());
            message.setContent(messageDTO.getContent());
            messages.add(message);
        }
        chatGptRequestDTO.setMessages(messages);

        return chatGptRequestDTO;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }

    private String messagesToString() {
        String result = "";
        for (MessageDTO message : messages) {
            result += message.toString() + "\n";
        }
        return result;
    }
    private String functionsToString() {
        String result = "";
        for (FunctionDTO function : functions) {
            result += function.toString() + "\n";
        }
        return result;
    }
    @Override
    public String toString() {
        return "ChatGptRequestDTO{" +
                "model='" + model + '\'' +
                ", messages=" + messagesToString() +
                ", functions=" + functions +
                '}';
    }
}

