package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
	MediaPlayer mediaPlayer;
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
	}

	/**public void backgroudMusic(){
		Media backgroudSound = new Media("/Users/albertoortenzi/IdeaProjects/230Labyrinth/resources/sounds/startScreenBackground.mp3");
		mediaPlayer = new MediaPlayer(backgroudSound);
		mediaPlayer.setAutoPlay(true);
	}**/

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
	}

	@FXML
	private void closeApplication() {
		Platform.exit();
	}
}
