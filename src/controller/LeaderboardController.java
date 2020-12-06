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

    public void createLeaderboardKnossos(ActionEvent actionEvent) throws FileNotFoundException {
        int rank = 1;
        tableHeader.setText("User Leaderboard for Knossos Board");
        Leaderboard knossos = new Leaderboard(1);
        ObservableList<Profile> choices = FXCollections.observableArrayList(knossos.getLeaderboard());
        leaderbordTable.setItems(choices);
        Media buttonSound = new Media(new File("resources/sounds/button.wav").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
        mediaPlayer.play();

    }
    public void createLeaderboardMarathon(ActionEvent actionEvent) throws FileNotFoundException {
        int rank = 1;
        tableHeader.setText("User Leaderboard for Marathon Board");
        Leaderboard marathon = new Leaderboard(2);
        ObservableList<Profile> choices = FXCollections.observableArrayList(marathon.getLeaderboard());
        leaderbordTable.setItems(choices);
        Media buttonSound = new Media(new File("resources/sounds/button.wav").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
        mediaPlayer.play();

    }

    public void createLeaderboardSparta(ActionEvent actionEvent) throws FileNotFoundException {
        int rank = 1;
        tableHeader.setText("User Leaderboard for Sparta Board");
        Leaderboard sparta = new Leaderboard(3);
        ObservableList<Profile> choices = FXCollections.observableArrayList(sparta.getLeaderboard());
        leaderbordTable.setItems(choices);
        Media buttonSound = new Media(new File("resources/sounds/button.wav").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
        mediaPlayer.play();
    }
    public void backgroundMusic(){
        Media backgroundSound = new Media(new File("resources/sounds/profileSelectionBackground.wav").toURI().toString());
        mediaPlayer1 = new MediaPlayer(backgroundSound);
        mediaPlayer1.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer1.setVolume(0.1);
        mediaPlayer1.setAutoPlay(true);
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
