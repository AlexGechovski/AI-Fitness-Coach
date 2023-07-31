package com.example.fittrainer.dtos;

import java.util.List;

public class EmbeddingDTO {

    private String object;
    private List<Float> embedding;
    private int index;

    public EmbeddingDTO() {
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<Float> getEmbedding() {
        return embedding;
    }

    public void setEmbedding(List<Float> embedding) {
        this.embedding = embedding;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
