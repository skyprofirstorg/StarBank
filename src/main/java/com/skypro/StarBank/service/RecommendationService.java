package com.skypro.StarBank.service;

import com.skypro.StarBank.dto.response.RecommendationDTO;
import com.skypro.StarBank.repository.RecommendationRepository;
import com.skypro.StarBank.rules.RecommendationRuleSet;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final List<RecommendationRuleSet> rules;
    public RecommendationService(List<RecommendationRuleSet> rules, RecommendationRepository repository) {
        this.rules = rules;
    }

    @Cacheable("recommendations")
    public List<RecommendationDTO> getRecommendations(String userId) {
        return rules.stream()
                .map(rule -> rule.check(userId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }


}