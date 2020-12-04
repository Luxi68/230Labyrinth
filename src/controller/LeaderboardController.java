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
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

public class LeaderboardController {

    public TableColumn rankCol;
    public TableColumn nameCol;
    public TableColumn gamePlayedCol;
    public TableColumn gameWonCol;
    public TableColumn winRateCol;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void backToProfileSelection(ActionEvent event) {
        try {
            Parent profileSelectionParent = FXMLLoader.load(getClass().getResource("/scene/ProfileSelection.fxml"));
            Scene profileSelectionScene = new Scene(profileSelectionParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(profileSelectionScene);
            window.show();
        } catch (IOException e) {
            System.out.println("Error returning to the profile selection from leaderboard");
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        

    }
}
