package com.example.watchlateryoutubebot;

import com.example.watchlateryoutubebot.configurations.TelegramConfig;
import com.example.watchlateryoutubebot.models.TelegramUser;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.PlaylistListResponse;
import com.google.api.services.youtube.model.ResourceId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Component
@AllArgsConstructor
public class YoutubeService {

    private final AuthorizationCodeFlow authorizationCodeFlow;
    private final TelegramConfig telegramConfig;

    HashMap<String, String> returnAllPlaylists(String userId) throws GeneralSecurityException, IOException, EntityNotFoundException, TokenResponseException {
        HashMap<String, String> playlists = new HashMap<>();
        Credential credential = authorizationCodeFlow.loadCredential(userId);
        YouTube youtubeService = new YouTube.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                credential)
                .setApplicationName(telegramConfig.getBotName())
                .build();
        YouTube.Playlists.List request = youtubeService.playlists()
                .list(Arrays.asList("snippet"));
        //todo: change all literals to variables
        PlaylistListResponse response = request.setMaxResults(25L)
                .setMine(true)
                .execute();

        response.getItems().forEach(t -> playlists.put(t.getId(), t.getSnippet().getTitle()));
        return playlists;
    }

    String addVideos(TelegramUser user, List<String> textLinks) throws IOException, GeneralSecurityException, EntityNotFoundException, TokenResponseException {
        Credential credential = authorizationCodeFlow.loadCredential(user.getTelegramId());

        YouTube youtubeService = new YouTube.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                credential)
                .setApplicationName(telegramConfig.getBotName())
                .build();


        YouTube.Playlists.List request = youtubeService.playlists()
                .list(Arrays.asList("snippet"))
                .setId(Arrays.asList(user.getPlaylistId()));

        PlaylistItemSnippet snippet = new PlaylistItemSnippet();
        snippet.setPlaylistId(user.getPlaylistId());

        ResourceId resourceId = new ResourceId();
        resourceId.setKind("youtube#video");
        //todo add processing of all links, not only first one
        //todo check for the link being youtube link and parsing videoId
        String videoUrl = textLinks.get(0);
        int indexOfVideoId = videoUrl.indexOf("v=");
        resourceId.setVideoId(videoUrl.substring(indexOfVideoId+2));

        snippet.setResourceId(resourceId);

        PlaylistItem playlistItem = new PlaylistItem();
        playlistItem.setSnippet(snippet);

        PlaylistItem response = youtubeService.playlistItems().insert(Collections.singletonList("snippet,contentDetails"), playlistItem).execute();
        return response.getSnippet().getTitle();
    }
}
