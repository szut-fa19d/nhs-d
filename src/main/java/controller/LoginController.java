package controller;

import datastorage.DAOFactory;
import datastorage.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import utils.PasswordHash;
import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button submit;
    @FXML
    private Label error;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    public void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            return;
        }

        UserDAO userDAO = DAOFactory.getInstance().createUserDAO();
        User user = userDAO.getUserByUsername(username);

        if (user == null) {
            error.setVisible(true);
            return;
        }

        PasswordHash passwordHash = new PasswordHash(user.getId());
        String hashedPassword = passwordHash.hashPassword(password);

        if(hashedPassword == null || !hashedPassword.equals(user.getPassword())) {
            error.setVisible(true);
            return;
        }

        UserController.getInstance().setUser(user);
        onSuccessfulLogin();
    }

    private void onSuccessfulLogin() {
        try {
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/MainWindowView.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
