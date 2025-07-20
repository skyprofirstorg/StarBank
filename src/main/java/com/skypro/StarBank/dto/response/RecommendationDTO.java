package com.skypro.StarBank.dto.response;

import lombok.Getter;

@Getter
public class RecommendationDTO {
    private String id;
    private String name;
    private String text;


    public RecommendationDTO(String id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getText() { return text; }
}