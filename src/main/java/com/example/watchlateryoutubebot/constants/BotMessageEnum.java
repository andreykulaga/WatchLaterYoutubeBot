package com.example.watchlateryoutubebot.constants;

public enum BotMessageEnum {
    //ответы на команды с клавиатуры
    HELP_MESSAGE("\uD83D\uDC4B Hello, I'm a bot that will save the links you send me from YouTube to the playlist you choose.\n\n" +
            "❗*What I can do:*\n" +
            "✅ Set the playlist where you want to save videos\n" +
            "✅ Send me the link to a video, and I'll add it to the playlist\n" +
            "To work, I need authorization to access your YouTube playlists.\n" +
            "\uD83D\uDC47"),
    EVERYTHING_IS_READY_MESSAGE("Everything is set up. Send me a youtube link and I will add it to your playlistName playlist"),
    SETTING_MESSAGE(""),
    AUTH_MESSAGE("To grant the bot permission to add videos to your YouTube playlists:\n" +
            "- Follow the link provided\n" +
            "- Log in to your Google account\n" +
            "- Grant the requested permissions to the bot"),
    NON_COMMAND_MESSAGE("Please, use the keyboard \uD83D\uDC47"),
    EXCEPTION_ILLEGAL_MESSAGE("I don't understand this message \uD83E\uDD37\u200D♂️"),
    EXCEPTION_WHAT_THE_FUCK("Something went wrong. Contact my creator");

    private final String message;

    BotMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
