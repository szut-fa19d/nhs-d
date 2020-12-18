package controller;

import datastorage.DAOFactory;
import datastorage.LogEntryDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import model.LogEntry;
import utils.Logger;
import utils.NewLogEntryListener;

public class AllLogController extends CommonListController<LogEntry, LogEntryDAO> implements NewLogEntryListener {
    @FXML
    private TableColumn<LogEntry, String> colTimestamp;
    @FXML
    private TableColumn<LogEntry, String> colType;
    @FXML
    private TableColumn<LogEntry, Integer> colObjID;
    @FXML
    private TableColumn<LogEntry, String> colUserName;
    @FXML
    private TableColumn<LogEntry, String> colDescription;

    @Override
    public void initialize() {
        readAllAndShowInTableView();

        this.colID.setCellValueFactory(new PropertyValueFactory<>("id"));

        // CellValuefactory zum Anzeigen der Daten in der TableView
        this.colTimestamp.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        this.colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        this.colObjID.setCellValueFactory(new PropertyValueFactory<>("objId"));
        this.colUserName.setCellValueFactory(new PropertyValueFactory<>("userId"));
        this.colDescription.setCellValueFactory(new PropertyValueFactory<>("desc"));

        // Anzeigen der Daten
        this.tableView.setItems(this.tableviewContent);

        Logger.getInstance().addNewLogEntryListener(this);
    }

    @Override
    protected void refreshDAO() {
        this.dao = DAOFactory.getInstance().createLogEntryDAO();
    }

    @Override
    public void newLogEntry(LogEntry logEntry) {
        this.tableviewContent.add(logEntry);
    }

    @Override
    protected void logDelete(LogEntry item) {
        // TODO Auto-generated method stub
    }
}
