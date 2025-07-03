package com.skypro.StarBank.controller;

import com.skypro.StarBank.dto.request.DynamicRuleRequest;
import com.skypro.StarBank.dto.response.DynamicRuleResponse;
import com.skypro.StarBank.model.DynamicRule;
import com.skypro.StarBank.model.Query;
import com.skypro.StarBank.repository.DynamicRuleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rule")
public class DynamicRuleController {
    private final DynamicRuleRepository ruleRepository;

    public DynamicRuleController(DynamicRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @PostMapping
    public ResponseEntity<DynamicRuleResponse> createRule(@RequestBody DynamicRuleRequest request) {
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
        return ResponseEntity.ok(new DynamicRuleResponse(
                rule.getName(),
                rule.getId(),
                rule.getText(),
                request.getRule()
        ));
    }


    @GetMapping
    public ResponseEntity<Map<String, List<DynamicRule>>> getAllRules() {
        Map<String, List<DynamicRule>> response = new HashMap<>();
        response.put("data", ruleRepository.findAll());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{product_id}")
    public ResponseEntity<Void> deleteRule(@PathVariable String product_id) {
        ruleRepository.deleteById(product_id);
        return ResponseEntity.noContent().build();
    }
}
