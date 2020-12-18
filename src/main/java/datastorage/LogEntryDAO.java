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
        return String.format("INSERT INTO log (type, objid, userid, desc) VALUES ('%s', '%s', '%s', '%s')",
            log.getType(), log.getObjId(), log.getUserId(), log.getDesc());
    }

    @Override
    protected String getReadByIDStatement(int id) {
        return "SELECT * FROM log WHERE id = " + id;
    }

    @Override
    protected LogEntry getInstanceFromResultSet(ResultSet result) throws SQLException {
        return new LogEntry(
            result.getInt("id"),
            result.getString("timestamp"),
            result.getString("type"),
            result.getInt("objid"),
            result.getInt("userid"),
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
        return String.format("UPDATE log SET timestamp = '%s', type = '%s', objid = '%s', userid = '%s', desc = '%s' WHERE id = %d",
            log.getTimestamp(), log.getType(), log.getObjId(), log.getUserId(), log.getDesc(), log.getId());
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
            log.setTimestamp(set.getString("timestamp"));
        }

        if (DatabaseUtils.hasColumn(set, "type")) {
            log.setType(set.getString("type"));
        }

        if (DatabaseUtils.hasColumn(set, "objid")) {
            log.setObjId(set.getInt("objid"));
        }

        if (DatabaseUtils.hasColumn(set, "userid")) {
            log.setUserId(set.getInt("userid"));
        }

        if (DatabaseUtils.hasColumn(set, "desc")) {
            log.setDesc(set.getString("desc"));
        }
    }
}
