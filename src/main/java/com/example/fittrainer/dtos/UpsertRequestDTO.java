package com.example.fittrainer.dtos;

import java.util.List;

public class UpsertRequestDTO {
    private List<Vector> vectors;

    public List<Vector> getVectors() {
        return vectors;
    }

    public void setVectors(List<Vector> vectors) {
        this.vectors = vectors;
    }

    public static class Vector {
        private String id;
        private List<Double> values;

        public Vector(String id, List<Double> values) {
            this.id = id;
            this.values = values;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<Double> getValues() {
            return values;
        }

        public void setValues(List<Double> values) {
            this.values = values;
        }
    }
}

