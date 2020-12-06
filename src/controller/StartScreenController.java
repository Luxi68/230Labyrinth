package controller;

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
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import service.Motd;

import java.io.IOException;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class StartScreenController {

	public Label labelMotd;
	public Slider volumeSlider;
	MediaPlayer mediaPlayer1;
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	public void initialize() throws IOException {
		Motd message = new Motd();
		labelMotd.setWrapText(true);
		labelMotd.setMaxWidth(600);
		labelMotd.setText("Message of the Day: " + message.getMessageOfTheDay());
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

	@FXML
	private void goToProfileSelection(javafx.event.ActionEvent actionEvent) {
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
			System.out.println("Error starting the profile select screen.");
			e.printStackTrace();
		}
		mediaPlayer1.stop();
	}

	public void backgroundMusic(){
		Media backgroundSound = new Media(new File("resources/sounds/startScreenBackground.wav").toURI().toString());
		mediaPlayer1 = new MediaPlayer(backgroundSound);
		mediaPlayer1.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer1.setVolume(0.1);
		mediaPlayer1.setAutoPlay(true);
	}


	@FXML
	private void goToNewGame(ActionEvent actionEvent) {
	    try {
		Parent newGameParent = FXMLLoader.load(getClass().getResource("/scene/PlayerSelection.fxml"));
		Scene newGameScene = new Scene(newGameParent);
		Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
		window.setScene(newGameScene);
		window.show();
		Media buttonSound = new Media(new File("resources/sounds/button.wav").toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
		mediaPlayer.play();
        } catch (IOException e) {
            System.out.println("Error starting the new game screen.");
            e.printStackTrace();
        }
	    mediaPlayer1.stop();
	}

	@FXML
	private void goToLoadGame(ActionEvent actionEvent) {
	    try {
		Parent loadGameParent = FXMLLoader.load(getClass().getResource("/scene/LoadGame.fxml"));
		Scene loadGameScene = new Scene(loadGameParent);
		Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
		window.setScene(loadGameScene);
		window.show();
		Media buttonSound = new Media(new File("resources/sounds/button.wav").toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
		mediaPlayer.play();
        } catch (IOException e) {
            System.out.println("Error starting the load game screen.");
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
