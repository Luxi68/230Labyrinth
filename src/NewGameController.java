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

public class NewGameController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {
    }

    public void backToStartScreen(ActionEvent actionEvent) throws IOException {
        Parent startScreenParent = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
        Scene startScreenScene = new Scene(startScreenParent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(startScreenScene);
        window.show();
    }
}
