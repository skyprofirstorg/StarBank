package com.skypro.StarBank.repository;

import com.skypro.StarBank.model.DynamicRule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DynamicRuleRepository extends JpaRepository<DynamicRule, String> {
    public void deleteById(String id);
}
