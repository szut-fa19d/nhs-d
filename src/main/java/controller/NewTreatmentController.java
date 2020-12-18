package controller;

import datastorage.CaregiverDAO;
import datastorage.TreatmentCaregiverDAO;
import datastorage.DAOFactory;
import datastorage.TreatmentDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Caregiver;
import model.Patient;
import model.Treatment;
import utils.DateConverter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class NewTreatmentController extends TreatmentControllerCommon {
    @FXML
    private Label lblLastName;
    @FXML
    private Label lblFirstName;
    @FXML
    private ComboBox<Caregiver> caregiverCombo;

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
    public void handleAdd(){
        LocalDate date = this.datepicker.getValue();
        LocalTime begin = DateConverter.convertStringToLocalTime(txtBegin.getText());
        LocalTime end = DateConverter.convertStringToLocalTime(txtEnd.getText());
        String description = txtDescription.getText();
        String remarks = taRemarks.getText();
        Caregiver caregiver = this.caregiverCombo.getSelectionModel().getSelectedItem();

        Treatment treatment = new Treatment(patient, date, begin, end, description, remarks);
        treatment.addCaregiver(caregiver);

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
