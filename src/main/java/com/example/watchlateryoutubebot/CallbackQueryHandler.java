package com.example.watchlateryoutubebot;

import com.example.watchlateryoutubebot.models.TelegramUser;
import com.example.watchlateryoutubebot.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.User;

@Component
@AllArgsConstructor
public class CallbackQueryHandler {

    private final UserRepository userRepository;
    private final KeyboardMaker keyboardMaker;

    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery) {
        User user = callbackQuery.getFrom();

        String callbackQueryData = callbackQuery.getData();
        int divider = callbackQueryData.indexOf(" ");
        String playlistId = callbackQueryData.substring(0, divider);
        String playlistName = callbackQueryData.substring(divider+1);

        TelegramUser telegramUser = new TelegramUser(String.valueOf(user.getId()),
                user.getUserName());
        telegramUser.setPlaylistId(playlistId);
        telegramUser.setPlaylistName(playlistName);
        userRepository.saveAndFlush(telegramUser);

//        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
//        answerCallbackQuery.setCallbackQueryId(callbackQuery.getId());
//        answerCallbackQuery.setText("I will save videos in " + playListName);

        SendMessage sendMessage = new SendMessage(callbackQuery.getMessage().getChatId().toString(), "I will save videos in \"" + playlistName + "\"");
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(keyboardMaker.getMainMenuKeyboard());

        return sendMessage;
    }
}
