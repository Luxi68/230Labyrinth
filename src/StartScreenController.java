import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartScreenController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {

    }
    public void goToProfileSelection(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent profileSelectionParent = FXMLLoader.load(getClass().getResource("ProfileSelection.fxml"));
        Scene profileSelectionScene = new Scene(profileSelectionParent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(profileSelectionScene);
        window.show();
    }
    public void goToNewGame(ActionEvent actionEvent) throws IOException {
        Parent newGameParent = FXMLLoader.load(getClass().getResource("NewGame.fxml"));
        Scene newGameScene = new Scene(newGameParent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(newGameScene);
        window.show();
    }
    public void goToLoadGame(ActionEvent actionEvent) throws IOException {
        Parent loadGameParent = FXMLLoader.load(getClass().getResource("LoadGame.fxml"));
        Scene loadGameScene = new Scene(loadGameParent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(loadGameScene);
        window.show();
    }
    public void closeApplication(ActionEvent actionEvent) {
        Platform.exit();
    }
}
