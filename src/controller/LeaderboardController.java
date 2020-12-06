package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entity.Leaderboard;
import entity.Profile;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class LeaderboardController {

    public TableColumn rankCol;
    public TableColumn nameCol;
    public TableColumn gamePlayedCol;
    public TableColumn gameWonCol;
    public TableView<Profile> leaderbordTable;
    public Label tableHeader;
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
    }

    public void createLeaderboardKnossos(ActionEvent actionEvent) throws FileNotFoundException {
        int rank = 1;
        tableHeader.setText("User Leaderboard for Knossos Board");
        Leaderboard knossos = new Leaderboard(1);
        ObservableList<Profile> choices = FXCollections.observableArrayList(knossos.getLeaderboard());
        leaderbordTable.setItems(choices);

    }
    public void createLeaderboardMarathon(ActionEvent actionEvent) throws FileNotFoundException {
        int rank = 1;
        tableHeader.setText("User Leaderboard for Marathon Board");
        Leaderboard marathon = new Leaderboard(2);
        ObservableList<Profile> choices = FXCollections.observableArrayList(marathon.getLeaderboard());
        leaderbordTable.setItems(choices);

    }

    public void createLeaderboardSparta(ActionEvent actionEvent) throws FileNotFoundException {
        int rank = 1;
        tableHeader.setText("User Leaderboard for Sparta Board");
        Leaderboard sparta = new Leaderboard(3);
        ObservableList<Profile> choices = FXCollections.observableArrayList(sparta.getLeaderboard());
        leaderbordTable.setItems(choices);
    }


}
