package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class PlayerSelectionController {

    public Slider volumeSlider;
    MediaPlayer mediaPlayer1;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {
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

    public void backgroundMusic(){
        Media backgroundSound = new Media(new File("resources/sounds/playerSelectionBackground.wav").toURI().toString());
        mediaPlayer1 = new MediaPlayer(backgroundSound);
        mediaPlayer1.setVolume(0.1);
        mediaPlayer1.setAutoPlay(true);
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
        mediaPlayer1.stop();
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
        mediaPlayer1.stop();
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
        mediaPlayer1.stop();
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
