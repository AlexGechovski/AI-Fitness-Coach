package com.example.fittrainer.dtos;

import java.text.DecimalFormat;
import java.util.List;

public class EmbeddingDTO {

    private String object;
    private List<Double> embedding;
    private int index;

    private static final DecimalFormat df = new DecimalFormat("0.0000000000"); // Your desired format


    public EmbeddingDTO() {
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<Double> getEmbedding() {
        return embedding;
    }

    public void setEmbedding(List<Double> embedding) {
        this.embedding = embedding;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void printEmbedding() {
        System.out.print('[');
        for (Double val : embedding) {
            System.out.print(df.format(val)+", ");
        }
        System.out.println(']');
    }
}
