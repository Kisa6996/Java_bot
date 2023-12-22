package defaultPackage.Infrastructure;

import defaultPackage.Core.User;
import defaultPackage.Infrastructure.Objects.DbParam;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;

// класс для работы с таблицей пользователей
public class UserRepository {

    private final DatabaseDAO _dao;

    public UserRepository(DatabaseDAO dao)
    {

        _dao = dao;
    }

    public void Add(User user)
    {
        var param1 = new DbParam();
        param1.key=1; param1.param=user.id;
        var param2 = new DbParam();
        param2.key=2; param2.param=user.chatId;
        var param3 = new DbParam();
        param3.key=3; param3.param=user.username;
        var array = new ArrayList<DbParam>();
        array.add(param1); array.add(param2); array.add(param3);
        // для каждой переменной '?' в запросе нужно создать собственный параметр, в определенном порядке, начиная с 1.
        _dao.ExecuteUpdate("INSERT INTO Users (id, chatId, username) VALUES (?, ?, ?)", array);

    }
    public UUID getId(long chatId){
        try{
            String query = "SELECT id FROM public.users WHERE chatid = ?";
            String userIdAsString = String.valueOf(chatId);

            var param1 = new DbParam();
            param1.key = 1;
            param1.param = userIdAsString;

            var dbParams = new ArrayList<DbParam>();
            dbParams.add(param1);

            ResultSet resultSet = _dao.ExecuteQuery(query, dbParams);
            if (resultSet.next()) {
                // Если есть результат, достаем значение id из результата
                UUID userId = (UUID) resultSet.getObject("id");
                // Вернуть найденное значение id
                return userId;
            } else {
                // Возвращаем null или что-то другое, чтобы показать, что значение id не найдено
                return null;
            }

        }catch (Exception e) {
            System.out.println("Ошибка при проверке существования пользователя: " + e.getMessage());
            return null;
        }
    }

    public boolean userExists(long userId) {
        try {
            String query = "SELECT COUNT(*) FROM Users WHERE chatId = ?";

            // Преобразуем userId в строку
            String userIdAsString = String.valueOf(userId);

            // Создаем объект параметра для передачи в запрос
            var param1 = new DbParam();
            param1.key = 1;
            param1.param = userIdAsString;

            // Создаем список параметров и добавляем в него параметры
            var dbParams = new ArrayList<DbParam>();
            dbParams.add(param1);

            // Выполняем запрос с использованием параметров
            ResultSet resultSet = _dao.ExecuteQuery(query, dbParams);

            // Извлекаем результат из результирующего набора
            if (resultSet != null && resultSet.next()) {
                int count = resultSet.getInt(1);

                // Проверяем, что количество больше нуля и возвращаем true
                return count > 0;
            }

            // Если результат не был извлечен или пуст, возвращаем false
            return false;
        } catch (Exception e) {
            System.out.println("Ошибка при проверке существования пользователя: " + e.getMessage());
            return false;
        }
    }


}
