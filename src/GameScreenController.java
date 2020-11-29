/**
 * Class that holds the actions and logic behind GameScreen.fxml
 *
 * @author Lucy
 */

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class GameScreenController implements Initializable {
	// The dimensions of the window
	private int windowWidth;
	private int windowHeight;
	// Game companents
	private SilkBag silkBag;
	private Tile newTile;

	@FXML
	private BorderPane borderPane;
	@FXML
	private Circle actionTrackerDraw;
	@FXML
	private Circle actionTrackerPlay;
	@FXML
	private Circle actionTrackerMove;
	@FXML
	public Rectangle newTileImg;

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
			borderPane.setPrefSize(windowWidth, windowHeight);
			setupGame();
			actionTrackerDraw.setFill(Paint.valueOf("Red"));
		});
		// Code that either loads past game or starts new game
	}

	/**
	 * Action that happens when player mouse over the Draw shape in the action tracker
	 */
	@FXML
	public void actionTrackerDrawMouseEnter() {
		actionTrackerDraw.setFill(Paint.valueOf("Grey"));
		actionTrackerPlay.setFill(Paint.valueOf("Red"));
	}

	/**
	 * Action that happens when player mouse over the Play shape in the action tracker
	 */
	@FXML
	public void actionTrackerPlayMouseEnter() {
		actionTrackerPlay.setFill(Paint.valueOf("Grey"));
		actionTrackerMove.setFill(Paint.valueOf("Red"));
	}

	/**
	 * Action that happens when player mouse over the Move shape in the action tracker
	 */
	@FXML
	public void actionTrackerMoveMouseEnter() {
		actionTrackerMove.setFill(Paint.valueOf("Grey"));
		actionTrackerDraw.setFill(Paint.valueOf("Red"));
	}

	//		TODO - img resources
	@FXML
	public void takeTileClicked() {
		newTile = silkBag.drawTile();
		ImageView newTileImg = new ImageView();
		newTileImg.setImage(newTile.getImage());
	}

	@FXML
	public void rotateTileClicked() {
		if (newTile.getTileType().equals("Floor")) {
			Floor tempFloor = (Floor) newTile;
			tempFloor.rotate();
			newTileImg.setRotate(90);
		} else {
			System.out.println("error type wrong");
			// TODO - find somewhere to print error message
		}
	}

	private void setupGame() {
		silkBag = new SilkBag();
		silkBag.addTile(new Action("fire"));
		silkBag.addTile(new Floor("floor", false, true, true, false,
				false, false, false, false, "corner"));
		// TODO - how in general
	}
}
