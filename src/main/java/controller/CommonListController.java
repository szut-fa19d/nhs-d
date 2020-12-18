package controller;

import java.sql.SQLException;
import java.util.List;

import datastorage.DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.DatabaseEntry;

public abstract class CommonListController<T extends DatabaseEntry, DAOType extends DAO<T>> { // NOSONAR
  @FXML
  protected TableView<T> tableView;

  protected ObservableList<T> tableviewContent = FXCollections.observableArrayList();
  protected DAOType dao;

  @FXML
  protected TableColumn<T, Integer> colID;

  /**
   * Initializes the corresponding fields. Is called as soon as the corresponding FXML file is to be displayed.
   */
  public abstract void initialize();

  protected abstract void logDelete(T item);

  /**
   * calls readAll in {@link DAOType} and shows items in the table
   */
  protected void readAllAndShowInTableView() {
    this.tableviewContent.clear();

    this.refreshDAO(); // TODO brauchen wir das?

    List<T> allItems;
    try {
      allItems = dao.readAll();
      for (T item: allItems) {
        this.tableviewContent.add(item);
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
  }

  protected abstract void refreshDAO();

  /**
   * Deletes the currently selected item and returns it to give extending classes the option for further clean up (deleting related entries)
   */
  @FXML
  public T handleDelete() {
    T item = this.tableView.getSelectionModel().getSelectedItem();
    this.tableviewContent.remove(item);

    this.refreshDAO(); // TODO brauchen wir das?

    try {
      this.dao.deleteById((int) item.getId());
      this.logDelete(item);
    } catch (SQLException exception) {
      exception.printStackTrace();
    }

    return item;
  }
}
