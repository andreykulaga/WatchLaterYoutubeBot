package com.example.watchlateryoutubebot;

import com.example.watchlateryoutubebot.constants.ButtonNameEnum;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class KeyboardMaker {

    public ReplyKeyboardMarkup getMainMenuKeyboard() {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(ButtonNameEnum.GET_AUTH_BUTTON.getButtonName()));

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton(ButtonNameEnum.GET_MY_PLAYLISTS_BUTTON.getButtonName()));

        KeyboardRow row3 = new KeyboardRow();
        row3.add(new KeyboardButton(ButtonNameEnum.HELP_BUTTON.getButtonName()));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);


        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }

    public InlineKeyboardMarkup getListOfPlaylistToChoose(HashMap<String, String> playlists) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (String id: playlists.keySet()) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(playlists.get(id));
            inlineKeyboardButton.setCallbackData(id + " " + playlists.get(id));

            ArrayList<InlineKeyboardButton> row = new ArrayList<>();
            row.add(inlineKeyboardButton);
            keyboard.add(row);
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }
}
