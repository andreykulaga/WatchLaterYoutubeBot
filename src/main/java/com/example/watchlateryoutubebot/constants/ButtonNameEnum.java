package com.example.watchlateryoutubebot.constants;

public enum ButtonNameEnum {
    GET_AUTH_BUTTON("Дать боту доступ к управлению плейлистами YouTube"),
    GET_MY_PLAYLISTS_BUTTON("Установить название плейлиста для сохранения видео"),
    HELP_BUTTON("Для чего нужен этот бот?");

    private final String buttonName;

    ButtonNameEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}
