package com.example.watchlateryoutubebot.constants;

public enum BotMessageEnum {
    //ответы на команды с клавиатуры
    HELP_MESSAGE("\uD83D\uDC4B Привет, я бот, который сохранит добавит присланные мне ссылки на youtube видео" +
            "в плейлист\n\n" +
            "❗ *Что Вы можете сделать:*\n" +
            "✅ установить в какой плейлист вы хотите сохранять видео\n" +
            "✅ пирслать мне ссылку на видео, которое я добавлю в плейлист\n" +
            "Для работы мне нужно получить авторизацию для доступа к твоим плейлистам\n" +
            "\uD83D\uDC47"),
    AUTH_MESSAGE("Чтобы дать боту возможность добавлять видео в ваши плейлисты на Youtube:\n" +
            "- перейдите по ссылке\n" +
            "- авторизуйетсь в аккаунте Google\n" +
            "- дайте боту запрашиваемые права"),
    UPLOAD_DICTIONARY_MESSAGE("Добавьте файл, соответствующий шаблону. Вы можете сделать это в любой момент"),
    NON_COMMAND_MESSAGE("Пожалуйста, воспользуйтесь клавиатурой\uD83D\uDC47"),
    EXCEPTION_ILLEGAL_MESSAGE("Не понимаю это сообщение \uD83E\uDD37\u200D♂️"),
    EXCEPTION_WHAT_THE_FUCK("Что-то пошло не так. Обратитесь к программисту");



    private final String message;

    BotMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
