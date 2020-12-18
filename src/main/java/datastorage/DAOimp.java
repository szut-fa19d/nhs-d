package datastorage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.DatabaseEntry;

public abstract class DAOimp<T extends DatabaseEntry> implements DAO<T>{
    protected Connection conn;

    protected DAOimp(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void create(T t) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(getCreateStatement(t), Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            this.updateInstanceByResultSet(t, generatedKeys);
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
            String sqlCode = this.getReadAllStatement();
            result = statement.executeQuery(sqlCode);
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

    /** Generate SQL code as string to create item of type {@link T} */
    protected abstract String getCreateStatement(T t);

    /** Generate SQL code as string to read item of type {@link T} */
    protected abstract String getReadByIDStatement(int id);

    /** Create and return single instance from a resultset */
    protected abstract T getInstanceFromResultSet(ResultSet set) throws SQLException;

    /** Generate SQL code as string to read all items of type {@link T} */
    protected abstract String getReadAllStatement();

    /** Create and return instances from a resultset */
    protected abstract ArrayList<T> getListFromResultSet(ResultSet set) throws SQLException;

    /** Generate SQL code as string to update item of type {@link T} */
    protected abstract String getUpdateStatement(T t);

    /** Generate SQL code as string to delete item of type {@link T} */
    protected abstract String getDeleteStatement(int key);

    protected abstract void updateInstanceByResultSet(T t, ResultSet set) throws SQLException;
}
