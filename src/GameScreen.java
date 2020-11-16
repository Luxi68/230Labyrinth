import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

/**
 * Opens the window for the game to be displayed onto.
 */
public class GameScreen extends Application {
	// The dimensions of the window
	private static final int WINDOW_WIDTH = 600;
	private static final int WINDOW_HEIGHT = 400;

	// The dimensions of the canvas
	private static final int CANVAS_WIDTH = 400;
	private static final int CANVAS_HEIGHT = 400;

	// The canvas in the GUI. This needs to be a global variable
	// (in this setup) as we need to access it in different methods.
	// We could use FXML to place code in the controller instead.
	private Canvas canvas;

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * The main entry point for all JavaFX applications.
	 * The start method is called after the init method has returned,
	 * and after the system is ready for the application to begin running.
	 *
	 * @param primaryStage the primary stage for this application, onto which
	 *                     the application scene can be set. The primary stage will be embedded in
	 *                     the browser if the application was launched as an applet.
	 *                     Applications may create other stages, if needed, but they will not be
	 *                     primary stages and will not be embedded in the browser.
	 */
	@Override
	public void start(Stage primaryStage) {
		// Build the GUI
		Pane root = buildGUI();

		// Create a scene from the GUI
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		// Display the scene on the stage
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Create the GUI.
	 *
	 * @return The panel that contains the created GUI.
	 */
	private Pane buildGUI() {
		// Create top-level panel that will hold all GUI
		BorderPane root = new BorderPane();

		// Create canvas
		canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		root.setCenter(canvas);

		// Create a sidebar with some nice padding and spacing
		VBox sidebar = new VBox();
		sidebar.setSpacing(10);
		sidebar.setPadding(new Insets(10, 10, 10, 10));
		root.setLeft(sidebar);

		Button changeRed = new Button("Red");
		Button changeBlue = new Button("Blue");
		Button changeGreen = new Button("Green");
		Button changeYellow = new Button("Yellow");
		sidebar.getChildren().addAll(changeRed, changeBlue, changeGreen, changeYellow);

		// Allow the buttons to grow in width to fill the panel space
		changeRed.setMaxWidth(Double.MAX_VALUE);
		changeBlue.setMaxWidth(Double.MAX_VALUE);
		changeGreen.setMaxWidth(Double.MAX_VALUE);
		changeYellow.setMaxWidth(Double.MAX_VALUE);

		// Create button behavior
		changeRed.setOnAction(e -> changeColourRed());
		changeBlue.setOnAction(e -> changeColourBlue());
		changeGreen.setOnAction(e -> changeColourGreen());
		changeYellow.setOnAction(e -> changeColourYellow());

		StackPane background = new StackPane();
		background.getAlignment();


		return root;
	}

	/**
	 * Changes the canvas colour to red.
	 */
	private void changeColourRed() {

	}

	/**
	 * Changes the canvas colour to blue.
	 */
	private void changeColourBlue() {
		// Create a new random number generator
		Random r = new Random();

		// Create a new coordinate
		int x = r.nextInt(CANVAS_WIDTH);
		int y = r.nextInt(CANVAS_HEIGHT);

		// Access the graphic context of the canvas
		GraphicsContext gc = canvas.getGraphicsContext2D();

		// Set the fill color to Red
		gc.setFill(Color.BLUE);

		// Draw a circle at the coordinates
		gc.fillRect(x, y, 20, 20);
	}

	/**
	 * Changes the canvas colour to green.
	 */
	private void changeColourGreen() {
	}

	/**
	 * Changes the canvas colour to yellow.
	 */
	private void changeColourYellow() {
	}
}
