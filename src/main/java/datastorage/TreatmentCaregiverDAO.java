package datastorage;

import java.sql.SQLException;
import java.sql.Statement;
import model.Caregiver;
import model.Treatment;
import java.sql.Connection;

public class TreatmentCaregiverDAO {
  protected Connection connection;

  public TreatmentCaregiverDAO(Connection connection) {
    this.connection = connection;
  }

  public void link(Treatment treatment, Caregiver caregiver) throws SQLException {
    try (Statement statement = this.connection.createStatement()) {
      statement.executeUpdate(
        "INSERT INTO treatment_caregiver (tid, cid)" +
        " VALUES (" + treatment.getId() + ", " +
        caregiver.getId() + ")"
      );
    }
  }

  public void unlink(Treatment treatment, Caregiver caregiver) throws SQLException {
    try (Statement statement = this.connection.createStatement()) {
      statement.execute(
        "DELETE FROM treatment_caregiver " +
        " WHERE tid = " + treatment.getId() +
        " AND cid = " + caregiver.getId()
      );
    }
  }
}
