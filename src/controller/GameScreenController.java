package controller;

import entity.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
// TODO - img resources

/**
 * Class that holds the actions and logic behind GameScreen.fxml
 *
 * @author Junjie, Rhys
 */
public class GameScreenController implements Initializable {
	// Game components
	private Board gameBoard;
	private SilkBag silkBag;
	private Tile silkBagTile;
	private String silkBagTileType;
	private Action actionToUse;
	// Game info
	private int totalPlayers;
	private Player currPlayer;
	private Player queuePlayer1;
	private Player queuePlayer2;
	private Player queuePlayer3;
	// TODO - Game checks
	private boolean isTileDrawn;
	private boolean isFloorPlayed;
	// Colour Scheme
	private Paint grey = Paint.valueOf("#3f443e");
	private Paint red = Paint.valueOf("#b53232");
	private Paint pink = Paint.valueOf("#c677b3");
	private Paint green = Paint.valueOf("#55b54c");
	private Paint gold = Paint.valueOf("#fdd14b");

	@FXML
	private BorderPane borderPane;
	@FXML
	private StackPane borderPaneCentre;
	@FXML
	private Circle actionTrackerDraw;
	@FXML
	private Circle actionTrackerPlay;
	@FXML
	private Circle actionTrackerMove;
	@FXML
	private Rectangle silkBagTileImg;
	@FXML
	private Button silkBagTileRotate;
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
	private Label q1Txt;
	@FXML
	private Label q2Txt;
	@FXML
	private Label q3Txt;
	@FXML
	private ImageView currPlayerImg;
	@FXML
	private ImageView q1Img;
	@FXML
	private ImageView q2Img;
	@FXML
	private ImageView q3Img;
	@FXML
	private TextArea gameLog;

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
			setupGame();
			setupBoard(gameBoard);

