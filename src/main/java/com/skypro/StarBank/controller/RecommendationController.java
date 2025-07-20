package com.skypro.StarBank.controller;

import com.skypro.StarBank.dto.response.RecommendationDTO;
import com.skypro.StarBank.service.RecommendationService;
import com.skypro.StarBank.dto.response.Response;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{user_id}")
    public Response getRecommendations(@PathVariable("user_id") String userId) {
        List<RecommendationDTO> recommendations = recommendationService.getRecommendations(userId);
        return new Response(userId, recommendations);
    }
}
    