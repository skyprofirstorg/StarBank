package com.skypro.StarBank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skypro.StarBank.model.DynamicRulesStat;


@Repository
public class DynamicRulesStatRepository {
    public interface InnerDynamicRulesStatRepository extends JpaRepository<DynamicRulesStat, String> {
        
    }
}
