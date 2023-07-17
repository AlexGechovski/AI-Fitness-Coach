package com.example.fittrainer.dtos;

import org.springframework.beans.MutablePropertyValues;

import java.util.List;

public class ChatResponse {

    private List<Choice> choices;


    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    // constructors, getters and setters


}
