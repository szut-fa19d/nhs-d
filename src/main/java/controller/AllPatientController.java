package controller;

import datastorage.PatientDAO;
import datastorage.TreatmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Patient;
import datastorage.DAOFactory;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


/**
 * The <code>AllPatientController</code> contains the entire logic of the patient view. It determines which data is displayed and how to react to events.
 */
public class AllPatientController {
    @FXML
    private TableView<Patient> tableView;
    @FXML
    private TableColumn<Patient, Integer> colID;
    @FXML
    private TableColumn<Patient, String> colFirstName;
    @FXML
    private TableColumn<Patient, String> colLastName;
    @FXML
    private TableColumn<Patient, String> colDateOfBirth;
    @FXML
    private TableColumn<Patient, String> colCareLevel;
    @FXML
    private TableColumn<Patient, String> colRoom;
    @FXML
    private TableColumn<Patient, String> colAssets;

    @FXML
    Button btnDelete;
    @FXML
    Button btnAdd;
    @FXML
    TextField txtFirstName;
    @FXML
    TextField txtLastName;
    @FXML
    DatePicker birthday;
    @FXML
    TextField txtCarelevel;
    @FXML
    TextField txtRoom;
    @FXML
    private TextField txtAssets;

    private ObservableList<Patient> tableviewContent = FXCollections.observableArrayList();
    private PatientDAO dao;

    /**
     * Initializes the corresponding fields. Is called as soon as the corresponding FXML file is to be displayed.
     */
    public void initialize() {
        readAllAndShowInTableView();

        this.colID.setCellValueFactory(new PropertyValueFactory<>("id"));

        //CellValuefactory zum Anzeigen der Daten in der TableView
        this.colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        //CellFactory zum Schreiben innerhalb der Tabelle
        this.colFirstName.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.colLastName.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        this.colDateOfBirth.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colCareLevel.setCellValueFactory(new PropertyValueFactory<>("careLevel"));
        this.colCareLevel.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colRoom.setCellValueFactory(new PropertyValueFactory<>("roomnumber"));
        this.colRoom.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colAssets.setCellValueFactory(new PropertyValueFactory<>("assets"));
        this.colAssets.setCellFactory(TextFieldTableCell.forTableColumn());

        //Anzeigen der Daten
        this.tableView.setItems(this.tableviewContent);
    }

    /**
     * handles new firstname value
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditFirstName(TableColumn.CellEditEvent<Patient, String> event){
        event.getRowValue().setFirstName(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new lastName value
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditLastName(TableColumn.CellEditEvent<Patient, String> event){
        event.getRowValue().setLastName(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new birthdate value
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditDateOfBirth(TableColumn.CellEditEvent<Patient, String> event){
        event.getRowValue().setDateOfBirth(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new carelevel value
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditCareLevel(TableColumn.CellEditEvent<Patient, String> event){
        event.getRowValue().setCareLevel(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new roomnumber value
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditRoomnumber(TableColumn.CellEditEvent<Patient, String> event){
        event.getRowValue().setRoomnumber(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new asset value
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditAssets(TableColumn.CellEditEvent<Patient, String> event){
        event.getRowValue().setAssets(event.getNewValue());
        doUpdate(event);
    }

    /**
     * updates a patient by calling the update-Method in the {@link PatientDAO}
     * @param t row to be updated by the user (includes the patient)
     */
    private void doUpdate(TableColumn.CellEditEvent<Patient, String> t) {
        try {
            dao.update(t.getRowValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * calls readAll in {@link PatientDAO} and shows patients in the table
     */
    private void readAllAndShowInTableView() {
        this.tableviewContent.clear();
        this.dao = DAOFactory.getInstance().createPatientDAO();
        List<Patient> allPatients;
        try {
            allPatients = dao.readAll();
            for (Patient p : allPatients) {
                this.tableviewContent.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * handles a delete-click-event. Calls the delete methods in the {@link PatientDAO} and {@link TreatmentDAO}
     */
    @FXML
    public void handleDeleteRow() {
        TreatmentDAO tDao = DAOFactory.getInstance().createTreatmentDAO();
        Patient selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        this.tableView.getItems().remove(selectedItem);
        try {
            dao.deleteById((int) selectedItem.getId());
            tDao.deleteByPatientId((int) selectedItem.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * handles a add-click-event. Creates a patient and calls the create method in the {@link PatientDAO}
     */
    @FXML
    public void handleAdd() {
        String lastName = this.txtLastName.getText();
        String firstName = this.txtFirstName.getText();
        LocalDate patientBirthday = this.birthday.getValue();
        String carelevel = this.txtCarelevel.getText();
        String room = this.txtRoom.getText();
        String assets = this.txtAssets.getText();

        try {
            Patient p = new Patient(firstName, lastName, patientBirthday, carelevel, room, assets);
            dao.create(p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        readAllAndShowInTableView();
        clearTextfields();
    }

    /**
     * removes content from all textfields
     */
    private void clearTextfields() {
        this.txtFirstName.clear();
        this.txtLastName.clear();
        this.birthday.setValue(null);
        this.txtCarelevel.clear();
        this.txtRoom.clear();
        this.txtAssets.clear();
    }
}