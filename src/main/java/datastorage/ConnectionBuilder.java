package datastorage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Initializes and holds the Database connection statically (singleton)
 */
public class ConnectionBuilder {
    private static Connection conn;

    private ConnectionBuilder() {}

    /**
     * Create the connection and set it statically
     */
    private static void setConnection() {
        try {
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            ConnectionBuilder.conn = DriverManager.getConnection("jdbc:hsqldb:db/nursingHomeDB;user=SA;password=SA");
        } catch (SQLException sqlException) {
            System.out.println("Verbindung zur Datenbank konnte nicht aufgebaut werden!");
            sqlException.printStackTrace();
        }
    }

    /**
     * Get the database connection. Will create and set it if it doesn't exist yet.
     */
    public static Connection getConnection() {
        if (ConnectionBuilder.conn == null) {
            ConnectionBuilder.setConnection();
        }
        return ConnectionBuilder.conn;
    }

    public static void closeConnection() {
        try {
            ConnectionBuilder.conn.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static boolean hasConnection() {
        return ConnectionBuilder.conn != null;
    }
}
