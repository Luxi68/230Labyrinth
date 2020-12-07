package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

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

/**
 * CreateProfileController.java. This class is the controller for the fxml file CreateProfile.fxml
 * @author - Alberto Ortenzi
 */

public class CreateProfileController {

    public TextField profileName;
    public AnchorPane createProfilePane;
    public Slider volumeSlider;
    MediaPlayer mediaPlayer1;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    /**
     * This method is called everytime the class is initialised. It executes the backgroundMusic method
     * which starts the background music and it also sets up the volume control slider.
     */
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

    /**
     * This method when called shows an Alert of a specific type.
     * @param alertType - the type of the alert you need
     * @param owner - the window which needs to display the alert
     * @param title - the title of the alert
     * @param message - the context message.
     */
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    /**
     * This method creates a new profile object and adds the newly created profile to the listview
     * It also checks if the textfield is empty and displays an error alert if that is the case
     * @param actionEvent - the action of pressing the button
     * @throws IOException - in case there is a problem creating the file
     */
    public void createNewProfile(ActionEvent actionEvent) throws IOException {
        if (profileName.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, createProfilePane.getScene().getWindow(), "Error!", "Please enter profile name");
        } else {
            Profile user = new Profile(profileName.getText());
            user.createProfileFile(user.getPlayerName());
            showAlert(Alert.AlertType.CONFIRMATION, createProfilePane.getScene().getWindow(),"Creation Successful", "Welcome " + profileName.getText());
        }
    }

    /**
     * This method created a new media object and then passes it to a predefined mediaPlayer object.
     * it then sets the volume to a predetermined amount and sets the cycle count to indefinite to endlessly loop the music
     * until the users leaves the page.
     */
    public void backgroundMusic(){
        Media backgroundSound = new Media(new File("resources/sounds/startScreenBackground.wav").toURI().toString());
        mediaPlayer1 = new MediaPlayer(backgroundSound);
        mediaPlayer1.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer1.setVolume(0.1);
        mediaPlayer1.setAutoPlay(true);
    }

    /**
     * This method loads the profile selection scene and plays a sound effect when the button is clicked
     * @param actionEvent - the action of clicking the button.
     */
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

    /**
     * This method quits the application on action. This is used for the menu bar under file
     * @param actionEvent - the action of selecting the option in the menubar
     */
    public void quitGameFromMenu(ActionEvent actionEvent) {
        Platform.exit();
    }

    /**
     * This method opens an information alert which contains the essential game information to know in order
     * to play the game.
     * @param actionEvent - the action of selecting the option in the menu bar.
     */
    public void openGameInstructions(ActionEvent actionEvent) throws FileNotFoundException {
        File instructions = new File("src/Instructions.txt");
        String outputText = "";
        Scanner in;
        in = new Scanner(instructions);
        while (in.hasNextLine()){
            outputText += in.nextLine() + System.lineSeparator();
        }
        Alert errorInfo = new Alert(Alert.AlertType.INFORMATION);
        errorInfo.setTitle("Game Instructions");
        errorInfo.setHeaderText("How to play the game");
        errorInfo.setContentText(outputText);
        errorInfo.show();
    }
}
