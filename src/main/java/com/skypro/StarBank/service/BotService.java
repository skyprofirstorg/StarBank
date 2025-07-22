package com.skypro.StarBank.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

@Service
public class BotService {
    private final UserService userService;
    private final RecommendationService recommendationService;
    private final DynamicRecommendationService dynamicRecommendationService;
    private final TelegramBot telegramBot;

    public BotService(UserService userService, RecommendationService recommendationService,
                      DynamicRecommendationService dynamicRecommendationService, TelegramBot telegramBot) {
        this.userService = userService;
        this.recommendationService = recommendationService;
        this.dynamicRecommendationService = dynamicRecommendationService;
        this.telegramBot = telegramBot;
    }

    public void sendMessage(String chatId, String message) {
        telegramBot.setUpdatesListener(updates -> {
            for (Update update : updates) {

                if (update.message() != null && update.message().text() != null) {
                    String text = update.message().text();
                    if (text.startsWith("/recommendations")) {
                        String[] parts = text.split(" ", 2);
                        if (parts.length == 2) {
                            String userName = parts[1].trim();
                            String fullName = userService.getFullName(userName)
                                    .orElse("Пользователь с ником " + userName + " не найден.");

                            String response = "Здравствуйте " + fullName + "\n Новые продукты для вас : \n";
                            Optional<UUID> userIdOpt = userService.getUserIdByName(userName);
                            if (userIdOpt.isPresent()) {
                                UUID userId = userIdOpt.get();
                                response += recommendationService.getRecommendations(userId.toString())
                                        .stream()
                                        .map(rec -> rec.getName() + ": " + rec.getText())
                                        .reduce("", (a, b) -> a + "\n" + b);
                                response += dynamicRecommendationService.getRecommendations(userId.toString())
                                        .stream()
                                        .map(rec -> rec.getName() + ": " + rec.getText())
                                        .reduce("", (a, b) -> a + "\n" + b);
                                telegramBot.execute(new SendMessage(chatId, response));
                            } else {
                                response = "Пользователь ником " + userName + " не найден.";
                                telegramBot.execute(new SendMessage(chatId, response));
                            }
                        } else {
                            telegramBot.execute(new SendMessage(chatId, "Пожалуйста, укажите никнэйм пользователя после команды /recommendations"));
                        }
                    } else {
                        switch (text) {
                            case "/start" -> telegramBot.execute(new SendMessage(chatId, "Добро пожаловать в StarBank!"));
                            case "/help" -> telegramBot.execute(new SendMessage(chatId,
                                    """
                                    Доступные команды:
                                    /start - Начать
                                    /help - Помощь
                                    /recommendations <nickName> - Получить рекомендации для пользователя
                                    """));
                            default -> telegramBot.execute(new SendMessage(chatId, "Неизвестная команда. Введите /help для получения списка команд."));
                        }
                    }
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
