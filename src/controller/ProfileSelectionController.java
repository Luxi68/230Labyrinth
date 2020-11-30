package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileSelectionController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	public void initialize() {

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
			System.out.println("Error returning to the main screen from profile selection.");
			e.printStackTrace();
		}
	}

    public void goToCreateProfile(ActionEvent actionEvent) throws IOException {
		Parent createProfileParent = FXMLLoader.load(getClass().getResource("/scene/CreateProfile.fxml"));
		Scene createProfilScene = new Scene(createProfileParent);
		Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
		window.setScene(createProfilScene);
		window.show();
    }
}
