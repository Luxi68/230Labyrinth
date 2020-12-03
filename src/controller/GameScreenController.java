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
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
/*
TODO - img resources
	 - Remake gridpane to inc triangle buttons
	 - set gridpane so the outer boxes are not drawn
	 - Write in method for tile movement
	 - tie tile movement to the arrows
	 - fix checks/disables
	 - properly implement movement code
	 - test tile and player movement
	 - action tiles decide how i wanna implement
	 - implement
	 - test
	 - wrap up all other tags
	 - DONE!!
 */

/**
 * Class that holds the actions and logic behind GameScreen.fxml
 *
 * @author Junjie, Rhys
 */
public class GameScreenController implements Initializable {
	// Colour Scheme
	private final Paint GREY = Paint.valueOf("#3f443e");
	// Game components
	private Board gameBoard;
	private SilkBag silkBag;
	private Tile silkBagTile;
	private Player currPlayer;
	private Player queuePlayer1;
	private Player queuePlayer2;
	private Player queuePlayer3;
	private Action actionToUse;
	// Game info
	private int turn;
	private int totalPlayers;
	private ArrayList<Floor> playerMoves;
	private int boardRows;
	private int boardColumns;
	private StackPane[][] boardImg; // A 2D array so the tiles on the board can be referenced
	// Game checks TODO
	private boolean isNewTileAction;
	private boolean isDoubleMoveUsed = false;

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
	public void initData(ArrayList<Object> data) {
		gameBoard = (Board) data.get(0);
		silkBag = (SilkBag) data.get(1);

		// There are coming from file reader so i'm 100% sure on the contents of data
		@SuppressWarnings("unchecked")
		ArrayList<Player> players = (ArrayList<Player>) data.get(2);
		@SuppressWarnings("unchecked")
		ArrayList<Integer> rowNoFixed = (ArrayList<Integer>) data.get(3);
		@SuppressWarnings("unchecked")
		ArrayList<Integer> columnNoFixed = (ArrayList<Integer>) data.get(4);

		totalPlayers = players.size();
		currPlayer = players.get(0);
		queuePlayer1 = players.get(1);
		if (totalPlayers >= 3) {
			queuePlayer2 = players.get(2);
		}
		if (totalPlayers >= 4) {
			queuePlayer3 = players.get(3);
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
		int tileSize = 80;
		int playerTokenSize = 50;

		// Make board for gui
		GridPane board = new GridPane();
		board.setPrefSize(500, 500);
		//Padding around the board
		board.setPadding(new Insets(10, 10, 10, 10));
		// Setting gaps between tiles
		board.setVgap(10);
		board.setHgap(10);

		// Setting up the board image
		boardImg = new StackPane[boardRows][boardColumns];

		for (int i = 0; i < boardRows; i++) {
			for (int j = 0; j < boardColumns; j++) {
				// Space for Floor tile
				Rectangle tile = new Rectangle(tileSize, tileSize);
				tile.setFill(Color.WHITE);
				tile.setStroke(Color.BLACK);
				tile.setStrokeType(StrokeType.OUTSIDE);

				// Space for player
				ImageView playerToken = new ImageView();
				playerToken.setFitHeight(playerTokenSize);
				playerToken.setFitWidth(playerTokenSize);
//				playerToken.setOpacity(0); TODO - test to see if needed

				// Holds both floor and player token
				StackPane stack = new StackPane(tile, playerToken);
				stack.setDisable(true);

				GridPane.setRowIndex(stack, i);
				GridPane.setColumnIndex(stack, j);

				board.getChildren().addAll(stack);
				boardImg[i][j] = stack;

				int finalI = i;
				int finalJ = j;
				stack.setOnMouseClicked(event -> { // TODO - Move to 'slide tile' method
					tile.setFill(new ImagePattern(silkBagTile.getImage()));
					tile.setRotate(silkBagTileImg.getRotate());

					silkBagTileImg.setFill(GREY);
					Floor tempFloor = (Floor) silkBagTile;
					tempFloor.setRotation(0);

					gameLog.appendText("Floor tile placed on board at (" + finalI + "," + finalJ + ").\n");
					silkBagTileRotate.setDisable(true);
					startPlayActionTurn();

//					// Moving backend TODO - implement(uncomment) when floor sliding implemented
//					Floor currFloor = currPlayer.getCurrentFloor();
//					Floor movedFloor = gameBoard.getTileAt(finalI, finalJ);
//					currPlayer.movePlayer(movedFloor);
//
//					// Moving frontend
//					ImageView currFloorImg =
//							(ImageView) boardImg[currFloor.getRow()][currFloor.getColumn()].getChildren().get(1);
//					ImageView movedFloorImg =
//							(ImageView) boardImg[movedFloor.getRow()][movedFloor.getColumn()].getChildren().get(1);
//					currFloorImg.setImage(null);
//					movedFloorImg.setImage(currPlayer.getImage());
//
//					checkEndMove();
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
	private void setDisableBoardTiles(boolean bool) {
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
		moveButton.setDisable(false);

		actionTrackerPlay.setFill(GREY);
		actionTrackerMove.setFill(currPlayer.getColour());
		gameLog.appendText("Move your character!\n");
	}

	/**
	 * Checks if the movement phase can be ended or not
	 */
	private void checkEndMove() {
		// Removes tile outlines
		for (Floor tile : playerMoves) {
			StackPane stack = boardImg[tile.getRow()][tile.getColumn()];
			stack.setDisable(true);

			Rectangle floor = (Rectangle) stack.getChildren().get(0);
			floor.setStroke(Color.BLACK);
			floor.setStrokeWidth(1);
		}
		// Checks if move button should be disabled
		if (!isDoubleMoveUsed) {
			moveButton.setDisable(true);
		} else {
			gameLog.appendText("Double Move was used earlier, you can move again if you wish.");
		}
		endTurnButton.setDisable(false);
		isDoubleMoveUsed = false;
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
	 * Button that displays the movement options and allows player to choose
	 */
	@FXML
	private void moveClick() {
		try {
			playerMoves = currPlayer.possibleMoves();
			if (!playerMoves.isEmpty()) {
				for (Floor tile : playerMoves) {
					StackPane stack = boardImg[tile.getRow()][tile.getColumn()];
					stack.setDisable(false);

					Rectangle floor = (Rectangle) stack.getChildren().get(0);
					floor.setStroke(currPlayer.getColour());
					floor.setStrokeWidth(4);
				}
			} else {
				gameLog.appendText("No movement possible, please end your turn.");
				endTurnButton.setDisable(false);
			}
		} catch (Exception e) {
			System.out.println("Move button failed to work again because of " + e); // TODO - Remove once move works
			endTurnButton.setDisable(false);
		}
	}

	/**
	 * Button that will wraps up the current players turn and start the next player's turn
	 */
	@FXML
	private void endTurnClick() {
		moveButton.setDisable(true); // Just in case it wasn't disabled earlier
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
