package controller;

import entity.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class that holds the actions and logic behind core.GameScreen.fxml
 *
 * @author Junjie, Rhys
 */
public class GameScreenController implements Initializable {
	// Game info
	private int windowWidth;
	private int windowHeight;
	// Game components
	private SilkBag silkBag;
	private Tile newTile;
	private String newTileType;
	// Game info
	private int totalPlayers;
	private Player currPlayer;
	private Player p1;
	private Player p2;
	private Player p3;
	// Game checks
	private boolean isTileDrawn;
	// Colour Scheme
	private Paint grey = Paint.valueOf("#3f443e");
	private Paint red = Paint.valueOf("#b53232");
	private Paint pink = Paint.valueOf("#c677b3");
	private Paint green = Paint.valueOf("#55b54c");
	private Paint gold = Paint.valueOf("#fdd14b");


	@FXML
	private BorderPane borderPane;
	@FXML
	private Circle actionTrackerDraw;
	@FXML
	private Circle actionTrackerPlay;
	@FXML
	private Circle actionTrackerMove;
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
	private Label p1Txt;
	@FXML
	private Label p2Txt;
	@FXML
	private Label p3Txt;
	@FXML
	private ImageView p1Img;
	@FXML
	private ImageView p2Img;
	@FXML
	private ImageView p3Img;
	@FXML
	public TextArea gameLog;

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
			gameLog.appendText("GAME START!\n" + currPlayer.getName() + "'s turn first.\n");
		});
		// TODO - Code that either loads past game or starts new game
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
	} // TODO - Actually integrate this

	/**
	 * Action that will display a new tile after the take tile button is clicked
	 */
	@FXML
	public void takeTileClick() {
		if (!isTileDrawn) {
			silkBagTileImg.setRotate(0);
			newTile = silkBag.drawTile();
			silkBagTileImg.setFill(new ImagePattern(newTile.getImage()));

			// Check the type of the tile for future use
			newTileType = newTile.getTileType();
			if (newTileType.equals("corner") || newTileType.equals("straight") || newTileType.equals("tee")) {
				newTileType = "floor";
				gameLog.appendText(currPlayer.getName() + " drew a Floor Tile!\n");
				isTileDrawn = true;
			} else if (newTileType.equals("fire") || newTileType.equals("ice") ||
					newTileType.equals("doubleMove") || newTileType.equals("backTrack")) {
				newTileType = "action";
				gameLog.appendText(currPlayer.getName() + " drew an Action Tile!\n");
				isTileDrawn = true;
			} else {
				gameLog.appendText("ERROR: Tile type " + newTileType + " is unknown, please take a different tile.\n");
			}
		} else {
			gameLog.appendText("ERROR: You cannot draw more than 1 tile per turn.\n");
		}
	};	// TODO - img resources

	/**
	 * Action that will rotate the tile nd its image if the rotate tile button is clicked.
	 * Will only work if the displayed tile is one of the floor tiles
	 */
	@FXML
	public void rotateTileClick() {
		// Checking if tile is one of the floor tiles
		if (newTileType.equals("floor")) {
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
			gameLog.appendText("Floor tile rotated 90 degrees.\n");

		} else {
			gameLog.appendText("ERROR: Cannot rotate non-floor tiles.\n");
		}
	}

	/**
	 * Wraps up the current players turn
	 */
	@FXML
	public void endTurnClick() {
		// Add tile to hand if tile is an action tile and remove from screen
		if (newTileType.equals("action")) {
			Action tempAction = (Action) newTile;
			currPlayer.addActionTile(tempAction);
			gameLog.appendText(currPlayer + "'s tile has been added to their hand.\n");
		}
		silkBagTileImg.setFill(Paint.valueOf("#3f443e"));

		// Change currPlayer to next player
		if (currPlayer.getOrder() == totalPlayers) {
			// TODO - method to get next player
		} else {
			// TODO - method to get next player
		}
		setupNextPlayer(currPlayer);
		gameLog.appendText("NEXT PLAYER\nIt's now " + currPlayer.getName() + "'s turn.\n");
	}

	/**
	 * Initialises data necessary to setup game
	 */
	public void initData() {
		// TODO - Need to link to newGameController
	}

	/**
	 * Method to setup the beginning of the game and initialise all the needed variables
	 */
	private void setupGame() {
		silkBag = new SilkBag();
		silkBag.addTile(new Floor("corner", new Image("corner.png"), false));
		silkBag.addTile(new Action("fire", new Image("fire.png")));
		currPlayer = new Player("Aries", 1, new Image("aries.png"));
		currPlayerFireImg.setImage(new Image("fire.png"));
		currPlayerIceImg.setImage(new Image("ice.png"));
		currPlayerDoubleMoveImg.setImage(new Image("doublemove.png"));
		currPlayerBacktrackImg.setImage(new Image("backtrack.png"));
		setupNextPlayer(currPlayer);
		// TODO - do properly
	}

	/**
	 * Setups the next player's hand including the correct number of each action tile
	 *
	 * @param player - player's hand that is being setup
	 */
	private void setupNextPlayer(Player player) {
		isTileDrawn = false;
		currPlayerTxt.setText(currPlayer.getName());

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
			imageView.setOpacity(1.0);
			label.setText("x" + num);
		} else {
			imageView.setOpacity(0.5);
			label.setText("None");
		}
	}
}
