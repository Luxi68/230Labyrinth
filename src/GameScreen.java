import controller.GameScreenController;
import core.FileReader;
import entity.Profile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Class that opens the main game screen that players will be using to interact with the game.
 *
 * @author Lucy
 */
public class GameScreen extends Application {
	// The dimensions of the window
	private static final int WINDOW_WIDTH = 1200;
	private static final int WINDOW_HEIGHT = 630;

	/**
	 * Main method declaration necessary for class to run
	 *
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
			URL url = getClass().getResource("/scene/GameScreen.fxml");
			FXMLLoader loader = new FXMLLoader(url);
			Pane root = loader.load();
			GameScreenController controller = loader.getController();

			// Temporary data for testing
			ArrayList<Profile> tempProfiles = new ArrayList<>();
			Profile lucy = new Profile("Lucy");
			tempProfiles.add(lucy);
			Profile rhys = new Profile("Rhys");
			tempProfiles.add(rhys);

			// Reading the data from file into controller
			controller.initData(FileReader.readDataFile("resources/levels/placeholder.txt", tempProfiles));

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
