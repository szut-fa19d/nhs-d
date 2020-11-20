package datastorage;

import model.Group;
import model.Patient;
import model.User;
import utils.DateConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class UserDAO extends DAOimp<User> {

    public UserDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected String getCreateStatement(User user) {
        return String.format("INSERT INTO user (username, password, group) VALUES ('%s', '%s', '%d')",
               user.getUsername(), user.getPassword(), user.getGroup()
        );    }

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
        return String.format("UPDATE user SET username = '%s', password = '%s',group = '%d' WHERE uid = %d", u.getUsername(), u.getPassword(), u.getGroup(), u.getId());
    }

    @Override
    protected String getDeleteStatement(int id) {
        return "DELETE FROM user WHERE uid = " + id;
    }

    public User getUserByUsername(String username) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE username = ?");
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
}
