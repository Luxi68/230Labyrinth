package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.Scanner;

public class NewGameController {

    public ChoiceBox<String> fileChoice;
	public Label labelSelectPlayers;
	public ListView<String> listOfProfiles;
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	public void initialize() {
		ObservableList<String> choices = FXCollections.observableArrayList(getAllLevelFilenames());
		fileChoice.setItems(choices);
		displayAllProfiles();
	}

	@FXML
	private void startGame(ActionEvent actionEvent) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene/GameScreen.fxml"));
			Parent gameScreenParent = loader.load();
			GameScreenController controller = loader.getController();

			// controller.initData(); TODO - needs to be edited to input stuff to screen

			Scene gameScreenScene = new Scene(gameScreenParent);
			Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
			window.setScene(gameScreenScene);
			window.show();
			window.setMaximized(true);
		} catch (IOException e) {
			System.out.println("Error starting the Game Screen from new game screen.");
			e.printStackTrace();
		}
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

	private void displayAllProfiles(){
		for(int i=0; i<getAllProfiles().size(); i++){
			String currentProfile = getAllProfiles().get(i);
			listOfProfiles.getItems().add(currentProfile.substring(0, currentProfile.length()-4));
		}
	}

	public void setLabel(String message){
		labelSelectPlayers.setText(message);
	}

	public void chooseXProfiles(int numberOfPlayers){

	}

	public void chooseProfile(ActionEvent actionEvent) {

	}

	@FXML
	private void backToStartScreen(ActionEvent actionEvent) {
		try {
			Parent startScreenParent = FXMLLoader.load(getClass().getResource("/scene/StartScreen.fxml"));
			Scene startScreenScene = new Scene(startScreenParent);
			Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
			window.setScene(startScreenScene);
			window.show();
		} catch (IOException e) {
			System.out.println("Error returning to the start screen from new game screen.");
			e.printStackTrace();
		}
	}
}
