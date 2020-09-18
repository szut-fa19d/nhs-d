package datastorage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DAOimp<T> implements DAO<T>{
    protected Connection conn;

    public DAOimp(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void create(T t) throws SQLException {
        conn.createStatement().executeUpdate(getCreateStatement(t));
    }

    @Override
    public T read(int id) throws SQLException {
        T object = null;
        ResultSet result = conn.createStatement().executeQuery(getReadByIDStatement(id));

        if (result.next()) {
            object = getInstanceFromResultSet(result);
        }

        return object;
    }

    @Override
    public List<T> readAll() throws SQLException {
        ResultSet result = conn.createStatement().executeQuery(getReadAllStatement());
        return getListFromResultSet(result);
    }

    @Override
    public void update(T t) throws SQLException {
        conn.createStatement().executeUpdate(getUpdateStatement(t));
    }

    @Override
    public void deleteById(int id) throws SQLException {
        conn.createStatement().executeUpdate(getDeleteStatement(id));
    }

    protected abstract String getCreateStatement(T t);

    protected abstract String getReadByIDStatement(int id);

    protected abstract T getInstanceFromResultSet(ResultSet set) throws SQLException;

    protected abstract String getReadAllStatement();

    protected abstract ArrayList<T> getListFromResultSet(ResultSet set) throws SQLException;

    protected abstract String getUpdateStatement(T t);

    protected abstract String getDeleteStatement(int key);
}
