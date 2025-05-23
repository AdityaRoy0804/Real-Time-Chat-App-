package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static final String URL = "jdbc:mysql://localhost:3306/chatapp";
    private static final String USER = "root";       // Change accordingly
    private static final String PASSWORD = "Aditya@2004";  // Change accordingly

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
