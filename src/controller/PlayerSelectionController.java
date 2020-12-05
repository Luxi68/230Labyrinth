package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PlayerSelectionController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {

    }

    @FXML
    void backToStartScreen(ActionEvent event) {
        try{
            Parent startScreenParent = FXMLLoader.load(getClass().getResource("/scene/StartScreen.fxml"));
            Scene startScreenScene = new Scene(startScreenParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(startScreenScene);
            window.show();
        } catch (IOException e) {
            System.out.println("Error returning to the start screen from new game screen.");
            e.printStackTrace();
        }
    }

    @FXML
    void fourPlayersSelected(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/scene/NewGame.fxml"));
        Parent root = (Parent) loader.load();
        NewGameController bob = loader.getController();
        bob.setLabel("Please Select 4 Players");
        Stage stage = new Stage();
        stage.setScene(new Scene (root));
        stage.show();
    }

    @FXML
    void threePlayersSelected(ActionEvent event) {
        try {
            Parent startScreenParent = FXMLLoader.load(getClass().getResource("/scene/NewGame.fxml"));
            Scene startScreenScene = new Scene(startScreenParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(startScreenScene);
            window.show();
        } catch (IOException e) {
            System.out.println("Error returning to the start screen from new game screen.");
            e.printStackTrace();
        }
    }

    @FXML
    void twoPlayersSelected(ActionEvent event) {
        try {
            Parent startScreenParent = FXMLLoader.load(getClass().getResource("/scene/NewGame.fxml"));
            Scene startScreenScene = new Scene(startScreenParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(startScreenScene);
            window.show();
        } catch (IOException e) {
            System.out.println("Error returning to the start screen from new game screen.");
            e.printStackTrace();
        }
    }
}
