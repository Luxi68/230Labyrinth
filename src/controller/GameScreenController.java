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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
/*
TODO - img resources
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
	private ArrayList<Player> playerRoster;
	private Player currPlayer;
	private Action actionToUse;
	// Game info
	private int turn;
	private ArrayList<Floor> playerMoves;
	private int boardRows; // Board height + 2 for arrow buttons
	private int boardColumns; // Board length + 2 for arrow buttons
	ArrayList<Integer> rowNoFixed;
	ArrayList<Integer> columnNoFixed;
	private StackPane[][] boardImg; // A 2D array so the tiles on the board can be referenced
	// Game checks TODO
	private boolean isNewTileAction;
	private boolean isDoubleMoveUsed = false;

	@FXML
	private BorderPane borderPane;
	@FXML
	private AnchorPane anchorPaneCentre;
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
			setupPlayerTokens();
			currPlayerFireImg.setImage(new Image("/assets/fire.png"));
			currPlayerIceImg.setImage(new Image("/assets/ice.png"));
			currPlayerDoubleMoveImg.setImage(new Image("/assets/doublemove.png"));
			currPlayerBacktrackImg.setImage(new Image("/assets/backtrack.png"));

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
		ArrayList<Integer> rows = (ArrayList<Integer>) data.get(3);
		@SuppressWarnings("unchecked")
		ArrayList<Integer> columns = (ArrayList<Integer>) data.get(4);

		currPlayer = players.get(0);

		// TODO - players array list to simplify turn order stuff
		playerRoster = players;
		rowNoFixed = rows;
		columnNoFixed = columns;
	}

	/**
	 * Method to setup the beginning of the game and initialise all the needed variables
	 */
	private void setupGame() { // TODO - delete once initialise is setup
		gameBoard = new Board(3, 3);
		rowNoFixed = new ArrayList<>();
		rowNoFixed.add(1);
		rowNoFixed.add(2);
		columnNoFixed = new ArrayList<>();
		columnNoFixed.add(0);
		gameBoard.insertTileAt(0, 0, new Floor("straight", new Image("/assets/straight.png"), false));
		gameBoard.insertTileAt(0, 1, new Floor("tee", new Image("/assets/fixedtee.png"), true));
		gameBoard.insertTileAt(0, 2, new Floor("goal", new Image("/assets/goal.png"), true));
		gameBoard.insertTileAt(1, 0, new Floor("straight", new Image("/assets/straight.png"), false));
		gameBoard.insertTileAt(1, 1, new Floor("tee", new Image("/assets/tee.png"), false));
		gameBoard.insertTileAt(1, 2, new Floor("corner", new Image("/assets/corner.png"), false));
		gameBoard.insertTileAt(2, 0, new Floor("tee", new Image("/assets/tee.png"), false));
		gameBoard.insertTileAt(2, 1, new Floor("straight", new Image("/assets/straight.png"), false));
		gameBoard.insertTileAt(2, 2, new Floor("straight", new Image("/assets/straight.png"), false));
		silkBag = new SilkBag();
		silkBag.addTile(new Floor("corner", new Image("/assets/corner.png"), false));
		silkBag.addTile(new Action("fire", new Image("/assets/fire.png")));
		Profile lucy = new Profile("Lucy");
		Profile rhys = new Profile("Rhys");
		currPlayer = new Player(new Image("/assets/aries.png"), "#b53232", 0, 0, gameBoard, lucy);
		Player queuePlayer1 = new Player(new Image("/assets/apollo.png"), "#fdd14b", 2, 2, gameBoard, rhys);
//		Player queuePlayer2 = new Player(new Image("/assets/artemis.png"), "#55b54c", 0, 0, gameBoard, lucy);
//		Player queuePlayer3 = new Player(new Image("/assets/aphrodite.png"), "#c677b3", 0, 0, gameBoard, rhys);
		playerRoster = new ArrayList<>();
		playerRoster.add(currPlayer);
		playerRoster.add(queuePlayer1);
	}

	/**
	 * Setups the necessary components for the board to function.
	 * This includes the grid pane displaying the board tiles and the 2d array referencing said tiles.
	 *
	 * @param boardObj - The board object that already holds all the Floor tiles to start a game.
	 */
	private void setupBoard(Board boardObj) {
		boardRows = boardObj.getHeight() + 2;
		boardColumns = boardObj.getLength() + 2;
		int tileSize = 50;
		int playerTokenSize = 33;

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

		// Setting up board
		for (int i = 1; i < boardRows - 1; i++) {
			for (int j = 1; j < boardColumns - 1; j++) {
				// Space for Floor tile
				Rectangle tile = new Rectangle(tileSize, tileSize);
				tile.setFill(new ImagePattern(boardObj.getTileAt(i - 1, j - 1).getImage()));
				tile.setRotate(boardObj.getTileAt(i - 1, j - 1).getRotation());
				tile.setStroke(Color.BLACK);
				tile.setStrokeType(StrokeType.OUTSIDE);

				// Space for player
				ImageView playerToken = new ImageView();
				playerToken.setFitHeight(playerTokenSize);
				playerToken.setFitWidth(playerTokenSize);
				playerToken.setMouseTransparent(true);
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
				stack.setOnMouseClicked(event -> {
					// Moving backend
					Floor currFloor = currPlayer.getCurrentFloor(gameBoard);
					Floor movedFloor = gameBoard.getTileAt(finalI - 1, finalJ - 1);
					currPlayer.movePlayer(gameBoard, movedFloor);

					// Moving frontend
					ImageView currFloorImg = (ImageView) boardImg[currFloor.getRow() + 1][currFloor.getColumn() + 1]
							.getChildren().get(1);
					ImageView movedFloorImg = (ImageView) boardImg[movedFloor.getRow() + 1][movedFloor.getColumn() + 1]
							.getChildren().get(1);
					currFloorImg.setImage(null);
					movedFloorImg.setImage(currPlayer.getImage());

					checkEndMove();
				});
			}
		}

		// Insert tile buttons
		for (int i = 0; i < boardRows; i++) {
			if (rowNoFixed.contains(i)) {
				setupTileInsertButton(i + 1, 0, board, 90);
				setupTileInsertButton(i + 1, boardColumns - 1, board, 270);
			}
		}
		for (int i = 0; i < boardColumns; i++) {
			if (columnNoFixed.contains(i)) {
				setupTileInsertButton(0, i + 1, board, 180);
				setupTileInsertButton(boardRows - 1, i + 1, board, 0);
			}
		}

		anchorPaneCentre.getChildren().add(board);
		AnchorPane.setTopAnchor(board, 10.0);
		AnchorPane.setLeftAnchor(board, 10.0);
		AnchorPane.setRightAnchor(board, 10.0);
		AnchorPane.setBottomAnchor(board, 10.0);
	}

	/**
	 * Given all the parameters, creates and inserts the triangle insert tile buttons onto the board/
	 *
	 * @param row - The row the button is to be inserted onto.
	 * @param column - The column the button is to be inserted onto.
	 * @param boardPhs - The board the button is to be inserted onto.
	 * @param rotation - The direction the button is facing (0 = downwards triangle).
	 */
	private void setupTileInsertButton(int row, int column, GridPane boardPhs, double rotation) {
		// Drawing and colouring the triangle
		Polygon insertTileButton = new Polygon();
		insertTileButton.getPoints().addAll(20.0, 40.0, 40.0, 40.0, 30.0, 20.0);
//		Alternate size triangle (15.0, 45.0, 45.0, 45.0, 30.0, 15.0);
		insertTileButton.setFill(GREY);
		insertTileButton.setStroke(Color.BLACK);
		insertTileButton.setStrokeType(StrokeType.OUTSIDE);
		insertTileButton.setRotate(rotation);

		// Inserting bottom button
		StackPane stack = new StackPane(insertTileButton);
		stack.setDisable(true);
		GridPane.setRowIndex(stack, row);
		GridPane.setColumnIndex(stack, column);
		boardPhs.getChildren().addAll(stack);
		boardImg[row][column] = stack;

		stack.setOnMouseClicked(event -> {
			int rowIndex = GridPane.getRowIndex(stack);
			int columnIndex = GridPane.getColumnIndex(stack);
			Floor ejectedTile;
			Floor insertedTile = (Floor) silkBagTile;

			if (row == 0) { // Top row
				try {
					ejectedTile = gameBoard.insertFromTop(insertedTile, columnIndex - 1);
					silkBag.addTile(ejectedTile);
					// TODO - add player checks

					for (int i = 1; i < boardRows - 1; i++) {
						Floor tempFloor = gameBoard.getTileAt(i - 1, columnIndex - 1);
						Rectangle tempRect = (Rectangle) boardImg[i][columnIndex].getChildren().get(0);
						tempRect.setFill(new ImagePattern(tempFloor.getImage()));
						tempRect.setRotate(tempFloor.getRotation());

						Player token = checkPlayerLoc(i - 1, columnIndex - 1);
						if (token != null) {
							ImageView oldLoc = (ImageView) boardImg[i][columnIndex].getChildren().get(1);
							oldLoc.setImage(null);
							ImageView newLoc;
							if (i == boardRows - 1) {
								newLoc = (ImageView) boardImg[1][columnIndex].getChildren().get(1);
							} else {
								newLoc = (ImageView) boardImg[i + 1][columnIndex].getChildren().get(1);
							}
							newLoc.setImage(token.getImage());
						}
					}
					silkBagTileImg.setFill(GREY);
					Floor tempFloor = (Floor) silkBagTile;
					tempFloor.setRotation(0);

					gameLog.appendText("Tile slid into column " + (columnIndex - 1) + ".\n");
					startPlayActionTurn();

				} catch (Exception e) {
					gameLog.appendText(e.getMessage());
//					gameLog.appendText("ERROR: This column cannot be moved.\n");
//					e.printStackTrace();
				}
			} else if (column == 0) { // Left column
				try {
					ejectedTile = gameBoard.insertFromLeft(insertedTile, rowIndex - 1);
					silkBag.addTile(ejectedTile);
					// TODO - add player checks

					for (int i = 1; i < boardColumns - 1; i++) {
						Floor tempFloor = gameBoard.getTileAt(rowIndex - 1, i - 1);
						Rectangle tempRect = (Rectangle) boardImg[rowIndex][i].getChildren().get(0);
						tempRect.setFill(new ImagePattern(tempFloor.getImage()));
						tempRect.setRotate(tempFloor.getRotation());
					}
					silkBagTileImg.setFill(GREY);
					Floor tempFloor = (Floor) silkBagTile;
					tempFloor.setRotation(0);

					gameLog.appendText("Tile slid into row " + (rowIndex - 1) + ".\n");
					startPlayActionTurn();

				} catch (Exception e) {
					gameLog.appendText(e.getMessage());
				}
			} else if (row == boardRows - 1) { // Bottom row
				try {
					ejectedTile = gameBoard.insertFromTop(insertedTile, columnIndex - 1);
					silkBag.addTile(ejectedTile);
					// TODO - add player checks

					for (int i = 1; i < boardRows - 1; i++) {
						Floor tempFloor = gameBoard.getTileAt(i - 1, columnIndex - 1);
						Rectangle tempRect = (Rectangle) boardImg[i][columnIndex].getChildren().get(0);
						tempRect.setFill(new ImagePattern(tempFloor.getImage()));
						tempRect.setRotate(tempFloor.getRotation());
					}
					silkBagTileImg.setFill(GREY);
					Floor tempFloor = (Floor) silkBagTile;
					tempFloor.setRotation(0);

					gameLog.appendText("Tile slid into column " + (columnIndex - 1) + ".\n");
					startPlayActionTurn();

				} catch (Exception e) {
					gameLog.appendText(e.getMessage());
				}
			} else if (column == boardColumns - 1) { // Right column
				try {
					ejectedTile = gameBoard.insertFromRight(insertedTile, rowIndex - 1);
					silkBag.addTile(ejectedTile);
					// TODO - add player checks

					for (int i = 1; i < boardColumns - 1; i++) {
						Floor tempFloor = gameBoard.getTileAt(rowIndex - 1, i - 1);
						Rectangle tempRect = (Rectangle) boardImg[rowIndex][i].getChildren().get(0);
						tempRect.setFill(new ImagePattern(tempFloor.getImage()));
						tempRect.setRotate(tempFloor.getRotation());
					}
					silkBagTileImg.setFill(GREY);
					Floor tempFloor = (Floor) silkBagTile;
					tempFloor.setRotation(0);

					gameLog.appendText("Tile slid into row " + (rowIndex - 1) + ".\n");
					startPlayActionTurn();

				} catch (Exception e) {
					gameLog.appendText(e.getMessage());
				}
			} else {
				gameLog.appendText("ERROR: Invalid insert tile button. Please choose another location\n");
			}
		});
	}

	/**
	 * Sets the specified tile image to that of the currently drawn floor tile.
	 *
	 * @param tile - the tile on the board that is taking on the silk bag tile.
	 */
	private void setTileImage(Rectangle tile) {
		tile.setFill(new ImagePattern(silkBagTile.getImage()));
		tile.setRotate(silkBagTileImg.getRotate());

		silkBagTileImg.setFill(GREY);
		Floor tempFloor = (Floor) silkBagTile;
		tempFloor.setRotation(0);

		silkBagTileRotate.setDisable(true);
		setDisabledBoardArrows(true);
	}

	/**
	 * Checks to see if any players are in a set location
	 *
	 * @param row - row to be checked
	 * @param column - column to be checked
	 * @return - player who is on the location, null if none
	 */
	private Player checkPlayerLoc(int row, int column) {
		for (Player player: playerRoster) {
			if (player.getRowLoc() == row && player.getColumnLoc() == column) {
				return player;
			}
		}
		return null;
	}

	/**
	 * Setup player images on the corresponding tile.
	 */
	private void setupPlayerTokens() {
		for (Player player: playerRoster) {
			int row = player.getRowLoc();
			int column = player.getColumnLoc();

			ImageView playerToken = (ImageView) boardImg[row + 1][column + 1].getChildren().get(1);
			playerToken.setImage(player.getImage());
		}
	}

	/**
	 * Changes if the board tiles displayed can be clicked or not.
	 *
	 * @param bool - The boolean value desired for the tile disable state.
	 */
	private void setDisableBoardTiles(boolean bool) {
		for (int i = 1; i < boardRows - 1; i++) {
			for (int j = 1; j < boardColumns - 1; j++) {
				if (boardImg[i][j] != null) {
					boardImg[i][j].setDisable(bool);
				}
			}
		}
	}

	/**
	 * Changes if the board arrows can be clicked or not
	 *
	 * @param bool - The boolean value desired for the arrow disable state
	 */
	private void setDisabledBoardArrows(boolean bool) {
		for (int i = 0; i < boardRows; i++) {
			if (boardImg[i][0] != null) {
				boardImg[i][0].setDisable(bool);
			}
			if (boardImg[i][boardColumns - 1] != null) {
				boardImg[i][boardColumns - 1].setDisable(bool);
			}
		}
		for (int j = 1; j < boardColumns - 1; j++) {
			if (boardImg[0][j] != null) {
				boardImg[0][j].setDisable(bool);
			}
			if (boardImg[boardRows - 1][j] != null) {
				boardImg[boardRows - 1][j].setDisable(bool);
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
		updatePlayerQueue(q1Img, q1Txt, playerRoster.get(1));
		if (playerRoster.size() >= 3) {
			updatePlayerQueue(q2Img, q2Txt, playerRoster.get(2));
		}
		if (playerRoster.size() >= 4) {
			updatePlayerQueue(q3Img, q3Txt, playerRoster.get(3));
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
		silkBagTileRotate.setDisable(true);
		setDisabledBoardArrows(true);
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
			StackPane stack = boardImg[tile.getRow() + 1][tile.getColumn() + 1];
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
	 * Setups the current player's hand including the correct number of each action tile
	 */
	private void setupCurrPlayerDisplay() {
		Player currPlayer = playerRoster.get(0);

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
			setDisabledBoardArrows(false);
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
			playerMoves = currPlayer.possibleMoves(gameBoard);

			for (Floor floor: playerMoves) {
				System.out.println(floor.getRow() + "," + floor.getColumn() + ":"
				+ floor.north + floor.east + floor.south + floor.west);
			}
			System.out.println(currPlayer.getRowLoc() + "," + currPlayer.getColumnLoc() + ","
					+ gameBoard.getTileAt(currPlayer.getRowLoc(), currPlayer.getColumnLoc()).north + ","
					+ gameBoard.getTileAt(currPlayer.getRowLoc(), currPlayer.getColumnLoc()).east + ","
					+ gameBoard.getTileAt(currPlayer.getRowLoc(), currPlayer.getColumnLoc()).south + ","
					+ gameBoard.getTileAt(currPlayer.getRowLoc(), currPlayer.getColumnLoc()).west + ",");
			System.out.println(currPlayer.getRowLoc()+1 + "," + currPlayer.getColumnLoc() + ","
					+ gameBoard.getTileAt(currPlayer.getRowLoc()+1, currPlayer.getColumnLoc()).north + ","
					+ gameBoard.getTileAt(currPlayer.getRowLoc()+1, currPlayer.getColumnLoc()).east + ","
					+ gameBoard.getTileAt(currPlayer.getRowLoc()+1, currPlayer.getColumnLoc()).south + ","
					+ gameBoard.getTileAt(currPlayer.getRowLoc()+1, currPlayer.getColumnLoc()).west + ",");


			if (!playerMoves.isEmpty()) {
				for (Floor tile: playerMoves) {
					StackPane stack = boardImg[tile.getRow() + 1][tile.getColumn() + 1];
					stack.setDisable(false);

					Rectangle floor = (Rectangle) stack.getChildren().get(0);
					floor.setStroke(currPlayer.getColour());
					floor.setStrokeWidth(4);
				}
			} else {
				gameLog.appendText("No movement possible, please end your turn.\n");
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

		playerRoster.remove(0);
		playerRoster.add(currPlayer);
		currPlayer = playerRoster.get(0);
		gameLog.appendText("Round " + turn + ": Next Player - " + currPlayer.getName() + "!\n");
		startNextTurn();
	}

}
