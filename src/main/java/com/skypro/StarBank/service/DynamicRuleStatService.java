package com.skypro.StarBank.service;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.skypro.StarBank.repository.DynamicRulesStatRepository;

@Service
public class DynamicRuleStatService {

    private final DynamicRulesStatRepository repository;

    public DynamicRuleStatService(DynamicRulesStatRepository repository) {
        this.repository = repository;
    }

    public void recordRuleExecution(String ruleId) {
        repository.incrementRuleFireCount(ruleId);
    }

    public List<Map<String, Object>> getStats() {
        return repository.getAllStats();
    }

    public void deleteStat(String ruleId) {
        repository.clearStat(ruleId);
    }
}

