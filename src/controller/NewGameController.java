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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

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
		if(fileChoice.getValue() != null && areAllProfilesChosen()){
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene/GameScreen.fxml"));
			Parent gameScreenParent = loader.load();
			GameScreenController controller = loader.getController();
			controller.initData(FileReader.readDataFile(fileChoice.getValue(),profiles));
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
			System.out.println("Error starting the Game Screen from new game screen.");
			e.printStackTrace();
		}
		} else if(!areAllProfilesChosen()) {
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
	public ArrayList<String> getAllLevelFilenames(){
		ArrayList<String> levelFileNames = new ArrayList<>();
		File directoryPath = new File("resources/levels");
		File listOfFiles[] = directoryPath.listFiles();
		Scanner sc = null;
		for (File currentFile : listOfFiles) {
			levelFileNames.add(currentFile.getName());
		}
		return levelFileNames;
	}

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
	public void backgroundMusic(){
		Media backgroundSound = new Media(new File("resources/sounds/playerSelectionBackground.wav").toURI().toString());
		mediaPlayer1 = new MediaPlayer(backgroundSound);
		mediaPlayer1.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer1.setVolume(0.1);
		mediaPlayer1.setAutoPlay(true);
	}

	private void displayAllProfiles(){
		for(int i=0; i<getAllProfiles().size(); i++){
			String currentProfile = getAllProfiles().get(i);
			listOfProfiles.getItems().add(currentProfile.substring(0, currentProfile.length()-4));
		}
	}

	public void chooseXProfiles(int playerNum){
		labelSelectPlayers.setText("Please select " + playerNum + " players");
	}

	public boolean areAllProfilesChosen(){
		return count == numberOfPlayers;
	}

	public void chooseProfile(ActionEvent actionEvent) {
		String selectedProfile = listOfProfiles.getSelectionModel().getSelectedItem();
		Profile chosenOne = new Profile(selectedProfile);
		if(selectedProfile == null){
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
		if(areAllProfilesChosen()){
			chooseProfileButton.setDisable(true);
		}
	}

	@FXML
	private void backToStartScreen(ActionEvent actionEvent) {
		try {
			Parent startScreenParent = FXMLLoader.load(getClass().getResource("/scene/PlayerSelection.fxml"));
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
