/**
 * The Class that setups the main game screen that players will be using to interact with the game.
 *
 * @author Lucy
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Opens the window for the game to be displayed onto.
 */
public class GameScreen extends Application {
	// The dimensions of the window
	private static final int WINDOW_WIDTH = 1000;
	private static final int WINDOW_HEIGHT = 600;

	/**
	 * Main method declaration necessary for class to run
	 * @param args - generic arguments that is run
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * The start method is called after the init method has returned,
	 * and after the system is ready for the application to begin running.
	 *
	 * @param primaryStage the primary stage for this application, onto which
	 *                     the application scene can be set.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			// Setting up the fxml
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScreen.fxml"));
			GameScreenController controller = loader.getController();
			controller.initData(WINDOW_WIDTH, WINDOW_HEIGHT);
			Pane root = loader.load();

			// Display the scene on the stage
			primaryStage.setTitle("Labyrinth");
			primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
			primaryStage.show();

		} catch (IOException e) {
			System.out.println("Error starting the primary stage.");
			e.printStackTrace();
		}
	}

}
