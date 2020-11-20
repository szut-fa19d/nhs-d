package datastorage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBuilder {
    private static Connection conn;

    private ConnectionBuilder() {
        try {
            System.out.println("Working Directory = " + System.getProperty("user.dir"));

            DriverManager.getConnection("jdbc:hsqldb:db/nursingHomeDB;user=SA;password=SA");
        } catch (SQLException sqlException) {
            System.out.println("Verbindung zur Datenbank konnte nicht aufgebaut werden!");
            sqlException.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (conn == null) {
            new ConnectionBuilder();
        }
        return conn;
    }

    public static void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean hasConnection() {
        return conn != null;
    }
}
