package com.skypro.StarBank.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;


@Service
public class BotService {

    private final DynamicRecommendationService dynamicRecommendationService;
    private final RecommendationService recommendationService;
    private final UserService userService;

    public BotService(UserService userService, RecommendationService recommendationService, DynamicRecommendationService dynamicRecommendationService) {
        this.userService = userService;
        this.recommendationService = recommendationService;
        this.dynamicRecommendationService = dynamicRecommendationService;
    }

    public String sendMessage(String message) {
        if (message == null || message.isBlank()) {
            return "Команда не распознана. Введите /help для списка команд.";
        }
        message = message.trim();
        if (message.equals("/start")) {
            return "Добро пожаловать в StarBank!";
        } else if (message.equals("/help")) {
            return """
                   Доступные команды:
                   /start - Начать
                   /help - Помощь
                   /recommendations <nickName> - Получить рекомендации для пользователя
                   """;
        } else if (message.startsWith("/recommendations")) {
            return getRecommendationsForUser(message);
        } else {
            return "Неизвестная команда. Попробуйте /help";
        }
    }

    private String getRecommendationsForUser(String text) {
        String[] parts = text.split(" ");
        String username = parts[1].trim();
        String fullName = userService.getFullName(username)
            .orElse("Пользователь с ником " + username +" не найден");
        String response = "Здравствуйте " + fullName + "\n Новые продукты для вас : \n";
        Optional<UUID> userIdOpt = userService.getUserIdByName(username);
        if(userIdOpt.isPresent()){
            UUID userId = userIdOpt.get();
            response += recommendationService.getRecommendations(userId.toString())
                    .stream()
                    .map(rec -> rec.getName() + ": " + rec.getText())
                    .reduce("", (a, b) -> a + "\n" + b);
            response += dynamicRecommendationService.getRecommendations(userId.toString())
                    .stream()
                    .map(rec -> rec.getName() + ": " + rec.getText())
                    .reduce("", (a, b) -> a + "\n" + b);
        }
        return response;
    }
}
