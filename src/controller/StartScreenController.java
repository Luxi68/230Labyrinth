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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import service.Motd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * StartScreenController.java
 * This class is the controller for the fxml file. StartScreen.fxml
 *
 * @author - Alberto Ortenzi
 */
public class StartScreenController {

	public Label labelMotd;
	public Slider volumeSlider;
	MediaPlayer mediaPlayer1;
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	/**
	 * This method is called everytime the class is initialised. It executes the backgroundMusic method
	 * which starts the background music and it also sets up the volume control slider. It also creates a Motd
	 * object. and then sets a specific label to become equal to the newly created message of the day string.
	 */
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

	/**
	 * This method loads the profile selection scene and plays a sound effect when the button is clicked
	 *
	 * @param actionEvent - the action of clicking the button.
	 */
	@FXML
	private void goToProfileSelection(javafx.event.ActionEvent actionEvent) {
		try {
			Parent profileSelectionParent = FXMLLoader.load(getClass().getResource("/scene/ProfileSelection.fxml"));
			profileSelectionParent.setStyle("-fx-background-image: url('assets/mount.png');" + "-fx-background-size: cover");
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

	/**
	 * This method created a new media object and then passes it to a predefined mediaPlayer object.
	 * it then sets the volume to a predetermined amount and sets the cycle count to indefinite to endlessly loop the music
	 * until the users leaves the page.
	 */
	private void backgroundMusic() {
		Media backgroundSound = new Media(new File("resources/sounds/startScreenBackground.wav").toURI().toString());
		mediaPlayer1 = new MediaPlayer(backgroundSound);
		mediaPlayer1.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer1.setVolume(0.1);
		mediaPlayer1.setAutoPlay(true);
	}

	/**
	 * This method loads the new game scene and plays a sound effect when the button is clicked
	 *
	 * @param actionEvent - the action of clicking the button.
	 */
	@FXML
	private void goToNewGame(ActionEvent actionEvent) {
		try {
			Parent newGameParent = FXMLLoader.load(getClass().getResource("/scene/PlayerSelection.fxml"));
			newGameParent.setStyle("-fx-background-image: url('assets/mount.png');" + "-fx-background-size: cover");
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

	/**
	 * This method loads the load game scene and plays a sound effect when the button is clicked
	 *
	 * @param actionEvent - the action of clicking the button.
	 */
	@FXML
	private void goToLoadGame(ActionEvent actionEvent) {
		try {
			Parent loadGameParent = FXMLLoader.load(getClass().getResource("/scene/LoadGame.fxml"));
			loadGameParent.setStyle("-fx-background-image: url('assets/mount.png');" + "-fx-background-size: cover");
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

	/**
	 * This method quits the application on action. This is used for the menu bar under file
	 *
	 * @param actionEvent - the action of selecting the option in the menubar
	 */
	@FXML
	private void quitGameFromMenu(ActionEvent actionEvent) {
		Platform.exit();
	}

	/**
	 * This method opens an information alert which contains the essential game information to know in order
	 * to play the game.
	 *
	 * @param actionEvent - the action of selecting the option in the menu bar.
	 */
	@FXML
	private void openGameInstructions(ActionEvent actionEvent) throws FileNotFoundException {
		File instructions = new File("src/Instructions.txt");
		String outputText = "";
		Scanner in;
		in = new Scanner(instructions);
		while (in.hasNextLine()) {
			outputText += in.nextLine() + System.lineSeparator();
		}
		Alert errorInfo = new Alert(Alert.AlertType.INFORMATION);
		errorInfo.setTitle("Game Instructions");
		errorInfo.setHeaderText("How to play the game");
		errorInfo.setContentText(outputText);
		errorInfo.show();
	}
}
