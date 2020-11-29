/**
 * Class that holds the actions and logic behind GameScreen.fxml
 *
 * @author Junjie, Rhys
 */

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class GameScreenController implements Initializable {
	// The dimensions of the window
	private int windowWidth;
	private int windowHeight;
	// Game components
	private SilkBag silkBag;
	private Tile newTile;
	// Game info
	private int totalPlayers;
	private Player currPlayer;

	@FXML
	private Rectangle silkBagTileImg;
	@FXML
	private ImageView currPlayerFireImg;
	@FXML
	private ImageView currPlayerIceImg;
	@FXML
	private ImageView currPlayerDoubleMoveImg;
	@FXML
	private ImageView currPlayerBacktrackImg;
	@FXML
	private Label currPlayerFireTxt;
	@FXML
	private Label currPlayerIceTxt;
	@FXML
	private Label currPlayerDoubleMoveTxt;
	@FXML
	private Label currPlayerBacktrackTxt;
	@FXML
	private Text currPlayerTxt;
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
	} // TODO -

	/**
	 * Action that will display a new tile after the take tile button is clicked
	 */
	@FXML
	public void takeTileClick() {
		silkBagTileImg.setRotate(0);
		newTile = silkBag.drawTile();
		silkBagTileImg.setFill(new ImagePattern(newTile.getImage()));
	} // TODO - img resources

	/**
	 * Action that will rotate the tile nd its image if the rotate tile button is clicked.
	 * Will only work if the displayed tile is one of the floor tiles
	 */
	@FXML
	public void rotateTileClick() {
		// Checking if tile is one of the floor tiles
		String tileType = newTile.getTileType();
		if (tileType.equals("corner") || tileType.equals("straight") || tileType.equals("tee")) {
			Floor tempFloor = (Floor) newTile;
			tempFloor.rotate();

			// Rotates the image since it is not 'locked' to tile
			double rotation = silkBagTileImg.getRotate();
			if (rotation == 0) {
				silkBagTileImg.setRotate(90);
			} else if (rotation == 90) {
				silkBagTileImg.setRotate(180);
			} else if (rotation == 180) {
				silkBagTileImg.setRotate(270);
			} else {
				silkBagTileImg.setRotate(0);
			}

		} else {
			System.out.println("error type wrong");
			// TODO - find somewhere to print error message
		}
	}

	/**
	 * Wraps up the current players turn
	 */
	@FXML
	public void endTurnClick() {
		silkBagTileImg.setFill(Paint.valueOf("#3f443e"));
		if (currPlayer.getOrder() == totalPlayers) {
			// TODO - method to get next player
		} else {
			// TODO - method to get next player
		}
		currPlayerTxt.setText(currPlayer.getName());
		setupPlayerHand(currPlayer);
	}

	/**
	 * Method to setup the beginning of the game and initialise all the needed variables.
	 */
	private void setupGame() {
		silkBag = new SilkBag();
		silkBag.addTile(new Floor("corner", new Image("tileBaseCorner.png"), false));
		silkBag.addTile(new Floor("corner", new Image("aries.png"), false));
		currPlayer = new Player("Aries", 1, new Image("aries.png"));
		currPlayerFireImg.setImage(new Image("fire.png"));
		currPlayerIceImg.setImage(new Image("ice.png"));
		currPlayerDoubleMoveImg.setImage(new Image("doublemove.png"));
		currPlayerBacktrackImg.setImage(new Image("backtrack.png"));
		// TODO - do properly
	}

	/**
	 * TODO
	 * @param player
	 */
	private void setupPlayerHand(Player player) {
		int fire = 0;
		int ice = 0;
		int backTrack = 0;
		int doubleMove = 0;

		for (Action actionTile : currPlayer.getHand()) {
			switch (actionTile.getTileType()) {
				case ("fire"):
					fire++;
					break;
				case ("ice"):
					ice++;
					break;
				case ("doubleMove"):
					doubleMove++;
					break;
				case ("backTrack"):
					backTrack++;
					break;
				default:
					break;
			}
		}
		setupActionNum(currPlayerFireImg, currPlayerFireTxt, fire);
		setupActionNum(currPlayerIceImg, currPlayerIceTxt, ice);
		setupActionNum(currPlayerDoubleMoveImg, currPlayerDoubleMoveTxt, doubleMove);
		setupActionNum(currPlayerBacktrackImg, currPlayerBacktrackTxt, backTrack);
	}

	/**
	 * Method to update the number of each action tile in the players hand
	 *
	 * @param imageView - the Image of the action tile
	 * @param label     - the text that displays the number of action tiles in the hand
	 * @param num       - the number of action tiles in the hand
	 */
	private void setupActionNum(ImageView imageView, Label label, int num) {
		if (num > 0) {
			imageView.setOpacity(100);
			label.setText("x" + num);
		} else {
			imageView.setOpacity(50);
			label.setText("None");
		}
	}
}
