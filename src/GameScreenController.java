/**
 * Class that holds the actions and logic behind GameScreen.fxml
 *
 * @author Lucy
 */

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class GameScreenController implements Initializable {
	// The dimensions of the window
	private int windowWidth;
	private int windowHeight;

	@FXML
	public VBox sideBar;
	@FXML
	public HBox actionTracker;
	@FXML
	private BorderPane borderPane;
	@FXML
	private Circle actionTrackerDraw;
	@FXML
	private Circle actionTrackerPlay;
	@FXML
	private Circle actionTrackerMove;

	/**
	 * Initialises data necessary to setup game screen
	 *
	 * @param width  - preferred width of the window
	 * @param height - preferred height of window
	 */
	public void initData(int width, int height) {
		this.windowWidth = width;
		this.windowHeight = height;
	}

	/**
	 * Called to initialize a controller after its root element has been
	 * completely processed.
	 *
	 * @param location  The location used to resolve relative paths for the root object, or
	 *                  <tt>null</tt> if the location is not known.
	 * @param resources The resources used to localize the root object, or <tt>null</tt> if
	 *                  not known.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(() -> {
			borderPane.setPrefSize(windowWidth, windowHeight); // Throws a NullPointerException for some reason
			actionTrackerDraw.setFill(Paint.valueOf("Red"));
		});
		// Code that either loads past game or starts new game
	}

	/**
	 * Action that happens when player mouses over the Draw shape in the action tracker
	 */
	@FXML
	public void actionTrackerDrawMouseEnter() {
		actionTrackerDraw.setFill(Paint.valueOf("Grey"));
		actionTrackerPlay.setFill(Paint.valueOf("Red"));
	}

	/**
	 * Action that happens when player mouses over the Play shape in the action tracker
	 */
	@FXML
	public void actionTrackerPlayMouseEnter() {
		actionTrackerPlay.setFill(Paint.valueOf("Grey"));
		actionTrackerMove.setFill(Paint.valueOf("Red"));
	}

	/**
	 * Action that happens when player mouses over the Move shape in the action tracker
	 */
	@FXML
	public void actionTrackerMoveMouseEnter() {
		actionTrackerMove.setFill(Paint.valueOf("Grey"));
		actionTrackerDraw.setFill(Paint.valueOf("Red"));
	}
}
