package com.example.watchlateryoutubebot;

import com.example.watchlateryoutubebot.configurations.GoogleConfig;
import com.example.watchlateryoutubebot.configurations.TelegramConfig;
import com.example.watchlateryoutubebot.constants.BotMessageEnum;
import com.example.watchlateryoutubebot.constants.ButtonNameEnum;
import com.example.watchlateryoutubebot.exceptions.UnsupportedServiceException;
import com.example.watchlateryoutubebot.models.TelegramUser;
import com.example.watchlateryoutubebot.repositories.UserRepository;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.TokenResponseException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MessageHandler {
    private final GoogleConfig googleConfig;
    private final KeyboardMaker keyboardMaker;
    private final AuthorizationCodeFlow authorizationCodeFlow;
    private final TelegramConfig telegramConfig;
    private final UserRepository userRepository;
    private final YoutubeService youtubeService;
    private final LinkParser linkParser;

    public BotApiMethod<?> answerMessage(Message message) {
        String userId = String.valueOf(message.getFrom().getId());
        String chatId = message.getChatId().toString();
        String inputText = message.getText();

        List<MessageEntity> messageEntities;
        List<String> textLinks = new ArrayList<>();
        if (message.hasEntities()) {
            messageEntities = message.getEntities();
            textLinks = messageEntities.stream()
                    .filter(t -> t.getType().equalsIgnoreCase("url"))
                    .map(t -> t.getText())
                    .collect(Collectors.toList());
        }

        if (inputText == null) {
            throw new IllegalArgumentException();
//        } else if (userRepository.findById(userId).isPresent()) {
//            return getStartMessage(chatId);
        } else if (inputText.equals("/start")) {
            return getStartMessage(chatId);
        } else if (inputText.equals("/settings")) {
            return getSettingMessage(chatId);
        } else if (inputText.equals(ButtonNameEnum.GET_AUTH_BUTTON.getButtonName())) {
            return getAuthMessage(chatId, userId);
        } else if (inputText.equals(ButtonNameEnum.GET_MY_PLAYLISTS_BUTTON.getButtonName())) {
            return configureMyPlayListsMessage(chatId, userId);
        } else if (inputText.equals(ButtonNameEnum.HELP_BUTTON.getButtonName())) {
            //show start message with bot description
            return getStartMessage(chatId);
        } else if (!textLinks.isEmpty()) {
            return processLink(chatId, userId, textLinks);
        } else {
            return new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
        }
    }

    private BotApiMethod<?> getSettingMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.SETTING_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(keyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }

    private SendMessage getStartMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.HELP_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(keyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }

//    private SendMessage everythingIsReadyMessage(String chatId, String playlistName) {
//        String message = BotMessageEnum.EVERYTHING_IS_READY_MESSAGE.getMessage().replace("playlistName", playlistName);
//        SendMessage sendMessage = new SendMessage(chatId, message);
//        sendMessage.enableMarkdown(true);
////        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
//        return sendMessage;
//    }

    private SendMessage getAuthMessage(String chatId, String userId) {
        String authUrl = authorizationCodeFlow.newAuthorizationUrl()
                .setRedirectUri(googleConfig.getRedirectUri())
                .setState(userId)
                .toString();
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.AUTH_MESSAGE.getMessage() +
                "\n" + "\uD83D\uDC49 " + "[Authorization link](" + authUrl + ")");
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(keyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }

    private SendMessage configureMyPlayListsMessage(String chatId, String userId) {
        String message = "Please press on a playlist you want me to use to save videos.\n";
        HashMap<String, String> playlists;
        try {
            playlists = youtubeService.returnAllPlaylists(userId);
        }  catch (TokenResponseException e) {
            //if token is revoked send a message to give authorization again
            return getAuthMessage(chatId, userId);
        }  catch (EntityNotFoundException e) {
            //TODO how to handle if there is no user in database
            return getAuthMessage(chatId, userId);
        } catch (IOException | GeneralSecurityException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(keyboardMaker.getListOfPlaylistToChoose(playlists));
        return sendMessage;
    }

    private SendMessage processLink(String chatId, String userId, List<String> textLinks) {

        //todo how to handle if there is no userId in database
        TelegramUser user = userRepository.getReferenceById(userId);
        String playListName = user.getPlaylistName();
        if (playListName == null) {
            return configureMyPlayListsMessage(chatId, userId);
        }

        //filter textLinks for YouTube
        List<String> youTubeVideoIds = textLinks.stream().filter(t -> {
            try {
                return linkParser.whatServiceIsIt(t).equalsIgnoreCase("YouTube");
            } catch (UnsupportedServiceException | URISyntaxException e) {
                return false;
            }
        })
                .map(t -> linkParser.getVideoIdFromLink(t))
                .filter(t -> t != null)
                .collect(Collectors.toList());


        List<String> titlesOfAddedVideo = new ArrayList<>();
        for (String st: youTubeVideoIds) {
            try {
                titlesOfAddedVideo.add(youtubeService.addVideo(user, st));
            }  catch (TokenResponseException e) {
                //if token is revoked send a message to give authorization again
                return getAuthMessage(chatId, userId);
            }  catch (EntityNotFoundException e) {
                //TODO how to handle if there is no user in database
                return getAuthMessage(chatId, userId);
            } catch (IOException | GeneralSecurityException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }


        StringBuilder stringBuilder = new StringBuilder();
        if (titlesOfAddedVideo.isEmpty()) {
            stringBuilder.append("I could not add any of the videos");
        } else {
            stringBuilder.append("Videos added to \"" + playListName + "\" playlist: \n");
            for (String string: titlesOfAddedVideo) {
                stringBuilder.append("✅ " + string + "\n");
            }
        }

        String message = stringBuilder.toString();
        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(keyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }
}
