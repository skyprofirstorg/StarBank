package com.skypro.StarBank.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Response {
    private String id;
    private List<RecommendationDTO> recommendations;

    public Response(String id, List<RecommendationDTO> recommendations) {
        this.id = id;
        this.recommendations = recommendations;
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public List<RecommendationDTO> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<RecommendationDTO> recommendations) {
        this.recommendations = recommendations;
    }
}
