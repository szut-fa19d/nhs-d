package controller;

import datastorage.PatientDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Patient;
import datastorage.DAOFactory;
import model.Treatment;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * The <code>AllPatientController</code> contains the entire logic of the patient view. It determines which data is displayed and how to react to events.
 */
public class AllPatientController extends CommonListController<Patient, PatientDAO> {
    @FXML
    private TableColumn<Patient, String> colFirstName;
    @FXML
    private TableColumn<Patient, String> colSurname;
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
    TextField txtSurname;
    @FXML
    TextField txtFirstname;
    @FXML
    DatePicker birthday;
    @FXML
    TextField txtCarelevel;
    @FXML
    TextField txtRoom;
    @FXML
    private TextField txtAssets;

    protected void refreshDAO() {
        this.dao = DAOFactory.getInstance().createPatientDAO();
    }

    @Override
    public void initialize() {
        readAllAndShowInTableView();

        this.colID.setCellValueFactory(new PropertyValueFactory<>("id"));

        //CellValuefactory zum Anzeigen der Daten in der TableView
        this.colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        //CellFactory zum Schreiben innerhalb der Tabelle
        this.colFirstName.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colLocked.setCellValueFactory(new PropertyValueFactory<>("locked"));

        this.colSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        this.colSurname.setCellFactory(TextFieldTableCell.forTableColumn());

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
    public void handleOnEditFirstname(TableColumn.CellEditEvent<Patient, String> event){
        event.getRowValue().setFirstName(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new surname value
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditSurname(TableColumn.CellEditEvent<Patient, String> event){
        event.getRowValue().setSurname(event.getNewValue());
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
     * @param event row to be updated by the user (includes the patient)
     */
    private void doUpdate(TableColumn.CellEditEvent<Patient, String> event) {
        try {
            this.dao.update(event.getRowValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the selected patient and all related treatments
     */
    @Override
    public Patient handleDelete() {
        Patient deletedPatient = super.handleDelete();

        try {
            DAOFactory.getInstance().createTreatmentDAO().deleteByPatientId((int) deletedPatient.getId());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return deletedPatient; // Only required for the override, no actuall purpose
    }

    /**
     * handles a add-click-event. Creates a patient and calls the create method in the {@link PatientDAO}
     */
    @FXML
    public void handleAdd() {
        String surname = this.txtSurname.getText();
        String firstname = this.txtFirstname.getText();
        LocalDate birthdayValue = this.birthday.getValue();
        String carelevel = this.txtCarelevel.getText();
        String room = this.txtRoom.getText();
        String assets = this.txtAssets.getText();
        try {
            Patient p = new Patient(firstname, surname, birthdayValue, carelevel, room, assets);
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
        this.txtFirstname.clear();
        this.txtSurname.clear();
        this.birthday.setValue(null);
        this.txtCarelevel.clear();
        this.txtRoom.clear();
        this.txtAssets.clear();
    }
}