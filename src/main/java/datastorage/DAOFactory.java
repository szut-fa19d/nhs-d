package datastorage;

import model.*;

public class DAOFactory {

    private static DAOFactory instance;

    private DAOFactory() {}

    /**
     * Get the singleton instance
     */
    public static DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }

    /**
     * Create and return a database access object for {@link Treatment}
     */
    public TreatmentDAO createTreatmentDAO() {
        return new TreatmentDAO(ConnectionBuilder.getConnection());
    }

    /**
     * Create and return a database access object for {@link Patient}
     */
    public PatientDAO createPatientDAO() {
        return new PatientDAO(ConnectionBuilder.getConnection());
    }

    public UserDAO createUserDAO() {
        return new UserDAO(ConnectionBuilder.getConnection());
    }
}
