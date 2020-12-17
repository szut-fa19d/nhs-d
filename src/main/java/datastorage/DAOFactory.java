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

//   public T createDAO(Class class) {
//     return new class(ConnectionBuilder.getConnection());
//   }

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

  /**
   * Create and return a database access object for {@link Patient}
   */
  public UserDAO createUserDAO() {
      return new UserDAO(ConnectionBuilder.getConnection());
  }

  /**
   * Create and return a database access object for {@link Patient}
   */
  public GroupDAO createGroupDAO() {
      return new GroupDAO(ConnectionBuilder.getConnection());
  }

  public CaregiverDAO createCaregiverDAO() {
      return new CaregiverDAO(ConnectionBuilder.getConnection());
  }
}
