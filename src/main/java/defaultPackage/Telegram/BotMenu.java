package defaultPackage.Telegram;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.sql.Timestamp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import defaultPackage.Infrastructure.DatabaseDAO;
import defaultPackage.Infrastructure.UserRepository;
import defaultPackage.Infrastructure.NotificationRepository;
import org.telegram.telegrambots.meta.api.objects.Message;
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
    private final NotificationRepository notificatuonRepo;

    public BotMenu(UserRepository userRepo, NotificationRepository notificatuonRepo) {
        super("6344773460:AAGlQbXDzHijBYpnMHuExjm9D90PATWr8aU");
        this.userRepo = userRepo;
        this.notificatuonRepo = notificatuonRepo;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String username = update.getMessage().getFrom().getUserName();

            // Добавляем пользователя в бд, если его там нет
            if (!userRepo.userExists(chatId)) {

                sendTextMessage(chatId,
                        "Это бот-напоминалка🔔. Ниже можно ознакомиться с нашим прайс-листом:\n ✅1 напоминание - 300р. \n ✅2 напоминания 500р. \n ✅3 напоминания - 700р. \n  ✅✅✅✅✅Безлимит - 1 касарь.✅✅✅✅✅ \n Номер киви: 89172498712 ");
                var user = new defaultPackage.Core.User();
                user.username = username;
                user.id = UUID.randomUUID();
                user.chatId = String.valueOf(chatId);

                userRepo.Add(user);
            }
            // Считываем сообщение
            Message message = update.getMessage();
            String messageText = message.getText();

            // Регулярка на "- "
            String regex = "^-\\s";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(messageText);
            // if (messageText.toLowerCase().contains("список")) {
            // System.out.println(1);
            // List<String> arr = notificatuonRepo.getList(chatId);
            // System.out.println(arr);
            // }
            if (messageText.toLowerCase().contains("привет")) {
                String greetingMessage = String.format("Здрав, %s! ", username);
                sendTextMessage(chatId, greetingMessage);
            } else if (matcher.find()) {
                messageText = messageText.substring(2);
                long messageTime = message.getDate();
                String greetingMessage = String.format("Ваше напоминание успешно записанно");
                sendTextMessage(chatId, greetingMessage);

                var notification = new defaultPackage.Core.Notification();
                notification.id = UUID.randomUUID();
                notification.title = messageText;
                notification.notifyDateTime = new Timestamp(messageTime * 1000);
                notification.userId = userRepo.getId(chatId);
                notificatuonRepo.Add(notification);

            } else if (messageText.toLowerCase().contains("список")) {
                List<String> arr = notificatuonRepo.getList(chatId);
                System.out.println(arr);

                for (int i = 0; i < arr.size(); i++) {
                    String greetingMessage = String.format(i+1+ ". " + arr.get(i));
                    sendTextMessage(chatId, greetingMessage);
                }

            } else {
                String greetingMessage = String.format(
                        "Это бот-напоминалка🔔\n Для создания уведомления нужно в начале текста написать '- '\n Например: '- Сходить на пары'");
                sendTextMessage(chatId, greetingMessage);
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

    @Override
    public String getBotUsername() {
        return username;
    }

}
