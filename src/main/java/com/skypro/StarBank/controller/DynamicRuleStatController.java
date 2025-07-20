package com.skypro.StarBank.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skypro.StarBank.service.DynamicRuleStatService;

@RestController
@RequestMapping("/rules")
public class DynamicRuleStatController {

    private final DynamicRuleStatService service;

    public DynamicRuleStatController(DynamicRuleStatService service) {
        this.service = service;
    }

    @GetMapping("/stats")
    public Map<String, List<Map<String, String>>> getAllStats() {
        List<Map<String, Object>> rawStats = service.getStats();

        List<Map<String, String>> stats = rawStats.stream()
            .map(row -> Map.of(
                "rule_id", String.valueOf(row.get("RULE_ID")),
                "count", String.valueOf(row.get("COUNT"))
            ))
            .toList();

        return Map.of("stats", stats);
    }

    @PostMapping("/stats/{ruleId}")
    public void delete(@PathVariable String ruleId) {
        service.deleteStat(ruleId);
    }
}

