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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * LoadGameController.java
 * This class is the controller for the fxml file. LoadGame.fxml
 * @author - Alberto Ortenzi
 */
public class LoadGameController {

	public ChoiceBox<String> savesChoice;
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

	/**
	 * This method on button click. loads the specified save file in the gameScreen by calling the gamescreen controller class
	 * and creating an instance of it.
	 * @param actionEvent - the button to be clicked
	 */
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

	/**
	 * This method gets all the filenames of the files in the specified directory and returns an arraylist
	 * of strings
	 * @return - the arraylist containing the filenames as strings
	 */
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
	 * This method on the button click goes back to the startScreen loading the scene
	 * @param actionEvent - event of button click
	 */
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
		errorInfo.setContentText("You have not selected a player please do and try again");
		errorInfo.show();
	}
}
