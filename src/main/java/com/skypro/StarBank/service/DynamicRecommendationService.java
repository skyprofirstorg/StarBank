package com.skypro.StarBank.service;

import com.skypro.StarBank.dto.response.RecommendationDTO;
import com.skypro.StarBank.repository.DynamicRuleRepository;
import com.skypro.StarBank.rules.DynamicRuleChecker;
import com.skypro.StarBank.rules.RecommendationRuleSet;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DynamicRecommendationService {
    private final List<RecommendationRuleSet> staticRules;
    private final DynamicRuleRepository dynamicRuleRepository;
    private final DynamicRuleChecker dynamicRuleChecker;

    public DynamicRecommendationService(List<RecommendationRuleSet> staticRules,
                                 DynamicRuleRepository dynamicRuleRepository,
                                 DynamicRuleChecker dynamicRuleChecker) {
        this.staticRules = staticRules;
        this.dynamicRuleRepository = dynamicRuleRepository;
        this.dynamicRuleChecker = dynamicRuleChecker;
    }

    public List<RecommendationDTO> getRecommendations(String userId) {
        List<RecommendationDTO> recommendations = staticRules.stream()
                .map(r -> r.check(userId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        List<RecommendationDTO> dynamicRecs = dynamicRuleRepository.findAll().stream()
                .filter(rule -> dynamicRuleChecker.checkRule(userId, rule))
                .map(rule -> new RecommendationDTO(
                        rule.getId(),
                        rule.getName(),
                        rule.getText()
                ))
                .collect(Collectors.toList());

        recommendations.addAll(dynamicRecs);
        return recommendations;
    }


}