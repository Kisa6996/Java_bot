package defaultPackage.Telegram;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import defaultPackage.Infrastructure.DatabaseDAO;
import defaultPackage.Infrastructure.UserRepository;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.User;


public class BotMenu extends TelegramLongPollingBot {

    private final String username = "@remembering_007_bot"; // имя бота
    private final UserRepository userRepo;

    public BotMenu(UserRepository userRepo)
    {
        super("6344773460:AAGlQbXDzHijBYpnMHuExjm9D90PATWr8aU");
        this.userRepo = userRepo;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String username = update.getMessage().getFrom().getUserName();

            // Добавляем пользователя в бд, если его там нет
            if (!userRepo.userExists(chatId)) {

                sendTextMessage(chatId, "Это бот-напоминалка🔔. Ниже можно ознакомиться с нашим прайс-листом:\n ✅1 напоминание - 300р. \n ✅2 напоминания 500р. \n ✅3 напоминания - 700р. \n  ✅✅✅✅✅Безлимит - 1 касарб.✅✅✅✅✅ \n Номер киви: 89172498712 ");
                var user = new defaultPackage.Core.User();
                user.username = username;
                user.id = UUID.randomUUID();
                user.chatId = String.valueOf(chatId);

                userRepo.Add(user);
            }

            String messageText = update.getMessage().getText();

            //Buttons(chatId);
            if (messageText.toLowerCase().contains("привет")) {
                    String greetingMessage = String.format("Здрав, %s!", username);
                    sendTextMessage(chatId, greetingMessage);

            } else if (messageText.toLowerCase().contains("дела")) {
                sendTextMessage(chatId, "Хорошо, спасибо! А у вас?");
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
    private void Buttons(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
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

}
