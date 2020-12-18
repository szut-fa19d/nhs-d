package controller;

import datastorage.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Patient;
import model.Treatment;
import utils.DateConverter;
import utils.LogType;
import utils.Logger;

import java.sql.SQLException;
import java.time.LocalDate;

public class TreatmentController extends TreatmentControllerCommon {
    @FXML
    private Label lblPatientName;
    @FXML
    private Label lblCarelevel;
    @FXML
    private Button btnChange;
    @FXML
    private Button btnCancel;

    private Treatment treatment;

    /**
     * @see TreatmentControllerCommon#initialize
     */
    public void initializeController(AllTreatmentController controller, Stage stage, Treatment treatment) {
        PatientDAO pDao = DAOFactory.getInstance().createPatientDAO();
    
        try {
            Patient patient = pDao.read((int) treatment.getPatient().getId());
            this.treatment = treatment;
            super.initialize(controller, stage, patient);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void showData(){
        this.lblPatientName.setText(patient.getLastName()+", "+patient.getFirstName());
        this.lblCarelevel.setText(patient.getCareLevel());
        LocalDate date = DateConverter.convertStringToLocalDate(treatment.getDate());
        this.datepicker.setValue(date);
        this.txtBegin.setText(this.treatment.getBegin());
        this.txtEnd.setText(this.treatment.getEnd());
        this.txtDescription.setText(this.treatment.getDescription());
        this.taRemarks.setText(this.treatment.getRemarks());
    }

    /**
     * Handler for treatment edits
     */
    @FXML
    public void handleChange(){
        this.treatment.setDate(this.datepicker.getValue().toString());
        this.treatment.setBegin(txtBegin.getText());
        this.treatment.setEnd(txtEnd.getText());
        this.treatment.setDescription(txtDescription.getText());
        this.treatment.setRemarks(taRemarks.getText());
        this.doUpdate();
        this.controller.readAllAndShowInTableView();
        this.stage.close();
    }

    private void doUpdate() {
        TreatmentDAO dao = DAOFactory.getInstance().createTreatmentDAO();
        try {
            dao.update(treatment);
            Logger.getInstance().log(LogType.TREATMENT, treatment.getId(), String.format("Treatment %s bearbeitet", treatment.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}