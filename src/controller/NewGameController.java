package controller;

import core.FileReader;
import entity.Profile;
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
import javafx.scene.control.*;
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
 * NewGameController.java
 * This class is the controller class for the fxml file NewGame.fxml
 *
 * @author - Alberto Ortenzi
 */
public class NewGameController {

	public ListView<String> chosenProfiles;
	public ArrayList<Profile> profiles = new ArrayList<>();
	public Button chooseProfileButton;
	public int numberOfPlayers;
	public int count = 0;
	public ChoiceBox<String> fileChoice;
	public Label labelSelectPlayers;
	public ListView<String> listOfProfiles;
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
		fileChoice.setItems(choices);
		chosenProfiles.setMouseTransparent(true);
		chosenProfiles.setFocusTraversable(false);
		displayAllProfiles();
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
	private void startGame(ActionEvent actionEvent) {
		if (fileChoice.getValue() != null && areAllProfilesChosen()) {
			try {
				URL url = getClass().getResource("/scene/GameScreen.fxml");
				FXMLLoader loader = new FXMLLoader(url);
				Parent gameScreenParent = loader.load();

				GameScreenController controller = loader.getController();
				controller.initData(FileReader.readDataFile(fileChoice.getValue(), profiles));

				Scene gameScreenScene = new Scene(gameScreenParent);
				Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
				window.setTitle("The First Olympian");
				window.setScene(gameScreenScene);
				window.show();
				window.setMaximized(true);

				Media buttonSound = new Media(new File("resources/sounds/button.wav").toURI().toString());
				MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
				mediaPlayer.play();
			} catch (IOException e) {
				System.out.println("Error starting the Game Screen from new game screen.");
				e.printStackTrace();
			}
		} else if (!areAllProfilesChosen()) {
			Alert errorInfo = new Alert(Alert.AlertType.ERROR);
			errorInfo.setTitle("Error");
			errorInfo.setHeaderText("Please Select all the required Players !");
			errorInfo.setContentText("You have not selected enough players please try again");
			errorInfo.show();
		} else {
			Alert errorInfo = new Alert(Alert.AlertType.ERROR);
			errorInfo.setTitle("Error");
			errorInfo.setHeaderText("Please Select a Game file");
			errorInfo.setContentText("You have not selected a map to play on. Please select one");
			errorInfo.show();
		}
		mediaPlayer1.stop();
	}

	/**
	 * This method gets all the filenames of the files in the specified directory and returns an arraylist
	 * of strings
	 *
	 * @return - the arraylist containing the filenames as strings
	 */
	public ArrayList<String> getAllLevelFilenames() {
		ArrayList<String> levelFileNames = new ArrayList<>();
		File directoryPath = new File("resources/levels");
		File listOfFiles[] = directoryPath.listFiles();
		Scanner sc = null;
		for (File currentFile : listOfFiles) {
			levelFileNames.add(currentFile.getName());
		}
		return levelFileNames;
	}

	/**
	 * This method gets all the files in the users folder and returns the name of the .txt files in an arraylist
	 *
	 * @return - the arraylist of profile names
	 */
	public ArrayList<String> getAllProfiles() {
		ArrayList<String> profileFileNames = new ArrayList<>();
		File directoryPath = new File("resources/users");
		File listOfFiles[] = directoryPath.listFiles();
		Scanner sc = null;
		for (File currentFile : listOfFiles) {
			profileFileNames.add(currentFile.getName());
		}
		return profileFileNames;
	}

	/**
	 * This method created a new media object and then passes it to a predefined mediaPlayer object.
	 * it then sets the volume to a predetermined amount and sets the cycle count to indefinite to endlessly loop the music
	 * until the users leaves the page.
	 */
	public void backgroundMusic() {
		Media backgroundSound = new Media(new File("resources/sounds/playerSelectionBackground.wav").toURI().toString());
		mediaPlayer1 = new MediaPlayer(backgroundSound);
		mediaPlayer1.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer1.setVolume(0.1);
		mediaPlayer1.setAutoPlay(true);
	}

	/**
	 * This method outputs the contents of the arraylist which is the product of the getAllprofiles method
	 * Is also removes the .txt extension for estetics
	 */
	private void displayAllProfiles() {
		for (int i = 0; i < getAllProfiles().size(); i++) {
			String currentProfile = getAllProfiles().get(i);
			listOfProfiles.getItems().add(currentProfile.substring(0, currentProfile.length() - 4));
		}
	}

	/**
	 * this method sets a label using the passed variable from the previous controller, it changes based on the
	 * button clicked in the previous scene
	 *
	 * @param playerNum - the selected number of players
	 */
	public void chooseXProfiles(int playerNum) {
		labelSelectPlayers.setText("Please select " + playerNum + " players");
	}

	/**
	 * this method checks if all the desired profiles have been selected
	 *
	 * @return - returns true if the condition is true
	 */
	public boolean areAllProfilesChosen() {
		return count == numberOfPlayers;
	}

	/**
	 * This method chooses a profile and adds it to the arraylist which will then be passed onto the gamescreen.
	 * The selected profile is then removed from the available profiles and added to the chosen profiles.
	 *
	 * @param actionEvent
	 */
	public void chooseProfile(ActionEvent actionEvent) {
		String selectedProfile = listOfProfiles.getSelectionModel().getSelectedItem();
		Profile chosenOne = new Profile(selectedProfile);
		if (selectedProfile == null) {
			Alert errorInfo = new Alert(Alert.AlertType.ERROR);
			errorInfo.setTitle("Error");
			errorInfo.setHeaderText("Please Select a Player !");
			errorInfo.setContentText("You have not selected a player please do and try again");
			errorInfo.show();
		} else {
			chosenProfiles.getItems().add(selectedProfile);
			listOfProfiles.getItems().remove(selectedProfile);
			profiles.add(chosenOne);
			count++;
		}
		if (areAllProfilesChosen()) {
			chooseProfileButton.setDisable(true);
		}
	}

	/**
	 * This method loads the start Screen scene and plays a sound effect when the button is clicked
	 *
	 * @param actionEvent - the action of clicking the button.
	 */
	@FXML
	private void backToStartScreen(ActionEvent actionEvent) {
		try {
			Parent startScreenParent = FXMLLoader.load(getClass().getResource("/scene/PlayerSelection.fxml"));
			startScreenParent.setStyle("-fx-background-image: url('assets/mount.png');" + "-fx-background-size: cover");
			Scene startScreenScene = new Scene(startScreenParent);
			Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
			window.setScene(startScreenScene);
			window.show();
			Media buttonSound = new Media(new File("resources/sounds/button.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
			mediaPlayer.play();
		} catch (IOException e) {
			System.out.println("Error returning to the start screen from new game screen.");
			e.printStackTrace();
		}
		mediaPlayer1.stop();
	}

	/**
	 * This method quits the application on action. This is used for the menu bar under file
	 *
	 * @param actionEvent - the action of selecting the option in the menubar
	 */
	public void quitGameFromMenu(ActionEvent actionEvent) {
		Platform.exit();
	}

	/**
	 * This method opens an information alert which contains the essential game information to know in order
	 * to play the game.
	 *
	 * @param actionEvent - the action of selecting the option in the menu bar.
	 */
	public void openGameInstructions(ActionEvent actionEvent) throws FileNotFoundException {
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
