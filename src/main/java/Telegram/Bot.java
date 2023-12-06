package Telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Bot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        //TODO
    }

    @Override
    public String getBotUsername() {
        //TODO
        return null;
    }

    @Override
    public String getBotToken(){
        //TODO разрбраться почему метод deprecated, найти актуальный способ передать токен и попробовать вызвать бота
        return null;
    }
}
