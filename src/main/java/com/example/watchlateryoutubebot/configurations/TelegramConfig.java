package com.example.watchlateryoutubebot.configurations;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramConfig {

    @Value("${telegram.webhook-path}")
    String webhookPath;
    @Value("${telegram.bot-name}")
    String botName;
    @Value("${telegram.bot-token}")
    String botToken;

//    @Value("https://d9b9-5-18-185-122.eu.ngrok.io/telegram/")
//    String webhookPath;
//    @Value("WatchLatterYoutubeBot")
//    String botUsername;
//    @Value("5471216892:AAGYuD11eAm40wKKctOcRIeTTTosvE_XzZo")
//    String botToken;

}
