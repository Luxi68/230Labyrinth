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
import javafx.scene.control.ListView;
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
 * ProfileSelectionController.java
 * This class is the controller for the profileSelection.fxml file
 *
 * @author Alberto Ortenzi
 */

public class ProfileSelectionController {

	public ListView<String> profileList;
	public MediaPlayer mediaPlayer1;
	public Slider volumeSlider;
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

	/**
	 * This method on the button click goes back to the startScreen loading the scene
	 *
	 * @param actionEvent - event of button click
	 */
	@FXML
	private void backToStartScreen(ActionEvent actionEvent) {
		try {
			Parent startScreenParent = FXMLLoader.load(getClass().getResource("/scene/StartScreen.fxml"));
			startScreenParent.setStyle("-fx-background-image: url('assets/mount.png');" + "-fx-background-size: cover");
			Scene startScreenScene = new Scene(startScreenParent);
			Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
			window.setScene(startScreenScene);
			window.show();
			Media buttonSound = new Media(new File("resources/sounds/button.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
			mediaPlayer.play();
		} catch (IOException e) {
			System.out.println("Error returning to the main screen from profile selection.");
			e.printStackTrace();
		}
		mediaPlayer1.stop();
	}

	/**
	 * This method takes the results of the getAllProfiles method and iterates through it displaying all
	 * available profiles and adds them to the listview. It also removes the .txt file extension
	 */
	private void displayAllProfiles() {
		for (int i = 0; i < getAllProfiles().size(); i++) {
			String currentProfile = getAllProfiles().get(i);
			profileList.getItems().add(currentProfile.substring(0, currentProfile.length() - 4));
		}
	}

	/**
	 * This method reads all the files in the folder resources/users and adds their filenames to an arraylist
	 *
	 * @return - the arraylist of filenames
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
	 * This method on button click loads the createprofile.fxml scene
	 *
	 * @param actionEvent - the button click
	 * @throws IOException - in case the file doesn't exist
	 */
	public void goToCreateProfile(ActionEvent actionEvent) throws IOException {
		Parent createProfileParent = FXMLLoader.load(getClass().getResource("/scene/CreateProfile.fxml"));
		Scene createProfileScene = new Scene(createProfileParent);
		Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
		window.setScene(createProfileScene);
		window.show();
		Media buttonSound = new Media(new File("resources/sounds/button.wav").toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
		mediaPlayer.play();
		mediaPlayer1.stop();
	}

	/**
	 * This method on button click opens an alert either displaying the selected profile info or opens
	 * an error window saying that there has been an error
	 *
	 * @param actionEvent - The button click
	 */
	public void viewProfileInformation(ActionEvent actionEvent) {
		try {
			Alert profileInfo = new Alert(Alert.AlertType.INFORMATION);
			profileInfo.setTitle("Profile Information");
			profileInfo.setHeaderText("Profile Information");
			String selectedFilename = profileList.getSelectionModel().getSelectedItem() + ".txt";
			String stats = fileToString(selectedFilename);
			profileInfo.setContentText(stats);
			Media buttonSound = new Media(new File("resources/sounds/button.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
			mediaPlayer.play();
			profileInfo.show();
		} catch (FileNotFoundException e) {
			Alert errorInfo = new Alert(Alert.AlertType.ERROR);
			errorInfo.setTitle("Error");
			errorInfo.setHeaderText("Please Select a Player !");
			errorInfo.setContentText("You have not selected a player please do and try again");
			errorInfo.show();
		}

	}

	/**
	 * This method on button deletes the selected file or opens an error window
	 * saying that there has been an error
	 *
	 * @param actionEvent - the button click
	 */
	public void deleteFile(ActionEvent actionEvent) {
		String filename = profileList.getSelectionModel().getSelectedItem() + ".txt";
		File fileToBeDeleted = new File("resources/users/" + filename);
		if (fileToBeDeleted.delete()) {
			profileList.getItems().clear();
			displayAllProfiles();
		} else {
			Alert errorInfo = new Alert(Alert.AlertType.ERROR);
			errorInfo.setTitle("Error");
			errorInfo.setHeaderText("Please Select a Player !");
			errorInfo.setContentText("You have not selected a player please do and try again");
			errorInfo.show();
		}
	}

	/**
	 * This method takes in a filename and converts the contents to a string so that it can be outputted
	 * in future methods
	 *
	 * @param filename - the file to be converted to string
	 * @return outputText - the string containing the file info
	 * @throws FileNotFoundException - if the file doesn't exist but it will always
	 */
	public String fileToString(String filename) throws FileNotFoundException {
		File selectedFile = new File("resources/users/" + filename);
		String outputText;
		Scanner in = null;
		in = new Scanner(selectedFile);
		outputText = "Profile Name: " + in.nextLine() + System.lineSeparator() + System.lineSeparator();
		while (in.hasNextLine()) {
			String currentLine = in.nextLine();
			String[] splitted = currentLine.split(",");
			switch (splitted[0]) {
				case "1":
					outputText += "Knossos board Stats: " + System.lineSeparator();
					break;
				case "2":
					outputText += "Marathon board Stats: " + System.lineSeparator();
					break;
				case "3":
					outputText += "Sparta board Stats: " + System.lineSeparator();
			}
			outputText += "Games Played: " + splitted[1] + System.lineSeparator();
			outputText += "Games Won: " + splitted[2] + System.lineSeparator() + System.lineSeparator();
		}
		return outputText;
	}

	/**
	 * This method loads the leaderboard scene and plays a sound effect when the button is clicked
	 *
	 * @param actionEvent - the action of clicking the button.
	 */
	public void goToLeaderboard(ActionEvent actionEvent) {
		try {
			Parent leaderboardParent = FXMLLoader.load(getClass().getResource("/scene/Leaderboard.fxml"));
			leaderboardParent.setStyle("-fx-background-image: url('assets/mount.png');" + "-fx-background-size: cover");
			Scene leaderboardScene = new Scene(leaderboardParent);
			Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
			window.setScene(leaderboardScene);
			window.show();
			Media buttonSound = new Media(new File("resources/sounds/button.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
			mediaPlayer.play();
		} catch (IOException e) {
			System.out.println("Error accessing leaderboard from profile selection");
			e.printStackTrace();
		}
		mediaPlayer1.stop();
	}

	/**
	 * This method created a new media object and then passes it to a predefined mediaPlayer object.
	 * it then sets the volume to a predetermined amount and sets the cycle count to indefinite to endlessly loop the music
	 * until the users leaves the page.
	 */
	public void backgroundMusic() {
		Media backgroundSound = new Media(new File("resources/sounds/profileSelectionBackground.wav").toURI().toString());
		mediaPlayer1 = new MediaPlayer(backgroundSound);
		mediaPlayer1.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer1.setVolume(0.1);
		mediaPlayer1.setAutoPlay(true);
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
