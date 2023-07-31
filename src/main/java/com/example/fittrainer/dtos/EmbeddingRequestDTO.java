package com.example.fittrainer.dtos;

public class EmbeddingRequestDTO {
    private String input;
    private String model;

    public EmbeddingRequestDTO(String input, String model) {
        this.input = input;
        this.model = model;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
