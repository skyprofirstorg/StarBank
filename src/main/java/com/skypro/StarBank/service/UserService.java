package com.skypro.StarBank.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.skypro.StarBank.repository.UserDAO;

@Service
public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Optional<UUID> getUserIdByName(String username) {
        return userDAO.getUserIdByName(username);
    }

    public Optional<String> getFullName(String username) {
        return userDAO.getFullName(username);
    }
}
