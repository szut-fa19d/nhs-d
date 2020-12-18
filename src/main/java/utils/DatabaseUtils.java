package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUtils {
    public static boolean hasColumn(ResultSet set, String columnName) {
        try {
            set.findColumn(columnName);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
