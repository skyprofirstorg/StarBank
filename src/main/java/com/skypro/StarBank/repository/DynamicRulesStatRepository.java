package com.skypro.StarBank.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skypro.StarBank.model.DynamicRulesStat;


@Repository
public interface DynamicRulesStatRepository extends JpaRepository<DynamicRulesStat, String> {
    @Modifying
    @Query("UPDATE DynamicRulesStat d SET d.executionCount = d.executionCount + 1 WHERE ruleId = :ruleId")
    void incrementRuleFireCount(String ruleId);

    @Modifying
    @Query( value = "SELECT d FROM DynamicRulesStat d",
            nativeQuery = true)
    List<Map<String, Object>> getAllStats();

    @Modifying
    @Query("DELETE FROM DynamicRulesStat WHERE ruleId = :ruleId")
    void clearStat(String ruleId);
}