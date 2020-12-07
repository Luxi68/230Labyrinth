package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entity.Leaderboard;
import entity.Profile;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * LeaderboardController.java. This class is the controller for the fxml file Leaderboard.fxml
 * @author - Alberto Ortenzi
 */
public class LeaderboardController {

    public TableColumn rankCol;
    public MediaPlayer mediaPlayer1;
    public TableColumn nameCol;
    public TableColumn gamePlayedCol;
    public TableColumn gameWonCol;
    public TableView<Profile> leaderbordTable;
    public Label tableHeader;
    public Slider volumeSlider;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    /**
     * This method is called everytime the class is initialised. It executes the backgroundMusic method
     * which starts the background music and it also sets up the volume control slider. It also sets the cell value factory
     * for each coloumn  of the table view so that it knows what value is to be inputted.
     * It also implements a method to return an autoincrement value in the rankCol to represent the rank
     */
    @FXML
    void initialize() {
        rankCol.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("1"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Profile, String>("playerName"));
        gamePlayedCol.setCellValueFactory(new PropertyValueFactory<Profile, String>("numberOfWins"));
        gameWonCol.setCellValueFactory(new PropertyValueFactory<Profile, String>("numberOfGamesPlayed"));
        rankCol.setCellFactory( new Callback<TableColumn, TableCell>()
        {
            @Override
            public TableCell call( TableColumn p )
            {
                return new TableCell()
                {
                    @Override
                    public void updateItem(Object item, boolean empty )
                    {
                        super.updateItem( item, empty );
                        setGraphic( null );
                        setText( empty ? null : getIndex() + 1 + "" );
                    }
                };
            }
        });
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
     * This method loads the profile selection scene and plays a sound effect when the button is clicked
     * @param event - the action of clicking the button.
     */
    @FXML
    void backToProfileSelection(ActionEvent event) {
        try {
            Parent profileSelectionParent = FXMLLoader.load(getClass().getResource("/scene/ProfileSelection.fxml"));
            Scene profileSelectionScene = new Scene(profileSelectionParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(profileSelectionScene);
            window.show();
            Media buttonSound = new Media(new File("resources/sounds/button.wav").toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
            mediaPlayer.play();
        } catch (IOException e) {
            System.out.println("Error returning to the profile selection from leaderboard");
            e.printStackTrace();
        }
        mediaPlayer1.stop();
    }

    /**
     * This method creates a new leaderboard object using the appropriate integer to represent each board.
     * It then creates an observable list from the leaderboard arraylist paramter and outputs said data to the table view-
     * also it plays a sound effect on button click.
     * @param actionEvent - the action of clicking the button
     * @throws FileNotFoundException - if the desired file doesn't exist
     */
    public void createLeaderboardKnossos(ActionEvent actionEvent) throws FileNotFoundException {
        tableHeader.setText("User Leaderboard for Knossos Board");
        Leaderboard knossos = new Leaderboard(1);
        ObservableList<Profile> choices = FXCollections.observableArrayList(knossos.getLeaderboard());
        leaderbordTable.setItems(choices);
        Media buttonSound = new Media(new File("resources/sounds/button.wav").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
        mediaPlayer.play();

    }
    /**
     * This method creates a new leaderboard object using the appropriate integer to represent each board.
     * It then creates an observable list from the leaderboard arraylist paramter and outputs said data to the table view-
     * also it plays a sound effect on button click.
     * @param actionEvent - the action of clicking the button
     * @throws FileNotFoundException - if the desired file doesn't exist
     */
    public void createLeaderboardMarathon(ActionEvent actionEvent) throws FileNotFoundException {
        tableHeader.setText("User Leaderboard for Marathon Board");
        Leaderboard marathon = new Leaderboard(2);
        ObservableList<Profile> choices = FXCollections.observableArrayList(marathon.getLeaderboard());
        leaderbordTable.setItems(choices);
        Media buttonSound = new Media(new File("resources/sounds/button.wav").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
        mediaPlayer.play();

    }
    /**
     * This method creates a new leaderboard object using the appropriate integer to represent each board.
     * It then creates an observable list from the leaderboard arraylist paramter and outputs said data to the table view-
     * also it plays a sound effect on button click.
     * @param actionEvent - the action of clicking the button
     * @throws FileNotFoundException - if the desired file doesn't exist
     */
    public void createLeaderboardSparta(ActionEvent actionEvent) throws FileNotFoundException {
        tableHeader.setText("User Leaderboard for Sparta Board");
        Leaderboard sparta = new Leaderboard(3);
        ObservableList<Profile> choices = FXCollections.observableArrayList(sparta.getLeaderboard());
        leaderbordTable.setItems(choices);
        Media buttonSound = new Media(new File("resources/sounds/button.wav").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
        mediaPlayer.play();
    }

    /**
     * This method created a new media object and then passes it to a predefined mediaPlayer object.
     * it then sets the volume to a predetermined amount and sets the cycle count to indefinite to endlessly loop the music
     * until the users leaves the page.
     */
    public void backgroundMusic(){
        Media backgroundSound = new Media(new File("resources/sounds/profileSelectionBackground.wav").toURI().toString());
        mediaPlayer1 = new MediaPlayer(backgroundSound);
        mediaPlayer1.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer1.setVolume(0.1);
        mediaPlayer1.setAutoPlay(true);
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
    public void openGameInstructions(ActionEvent actionEvent) {
        Alert errorInfo = new Alert(Alert.AlertType.INFORMATION);
        errorInfo.setTitle("Game Instructions");
        errorInfo.setHeaderText("How to play the game");
        errorInfo.setContentText("You have not selected a player please do and try again");
        errorInfo.show();
    }


}
