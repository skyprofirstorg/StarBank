package com.skypro.StarBank.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DynamicRulesStatDAO {
    private final JdbcTemplate jdbcTemplate;

    public DynamicRulesStatDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    

        public void incrementRuleFireCount(String ruleId) {
        String selectSql = "SELECT COUNT(*) FROM DYNAMIC_RULES_STAT WHERE RULE_ID = ?";
        Integer count = jdbcTemplate.queryForObject(selectSql, Integer.class, ruleId);

        if (count != null && count > 0) {
            String updateSql = """
                UPDATE DYNAMIC_RULES_STAT
                SET COUNT = COUNT + 1,
                    LAST_EXECUTED = CURRENT_TIMESTAMP
                WHERE RULE_ID = ?
            """;
            jdbcTemplate.update(updateSql, ruleId);
        } else {
            String insertSql = """
                INSERT INTO DYNAMIC_RULES_STAT (RULE_ID, COUNT, LAST_EXECUTED)
                VALUES (?, 1, CURRENT_TIMESTAMP)
            """;
            jdbcTemplate.update(insertSql, ruleId);
        }
    }

    public List<Map<String, Object>> getAllStats() {
        String sql = "SELECT RULE_ID, COUNT, LAST_EXECUTED FROM DYNAMIC_RULES_STAT";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        
        return result != null ? result : new ArrayList<>();
    }

    public void deleteStat(String ruleId) {
        String sql = "DELETE FROM DYNAMIC_RULES_STAT WHERE RULE_ID = ?";
        jdbcTemplate.update(sql, ruleId);
    }
}