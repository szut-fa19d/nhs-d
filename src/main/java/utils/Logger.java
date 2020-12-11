package utils;

import java.sql.SQLException;

import datastorage.DAOFactory;
import datastorage.LogEntryDAO;
import model.LogEntry;

public class Logger {
    private static Logger instance;
    private LogEntryDAO logEntryDAO;

    private Logger(LogEntryDAO logEntryDAO) {
        this.logEntryDAO = logEntryDAO;
    }

    public void log(String type, long obj_id, int user_id, String desc) {
        LogEntry newlog = new LogEntry(type, obj_id, user_id, desc);

        try {
            logEntryDAO.create(newlog);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Logger getInstance() {
        if (instance == null) {
            LogEntryDAO dao = DAOFactory.getInstance().createLogEntryDAO();
            instance = new Logger(dao);
        }
        return instance;
    }
}
