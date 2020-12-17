package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowController {
    @FXML
    Text currentLogin;
    @FXML
    StackPane stackPane;
    @FXML
    Tab userView;
    @FXML
    Tab caregiverView;

    UserController userController;

    public void initialize() {
        userController = UserController.getInstance();
        currentLogin.setText("Eingeloggt als: " + userController.getUser().getUsername());

        if (userController.isAdmin()) {
            this.userView.setDisable(false);
            this.userView.setStyle("");

            this.caregiverView.setDisable(false);
            this.caregiverView.setStyle("");
        }
    }

    @FXML
    public void logout() {
        try {
            Stage stage = (Stage) stackPane.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/LoginView.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
            currentLogin.setText("");
            UserController.getInstance().logout();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
