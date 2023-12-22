package defaultPackage.Infrastructure;

import defaultPackage.Core.Notification;
import defaultPackage.Infrastructure.Objects.DbParam;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NotificationRepository {
    private final DatabaseDAO _dao;
    public NotificationRepository(DatabaseDAO dao)
    {

        _dao = dao;
    }
    public void Add(Notification notification)
    {
        var param1 = new DbParam();
        param1.key=1; param1.param=notification.id;
        var param2 = new DbParam();
        param2.key=2; param2.param=notification.userId;
        var param3 = new DbParam();
        param3.key=3; param3.param=notification.title;
        var param4 = new DbParam();
        param4.key=4; param4.param=notification.notifyDateTime;
        var array = new ArrayList<DbParam>();
        array.add(param1); array.add(param2); array.add(param3);array.add(param4);
        // для каждой переменной '?' в запросе нужно создать собственный параметр, в определенном порядке, начиная с 1.
        _dao.ExecuteUpdate("INSERT INTO notifications  (id, user_id, title, notify_date_time) VALUES (?, ?, ?, ?)", array);

    }

    // public void getList(long chatId)
    // {
    //     String query  = "SELECT n.title " +
    //     "FROM public.notifications n " +
    //     "INNER JOIN public.users u ON n.user_id = u.id " +
    //     "WHERE u.chatid = ?";  

    //     String userIdAsString = String.valueOf(chatId);

    //     var param1 = new DbParam();
    //     param1.key = 1;
    //     param1.param = userIdAsString;

    //     var dbParams = new ArrayList<DbParam>();
    //     dbParams.add(param1);

    //     ResultSet resultSet = _dao.ExecuteQuery(query, dbParams);

    //     List<String> titles = new ArrayList<>();

    //     while (resultSet.next()) {
    //         String title = resultSet.getString("title");
    //         titles.add(title);
    //     }
    // }
    
}
