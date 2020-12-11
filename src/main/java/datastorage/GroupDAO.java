package datastorage;

import model.Group;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO extends DAOimp<Group>{

    public GroupDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected String getCreateStatement(Group group) {
        return String.format("INSERT INTO group (groupName) VALUES ('%s')",
                group.getGroupName());
    }

    @Override
    protected String getReadByIDStatement(int id) {
        return "SELECT * FROM group WHERE gid = " + id;
    }

    @Override
    protected Group getInstanceFromResultSet(ResultSet result) throws SQLException {
        return new Group(result.getInt(1), result.getString(2));
    }

    @Override
    protected String getReadAllStatement() {
        return "SELECT * FROM group";
    }

    @Override
    protected ArrayList<Group> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Group> list = new ArrayList<>();

        while (result.next()) {
            list.add(getInstanceFromResultSet(result));
        }

        return list;
    }

    @Override
    protected String getUpdateStatement(Group u) {
        return String.format("UPDATE group SET groupName = '%s' WHERE gid = %d", u.getGroupName(), u.getId());
    }

    @Override
    protected String getDeleteStatement(int id) {
        return "DELETE FROM group WHERE gid = " + id;
    }

    public Group getInstanceById(long id) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM group WHERE gid = ?");
            ps.setLong(1, id);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                return getInstanceFromResultSet(result);
            }
            return null;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
