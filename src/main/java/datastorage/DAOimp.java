package datastorage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class DAOimp<T> implements DAO<T>{
    protected Connection conn;

    protected DAOimp(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void create(T t) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(getCreateStatement(t));
        } catch (SQLException e) {
            throw new RuntimeException("Error executing sql:\n", e);
        }
    }

    @Override
    public T read(int id) throws SQLException {
        T object = null;

        ResultSet result;

        try (Statement statement = conn.createStatement()) {
            result = statement.executeQuery(getReadByIDStatement(id));
        } catch (SQLException e) {
            throw new RuntimeException("Error executing sql:\n", e);
        }

        if (result.next()) {
            object = getInstanceFromResultSet(result);
        }

        return object;
    }

    @Override
    public List<T> readAll() throws SQLException {
        ResultSet result = null;

        try (Statement statement = this.conn.createStatement()) {
            result = statement.executeQuery(getReadAllStatement());
        }

        return this.getListFromResultSet(result);
    }

    @Override
    public void update(T t) throws SQLException {
        try (Statement statement = this.conn.createStatement()) {
            statement.executeUpdate(this.getUpdateStatement(t));
        }
    }

    @Override
    public void deleteById(int id) throws SQLException {
        try (Statement statement = this.conn.createStatement()) {
            statement.executeUpdate(getDeleteStatement(id));
        }
    }

    protected abstract String getCreateStatement(T t);

    protected abstract String getReadByIDStatement(int id);

    protected abstract T getInstanceFromResultSet(ResultSet set) throws SQLException;

    protected abstract String getReadAllStatement();

    protected abstract ArrayList<T> getListFromResultSet(ResultSet set) throws SQLException;

    protected abstract String getUpdateStatement(T t);

    protected abstract String getDeleteStatement(int key);
}
