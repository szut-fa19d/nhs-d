package datastorage;

import model.*;
import utils.DatabaseUtils;
import utils.DateConverter;
import utils.Memoize;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class TreatmentDAO extends DAOimp<Treatment> {
    private Memoize<Patient> patientsMemo;
    private Memoize<Caregiver> caregiverMemo;

    /**
     * Store the connection and setup the memoization for the patients
     */
    public TreatmentDAO(Connection conn) {
        super(conn);
        this.patientsMemo = new Memoize<>(
            () -> DAOFactory.getInstance().createPatientDAO().readAll()
        );
        this.caregiverMemo = new Memoize<>(
            () -> DAOFactory.getInstance().createCaregiverDAO().readAll()
        );
    }

    @Override
    protected String getCreateStatement(Treatment treatment) {
        String stringValues = String.join(
            "', '",
            treatment.getDate(),
            treatment.getBegin(),
            treatment.getEnd(),
            treatment.getDescription(),
            treatment.getRemarks()
        );

        return "INSERT INTO treatment (pid, treatment_date, begin, end, description, remarks)" +
            "VALUES ("
           + treatment.getPatient().getId()
           + ", '" + stringValues + "', "
           + treatment.getLocked()
          + ")";
    }

    @Override
    protected String getReadByIDStatement(int id) {
        return "SELECT t.*, tc.cid FROM treatment t" +
            " LEFT JOIN treatment_caregiver tc ON tc.tid = treatment.tid" +
            " WHERE t.tid = " + id;
    }

    @Override
    protected Treatment getInstanceFromResultSet(ResultSet result) throws SQLException {
        Optional<Patient> optionalPatient = this.patientsMemo.get().stream()
            .filter(patient -> {
                try {
                    return patient.getId() == (int) result.getLong(2);
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

        Treatment treatment = new Treatment(result.getLong(1), optionalPatient.get(), date, begin, end, result.getString(6), result.getString(7), result.getBoolean(8));
        this.hydrate(treatment, result);

        return treatment;
    }
    
    /**
     * Read and add caregivers to treatment
     */
    private void hydrate(Treatment treatment, ResultSet result) throws SQLException {
        List<Long> caregiverIds = new ArrayList<>();
    
        do {
            long caregiverIdField = result.getLong(9);

            if (caregiverIdField != 0) {
                caregiverIds.add(caregiverIdField);
            }
        } while (result.next() && treatment.getId() == result.getLong(1));
        // as long as the row is still for the same treatment

        for (long caregiverId: caregiverIds) {
            Caregiver caregiver = this.caregiverMemo.get().stream()
                .filter(c -> c.getId() == caregiverId)
                .findAny().orElse(null);

            treatment.addCaregiver(caregiver);
        }
    }

    @Override
    protected String getReadAllStatement() {
        return "SELECT t.*, tc.cid FROM treatment t" +
            " LEFT JOIN treatment_caregiver tc on tc.tid = t.tid";
    }

    @Override
    protected ArrayList<Treatment> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Treatment> treatments = new ArrayList<>();

        boolean noResults = !result.next();

        if (noResults) {
            return treatments;
        }
        
        while (true) {
            Treatment treatment = this.getInstanceFromResultSet(result);
            treatments.add(treatment);

            boolean currentRowIsValid = result.getRow() != 0;

            if (!currentRowIsValid) {
                break;
            }
        }

        return treatments;
    }

    @Override
    protected String getUpdateStatement(Treatment treatment) {
        return String.format(
            "UPDATE treatment SET pid = %d,"
         + "   treatment_date ='%s',"
         + "   begin = '%s',"
         + "   end = '%s',"
         + "   description = '%s',"
         + "   remarks = '%s'"
         + "   locked = '%b'"
         + " WHERE tid = %d",
            treatment.getPatient().getId(),
            treatment.getDate(),
            treatment.getBegin(),
            treatment.getEnd(),
            treatment.getDescription(),
            treatment.getRemarks(),
            treatment.getLocked(),
            treatment.getId()
        );
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
            result = statement.executeQuery(
                "SELECT t.*, tc.cid FROM treatment t"
             + " JOIN treatment_caregiver tc on tc.tid = t.tid"
             + " WHERE t.pid = " + patientId
            );
        }

        return getListFromResultSet(result);
    }

    @Override
    public void deleteById(int id) throws SQLException {
        try (Statement statement = this.conn.createStatement()) {
            statement.executeUpdate(
                "DELETE FROM treatment_caregiver tc"
             + " WHERE tc.tid = " + id
            );

            statement.executeUpdate(getDeleteStatement(id));
        }
    }

    /**
     * Delete all Treatments that belong to the Patient with this id
     */
    public void deleteByPatientId(int patientId) throws SQLException {
        try (Statement statement = this.conn.createStatement()) {
            // Delete treatment-caregiver links
            statement.executeUpdate(
                "DELETE FROM treatment_caregiver tc"
             + " JOIN treatment t ON t.id = tc.tid"
             + " WHERE t.pid = " + patientId
            );

            // Delete actual treatments
            statement.executeUpdate("DELETE FROM treatment WHERE pid = " + patientId);
        }
    }

    @Override
    protected void updateInstanceByResultSet(Treatment treatment, ResultSet set) throws SQLException {
        if (DatabaseUtils.hasColumn(set, "tid")) {
            treatment.setId(set.getLong("tid"));
        }

        if (DatabaseUtils.hasColumn(set, "treatment_date")) {
            treatment.setDate(set.getString("treatment_date"));
        }

        if (DatabaseUtils.hasColumn(set, "begin")) {
            treatment.setBegin(set.getString("begin"));
        }

        if (DatabaseUtils.hasColumn(set, "end")) {
            treatment.setEnd(set.getString("end"));
        }

        if (DatabaseUtils.hasColumn(set, "description")) {
            treatment.setDescription(set.getString("description"));
        }

        if (DatabaseUtils.hasColumn(set, "remarks")) {
            treatment.setRemarks(set.getString("remarks"));
        }
    }
}