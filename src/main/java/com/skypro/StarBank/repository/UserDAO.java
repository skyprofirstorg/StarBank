package com.skypro.StarBank.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class UserDAO {

    private final JdbcTemplate jdbcTemplate;

    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<UUID> getUserIdByName(String username){
        String sql = "SELECT id FROM users WHERE username = ?";
        try {
            UUID userId = jdbcTemplate.queryForObject(sql, new Object[]{username}, UUID.class);
            return Optional.ofNullable(userId);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<String> getFullName(String username){
        String firstName = "SELECT first_name FROM users WHERE username = ?";
        String lastName = "SELECT last_name FROM users WHERE username = ?";
        try {
            String first = jdbcTemplate.queryForObject(firstName, new Object[]{username}, String.class);
            String last = jdbcTemplate.queryForObject(lastName, new Object[]{username}, String.class);
            if (first != null && last != null) {
                return Optional.of(first + " " + last);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
