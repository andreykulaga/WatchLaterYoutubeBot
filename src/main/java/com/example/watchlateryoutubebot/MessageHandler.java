package com.example.watchlateryoutubebot;

import com.example.watchlateryoutubebot.configurations.GoogleConfig;
import com.example.watchlateryoutubebot.configurations.TelegramConfig;
import com.example.watchlateryoutubebot.constants.BotMessageEnum;
import com.example.watchlateryoutubebot.constants.ButtonNameEnum;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistListResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class MessageHandler {
    private final GoogleConfig googleConfig;
    private final ReplyKeyboardMaker replyKeyboardMaker;
    private final AuthorizationCodeFlow authorizationCodeFlow;
    private final TelegramConfig telegramConfig;

    public BotApiMethod<?> answerMessage(Message message) {
        String userId = String.valueOf(message.getFrom().getId());
        String chatId = message.getChatId().toString();
        String inputText = message.getText();

        if (inputText == null) {
            throw new IllegalArgumentException();
        } else if (inputText.equals("/start")) {
            return getStartMessage(chatId);
        } else if (inputText.equals(ButtonNameEnum.GET_AUTH_BUTTON.getButtonName())) {
            return getAuthMessage(chatId, userId);
        } else if (inputText.equals(ButtonNameEnum.GET_MY_PLAYLISTS_BUTTON.getButtonName())) {
            return getMyPlayListsMessage(chatId, userId);
        } else if (inputText.equals(ButtonNameEnum.HELP_BUTTON.getButtonName())) {
            SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.HELP_MESSAGE.getMessage());
            sendMessage.enableMarkdown(true);
            return sendMessage;
        } else {
            return new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
        }
    }

    private SendMessage getStartMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.HELP_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }

    private SendMessage getAuthMessage(String chatId, String userId) {
        String authUrl = authorizationCodeFlow.newAuthorizationUrl()
                .setRedirectUri(googleConfig.getRedirectUri())
                .setState(userId)
                .toString();
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.AUTH_MESSAGE.getMessage() +
                "\n" + "\uD83D\uDC49 " + "[Ссылка для авторизации](" + authUrl + ")");
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }

    private SendMessage getMyPlayListsMessage(String chatId, String userId) {
        String message = "Все твои плейлисты YouTube:\n";
        try {
            Credential credential = authorizationCodeFlow.loadCredential(userId);
            System.out.println("credential loaded for " + userId);
            YouTube youtubeService = new YouTube.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    GsonFactory.getDefaultInstance(),
                    credential)
                    .setApplicationName(telegramConfig.getBotName())
                    .build();
            System.out.println(("YoutubeService is build for " + youtubeService.getApplicationName()));
            YouTube.Playlists.List request = youtubeService.playlists()
                    .list(Arrays.asList("snippet"));
            //todo: change all literals to variables
            PlaylistListResponse response = request.setMaxResults(25L)
                    .setMine(true)
                    .execute();

            List<String> playlists = new ArrayList<>();
            response.getItems().forEach(t -> playlists.add("- " + t.getSnippet().getTitle() + "\n"));
            for (String item:playlists) {
                message = message.concat(item);
            }
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }

        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }
}
