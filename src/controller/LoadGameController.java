package controller;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LoadGameController {

	public ChoiceBox<String> savesChoice;
	public Slider volumeSlider;
    MediaPlayer mediaPlayer1;
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	public void initialize() {
		ObservableList<String> choices = FXCollections.observableArrayList(getAllLevelFilenames());
		savesChoice.setItems(choices);
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
	private void loadGame(ActionEvent actionEvent) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene/GameScreen.fxml"));
			Parent gameScreenParent = loader.load();
			GameScreenController controller = loader.getController();

			// controller.initData(); TODO - needs to be edited to input stuff to screen

			Scene gameScreenScene = new Scene(gameScreenParent);
			Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
			window.setTitle("The First Olympian");
			window.setScene(gameScreenScene);
			window.show();
			Media buttonSound = new Media(new File("resources/sounds/button.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
			mediaPlayer.play();
			window.setMaximized(true);
		} catch (IOException e) {
			System.out.println("Error starting the Game Screen from load game screen.");
			e.printStackTrace();
		}
		mediaPlayer1.stop();
	}

	public ArrayList<String> getAllLevelFilenames(){
		ArrayList<String> levelFileNames = new ArrayList<>();
		File directoryPath = new File("resources/saves");
		File listOfFiles[] = directoryPath.listFiles();
		Scanner sc = null;
		for (File currentFile : listOfFiles) {
			levelFileNames.add(currentFile.getName());
		}
		return levelFileNames;
	}
	public void backgroundMusic(){
		Media backgroundSound = new Media(new File("resources/sounds/startScreenBackground.wav").toURI().toString());
		mediaPlayer1 = new MediaPlayer(backgroundSound);
		mediaPlayer1.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer1.setVolume(0.1);
		mediaPlayer1.setAutoPlay(true);
	}

	@FXML
	private void backToStartScreen(ActionEvent actionEvent) {
		try {
			Parent startScreenParent = FXMLLoader.load(getClass().getResource("/scene/StartScreen.fxml"));
			Scene startScreenScene = new Scene(startScreenParent);
			Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
			window.setScene(startScreenScene);
			window.show();
			Media buttonSound = new Media(new File("resources/sounds/button.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
			mediaPlayer.play();
		} catch (IOException e) {
			System.out.println("Error returning to the start screen from load game screen.");
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
