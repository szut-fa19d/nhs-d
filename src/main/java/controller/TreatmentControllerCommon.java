package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Patient;

public abstract class TreatmentControllerCommon {
  @FXML
  protected TextField txtBegin;
  @FXML
  protected TextField txtEnd;
  @FXML
  protected TextField txtDescription;
  @FXML
  protected TextArea taRemarks;
  @FXML
  protected DatePicker datepicker;

  protected AllTreatmentController controller;
  protected Stage stage;
  protected Patient patient;

  protected void initialize(AllTreatmentController controller, Stage stage, Patient patient) {
    this.controller = controller;
    this.stage = stage;
    this.patient = patient;
    this.showData();
  }

  @FXML
  public void handleCancel() {
    this.stage.close();
  }

  protected abstract void showData();
}
