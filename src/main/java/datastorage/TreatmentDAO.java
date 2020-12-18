package datastorage;

import model.*;
import utils.DateConverter;
import utils.Memoize;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class TreatmentDAO extends DAOimp<Treatment> {
    private Memoize<Patient> patientsMemo;

    /**
     * Store the connection and setup the memoization for thee patients
     */
    public TreatmentDAO(Connection conn) {
        super(conn);
        this.patientsMemo = new Memoize<>(
            () -> DAOFactory.getInstance().createPatientDAO().readAll()
        );
    }

    @Override
    protected String getCreateStatement(Treatment treatment) {
        return "INSERT INTO treatment (pid, treatment_date, begin, end, description, remarks, locked)" +
            "VALUES (" + treatment.getPatient().getId() + ", '" +
            String.join(
                "', '",
                treatment.getDate(),
                treatment.getBegin(),
                treatment.getEnd(),
                treatment.getDescription(),
                treatment.getRemarks()
            ) +
        treatment.getLocked()
                +
            "')"; // TODO Das geht safe schöner
    }

    @Override
    protected String getReadByIDStatement(int id) {
        return "SELECT * FROM treatment WHERE tid = " + id;
    }

    @Override
    protected Treatment getInstanceFromResultSet(ResultSet result) throws SQLException {
        Optional<Patient> optionalPatient = this.patientsMemo.get().stream()
            .filter(patient -> {
                try {
                    return patient.getId() == result.getLong(2);
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

        return new Treatment(result.getLong(1), optionalPatient.get(), date, begin, end, result.getString(6), result.getString(7), result.getBoolean(8));
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
    protected String getUpdateStatement(Treatment treatment) {
        return String.format("UPDATE treatment SET pid = %d, treatment_date ='%s', begin = '%s', end = '%s',description = '%s', remarks = '%s', locked = '%b' WHERE tid = %d",
            treatment.getPatient().getId(), treatment.getDate(), treatment.getBegin(), treatment.getEnd(), treatment.getDescription(), treatment.getRemarks(),treatment.getLocked(), treatment.getId());
    }

    @Override
    protected String getDeleteStatement(int id) {
        return "DELETE FROM treatment WHERE tid = " + id;
    }

    /**
     * Read and return all treatments that belong to a certain patient
     */
    public List<Treatment> readTreatmentsByPatientId(long patientId) throws SQLException {
        ResultSet result;

        try (Statement statement = this.conn.createStatement()) {
            result = statement.executeQuery("SELECT * FROM treatment WHERE pid = " + patientId);
        }

        return getListFromResultSet(result);
    }

    /**
     * Delete all Treatments that belong to the Patient with this id
     */
    public void deleteByPatientId(int patientId) throws SQLException {
        try (Statement statement = this.conn.createStatement()) {
            statement.executeQuery("DELETE FROM treatment WHERE pid = " + patientId);
        }
    }
}