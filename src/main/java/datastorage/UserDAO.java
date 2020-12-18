package datastorage;

import model.Group;
import model.User;
import utils.DatabaseUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO extends DAOimp<User> {

    public UserDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected String getCreateStatement(User user) {
        return String.format("INSERT INTO user (gid, username, password) VALUES ('%d', '%s', '%s')",
                user.getGroup(), user.getUsername(), user.getPassword());
    }

    @Override
    protected String getReadByIDStatement(int id) {
        return "SELECT * FROM user WHERE uid = " + id;
    }

    @Override
    protected User getInstanceFromResultSet(ResultSet result) throws SQLException {
        GroupDAO groupDAO = new GroupDAO(conn);
        Group group = groupDAO.getInstanceById(result.getInt(2));
        return new User(result.getInt(1), result.getString(3), result.getString(4), group);
    }

    @Override
    protected String getReadAllStatement() {
        return "SELECT * FROM user";
    }

    @Override
    protected ArrayList<User> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<User> list = new ArrayList<>();

        while (result.next()) {
            list.add(getInstanceFromResultSet(result));
        }

        return list;
    }

    @Override
    protected String getUpdateStatement(User u) {
        return String.format("UPDATE user SET username = '%s', password = '%s',gid = '%d' WHERE uid = %d", u.getUsername(), u.getPassword(), u.getGroup(), u.getId());
    }

    @Override
    protected String getDeleteStatement(int id) {
        return "DELETE FROM user WHERE uid = " + id;
    }

    public User getUserByUsername(String username) {
        try (PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM user WHERE username = ?")) {
            ps.setString(1, username);
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

    @Override
    protected void updateInstanceByResultSet(User u, ResultSet set) throws SQLException {
        if (DatabaseUtils.hasColumn(set, "uid")) {
            u.setId(set.getLong("uid"));
        }

        if (DatabaseUtils.hasColumn(set, "username")) {
            u.setUsername(set.getString("username"));
        }

        if (DatabaseUtils.hasColumn(set, "password")) {
            u.setPassword(set.getString("password"));
        }

        if (DatabaseUtils.hasColumn(set, "gid")) {
            GroupDAO groupDAO = new GroupDAO(conn);
            Group group = groupDAO.getInstanceById(set.getInt("gid"));
            u.setGroup(group);
        }
    }
}
