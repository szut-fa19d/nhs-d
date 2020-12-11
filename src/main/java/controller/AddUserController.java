package controller;

import datastorage.DAOFactory;
import datastorage.GroupDAO;
import datastorage.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Group;
import model.User;
import utils.PasswordHash;

import java.sql.SQLException;

public class AddUserController {
    @FXML
    private ComboBox<Group> comboGroup;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    AnchorPane anchorPane;

    private GroupDAO groupDAO;

    public void initialize() {
        groupDAO = DAOFactory.getInstance().createGroupDAO();

        try {
            ObservableList<Group> groups = FXCollections.observableArrayList(groupDAO.readAll());
            comboGroup.setItems(groups);
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void handleAdd(){
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        Group group = comboGroup.getValue();
        User user = new User(username, password, group);
        createUser(user);
    }

    private void createUser(User user) {
        UserDAO userDAO = DAOFactory.getInstance().createUserDAO();
        try {
            userDAO.create(user);
            user = userDAO.getUserByUsername(user.getUsername());

            PasswordHash passwordHash = new PasswordHash(user.getId());
            String hashedPassword = passwordHash.hashPassword(user.getPassword());
            user.setPassword(hashedPassword);

            userDAO.update(user);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCancel(){
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }
}
