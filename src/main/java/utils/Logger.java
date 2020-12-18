package utils;

import java.sql.SQLException;
import java.util.ArrayList;

import controller.UserController;
import datastorage.DAOFactory;
import datastorage.LogEntryDAO;
import model.LogEntry;

public class Logger {
    private static Logger instance;
    private LogEntryDAO logEntryDAO;
    private ArrayList<NewLogEntryListener> listeners;

    private Logger(LogEntryDAO logEntryDAO) {
        this.logEntryDAO = logEntryDAO;
        this.listeners = new ArrayList<>();
    }

    public void log(LogType type, long objId, String desc) {
        long userId = UserController.getInstance().getUser().getId();
        LogEntry newlog = new LogEntry(type.getType(), objId, userId, desc);

        try {
            logEntryDAO.create(newlog);

            for (NewLogEntryListener listener : this.listeners) {
                listener.newLogEntry(newlog);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void addNewLogEntryListener(NewLogEntryListener toAdd) {
        listeners.add(toAdd);
    }

    public static Logger getInstance() {
        if (instance == null) {
            LogEntryDAO dao = DAOFactory.getInstance().createLogEntryDAO();
            instance = new Logger(dao);
        }
        return instance;
    }
}
