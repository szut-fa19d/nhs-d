package controller;

import datastorage.DAOFactory;
import datastorage.GroupDAO;
import datastorage.UserDAO;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AllUserController extends CommonListController<User, UserDAO>{
    @FXML
    private TableColumn<User, Integer> colID;
    @FXML
    private TableColumn<User, String> colUserName;
    @FXML
    private TableColumn<User, String> colUserGroup;
    @FXML
    private TableView<User> tableView;
    @FXML
    Button btnNewUser;
    @FXML
    Button btnDelete;

    private ObservableList<User> tableviewContent = FXCollections.observableArrayList();
    private UserDAO userDAO;
    private GroupDAO groupDAO;

    public void initialize() {
        groupDAO = DAOFactory.getInstance().createGroupDAO();
        readAllAndShowInTableView();

        this.colID.setCellValueFactory(new PropertyValueFactory<>("id"));

        this.colUserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        this.colUserName.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colUserGroup.setCellValueFactory(data -> new ReadOnlyStringWrapper(
                groupDAO.getInstanceById(data.getValue().getGroup()).getGroupName()
        ));
        this.colUserGroup.setCellFactory(TextFieldTableCell.forTableColumn());

        this.tableView.setItems(this.tableviewContent);
    }

    public void readAllAndShowInTableView() {
        this.tableviewContent.clear();
        this.userDAO = DAOFactory.getInstance().createUserDAO();
        List<User> allUsers;
        try {
            allUsers = userDAO.readAll();
            UserController userController = UserController.getInstance();
            float currentUserId =userController.getUser().getId();
            for (User user : allUsers) {
                if (user.getId() == currentUserId) {
                    allUsers.remove(user);
                    break;
                }
            }
            this.tableviewContent.addAll(allUsers);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void refreshDAO() {
        this.dao = DAOFactory.getInstance().createUserDAO();
    }

    @FXML
    public void openAddUser() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/AddUserView.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setOnHidden((EventHandler<WindowEvent>) arg0 -> readAllAndShowInTableView());
            stage.showAndWait();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    public User handleDelete() {
        int index = this.tableView.getSelectionModel().getSelectedIndex();
        User user = this.tableviewContent.remove(index);
        try {
            userDAO.deleteById((int) user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

}
