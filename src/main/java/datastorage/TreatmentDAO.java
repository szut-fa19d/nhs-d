package datastorage;

import model.Patient;
import model.Treatment;
import utils.DateConverter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TreatmentDAO extends DAOimp<Treatment> {
    private List<Patient> patients;

    public TreatmentDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected String getCreateStatement(Treatment treatment) {
        return "INSERT INTO treatment (pid, treatment_date, begin, end, description, remarks)" +
            "VALUES (" + treatment.getId() + ", '" +
                String.join("', '", treatment.getDate(), treatment.getBegin(), treatment.getEnd(), treatment.getDescription(), treatment.getRemarks()) +
            "')";
    }

    @Override
    protected String getReadByIDStatement(int id) {
        return "SELECT * FROM treatment WHERE tid = " + id;
    }

    private List<Patient> getAllPatients() throws SQLException {
        if (this.patients == null) {
            this.patients = DAOFactory.getInstance().createPatientDAO().readAll();
        }

        return this.patients;
    }

    @Override
    protected Treatment getInstanceFromResultSet(ResultSet result) throws SQLException {
        Optional<Patient> optionalPatient = getAllPatients().stream()
            .filter(p -> {
                try {
                    return p.getId() == result.getLong(2);
                } catch (SQLException sqlException) {
                    return false;
                }
            }).findFirst();

        if (!optionalPatient.isPresent()) {
            throw new SQLException("Patient für die Behandlung wurde nicht gefunden!"); // TODO bessere Exception
        }

        LocalDate date = DateConverter.convertStringToLocalDate(result.getString(3));
        LocalTime begin = DateConverter.convertStringToLocalTime(result.getString(4));
        LocalTime end = DateConverter.convertStringToLocalTime(result.getString(5));

        return new Treatment(result.getLong(1), optionalPatient.get(), date, begin, end, result.getString(6), result.getString(7));
    }

    @Override
    protected String getReadAllStatement() {
        return "SELECT * FROM treatment";
    }

    @Override
    protected ArrayList<Treatment> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Treatment> list = new ArrayList<>();

        while (result.next()) {
            list.add(getInstanceFromResultSet(result));
        }

        return list;
    }

    @Override
    protected String getUpdateStatement(Treatment t) {
        return String.format("UPDATE treatment SET pid = %d, treatment_date ='%s', begin = '%s', end = '%s',description = '%s', remarks = '%s' WHERE tid = %d",
            t.getPatient().getId(), t.getDate(), t.getBegin(), t.getEnd(), t.getDescription(), t.getRemarks(), t.getId());
    }

    @Override
    protected String getDeleteStatement(int id) {
        return "DELETE FROM treatment WHERE tid = " + id;
    }

    public List<Treatment> readTreatmentsByPatientId(long patientId) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM treatment WHERE pid = " + patientId);
        return getListFromResultSet(result);
    }

    public void deleteByPatientId(int patientId) throws SQLException {
        conn.createStatement().executeQuery("DELETE FROM treatment WHERE pid = " + patientId);
    }
}