package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

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

/**
 * PlayerSelectionController.java
 * This class is the controller class for the fxml file PlayerSelection.fxml
 * @author - Alberto Ortenzi
 */
public class PlayerSelectionController {

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

    /**
     * This method created a new media object and then passes it to a predefined mediaPlayer object.
     * it then sets the volume to a predetermined amount and sets the cycle count to indefinite to endlessly loop the music
     * until the users leaves the page.
     */
    public void backgroundMusic(){
        Media backgroundSound = new Media(new File("resources/sounds/playerSelectionBackground.wav").toURI().toString());
        mediaPlayer1 = new MediaPlayer(backgroundSound);
        mediaPlayer1.setVolume(0.1);
        mediaPlayer1.setAutoPlay(true);
    }

    /**
     * This method loads the start screen scene and plays a sound effect when the button is clicked
     * @param event - the action of clicking the button.
     */
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

    /**
     * This method passes a parameter to the newGame class by loading it's controller.
     * is passes the selected number of players through and plays a sound effect
     * it also stops the music to prevent it overlapping with the next stages music.
     * @param event - the button click
     * @throws IOException - in case the sound files don't exist
     */
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

    /**
     * This method passes a parameter to the newGame class by loading it's controller.
     * is passes the selected number of players through and plays a sound effect
     * it also stops the music to prevent it overlapping with the next stages music.
     * @param event - the button click
     * @throws IOException - in case the sound files don't exist
     */
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

    /**
     * This method passes a parameter to the newGame class by loading it's controller.
     * is passes the selected number of players through and plays a sound effect
     * it also stops the music to prevent it overlapping with the next stages music.
     * @param event - the button click
     * @throws IOException - in case the sound files don't exist
     */
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
