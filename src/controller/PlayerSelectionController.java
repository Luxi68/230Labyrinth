package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
            Media buttonSound = new Media(new File("resources/sounds/button.wav").toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
            mediaPlayer.play();
        } catch (IOException e) {
            System.out.println("Error returning to the start screen from new game screen.");
            e.printStackTrace();
        }
    }

    @FXML
    void fourPlayersSelected(ActionEvent event) throws IOException {
        int playerNum = 4;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/scene/NewGame.fxml"));
        Parent newGameParent = loader.load();

        NewGameController newGameController = loader.getController();
        newGameController.chooseXProfiles(playerNum);
        newGameController.numberOfPlayers = 4;

        Scene newGameScene = new Scene(newGameParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(newGameScene);
        window.show();
        Media buttonSound = new Media(new File("resources/sounds/button.wav").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
        mediaPlayer.play();
    }

    @FXML
    void threePlayersSelected(ActionEvent event) throws IOException {
        int playerNum = 3;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/scene/NewGame.fxml"));
        Parent newGameParent = loader.load();

        NewGameController newGameController = loader.getController();
        newGameController.chooseXProfiles(playerNum);
        newGameController.numberOfPlayers = 3;

        Scene newGameScene = new Scene(newGameParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(newGameScene);
        window.show();
        Media buttonSound = new Media(new File("resources/sounds/button.wav").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
        mediaPlayer.play();
    }

    @FXML
    void twoPlayersSelected(ActionEvent event) throws IOException {
        int playerNum = 2;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/scene/NewGame.fxml"));
        Parent newGameParent = loader.load();

        NewGameController newGameController = loader.getController();
        newGameController.chooseXProfiles(playerNum);
        newGameController.numberOfPlayers = 2;

        Scene newGameScene = new Scene(newGameParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(newGameScene);
        window.show();
        Media buttonSound = new Media(new File("resources/sounds/button.wav").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
        mediaPlayer.play();
    }
}
