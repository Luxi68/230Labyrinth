import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL url = getClass().getResource("/scene/StartScreen.fxml");
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Labyrinth");
        primaryStage.setScene(new Scene(root, 640, 371));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}