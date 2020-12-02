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
	private Action actionToUse;
	// Game info
	private int turn;
	private int boardRows;
	private int boardColumns;
	private Rectangle[][] boardImg; // A 2D array so the tiles on the board can be referenced
	private int totalPlayers;
	private Player currPlayer;
	private Player queuePlayer1;
	private Player queuePlayer2;
	private Player queuePlayer3;
	// Game checks TODO
	private boolean isNewTileAction;
	// Colour Scheme
	private final Paint GREY = Paint.valueOf("#3f443e");
//	private Paint red = Paint.valueOf("#b53232");
//	private Paint pink = Paint.valueOf("#c677b3");
//	private Paint green = Paint.valueOf("#55b54c");
//	private Paint gold = Paint.valueOf("#fdd14b");

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
	private Button takeSilkBagTileButton;
	@FXML
	private Rectangle silkBagTileImg;
	@FXML
	private Button silkBagTileRotate;
	@FXML
	private Text currPlayerTxt;
	@FXML
	private ImageView currPlayerImg;
	@FXML
	private Label currPlayerFireTxt;
	@FXML
	private Label currPlayerIceTxt;
	@FXML
	private Label currPlayerDoubleMoveTxt;
	@FXML
	private Label currPlayerBacktrackTxt;
	@FXML
	private ImageView currPlayerFireImg;
	@FXML
	private ImageView currPlayerIceImg;
	@FXML
	private ImageView currPlayerDoubleMoveImg;
	@FXML
	private ImageView currPlayerBacktrackImg;
	@FXML
	private Button skipActionButton;
	@FXML
	private Label q1Txt;
	@FXML
	private Label q2Txt;
	@FXML
	private Label q3Txt;
	@FXML
	private ImageView q1Img;
	@FXML
	private ImageView q2Img;
	@FXML
	private ImageView q3Img;
	@FXML
	private Button moveButton;
	@FXML
	private Button endTurnButton;
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

			turn = 0;
			gameLog.appendText("GAME START!\n");
			gameLog.appendText("Round 1: First Player - " + currPlayer.getName() + "!\n");
			startNextTurn();
		});
		// TODO - Code that either loads past game or starts new game
	}

	/**
	 * Initialises data necessary to setup game
	 */
	public void initData(SilkBag bag, Board board, Player[] players, int[] xNotFixed, int[]yNotFixed) {
		silkBag = bag;
		gameBoard = board;
		totalPlayers = players.length;
		currPlayer = players[0];
		queuePlayer1 = players[1];
		if (totalPlayers >= 3) {
			queuePlayer2 = players[2];
		}
		if (totalPlayers >= 4) {
			queuePlayer3 = players[3];
		}
		// TODO - int[] xNotFixed, int[]yNotFixed
	}

	/**
	 * Method to setup the beginning of the game and initialise all the needed variables
	 */
	private void setupGame() { // TODO - delete once initialise is setup
		gameBoard = new Board(6, 6);
		silkBag = new SilkBag();
		silkBag.addTile(new Floor("corner", new Image("/assets/corner.png"), false));
		silkBag.addTile(new Action("fire", new Image("/assets/fire.png")));
		Profile lucy = new Profile("Lucy");
		Profile rhys = new Profile("Rhys");
		currPlayer = new Player(new Image("/assets/aries.png"), "#b53232", 0, 0, gameBoard, lucy);
		queuePlayer1 = new Player(new Image("/assets/apollo.png"), "#fdd14b", 5, 5, gameBoard, rhys);
//		queuePlayer2 = new Player(new Image("/assets/artemis.png"), "#55b54c", 0, 0, gameBoard, lucy);
//		queuePlayer3 = new Player(new Image("/assets/aphrodite.png"), "#c677b3", 0, 0, gameBoard, rhys);
		totalPlayers = 2;
		currPlayerFireImg.setImage(new Image("/assets/fire.png"));
		currPlayerIceImg.setImage(new Image("/assets/ice.png"));
		currPlayerDoubleMoveImg.setImage(new Image("/assets/doublemove.png"));
		currPlayerBacktrackImg.setImage(new Image("/assets/backtrack.png"));
	}

	/**
	 * Setups the necessary components for the board to function.
	 * This includes the grid pane displaying the board tiles and the 2d array referencing said tiles.
	 *
	 * @param boardObj - The board object that already holds all the Floor tiles to start a game.
	 */
	private void setupBoard(Board boardObj) {
		boardRows = boardObj.getHeight();
		boardColumns = boardObj.getLength();

		// Make board for gui
		GridPane board = new GridPane();
		board.setPrefSize(500, 500);
		//Padding around the board
		board.setPadding(new Insets(10, 10, 10, 10));
		// Setting gaps between tiles
		board.setVgap(10);
		board.setHgap(10);

		// Setting up the board image
		boardImg = new Rectangle[boardRows][boardColumns];

		for (int i = 0; i < boardRows; i++) {
			for (int j = 0; j < boardColumns; j++) {
				Rectangle tile = new Rectangle(50, 50);
				tile.setFill(Color.WHITE);
				tile.setStroke(Color.BLACK);

				GridPane.setRowIndex(tile, i);
				GridPane.setColumnIndex(tile, j);

				tile.setDisable(true);
				board.getChildren().addAll(tile);
				boardImg[i][j] = tile;

				int finalI = i;
				int finalJ = j;
				tile.setOnMouseClicked(event -> { // TODO - Move to 'place tile' + adapt for move
					tile.setFill(new ImagePattern(silkBagTile.getImage()));
					tile.setRotate(silkBagTileImg.getRotate());

					silkBagTileImg.setFill(GREY);
					Floor tempFloor = (Floor) silkBagTile;
					tempFloor.setRotation(0);

					gameLog.appendText("Floor tile placed on board at (" + finalI + "," + finalJ + ").\n");
					silkBagTileRotate.setDisable(true);
					startPlayActionTurn();
				});
			}
		}
		borderPaneCentre.getChildren().add(board);
	}

	/**
	 * Changes if the board tiles displayed can be clicked or not.
	 *
	 * @param bool - The boolean value desired for the tile disable state.
	 */
	private void setDisableBoardTiles(Boolean bool) {
		for (int i = 0; i < boardRows; i++) {
			for (int j = 0; j < boardColumns; j++) {
				boardImg[i][j].setDisable(bool);
			}
		}
	}

	/**
	 * Loads in a new player and the 'take an tile' section of the game
	 */
	private void startNextTurn() {
		++turn;
//		System.out.println((turn+totalPlayers-1)/totalPlayers);
		setupCurrPlayerDisplay();
		updatePlayerQueue(q1Img, q1Txt, queuePlayer1);
		if (totalPlayers >= 3) {
			updatePlayerQueue(q2Img, q2Txt, queuePlayer2);
		}
		if (totalPlayers >= 4) {
			updatePlayerQueue(q3Img, q3Txt, queuePlayer3);
		}

		takeSilkBagTileButton.setDisable(false);

		actionTrackerMove.setFill(GREY);
		actionTrackerDraw.setFill(currPlayer.getColour());
		gameLog.appendText("Take a Tile!\n");
	}

	/**
	 * Loads the 'play action tiles' section of the game
	 */
	private void startPlayActionTurn() {
		setDisableBoardTiles(true); // TODO - change to arrows
		// TODO - Enable play action button
		skipActionButton.setDisable(false);

		actionTrackerDraw.setFill(GREY);
		actionTrackerPlay.setFill(currPlayer.getColour());
		gameLog.appendText("Play an Action!\n");
	}

	/**
	 * Loads the movement section of the game
	 */
	private void startMoveActionTurn() { // TODO - Disable 'Play action'
		skipActionButton.setDisable(true);
		moveButton.setDisable(false); // LUCY - need to finish checks for this and sort out where it and tiles are disabled again

		endTurnButton.setDisable(false); // TODO - need to move after move implemented

		actionTrackerPlay.setFill(GREY);
		actionTrackerMove.setFill(currPlayer.getColour());
		gameLog.appendText("Move your character!\n");
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
				gameLog.appendText("ERROR: Player order disrupted. Please reload from last save file.\n");
				break;
		}
		currPlayer = tempPlayer;
	}

	/**
	 * Setups the current player's hand including the correct number of each action tile
	 */
	private void setupCurrPlayerDisplay() {
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
	 * Button that will display a new tile after the take tile button is clicked
	 */
	@FXML
	private void takeTileClick() {
		silkBagTileImg.setRotate(0);
		silkBagTile = silkBag.drawTile();
		silkBagTileImg.setFill(new ImagePattern(silkBagTile.getImage()));

		// Check the type of the tile
		String tempTileType = silkBagTile.getTileType();
		if (tempTileType.equalsIgnoreCase("corner")
				|| tempTileType.equalsIgnoreCase("straight")
				|| tempTileType.equalsIgnoreCase("tee")) {
			// If is a floor type
			isNewTileAction = false;
			gameLog.appendText(currPlayer.getName() + " drew a Floor Tile!\n");
			takeSilkBagTileButton.setDisable(true);
			silkBagTileRotate.setDisable(false);
			setDisableBoardTiles(false); // TODO - change to arrows
		} else if (tempTileType.equalsIgnoreCase("fire")
				|| tempTileType.equalsIgnoreCase("ice")
				|| tempTileType.equalsIgnoreCase("doubleMove")
				|| tempTileType.equalsIgnoreCase("backTrack")) {
			// If is action type
			isNewTileAction = true;
			gameLog.appendText(currPlayer.getName() + " drew an Action Tile!\n");
			takeSilkBagTileButton.setDisable(true);
			startPlayActionTurn();
		} else {
			gameLog.appendText("ERROR: Unknown tile has been drawn, please redraw a new tile.\n");
		}
	}

	/**
	 * Button that will rotate the tile and its image if the rotate tile button is clicked.
	 * Will only work if the displayed tile is one of the floor tiles
	 */
	@FXML
	private void rotateTileClick() {
		// Checking if tile is one of the floor tiles
		Floor tempFloor = (Floor) silkBagTile;
		tempFloor.rotate();


		// Rotates the image since it is not 'locked' to tile // TODO - try to 'lock' tile onto rectangle (setdata)
		silkBagTileImg.setRotate(tempFloor.getRotation());
		gameLog.appendText("Floor tile rotated 90 degrees.\n");

	}

//	/**
//	 * Button that will use a fire action tile
//	 */
//	@FXML
//	private void fireClick() {
//		useActionTile("fire");
//	}
//
//	/**
//	 * Button that will use a ice action tile
//	 */
//	@FXML
//	private void iceClick() {
//		useActionTile("ice");
//	}
//
//	/**
//	 * Button that will use a double move action tile
//	 */
//	@FXML
//	private void doubleMoveClick() {
//		useActionTile("doubleMove");
//	}
//
//	/**
//	 * Button that will use a backtrack action tile
//	 */
//	@FXML
//	private void backtrackClick() {
//		useActionTile("backTrack");
//	}

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
	 * Button to skip the play action turn and move onto the movement turn.
	 */
	@FXML
	private void skipActionClick() {
		gameLog.appendText(currPlayer.getName() + " skipped playing an action tile.\n");
		startMoveActionTurn();
	}

	/**
	 * Button that will wraps up the current players turn and start the next player's turn
	 */
	@FXML
	private void endTurnClick() {
		endTurnButton.setDisable(true);

		// Add tile to hand if tile is an action tile and remove from screen
		if (isNewTileAction) {
			Action tempAction = (Action) silkBagTile;
			currPlayer.addActionTile(tempAction);
			silkBagTileImg.setFill(GREY);
			gameLog.appendText(currPlayer.getName() + "'s tile has been added to their hand.\n");
		}

		updatePlayerOrder();
		gameLog.appendText("Round " + turn + ": Next Player - " + currPlayer.getName() + "!\n");
		startNextTurn();
	}
}
