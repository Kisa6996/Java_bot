package defaultPackage.Infrastructure;

import defaultPackage.Core.User;
import defaultPackage.Infrastructure.Objects.DbParam;

import java.util.ArrayList;

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
}