			gameLog.appendText("GAME START!\n");
			startNextTurn();
		});
		// TODO - Code that either loads past game or starts new game
	}

	/**
	 * Initialises data necessary to setup game
	 */
	public void initData() {
		// TODO - Need to link to newGameController
		// Silk Bag; Players; Board
	}

	/**
	 * TODO - delete once initialise is setup
	 * Method to setup the beginning of the game and initialise all the needed variables
	 */
	private void setupGame() {
		actionTrackerDraw.setFill(Paint.valueOf("Red"));
		silkBag = new SilkBag();
		silkBag.addTile(new Floor("corner", new Image("/assets/corner.png"), false));
		silkBag.addTile(new Action("fire", new Image("/assets/fire.png")));
		currPlayer = new Player("Aries", new Image("/assets/aries.png"), "#b53232", 0, 0);
		queuePlayer1 = new Player("Apollo", new Image("/assets/apollo.png"), "#c677b3", 0, 0);
//		queuePlayer2 = new Player("Artemis", new Image("/assets/artemis.png"), "#55b54c", 0, 0);
//		queuePlayer3 = new Player("Aphrodite", new Image("/assets/aphrodite.png"), "#fdd14b", 0, 0);
		totalPlayers = 2;
		currPlayerFireImg.setImage(new Image("/assets/fire.png"));
		currPlayerIceImg.setImage(new Image("/assets/ice.png"));
		currPlayerDoubleMoveImg.setImage(new Image("/assets/doublemove.png"));
		currPlayerBacktrackImg.setImage(new Image("/assets/backtrack.png"));
		gameBoard = new Board(6, 6);
	}

	/**
	 *
	 * @param boardObj
	 */
	private void setupBoard(Board boardObj) {
		// Make board for gui
		GridPane boardImg = new GridPane();
		boardImg.setPrefSize(500, 500);
		//Padding around the board
		boardImg.setPadding(new Insets(10, 10, 10, 10));
		// Setting gaps between tiles
		boardImg.setVgap(10);
		boardImg.setHgap(10);

		final int BOARD_ROWS = boardObj.getHEIGHT();
		final int BOARD_COLUMNS = boardObj.getLENGTH();

		for (int i = 0; i < BOARD_ROWS; i++) {
			for (int j = 0; j < BOARD_COLUMNS; j++) {

				Rectangle tile = new Rectangle(50, 50);
				tile.setFill(Color.WHITE);
				tile.setStroke(Color.BLACK);

				GridPane.setRowIndex(tile, i);
				GridPane.setColumnIndex(tile, j);
				boardImg.getChildren().addAll(tile);
				tile.setOnMouseClicked(event -> {
					if (silkBagTileType.equalsIgnoreCase("floor")) {
						if (!isFloorPlayed) {
							tile.setFill(new ImagePattern(silkBagTile.getImage()));
							tile.setRotate(silkBagTileImg.getRotate());
							isFloorPlayed = true;
						} else {
							gameLog.appendText("ERROR: Floor tile has already been placed on the board.\n");
						}
					} else {
						gameLog.appendText("ERROR: Cannot slide action tile onto game board.\n");
					}
				});
			}
		}
		borderPaneCentre.getChildren().add(boardImg);
	}

	/**
	 * Loads the next turn of the game
	 */
	private void startNextTurn() {
		setupNextPlayerDisplay(currPlayer);
		updatePlayerQueue(q1Img, q1Txt, queuePlayer1);
		if (totalPlayers >= 3) {
			updatePlayerQueue(q2Img, q2Txt, queuePlayer2);
		}
		if (totalPlayers >= 4) {
			updatePlayerQueue(q3Img, q3Txt, queuePlayer3);
		}
		gameLog.appendText("It's now " + currPlayer.getName() + "'s turn.\n");
	}

	/**
	 * Takes the current player and moves them to the back of the queue
	 */
	private void updatePlayerOrder() {
		Player tempPlayer = queuePlayer1;
		switch (totalPlayers) {
			case 2:
				queuePlayer1 = currPlayer;
				break;
			case 3:
				queuePlayer1 = queuePlayer2;
				queuePlayer2 = currPlayer;
				break;
			case 4:
				queuePlayer1 = queuePlayer2;
				queuePlayer2 = queuePlayer3;
				queuePlayer3 = currPlayer;
				break;
			default:
				gameLog.appendText("ERROR: Player order disrupted. Please reload from last save file.");
				break;
		}
		currPlayer = tempPlayer;
	}

	/**
	 * Setups the next player's hand including the correct number of each action tile
	 *
	 * @param player - player's hand that is being setup
	 */
	private void setupNextPlayerDisplay(Player player) {
		currPlayerImg.setImage(currPlayer.getImage());
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
	 * Setups the player tracker with the correct player details
	 *
	 * @param img    - The imageview displaying the image of the player
	 * @param txt    - The label displaying the name assigned to the player
	 * @param player - The player who is being displayed
	 */
	private void updatePlayerQueue(ImageView img, Label txt, Player player) {
		img.setImage(player.getImage());
		txt.setText(player.getName());
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

	/**
	 * Action that happens when player mouse over the Draw shape in the action tracker
	 */
	@FXML
	private void actionTrackerDrawMouseEnter() {
		actionTrackerDraw.setFill(Paint.valueOf("Grey"));
		actionTrackerPlay.setFill(Paint.valueOf("Red"));
	}

	/**
	 * Action that happens when player mouse over the Play shape in the action tracker
	 */
	@FXML
	private void actionTrackerPlayMouseEnter() {
		actionTrackerPlay.setFill(Paint.valueOf("Grey"));
		actionTrackerMove.setFill(Paint.valueOf("Red"));
	}

	/**
	 * Action that happens when player mouse over the Move shape in the action tracker
	 */
	@FXML
	private void actionTrackerMoveMouseEnter() {
		actionTrackerMove.setFill(Paint.valueOf("Grey"));
		actionTrackerDraw.setFill(Paint.valueOf("Red"));
	} // TODO - Actually integrate this

	/**
	 * Button that will display a new tile after the take tile button is clicked
	 */
	@FXML
	private void takeTileClick() {
		if (!isTileDrawn) {
			silkBagTileImg.setRotate(0);
			silkBagTile = silkBag.drawTile();
			silkBagTileImg.setFill(new ImagePattern(silkBagTile.getImage()));

			// Check the type of the tile for future use
			silkBagTileType = silkBagTile.getTileType();
			if (silkBagTileType.equals("corner") || silkBagTileType.equals("straight") || silkBagTileType.equals("tee")) {
				silkBagTileType = "floor";
				gameLog.appendText(currPlayer.getName() + " drew a Floor Tile!\n");
				silkBagTileRotate.setDisable(false);
				isTileDrawn = true;
			} else if (silkBagTileType.equals("fire") || silkBagTileType.equals("ice") ||
					silkBagTileType.equals("doubleMove") || silkBagTileType.equals("backTrack")) {
				silkBagTileType = "action";
				gameLog.appendText(currPlayer.getName() + " drew an Action Tile!\n");
				isTileDrawn = true;
			} else {
				gameLog.appendText("ERROR: Tile type " + silkBagTileType + " is unknown, please take a different tile.\n");
			}
		} else {
			gameLog.appendText("ERROR: You cannot draw more than 1 tile per turn.\n");
		}
	}

	/**
	 * Button that will rotate the tile and its image if the rotate tile button is clicked.
	 * Will only work if the displayed tile is one of the floor tiles
	 */
	@FXML
	private void rotateTileClick() {
		// Checking if tile is one of the floor tiles
		if (silkBagTileType.equals("floor")) { // TODO - sort out rest of button disable so i can delete this
			Floor tempFloor = (Floor) silkBagTile;
			tempFloor.rotate();

			// Rotates the image since it is not 'locked' to tile
			double rotation = silkBagTileImg.getRotate();
			silkBagTileImg.setRotate(tempFloor.getRotation());
			gameLog.appendText("Floor tile rotated 90 degrees.\n");

		} else {
			gameLog.appendText("ERROR: Cannot rotate non-floor tiles.\n");
		}
	}

	/**
	 * Button that will use a fire action tile
	 */
	@FXML
	private void fireClick() {
		useActionTile("fire");
	}

	/**
	 * Button that will use a ice action tile
	 */
	@FXML
	private void iceClick() {
		useActionTile("ice");
	}

	/**
	 * Button that will use a double move action tile
	 */
	@FXML
	private void doubleMoveClick() {
		useActionTile("doubleMove");
	}

	/**
	 * Button that will use a backtrack action tile
	 */
	@FXML
	private void backtrackClick() {
		useActionTile("backTrack");
	}

	/**
	 * Sets the action that will be used on this turn
	 *
	 * @param actionType - String of the action type
	 */
	private void useActionTile(String actionType) {
		try {
			actionToUse = currPlayer.playActionTile(actionType);
			currPlayerFireTxt.setText("Using"); // TODO - Figure out how to change this when others are pressed instead
		} catch (NullPointerException e) {
			gameLog.appendText(e.getMessage() + "\n");
		}
	}

	/**
	 * Button that will wraps up the current players turn and start the next player's turn
	 */
	@FXML
	private void endTurnClick() {
		if (!isTileDrawn) {
			gameLog.appendText("ERROR: New tile has not been drawn. Cannot end your turn yet.\n");
		} else if (!isFloorPlayed) {
			gameLog.appendText("ERROR: Floor tile has not been placed on the board. Please place your floor tile\n");
		} else {
			// Add tile to hand if tile is an action tile and remove from screen
			if (silkBagTileType.equals("action")) {
				Action tempAction = (Action) silkBagTile;
				currPlayer.addActionTile(tempAction);
				gameLog.appendText(currPlayer.getName() + "'s tile has been added to their hand.\n");
			}
			silkBagTileImg.setFill(Paint.valueOf("#3f443e"));

			// Resetting game state
			silkBagTileRotate.setDisable(true);
			isTileDrawn = false;
			isFloorPlayed = false;

			updatePlayerOrder();
			gameLog.appendText("NEXT PLAYER\n");
			startNextTurn();
		}
	}

}
