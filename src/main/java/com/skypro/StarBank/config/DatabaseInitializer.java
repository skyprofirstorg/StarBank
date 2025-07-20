package com.skypro.StarBank.config;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DatabaseInitializer {
    private final JdbcTemplate jdbcTemplate;

    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS DYNAMIC_RULES_STAT (
                RULE_ID BIGINT PRIMARY KEY,
                COUNT BIGINT NOT NULL DEFAULT 0,
                LAST_EXECUTED TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
        """);
        System.out.println("Таблица DYNAMIC_RULES_STAT готова.");
    }
}
