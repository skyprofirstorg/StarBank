package com.skypro.StarBank.repository;

import com.skypro.StarBank.model.DynamicRule;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DynamicRuleRepository extends JpaRepository<DynamicRule, String> {
    @Query("SELECT * FROM DynamicRule WHERE id = :id")
    public void deleteById(String id);
}
