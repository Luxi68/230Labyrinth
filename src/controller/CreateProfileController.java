package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entity.Profile;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.Window;

public class CreateProfileController {

    public TextField profileName;
    public AnchorPane createProfilePane;
    public Slider volumeSlider;
    MediaPlayer mediaPlayer1;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {
        backgroundMusic();
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setValue(mediaPlayer1.getVolume() * 100);
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaPlayer1.setVolume(volumeSlider.getValue() / 100);
            }
        });

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
    public void backgroundMusic(){
        Media backgroundSound = new Media(new File("resources/sounds/startScreenBackground.wav").toURI().toString());
        mediaPlayer1 = new MediaPlayer(backgroundSound);
        mediaPlayer1.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer1.setVolume(0.1);
        mediaPlayer1.setAutoPlay(true);
    }

    public void goBackToProfileSelection(ActionEvent actionEvent) {
        try {
            Parent profileSelectionParent = FXMLLoader.load(getClass().getResource("/scene/ProfileSelection.fxml"));
            Scene profileSelectionScene = new Scene(profileSelectionParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(profileSelectionScene);
            window.show();
            Media buttonSound = new Media(new File("resources/sounds/button.wav").toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
            mediaPlayer.play();
        } catch (IOException e) {
            System.out.println("Error returning to the Profile Selction from create profile screen.");
            e.printStackTrace();
        }
        mediaPlayer1.stop();
    }
    public void quitGameFromMenu(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void openGameInstructions(ActionEvent actionEvent) {
        Alert errorInfo = new Alert(Alert.AlertType.INFORMATION);
        errorInfo.setTitle("Game Instructions");
        errorInfo.setHeaderText("How to play the game");
        errorInfo.setContentText("You have not selected a player please do and try again");
        errorInfo.show();
    }
}
