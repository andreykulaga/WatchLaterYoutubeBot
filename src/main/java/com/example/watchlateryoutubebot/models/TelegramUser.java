package com.example.watchlateryoutubebot.models;

import lombok.*;


import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class TelegramUser {
    @Id
    private String telegramId;

    private String userName;
    private String playlistName;
    private String playlistId;
    private boolean scopeIsEnough;

    public TelegramUser(String telegramId, String userName) {
        this.telegramId = telegramId;
        this.userName = userName;
    }
}
