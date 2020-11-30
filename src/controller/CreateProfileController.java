package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entity.Profile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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

    public void createNewProfile(ActionEvent actionEvent) throws IOException {
        if (profileName.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, createProfilePane.getScene().getWindow(), "Error!", "Please enter profile name");
        } else {
            Profile user = new Profile(profileName.getText());
            user.createProfileFile(user.getPlayerName());
            showAlert(Alert.AlertType.CONFIRMATION, createProfilePane.getScene().getWindow(),"Creation Successful", "Welcome " + profileName.getText());
        }
    }

    public void goBackToProfileSelection(ActionEvent actionEvent) {
        try {
            Parent profileSelectionParent = FXMLLoader.load(getClass().getResource("/scene/ProfileSelection.fxml"));
            Scene profileSelectionScene = new Scene(profileSelectionParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(profileSelectionScene);
            window.show();
        } catch (IOException e) {
            System.out.println("Error returning to the Profile Selction from create profile screen.");
            e.printStackTrace();
        }
    }
}
