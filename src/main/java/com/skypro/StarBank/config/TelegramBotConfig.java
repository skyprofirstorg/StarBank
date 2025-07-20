package com.skypro.StarBank.config;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class TelegramBotConfig {
    @Value("${telegram.bot.token}")
    private String botToken;

    @Bean
    public TelegramBot telegramBot() {
        return new TelegramBot(botToken);
    }
}
