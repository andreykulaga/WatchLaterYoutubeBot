package com.example.watchlateryoutubebot.constants;

import java.util.HashMap;

public enum ButtonNameEnum {
    GET_AUTH_BUTTON("Give the bot access to YouTube playlist management"),
    GET_MY_PLAYLISTS_BUTTON("Select a playlist to save video"),
    HELP_BUTTON("What is this bot for?");

    private final String buttonName;

    ButtonNameEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}
