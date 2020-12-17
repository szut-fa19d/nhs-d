package datastorage;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
    /** Create database row from instance */
    void create(T t) throws SQLException;

    /** Read and return single database row by id as instance */
    T read(int id) throws SQLException;

    /** Read all table rows and return as instances */
    List<T> readAll() throws SQLException;

    /** Update database row from instance state */
    void update(T t) throws SQLException;

    /** Delete database row by id */
    void deleteById(int id) throws SQLException;
}
