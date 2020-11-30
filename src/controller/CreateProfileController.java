package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;

public class CreateProfileController {

    public TextField profileName;
    public AnchorPane createProfilePane;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {

    }
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public void createNewProfile(ActionEvent actionEvent) {
        if (profileName.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, createProfilePane.getScene().getWindow(), "Error!", "Please enter profile name");
        } else {

        }
    }
}
