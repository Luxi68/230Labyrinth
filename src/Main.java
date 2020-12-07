import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Main.java
 * This class starts the application loading the stage and the first scene.
 *
 * @author - Alberto Ortenzi
 */
public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		URL url = getClass().getResource("/scene/StartScreen.fxml");
		Parent root = FXMLLoader.load(url);
		root.setStyle("-fx-background-image: url('assets/mount.png');" + "-fx-background-size: cover");
		primaryStage.setTitle("The First Olympian");
		primaryStage.getIcons().add(new Image("assets/olympian.png"));
		primaryStage.setScene(new Scene(root, 640, 371));
		primaryStage.show();
	}
}