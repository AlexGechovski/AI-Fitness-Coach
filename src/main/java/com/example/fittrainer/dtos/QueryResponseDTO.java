package com.example.fittrainer.dtos;

import java.util.List;

public class QueryResponseDTO {
    private List<MatchDTO> matches;
    private String namespace;

    public QueryResponseDTO() {
    }

    // Constructor
    public QueryResponseDTO(List<MatchDTO> matches, String namespace) {
        this.matches = matches;
        this.namespace = namespace;
    }

    // Getters and Setters
    public List<MatchDTO> getMatches() {
        return matches;
    }

    public void setMatches(List<MatchDTO> matches) {
        this.matches = matches;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}

