package datastorage;

import model.Patient;
import utils.DatabaseUtils;
import utils.DateConverter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Overrides methods to generate specific patient-SQL-queries.
 */
public class PatientDAO extends DAOimp<Patient> {

    public PatientDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected String getCreateStatement(Patient patient) {
        return String.format("INSERT INTO patient (firstname, surname, dateOfBirth, carelevel, roomnumber, assets) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
                patient.getFirstName(), patient.getSurname(), patient.getDateOfBirth(), patient.getCareLevel(), patient.getRoomnumber(), patient.getAssets());
    }

    @Override
    protected String getReadByIDStatement(int id) {
        return "SELECT * FROM patient WHERE pid = " + id;
    }

    @Override
    protected Patient getInstanceFromResultSet(ResultSet result) throws SQLException {
        LocalDate date = DateConverter.convertStringToLocalDate(result.getString(4));
        return new Patient(result.getInt(1), result.getString(2),
                result.getString(3), date, result.getString(5),
                result.getString(6), result.getString(7));
    }

    @Override
    protected String getReadAllStatement() {
        return "SELECT * FROM patient";
    }

    @Override
    protected ArrayList<Patient> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Patient> list = new ArrayList<>();

        while (result.next()) {
            list.add(getInstanceFromResultSet(result));
        }

        return list;
    }

    @Override
    protected String getUpdateStatement(Patient p) {
        return String.format("UPDATE patient SET firstname = '%s', surname = '%s', dateOfBirth = '%s', carelevel = '%s', " +
                "roomnumber = '%s', assets = '%s' WHERE pid = %d", p.getFirstName(), p.getSurname(), p.getDateOfBirth(),
                p.getCareLevel(), p.getRoomnumber(), p.getAssets(), p.getId());
    }

    @Override
    protected String getDeleteStatement(int id) {
        return "DELETE FROM patient WHERE pid = " + id;
    }

    @Override
    protected void updateInstanceByResultSet(Patient p, ResultSet set) throws SQLException {
        if (DatabaseUtils.hasColumn(set, "pid")) {
            p.setId(set.getLong("pid"));
        }

        if (DatabaseUtils.hasColumn(set, "firstname")) {
            p.setFirstName(set.getString("firstname"));
        }

        if (DatabaseUtils.hasColumn(set, "surname")) {
            p.setSurname(set.getString("surname"));
        }

        if (DatabaseUtils.hasColumn(set, "dateOfBirth")) {
            p.setDateOfBirth(set.getString("dateOfBirth"));
        }

        if (DatabaseUtils.hasColumn(set, "carelevel")) {
            p.setCareLevel(set.getString("carelevel"));
        }

        if (DatabaseUtils.hasColumn(set, "roomnumber")) {
            p.setRoomnumber(set.getString("roomnumber"));
        }

        if (DatabaseUtils.hasColumn(set, "assets")) {
            p.setAssets(set.getString("assets"));
        }
    }
}
