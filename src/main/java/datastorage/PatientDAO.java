package datastorage;

import model.Patient;
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
        return String.format("INSERT INTO patient (firstname, surname, dateOfBirth, carelevel, roomnumber, assets, locked) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%b')",
                patient.getFirstName(), patient.getSurname(), patient.getDateOfBirth(), patient.getCareLevel(), patient.getRoomnumber(), patient.getAssets(), patient.getLocked());
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
                result.getString(6), result.getString(7), result.getBoolean(8));
    }

    @Override
    protected String getReadAllStatement() {
        return "SELECT * FROM patient";
    }

    @Override
    protected ArrayList<Patient> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Patient> list = new ArrayList<Patient>();

        while (result.next()) {
            list.add(getInstanceFromResultSet(result));
        }

        return list;
    }

    @Override
    protected String getUpdateStatement(Patient p) {
        return String.format("UPDATE patient SET firstname = '%s', surname = '%s', dateOfBirth = '%s', carelevel = '%s', " +
                "roomnumber = '%s', assets = '%s', locked = '%b' WHERE pid = %d", p.getFirstName(), p.getSurname(), p.getDateOfBirth(),
                p.getCareLevel(), p.getRoomnumber(), p.getAssets(),p.getLocked(), p.getId());
    }

    @Override
    protected String getDeleteStatement(int id) {
        return "DELETE FROM patient WHERE pid = " + id;
    }
}
