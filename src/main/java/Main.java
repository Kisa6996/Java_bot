import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.UUID;

import defaultPackage.Core.Notification;
import defaultPackage.Core.User;
import defaultPackage.Infrastructure.DatabaseDAO;
import defaultPackage.Infrastructure.UserRepository;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import defaultPackage.Telegram.BotMenu;

public class Main {
    public static void main(String[] args) throws TelegramApiException, IOException {

        String url = "jdbc:postgresql://localhost:5432/BotDatabase";
        Properties props = new Properties();
        props.setProperty("user", "user");
        props.setProperty("password", "password");
        props.setProperty("ssl", "false");
        var database = new DatabaseDAO(url, props);
        var userRepo = new UserRepository(database);

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

        botsApi.registerBot(new BotMenu(userRepo));
    }
}