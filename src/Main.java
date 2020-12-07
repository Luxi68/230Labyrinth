import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

/**
 * Main.java
 * This class starts the application loading the stage and the first scene.
 * @author - Alberto Ortenzi
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL url = getClass().getResource("/scene/StartScreen.fxml");
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("The First Olympian");
        primaryStage.setScene(new Scene(root, 640, 371));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}