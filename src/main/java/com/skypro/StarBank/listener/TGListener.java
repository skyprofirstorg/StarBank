package com.skypro.StarBank.listener;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.StarBank.service.BotService;
import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class TGListener {
    private final TelegramBot telegramBot;
    private final BotService botService;

    public TGListener(TelegramBot telegramBot, BotService botService) {
        this.telegramBot = telegramBot;
        this.botService = botService;

    }
    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                if (update.message() != null && update.message().text() != null) {
                    String chatId = String.valueOf(update.message().chat().id());
                    String message = botService.sendMessage(update.message().text());
                    telegramBot.execute(new SendMessage(chatId, message));
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
