package com.example.watchlateryoutubebot;
import com.example.watchlateryoutubebot.configurations.GoogleConfig;
import com.example.watchlateryoutubebot.configurations.TelegramConfig;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;


@Configuration
@AllArgsConstructor
public class SpringConfig {
    private final TelegramConfig telegramConfig;
    private final GoogleConfig googleConfig;
    private final CredentialRepository credentialRepository;


    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(telegramConfig.getWebhookPath()).build();
    }

    @Bean
    public WatchLaterYoutubeBot springWebhookBot(SetWebhook setWebhook, MessageHandler messageHandler) {
        WatchLaterYoutubeBot bot = new WatchLaterYoutubeBot(setWebhook, messageHandler);

        bot.setBotPath(telegramConfig.getWebhookPath());
        bot.setBotUsername(telegramConfig.getBotName());
        bot.setBotToken(telegramConfig.getBotToken());

        return bot;
    }

    //TODO добавление видео youtube в плейлист, обновление токена, хранение кредентиал в базе данных вместе с userID,
//    @Bean
//    public FileDataStoreFactory fileDataStoreFactory() throws IOException {
//        return new FileDataStoreFactory(new File("src/main/resources/credential"));
//    }

    @Bean
    public MyDataStoreFactory myDataStoreFactory() throws IOException {
        return new MyDataStoreFactory(credentialRepository);
    }


    @Bean
    public GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow() throws IOException {
        GoogleAuthorizationCodeFlow authorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                googleConfig.getClientId(),
                googleConfig.getClientSecret(),
                Arrays.asList("https://www.googleapis.com/auth/youtube.force-ssl", "https://www.googleapis.com/auth/youtube.readonly"))
                .setCredentialDataStore(StoredCredential.getDefaultDataStore(myDataStoreFactory()))
                .setAccessType("offline")
                //TODO сделать переменную для offline
//                .setApprovalPrompt("force")
                .build();
        return authorizationCodeFlow;
        //TODO refactoring, check refreshed token, implement my own data store factory for credentials
    }
}