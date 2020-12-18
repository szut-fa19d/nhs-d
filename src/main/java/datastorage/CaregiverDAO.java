package datastorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Caregiver;
import utils.DatabaseUtils;

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

  public List<Caregiver> readByIds(List<Integer> caregiverIds) throws SQLException {
    ResultSet result;

    // "[1, 4, 7]", so it includes the required angled brackets!
    String idSet = caregiverIds.toString().replace("[", "(").replace("]", ")");

    try (Statement statement = this.conn.createStatement()) {
      result = statement.executeQuery(
        "SELECT * FROM caregiver WHERE cid in " + idSet
        // SELECT * FROM caregiver WHERE cid in [1, 2, 4]
      );
    }

    return this.getListFromResultSet(result);
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

  @Override
  protected void updateInstanceByResultSet(Caregiver treatment, ResultSet set) throws SQLException {
      if (DatabaseUtils.hasColumn(set, "tid")) {
          treatment.setId(set.getLong("tid"));
      }

      if (DatabaseUtils.hasColumn(set, "firstname")) {
          treatment.setFirstName(set.getString("firstname"));
      }

      if (DatabaseUtils.hasColumn(set, "lastname")) {
          treatment.setLastName(set.getString("lastname"));
      }

      if (DatabaseUtils.hasColumn(set, "phonenumber")) {
          treatment.setPhoneNumber(set.getString("phonenumber"));
      }
  }
}
