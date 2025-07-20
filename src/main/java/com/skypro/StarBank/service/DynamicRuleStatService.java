package com.skypro.StarBank.service;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.skypro.StarBank.repository.DynamicRulesStatDAO;

@Service
public class DynamicRuleStatService {

    private final DynamicRulesStatDAO dao;

    public DynamicRuleStatService(DynamicRulesStatDAO dao) {
        this.dao = dao;
    }

    public void recordRuleExecution(String ruleId) {
        dao.incrementRuleFireCount(ruleId);
    }

    public List<Map<String, Object>> getStats() {
        return dao.getAllStats();
    }

    public void deleteStat(String ruleId) {
        dao.deleteStat(ruleId);
    }
}

