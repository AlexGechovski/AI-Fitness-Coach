package com.example.fittrainer.dtos;

import java.util.List;

public class QueryRequestDTO {
    private int topK;
    private List<Double> vector;

    public int getTopK() {
        return topK;
    }

    public void setTopK(int topK) {
        this.topK = topK;
    }

    public List<Double> getVector() {
        return vector;
    }

    public void setVector(List<Double> vector) {
        this.vector = vector;
    }
}

