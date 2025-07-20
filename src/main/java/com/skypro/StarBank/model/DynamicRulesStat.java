package com.skypro.StarBank.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "dynamic_rules_stat")
public class DynamicRulesStat {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "VARCHAR(255)")
    private String ruleId;

    @OneToOne
    @JoinColumn(name = "rule_id")
    private DynamicRule dynamicRule;

    @Column(name = "count")
    private long executionCount;
    @Column(name = "last_executed")
    private java.sql.Timestamp lastExecuted;

    public DynamicRulesStat() {}

    public DynamicRulesStat(String ruleId, long executionCount) {
        this.ruleId = ruleId;
        this.executionCount = executionCount;
        this.lastExecuted = new java.sql.Timestamp(System.currentTimeMillis());
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public long getExecutionCount() {
        return executionCount;
    }

    public void setExecutionCount(long executionCount) {
        this.executionCount = executionCount;
    }

    public java.sql.Timestamp getLastExecuted() {
        return lastExecuted;
    }

    public void setLastExecuted(java.sql.Timestamp lastExecuted) {
        this.lastExecuted = lastExecuted;
    }
}
