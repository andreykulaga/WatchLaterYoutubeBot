package com.example.watchlateryoutubebot;
import com.example.watchlateryoutubebot.constants.BotMessageEnum;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;

import com.google.api.client.util.store.DataStoreFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
//@Component
public class WatchLaterYoutubeBot extends TelegramWebhookBot {
    String botPath;
    String botUsername;
    String botToken;

    private final MessageHandler messageHandler;
    private final CallbackQueryHandler callbackQueryHandler;

    public WatchLaterYoutubeBot(SetWebhook setWebhook,
                                MessageHandler messageHandler, CallbackQueryHandler callbackQueryHandler) {
        super();
        this.messageHandler = messageHandler;
        this.callbackQueryHandler = callbackQueryHandler;
    }

    //       if (update.getMessage().getText().equalsIgnoreCase("/authorize")) {
//    private String getAuthUrl(String userId) {
//        return authorizationCodeFlow.newAuthorizationUrl()
//                .setRedirectUri("https://d9b9-5-18-185-122.eu.ngrok.io/googleapiresponse/code")
//                .setState(userId)
//                .toString();
//    }
    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            return handleUpdate(update);
        } catch (IllegalArgumentException e) {
            return new SendMessage(update.getMessage().getChatId().toString(),
                    BotMessageEnum.EXCEPTION_ILLEGAL_MESSAGE.getMessage());
        } catch (Exception e) {
            return new SendMessage(update.getMessage().getChatId().toString(),
                    BotMessageEnum.EXCEPTION_WHAT_THE_FUCK.getMessage());
        }
    }

    private BotApiMethod<?> handleUpdate(Update update) throws IOException {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return callbackQueryHandler.answerCallbackQuery(callbackQuery);
//            return messageHandler.answerMessage(update.getMessage());
        } else {
            Message message = update.getMessage();
            if (message != null) {
                return messageHandler.answerMessage(update.getMessage());
            }
        }
        return null;
    }
}

