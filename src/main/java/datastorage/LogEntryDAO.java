package datastorage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.LogEntry;
import utils.DatabaseUtils;

public class LogEntryDAO extends DAOimp<LogEntry> {

    public LogEntryDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected String getCreateStatement(LogEntry log) {
        return String.format("INSERT INTO log (type, obj_id, user_id, desc) VALUES ('%s', '%s', '%s', '%s')",
            log.getType(), log.getObj_id(), log.getUser_id(), log.getDesc());
    }

    @Override
    protected String getReadByIDStatement(int id) {
        return "SELECT * FROM log WHERE id = " + id;
    }

    @Override
    protected LogEntry getInstanceFromResultSet(ResultSet result) throws SQLException {
        return new LogEntry(
            result.getInt("id"),
            result.getLong("timestamp"),
            result.getString("type"),
            result.getInt("obj_id"),
            result.getInt("user_id"),
            result.getString("desc")
        );
    }

    @Override
    protected String getReadAllStatement() {
        return "SELECT * FROM log";
    }

    @Override
    protected ArrayList<LogEntry> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<LogEntry> list = new ArrayList<>();

        while (result.next()) {
            list.add(getInstanceFromResultSet(result));
        }

        return list;
    }

    @Override
    protected String getUpdateStatement(LogEntry log) {
        return String.format("UPDATE log SET timestamp = '%s', type = '%s', obj_id = '%s', user_id = '%s', desc = '%s' WHERE id = %d",
            log.getTimestamp(), log.getType(), log.getObj_id(), log.getUser_id(), log.getDesc(), log.getId());
    }

    @Override
    protected String getDeleteStatement(int id) {
        return "DELETE FROM log WHERE id = " + id;
    }

    @Override
    protected void updateInstanceByResultSet(LogEntry log, ResultSet set) throws SQLException {
        if(DatabaseUtils.hasColumn(set, "id")) {
            log.setId(set.getInt("id"));
        }

        if(DatabaseUtils.hasColumn(set, "timestamp")) {
            log.setTimestamp(set.getInt("timestamp"));
        }

        if (DatabaseUtils.hasColumn(set, "type")) {
            log.setType(set.getString("type"));
        }

        if (DatabaseUtils.hasColumn(set, "obj_id")) {
            log.setObj_id(set.getInt("obj_id"));
        }

        if (DatabaseUtils.hasColumn(set, "user_id")) {
            log.setUser_id(set.getInt("user_id"));
        }

        if (DatabaseUtils.hasColumn(set, "desc")) {
            log.setDesc(set.getString("desc"));
        }
    }
}
