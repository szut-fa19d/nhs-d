package controller;

import java.sql.SQLException;
import java.util.List;

import datastorage.CaregiverDAO;
import datastorage.DAOFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Caregiver;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class AllCaregiverController {
  @FXML
  private TableView<Caregiver> tableView;
  @FXML
  private TableColumn<Caregiver, Integer> colID;
  @FXML
  private TableColumn<Caregiver, String> colFirstName;
  @FXML
  private TableColumn<Caregiver, String> colLastName;
  @FXML
  private TableColumn<Caregiver, String> colPhoneNumber; // ist string die richtige Wahl?

  @FXML
  Button btnDelete;
  @FXML
  Button btnAdd;
  @FXML
  TextField txtFirstName;
  @FXML
  TextField txtLastName;
  @FXML
  TextField txtPhoneNumber;

  private ObservableList<Caregiver> tableviewContent = FXCollections.observableArrayList();
  private CaregiverDAO dao;

  public void initialize() {
    this.readAllAndShowInTableView();

    this.colID.setCellValueFactory(
      new PropertyValueFactory<>("id")
    );

    this.colFirstName.setCellValueFactory(
      new PropertyValueFactory<>("firstName")
    );
    this.colFirstName.setCellFactory(TextFieldTableCell.forTableColumn());

    this.colLastName.setCellValueFactory(
      new PropertyValueFactory<>("lastName")
    );
    this.colLastName.setCellFactory(TextFieldTableCell.forTableColumn());

    this.colPhoneNumber.setCellValueFactory(
      new PropertyValueFactory<>("phoneNumber")
    );
    this.colPhoneNumber.setCellFactory(TextFieldTableCell.forTableColumn());

    this.tableView.setItems(this.tableviewContent);
  }

  @FXML
  public void handleOnEditFirstName(TableColumn.CellEditEvent<Caregiver, String> event) {
    event.getRowValue().setFirstName(event.getNewValue());
    this.doUpdate(event);
  }

  @FXML
  public void handleOnEditLastName(TableColumn.CellEditEvent<Caregiver, String> event) {
    event.getRowValue().setLastName(event.getNewValue());
    this.doUpdate(event);
  }

  @FXML
  public void handleOnEditPhoneNumber(TableColumn.CellEditEvent<Caregiver, String> event) {
    event.getRowValue().setPhoneNumber(event.getNewValue());
    this.doUpdate(event);
  }

  private void doUpdate(TableColumn.CellEditEvent<Caregiver, String> t) {
    try {
      this.dao.update(t.getRowValue());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void readAllAndShowInTableView() {
    this.tableviewContent.clear();
    this.dao = DAOFactory.getInstance().createCaregiverDAO();
    List<Caregiver> allCaregivers;

    try {
      allCaregivers = dao.readAll();

      this.tableviewContent.addAll(allCaregivers);

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void handleDeleteRow() {
    Caregiver selectedItem = this.tableView.getSelectionModel().getSelectedItem();
    this.tableView.getItems().remove(selectedItem);

    // vielleicht müssen hier ggf. bestimmte einträge bearbeitet oder gelöscht werden

    try {
      dao.deleteById((int) selectedItem.getId());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void handleAdd() {
    String lastName = this.txtLastName.getText();
    String firstName = this.txtFirstName.getText();
    String phoneNumber = this.txtPhoneNumber.getText();

    try {
      Caregiver c = new Caregiver(firstName, lastName, phoneNumber);
      dao.create(c);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    this.readAllAndShowInTableView();
    this.clearTextfields();
  }

  private void clearTextfields() {
    this.txtFirstName.clear();
    this.txtLastName.clear();
    this.txtPhoneNumber.clear();
  }
}
