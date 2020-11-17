package datastorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Caregiver;
import java.sql.Connection;

public class CaregiverDAO extends DAOimp<Caregiver> {
  public CaregiverDAO(Connection conn) {
    super(conn);
  }

  @Override
  protected String getCreateStatement(Caregiver caregiver) {
    return String.format(
      "INSERT INTO caregiver (firstname, lastname, phoneNumber) VALUES ('%s', '%s', '%s')",
      caregiver.getFirstName(),
      caregiver.getLastName(),
      caregiver.getPhoneNumber()
    );
  }

  @Override
  protected String getReadByIDStatement(int id) {
    return "SELECT * FROM caregiver WHERE cid = " + id;
  }

  @Override
  protected Caregiver getInstanceFromResultSet(ResultSet result) throws SQLException {
    return new Caregiver(
      result.getInt(1),
      result.getString(2),
      result.getString(3),
      result.getString(4)
    );
  }

  @Override
  protected String getReadAllStatement() {
    return "SELECT * FROM caregiver";
  }

  @Override
  protected ArrayList<Caregiver> getListFromResultSet(ResultSet result) throws SQLException {
    ArrayList<Caregiver> list = new ArrayList<>();

    while (result.next()) {
      list.add(getInstanceFromResultSet(result));
    }

    return list;
  }

  @Override
  protected String getUpdateStatement(Caregiver c) {
    return String.format(
      "UPDATE caregiver SET " +
        "firstname = '%s', " +
        "lastname = '%s', " +
        "phoneNumber = '%s' " +
      "WHERE cid = %d",
      c.getFirstName(),
      c.getLastName(),
      c.getPhoneNumber(),
      c.getId()
    );
  }

  @Override
  protected String getDeleteStatement(int id) {
    return "DELETE FROM caregiver WHERE cid = " + id;
  }
}
