package com.skypro.StarBank.service;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.skypro.StarBank.dto.request.DynamicRuleRequest;
import com.skypro.StarBank.dto.response.DynamicRuleResponse;
import com.skypro.StarBank.model.DynamicRule;
import com.skypro.StarBank.model.Query;
import com.skypro.StarBank.repository.DynamicRuleRepository;

@Service
public class DynamicRuleService {
    private final DynamicRuleRepository ruleRepository;

    public DynamicRuleService(DynamicRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    public DynamicRuleResponse createRule(DynamicRuleRequest request) {
        DynamicRule rule = new DynamicRule();
        rule.setName(request.getName());
        rule.setText(request.getText());
        rule.setRule(request.getRule().stream()
                .map(q -> {
                    Query query = new Query();
                    query.setQuery(q.getQuery());
                    query.setArguments(q.getArguments());
                    query.setNegate(q.isNegate());
                    return query;
                }).collect(Collectors.toList()));
        ruleRepository.save(rule);
        return new DynamicRuleResponse(
                rule.getName(),
                rule.getId(),
                rule.getText(),
                request.getRule()
        );
    }
}
