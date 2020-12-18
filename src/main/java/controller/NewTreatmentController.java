package controller;

import datastorage.CaregiverDAO;
import datastorage.TreatmentCaregiverDAO;
import datastorage.DAOFactory;
import datastorage.TreatmentDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Caregiver;
import model.Patient;
import model.Treatment;
import utils.DateConverter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import org.controlsfx.control.CheckComboBox;

public class NewTreatmentController extends TreatmentControllerCommon {
    @FXML
    private Label lblLastName;
    @FXML
    private Label lblFirstName;
    @FXML
    private CheckComboBox<Caregiver> caregiverCombo;

    /**
     * @see TreatmentControllerCommon#initialize
     */
    public void initializeController(AllTreatmentController controller, Stage stage, Patient patient) {
        super.initialize(controller, stage, patient);
    }

    protected void showData(){
        this.lblFirstName.setText(patient.getFirstName());
        this.lblLastName.setText(patient.getLastName());
        CaregiverDAO caregiverDao = DAOFactory.getInstance().createCaregiverDAO();

        try {
            this.caregiverCombo.getItems().addAll(caregiverDao.readAll());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Handles the Create button to create a new treatment
     */
    @FXML
    public void handleAdd() throws SQLException {
        LocalDate date = this.datepicker.getValue();
        LocalTime begin = DateConverter.convertStringToLocalTime(txtBegin.getText());
        LocalTime end = DateConverter.convertStringToLocalTime(txtEnd.getText());
        String description = txtDescription.getText();
        String remarks = taRemarks.getText();
        ObservableList<Caregiver> caregivers = this.caregiverCombo.getCheckModel().getCheckedItems();

        Treatment treatment = new Treatment(patient, date, begin, end, description, remarks);
        TreatmentCaregiverDAO tcDao = DAOFactory.getInstance().createTreatmentCaregiverDAO();

        for (Caregiver caregiver: caregivers) {
            treatment.addCaregiver(caregiver);
            tcDao.link(treatment, caregiver);
        }

        this.createTreatment(treatment);
        this.controller.readAllAndShowInTableView();
        this.stage.close();
    }

    private void createTreatment(Treatment treatment) {
        TreatmentDAO dao = DAOFactory.getInstance().createTreatmentDAO();
        TreatmentCaregiverDAO caretreatDAO = DAOFactory.getInstance().createTreatmentCaregiverDAO();

        try {
            dao.create(treatment);

            for (Caregiver caregiver: treatment.getCaregivers()) {
                caretreatDAO.link(treatment, caregiver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
