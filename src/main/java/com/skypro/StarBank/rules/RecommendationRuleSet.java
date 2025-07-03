package com.skypro.StarBank.rules;

import java.util.Optional;

import com.skypro.StarBank.dto.response.RecommendationDTO;


public interface RecommendationRuleSet {
    Optional<RecommendationDTO> check(String userId);
}
