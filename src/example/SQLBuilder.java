package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by ADI on 20-10-2015.
 */
public class SQLBuilder {

    private static String sqlUrl = "jdbc:mysql://localhost:3306/dbcon";
    private static String sqlUser = "root";
    private static String sqlPassword = "";

    private Connection connection = null;

    public SQLBuilder(){

        try {
            connection = DriverManager.getConnection(sqlUrl, sqlUser, sqlPassword);

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public String getSqlUsers(){

        return "SELECT * FROM users";
    }

    public String getSqlUser(){

        return "SELECT * FROM users WHERE id = ?";
    }

    public String updateSqlUser(){

        return "UPDATE users SET first_name = ?, last_name = ?, email = ?, password = ?, " +
                "status = ?, type = ? WHERE id = ?";
    }

    public Connection getConnection() {

        return connection;
    }
}
