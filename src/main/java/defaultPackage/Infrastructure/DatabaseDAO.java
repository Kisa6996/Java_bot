package defaultPackage.Infrastructure;

import defaultPackage.Infrastructure.Objects.DbParam;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

// класс для доступа к базе данных
public class DatabaseDAO {

    private String url;
    private Properties databaseConnectionProperties;

    public DatabaseDAO(String host, String user, String password)
    {
        url = host;
        databaseConnectionProperties = new Properties();
        databaseConnectionProperties.setProperty("user", user);
        databaseConnectionProperties.setProperty("password",password);
        databaseConnectionProperties.setProperty("ssl", "false");
    }

    public DatabaseDAO(String host, Properties props) {
        url = host;
        databaseConnectionProperties = props;
    }

    // Вызов sql-запроса для получения данных
    public ResultSet ExecuteQuery(String sql, ArrayList<DbParam> params) {
        try {
            Connection connection = DriverManager.getConnection(url, databaseConnectionProperties);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            if (params != null) {
                for (DbParam param : params) {
                    preparedStatement.setObject(param.key, param.param);
                }
            }

            return preparedStatement.executeQuery();
        } catch (Exception ex) {
            System.out.println("Ошибка при выполнении запроса: " + sql + ";\n Текст ошибки -" + ex.getMessage());
            return null;
        }
    }


    // Вызов sql-запроса для изменения данных
    public void ExecuteUpdate(String sql, ArrayList<DbParam> dbParams)
    {
        try{
            Connection connection = DriverManager.getConnection(url, databaseConnectionProperties);
            var preparedStatement = connection.prepareStatement(sql);
            for (var dbParam:dbParams) {
                preparedStatement.setObject(dbParam.key, dbParam.param);
            }
            preparedStatement.execute();
        }
        catch (Exception ex)
        {
            System.out.println("Ошибка при выполнении изменяющего запроса: "+sql + ";\n Текст ошибки -" + ex.getMessage());
        }
    }
}
