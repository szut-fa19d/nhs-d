package datastorage;

import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO extends DAOimp<User> {

    public UserDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected String getCreateStatement(User user) {
        return String.format("INSERT INTO user (firstname, surname, dateOfBirth, carelevel, roomnumber, assets) VALUES ('%s', '%s', '%s')",
               user.getUsername(), user.getPassword(), user.getGroup(),
        );    }

    @Override
    protected String getReadByIDStatement(int id) {
        return null;
    }

    @Override
    protected User getInstanceFromResultSet(ResultSet set) throws SQLException {
        return null;
    }

    @Override
    protected String getReadAllStatement() {
        return null;
    }

    @Override
    protected ArrayList<User> getListFromResultSet(ResultSet set) throws SQLException {
        return null;
    }

    @Override
    protected String getUpdateStatement(User user) {
        return null;
    }

    @Override
    protected String getDeleteStatement(int key) {
        return null;
    }
}
