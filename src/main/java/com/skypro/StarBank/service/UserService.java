package com.skypro.StarBank.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.skypro.StarBank.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userDAO;

    public UserService(UserRepository userDAO) {
        this.userDAO = userDAO;
    }

    public Optional<UUID> getUserIdByName(String username) {
        return userDAO.getUserIdByName(username);
    }

    public Optional<String> getFullName(String username) {
        return userDAO.getFullName(username);
    }
}
