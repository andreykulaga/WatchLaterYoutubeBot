package com.example.watchlateryoutubebot;
import com.example.watchlateryoutubebot.constants.BotMessageEnum;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;

import com.google.api.client.util.store.DataStoreFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
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

    public WatchLaterYoutubeBot(SetWebhook setWebhook,
                                MessageHandler messageHandler) {
        super();
        this.messageHandler = messageHandler;
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
//            return callbackQueryHandler.processCallbackQuery(callbackQuery);
            return messageHandler.answerMessage(update.getMessage());
        } else {
            Message message = update.getMessage();
            if (message != null) {
                return messageHandler.answerMessage(update.getMessage());
            }
        }
        return null;
    }
}
//    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
//        if (!update.hasMessage()) {
//            System.out.println("апдейт без сообщения");
//            return null;
//        }
//        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
//
//        message.setChatId(update.getMessage().getChatId().toString());
//
//        message.setText("неизвестная команда");
//
//        String userId = String.valueOf(update.getMessage().getFrom().getId());
//
//        try {
//            execute(message); // Call method to send the message
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

//        if (userRepository.findById(userId).isPresent()) {
//            TelegramUser telegramUser = userRepository.getReferenceById(userId);
//        } else {
//            TelegramUser telegramUser = new TelegramUser();
//            telegramUser.setTelegramId(userId);
//            telegramUser.setUserName(update.getMessage().getFrom().getUserName());
//            userRepository.save(telegramUser);
//        }


//        if (update.getMessage().getText().startsWith("https://youtu.be/")) {
//            String videoId = update.getMessage().getText().substring(17);
//
//            try {
//                Credential credential = authorizationCodeFlow.loadCredential(userId);
//                YouTube youtubeService = new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), GsonFactory.getDefaultInstance(), credential)
//                        .setApplicationName("WatchLaterYoutubeBot")
//                        .build();
//
//                YouTube.Playlists.List request = youtubeService.playlists()
//                        .list(Arrays.asList("snippet"));
//                PlaylistListResponse response = request.setMaxResults(25L)
//                        .setMine(true)
//                        .execute();
//                Map<String, String> playlistsId = new HashMap<>();
//                response.getItems().forEach(t -> playlistsId.put(t.getSnippet().getTitle(), t.getId()));
//                String playlistId = playlistsId.get("Watch Later from Telegram");
//
//                // Define the PlaylistItem object, which will be uploaded as the request body.
//                PlaylistItem playlistItem = new PlaylistItem();
//
//                // Add the snippet object property to the PlaylistItem object.
//                PlaylistItemSnippet snippet = new PlaylistItemSnippet();
//                snippet.setPlaylistId(playlistId);
//                snippet.setPosition(0L);
//                ResourceId resourceId = new ResourceId();
//                resourceId.setKind("youtube#video");
//                resourceId.setVideoId(videoId);
//                snippet.setResourceId(resourceId);
//                playlistItem.setSnippet(snippet);
//
//                // Define and execute the API request
//                YouTube.PlaylistItems.Insert newRequest = youtubeService.playlistItems()
//                        .insert(Collections.singletonList("snippet"), playlistItem);
//                newRequest.execute();
//                message.setText("видео добавлено в плейлист \"Watch Later from Telegram\"");
//
//
//            } catch (IOException | GeneralSecurityException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        if (update.getMessage().getText().equalsIgnoreCase("/create_playlist")) {
//            try {
//                Credential credential = authorizationCodeFlow.loadCredential(userId);
//                YouTube youtubeService = new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), GsonFactory.getDefaultInstance(), credential)
//                        .setApplicationName("WatchLaterYoutubeBot")
//                        .build();
//
//                YouTube.Playlists.List request = youtubeService.playlists()
//                        .list(Arrays.asList("snippet"));
//                PlaylistListResponse response = request.setMaxResults(25L)
//                        .setMine(true)
//                        .execute();
//                List<String> playlists = new ArrayList<>();
//                response.getItems().forEach(t -> playlists.add(t.getSnippet().getTitle()));
//
//                if (playlists.contains("Watch Later from Telegram")) {
//                    message.setText("Плейлист \"Watch Later from Telegram\" уже существует");
//                } else {
//                    // Define the Playlist object, which will be uploaded as the request body.
//                    Playlist playlist = new Playlist();
//
//                    // Add the snippet object property to the Playlist object.
//                    PlaylistSnippet snippet = new PlaylistSnippet();
//                    snippet.setDescription("Playlist from WatchLaterYoutubeBot");
//                    snippet.setTitle("Watch Later from Telegram");
//                    playlist.setSnippet(snippet);
//
//                    // Add the status object property to the Playlist object.
//                    PlaylistStatus status = new PlaylistStatus();
//                    status.setPrivacyStatus("private");
//                    playlist.setStatus(status);
//
//                    // Define and execute the API request
//                    YouTube.Playlists.Insert newRequest = youtubeService.playlists()
//                            .insert(Arrays.asList("snippet", "status"), playlist);
//                    newRequest.execute();
//
//
//                    message.setText("Создан плейлист \"Watch Later from Telegram\"");
//
//                }
//            } catch (IOException | GeneralSecurityException e) {
//                throw new RuntimeException(e);
//
//            }
//        }

