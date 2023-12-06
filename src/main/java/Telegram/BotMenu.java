package Telegram;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class BotMenu extends TelegramLongPollingBot {

    private final String username = "@remembering_007_bot"; // имя бота
    private final String token = "6344773460:AAGlQbXDzHijBYpnMHuExjm9D90PATWr8aU"; // token

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();
            Bootom(chatId);
            if (messageText.toLowerCase().contains("привет")) {
                sendTextMessage(chatId, "Привет! Как дела?");
            } else if (messageText.toLowerCase().contains("дела")) {
                sendTextMessage(chatId, "Хорошо, спасибо! А у вас?");
            } else {
                sendTextMessage(chatId, "Извините, я не понимаю.");
            }
        }
    }

    // отправка сообщения
    private void sendTextMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId); // Установка идентификатора чата
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Компонент кнопки
    private void Bootom(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId); // Установка идентификатора чата
        message.setText("Выберите команду:");
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Команда 1"));
        row1.add(new KeyboardButton("Команда 2"));

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("Команда 3"));
        row2.add(new KeyboardButton("Команда 4"));

        keyboard.add(row1);
        keyboard.add(row2);

        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

}
