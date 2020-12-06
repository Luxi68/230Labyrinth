package controller;

import entity.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
/*
TODO - img resources
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
	public Slider volumeSlider;
	ArrayList<Integer> rowNoFixed;
	ArrayList<Integer> columnNoFixed;
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
	private StackPane[][] boardImg; // A 2D array for referencing gridPane contents. Is one step bigger all the way around
	private ArrayList<Floor> inflictableTiles;
	private ArrayList<Floor> fireInfectedTiles;
	private ArrayList<Floor> iceInfectedTiles;
	// Game checks TODO
	private boolean isNewTileAction;
	private boolean isDoubleMoveUsed;

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
	private StackPane fireButton;
	@FXML
	private Label currPlayerFireTxt;
	@FXML
	private ImageView currPlayerFireImg;
	@FXML
	private StackPane iceButton;
	@FXML
	private Label currPlayerIceTxt;
	@FXML
	private ImageView currPlayerIceImg;
	@FXML
	private StackPane doubleMoveButton;
	@FXML
	private Label currPlayerDoubleMoveTxt;
	@FXML
	private ImageView currPlayerDoubleMoveImg;
	@FXML
	private StackPane backTrackButton;
	@FXML
	private Label currPlayerBacktrackTxt;
	@FXML
	private ImageView currPlayerBacktrackImg;
	@FXML
	private Button takeActionButton;
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
			setupBoard(gameBoard);
			setupPlayerTokens();
			currPlayerFireImg.setImage(new Image("/assets/fire.png"));
			currPlayerIceImg.setImage(new Image("/assets/ice.png"));
			currPlayerDoubleMoveImg.setImage(new Image("/assets/doublemove.png"));
			currPlayerBacktrackImg.setImage(new Image("/assets/backtrack.png"));

			turn = 0;
			gameLog.appendText("GAME START!\n");
			gameLog.appendText("Round 1: First Deity - " + currPlayer.getName() + "!\n");
			startNextTurn();
		});
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

		playerRoster = players;
		rowNoFixed = rows;
		columnNoFixed = columns;

		// TODO - need a check for save files
		isDoubleMoveUsed = false;
		fireInfectedTiles = new ArrayList<>();
		iceInfectedTiles = new ArrayList<>();
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
		int tileSize = 90;
		int playerTokenSize = 50;

		// Make board for gui
		GridPane board = new GridPane();
		board.setPrefSize(500, 500);
		//Padding around the board
		board.setPadding(new Insets(10, 10, 10, 10));

		// Setting up the board image
		boardImg = new StackPane[boardRows][boardColumns];

		// Setting up board
		for (int i = 1; i < boardRows - 1; i++) {
			for (int j = 1; j < boardColumns - 1; j++) {
				// Space for Floor tile
				Rectangle tile = new Rectangle(tileSize, tileSize);
				tile.setFill(new ImagePattern(boardObj.getTileAt(i - 1, j - 1).getImage()));
				tile.setRotate(boardObj.getTileAt(i - 1, j - 1).getRotation());
				tile.setStroke(Color.TRANSPARENT);
				tile.setStrokeType(StrokeType.INSIDE);

				// Space for Fire gradient
				Stop[] fireStop = {new Stop(0, Color.ORANGERED), new Stop(0.5, Color.TRANSPARENT)};
				Rectangle fire = new Rectangle(tileSize, tileSize);
				fire.setFill(new LinearGradient(0, 0, 1, 1,
						true, CycleMethod.NO_CYCLE, fireStop));
				fire.setOpacity(0);

				// Space for Ice gradient
				Stop[] iceStop = {new Stop(0.5, Color.TRANSPARENT), new Stop(1, Color.LIGHTBLUE)};
				Rectangle ice = new Rectangle(tileSize, tileSize);
				ice.setFill(new LinearGradient(0, 0, 1, 1,
						true, CycleMethod.NO_CYCLE, iceStop));
				ice.setOpacity(0);

				// Space for player
				ImageView playerToken = new ImageView();
				playerToken.setFitHeight(playerTokenSize);
				playerToken.setFitWidth(playerTokenSize);
				playerToken.setMouseTransparent(true);

				// Holds both floor and player token
				StackPane stack = new StackPane(tile, fire, ice, playerToken);
				stack.setDisable(true);

				GridPane.setRowIndex(stack, i);
				GridPane.setColumnIndex(stack, j);

				board.getChildren().addAll(stack);
				boardImg[i][j] = stack;

				int finalI = i;
				int finalJ = j;
				stack.setOnMouseClicked(event -> {
					Paint colour = tile.getStroke();

					if (actionTrackerMove.getFill() == currPlayer.getColour()) { // If movement turn
						// Moving backend
						Media buttonSound = new Media(new File("resources/sounds/footstep.wav").toURI().toString());
						MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
						mediaPlayer.play();
						Floor currFloor = currPlayer.getCurrentFloor(gameBoard);
						Floor movedFloor = gameBoard.getTileAt(finalI - 1, finalJ - 1);
						currPlayer.movePlayer(gameBoard, movedFloor);

						// Moving frontend
						ImageView currFloorImg = (ImageView)
								boardImg[currFloor.getRow() + 1][currFloor.getColumn() + 1].getChildren().get(3);
						ImageView movedFloorImg = (ImageView)
								boardImg[movedFloor.getRow() + 1][movedFloor.getColumn() + 1].getChildren().get(3);
						currFloorImg.setImage(null);
						movedFloorImg.setImage(currPlayer.getImage());

						gameLog.appendText(currPlayer.getName() + " moved to island ("
								+ (finalJ - 1) + ", " + (finalI - 1) + ").\n");
						checkEndMove();

					} else if (actionTrackerPlay.getFill() == currPlayer.getColour()) {
						if (colour == currPlayer.getColour()) {
							Player victim = checkPlayerLoc(finalI - 1, finalJ - 1);
							// Erase buttons highlights
							for (Player player : playerRoster) {
//								int row = player.getRowLoc();
//								int col = player.getColumnLoc();
//								toggleRectDisable(boardImg[row + 1][col + 1], true, Color.TRANSPARENT);
//								if (row == finalI - 1 && col == finalJ) {
//									victim = player;
//								}
								toggleRectDisable(boardImg[player.getRowLoc() + 1][player.getColumnLoc() + 1], true, Color.TRANSPARENT);
							}
							if (victim != null) {
								try {
									setPlayerImg(null,
											victim.getRowLoc() + 1, victim.getColumnLoc() + 1);
									System.out.println((victim.getRowLoc() + 1) + "," + (victim.getColumnLoc() + 1));
									victim.backtrack(gameBoard);
									setPlayerImg(victim.getImage(),
											victim.getRowLoc() + 1, victim.getColumnLoc() + 1);
									System.out.println((victim.getRowLoc() + 1) + "," + (victim.getColumnLoc() + 1));
									gameLog.appendText(victim.getName() + " was forcibly moved back in time.\n");
								} catch (Exception e) {
									gameLog.appendText(e.getMessage());
								}
							}
							startMoveActionTurn();
						} else {
							// Remove the colour and disable tiles
							for (Floor inflictable : inflictableTiles) {
								toggleRectDisable(boardImg[inflictable.getRow() + 1][inflictable.getColumn() + 1],
										true, Color.TRANSPARENT);
							}

							Floor currFloor = gameBoard.getTileAt(finalI - 1, finalJ - 1);
							ArrayList<Floor> inflictedTiles = gameBoard.getSurroundingTiles(currFloor);

							if (colour == Color.ORANGERED) { // Fire was played
								int endTurn = turn + (playerRoster.size() * 2);

								for (Floor effected : inflictedTiles) {
									StackPane tempStack = boardImg[effected.getRow() + 1][effected.getColumn() + 1];
									Rectangle tempEffect = (Rectangle) tempStack.getChildren().get(1);
									tempEffect.setOpacity(1);
									effected.setIsFire(true);
									effected.setFireOver(endTurn);
									if (!fireInfectedTiles.contains(effected)) {
										fireInfectedTiles.add(effected);
									}
								}
								gameLog.appendText(currPlayer.getName() + " cast FIRE onto island ("
										+ (finalJ - 1) + ", " + (finalI - 1) + "). " +
										"Those islands are now too dangerous to traverse on.\n");
								startMoveActionTurn();

							} else if (colour == Color.LIGHTBLUE) { // Ice was played
								int endTurn = turn + playerRoster.size();

								for (Floor effected : inflictedTiles) {
									StackPane tempStack = boardImg[effected.getRow() + 1][effected.getColumn() + 1];
									Rectangle tempEffect = (Rectangle) tempStack.getChildren().get(2);
									tempEffect.setOpacity(1);
									effected.setIsIce(true);
									effected.setIceOver(endTurn);
									if (!iceInfectedTiles.contains(effected)) {
										iceInfectedTiles.add(effected);
									}
								}
								gameLog.appendText(currPlayer.getName() + " cast ICE onto island ("
										+ (finalJ - 1) + ", " + (finalI - 1) + "). Those island are now stuck in place.\n");
								startMoveActionTurn();
							}
						}
					}
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
	 * @param row      - The row the button is to be inserted onto.
	 * @param column   - The column the button is to be inserted onto.
	 * @param boardPhs - The board the button is to be inserted onto.
	 * @param rotation - The direction the button is facing (0 = downwards triangle).
	 */
	private void setupTileInsertButton(int row, int column, GridPane boardPhs, double rotation) {
		// Drawing and colouring the triangle
		Polygon insertTileButton = new Polygon();
		insertTileButton.getPoints().addAll(20.0, 40.0, 40.0, 40.0, 30.0, 20.0);
//		Alternate size triangle (15.0, 45.0, 45.0, 45.0, 30.0, 15.0);
		insertTileButton.setFill(GREY);
		insertTileButton.setStroke(Color.TRANSPARENT);
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
			int rowImg = GridPane.getRowIndex(stack);
			int colImg = GridPane.getColumnIndex(stack);
			Floor ejectedTile;
			Player ejectedPlayer;
			Floor insertedTile = (Floor) silkBagTile;
			String axis = "";
			String direction = "";

			try {
				if (row == 0 || row == boardRows - 1) {
					axis = "longitude";
					Media buttonSound = new Media(new File("resources/sounds/wind.wav").toURI().toString());
					MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
					mediaPlayer.play();
					// Move all the tiles along and return the ejected tile
					if (row == 0) {
						try {
							direction = "north";
							ejectedTile = gameBoard.insertFromTop(insertedTile, colImg - 1);
							silkBag.addTile(true, ejectedTile); //tile is removed
							ejectedPlayer = checkPlayerLoc(boardRows - 3, colImg - 1);

							// Loop up through the column to update all images
							for (int i = boardRows - 2; i > 0; i--) {
								updateTileImgs(i, colImg);
								Player token = checkPlayerLoc(i - 1, colImg - 1);
								if (token != null) {
									setPlayerImg(null, i, colImg);
//								System.out.println("oldLoc: " + (i - 1) + "," + (colImg - 1)); TODO - print
									if (i != boardRows - 2) {
										// set image of tile below
										setPlayerImg(token.getImage(), i + 1, colImg);
										token.movePlayer(gameBoard, gameBoard.getTileAt(i, colImg - 1));
//									System.out.println("newLoc: " + (i) + "," + (colImg - 1)); TODO - print
									}
								}
							}
							if (ejectedPlayer != null) {
								ejectedPlayer.movePlayer(gameBoard, gameBoard.getTileAt(0, colImg - 1));
								setPlayerImg(ejectedPlayer.getImage(), 1, colImg);
							}
							endDrawTurn(axis, direction, colImg - 1);
						} catch (Exception e) {
							gameLog.appendText(e.getMessage());
						}
					} else {
						try {
							direction = "south";
							ejectedTile = gameBoard.insertFromBottom(insertedTile, colImg - 1);
							silkBag.addTile(true, ejectedTile); //tile is removed
							ejectedPlayer = checkPlayerLoc(0, colImg - 1);

							// Loop down through the column to update all images
							for (int i = 1; i < boardRows - 1; i++) {
								updateTileImgs(i, colImg);
								Player token = checkPlayerLoc(i - 1, colImg - 1);
								if (token != null) {
									setPlayerImg(null, i, colImg);
//								System.out.println("oldLoc: " + (i - 1) + "," + (colImg - 1)); TODO - print
									if (i != 1) {
										// set image of tile above
										setPlayerImg(token.getImage(), i - 1, colImg);
										token.movePlayer(gameBoard, gameBoard.getTileAt(i - 2, colImg - 1));
//									System.out.println("newLoc: " + (i - 2) + "," + (colImg - 1)); TODO - print
									}
								}
							}
							if (ejectedPlayer != null) {
//							System.out.println("newLoc: " + (boardRows - 3) + "," + (colImg - 1));TODO - print
//							System.out.println(ejectedPlayer.getName()
//									+ "(" + ejectedPlayer.getRowLoc() + ", " + ejectedPlayer.getColumnLoc() + ")");
								ejectedPlayer.movePlayer(gameBoard, gameBoard.getTileAt(boardRows - 3, colImg - 1));
//							System.out.println(ejectedPlayer.getName()
//									+ "(" + ejectedPlayer.getRowLoc() + ", " + ejectedPlayer.getColumnLoc() + ")");
								setPlayerImg(ejectedPlayer.getImage(), boardRows - 2, colImg);

							}
							endDrawTurn(axis, direction, colImg - 1);
						} catch (Exception e) {
							gameLog.appendText(e.getMessage());
						}
					}
				} else if (column == 0 || column == boardColumns - 1) { // Left column
					axis = "latitude";
					Media buttonSound = new Media(new File("resources/sounds/wind.wav").toURI().toString());
					MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
					mediaPlayer.play();
					// Move all the tiles along and return the ejected tile
					if (column == 0) {
						try {
							direction = "west";
							ejectedTile = gameBoard.insertFromLeft(insertedTile, rowImg - 1);
							silkBag.addTile(true, ejectedTile);
							ejectedPlayer = checkPlayerLoc(rowImg - 1, boardColumns - 3);

							// Loop right through the row to update all images
							for (int i = boardColumns - 2; i > 0; i--) {
								updateTileImgs(rowImg, i);
								Player token = checkPlayerLoc(rowImg - 1, i - 1);
								if (token != null) {
									setPlayerImg(null, rowImg, i);
//								System.out.println("oldLoc: " + (rowImg - 1) + "," + (i - 1));TODO - print
									if (i != boardColumns - 2) {
										// set image of tile right
										setPlayerImg(token.getImage(), rowImg, i + 1);
										token.movePlayer(gameBoard, gameBoard.getTileAt(rowImg - 1, i));
//									System.out.println("newLoc: " + (rowImg - 1) + "," + (i)); TODO - print
									}
								}
							}
							if (ejectedPlayer != null) {
								ejectedPlayer.movePlayer(gameBoard, gameBoard.getTileAt(rowImg - 1, 0));
								setPlayerImg(ejectedPlayer.getImage(), rowImg, 1);
							}
							endDrawTurn(axis, direction, rowImg - 1);
						} catch (Exception e) {
							gameLog.appendText(e.getMessage());
						}
					} else {
						try {
							direction = "east";
							ejectedTile = gameBoard.insertFromRight(insertedTile, rowImg - 1);
							silkBag.addTile(true, ejectedTile);
							ejectedPlayer = checkPlayerLoc(rowImg - 1, 0);

							// Loop left through the row to update all images
							for (int i = 1; i < boardColumns - 1; i++) {
								updateTileImgs(rowImg, i);
								Player token = checkPlayerLoc(rowImg - 1, i - 1);
								if (token != null) {
									setPlayerImg(null, rowImg, i);
//								System.out.println("oldLoc: " + (rowImg - 1) + "," + (i - 1)); TODO - print
									if (i != 1) {
										// set image of tile left
										setPlayerImg(token.getImage(), rowImg, i - 1);
										token.movePlayer(gameBoard, gameBoard.getTileAt(rowImg - 1, i - 2));
//									System.out.println("newLoc: " + (rowImg - 1) + "," + (i - 2)); TODO - print
									}
								}
							}
							if (ejectedPlayer != null) {
//							System.out.println("newLoc: " + (rowImg - 1) + "," + (boardColumns - 3)); TODO - print
//							System.out.println(ejectedPlayer.getName()
//									+ "(" + ejectedPlayer.getRowLoc() + ", " + ejectedPlayer.getColumnLoc() + ")");
								ejectedPlayer.movePlayer(gameBoard, gameBoard.getTileAt(rowImg - 1, boardColumns - 3));
//							System.out.println(ejectedPlayer.getName()
//									+ "(" + ejectedPlayer.getRowLoc() + ", " + ejectedPlayer.getColumnLoc() + ")");
								setPlayerImg(ejectedPlayer.getImage(), rowImg, boardColumns - 2);
							}
							endDrawTurn(axis, direction, rowImg - 1);
						} catch (Exception e) {
							gameLog.appendText(e.getMessage());
						}
					}
				}
			} catch (Exception e) {
//				gameLog.appendText(e.getMessage());
				gameLog.appendText("WARNING: These islands cannot be moved currently.\n");
				e.printStackTrace();
			}
		});
	}

	/**
	 * Setup player images on the corresponding tile.
	 */
	private void setupPlayerTokens() {
		for (Player player : playerRoster) {
			int row = player.getRowLoc();
			int column = player.getColumnLoc();

			ImageView playerToken = (ImageView) boardImg[row + 1][column + 1].getChildren().get(3);
			playerToken.setImage(player.getImage());
		}
	}

	/**
	 * Updates the floor image on the selected tile
	 *
	 * @param boardImgRow - boardImg row the tile to be changed is
	 * @param boardImgCol - boardImg column the tile to be changed is
	 */
	private void updateTileImgs(int boardImgRow, int boardImgCol) {
		Floor tempFloor = gameBoard.getTileAt(boardImgRow - 1, boardImgCol - 1);
		StackPane tempStack = boardImg[boardImgRow][boardImgCol];
		Rectangle tempRect = (Rectangle) tempStack.getChildren().get(0);
		tempRect.setFill(new ImagePattern(tempFloor.getImage()));
		tempRect.setRotate(tempFloor.getRotation());
		// Update fire infliction locations
		Rectangle tempFire = (Rectangle) tempStack.getChildren().get(1);
		if (tempFloor.getIsFire()) {
			tempFire.setOpacity(1);
		} else {
			tempFire.setOpacity(0);
		}
//		System.out.println("Update- " + tempFloor.getRow() + "," + tempFloor.getColumn() + ":" TODO - print
//				+ tempFloor.isNorth() + tempFloor.isEast() + tempFloor.isSouth() + tempFloor.isWest());
	}

	/**
	 * Checks to see if any players are in a set location
	 *
	 * @param boardPhyRow - gameBoard row to be checked
	 * @param boardPhyCol - gameBoard column to be checked
	 * @return - player who is on the location, null if none
	 */
	private Player checkPlayerLoc(int boardPhyRow, int boardPhyCol) {
		for (Player player : playerRoster) {
			if (player.getRowLoc() == boardPhyRow && player.getColumnLoc() == boardPhyCol) {
				return player;
			}
		}
		return null;
	}

	/**
	 * Sets the player token image at a specified tile
	 *
	 * @param boardImgRow - boardImg row to be set
	 * @param boardImgCol - boardImg column to be set
	 */
	private void setPlayerImg(Image img, int boardImgRow, int boardImgCol) {
		ImageView view = (ImageView) boardImg[boardImgRow][boardImgCol].getChildren().get(3);
		view.setImage(img);
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
	 * Toggles the disable state of buttons and its highlighting
	 *
	 * @param button - the button to be changes
	 * @param bool   - the state of the button
	 * @param colour - the colour of the highlight
	 */
	private void toggleRectDisable(StackPane button, boolean bool, Paint colour) {
		Rectangle action = (Rectangle) button.getChildren().get(0);
		action.setStroke(colour);
		if (!bool) {
			action.setStrokeWidth(4);
		} else {
			action.setStrokeWidth(1);
		}
		button.setDisable(bool);
	}

	/**
	 * Disables the other action tiles that have not been selected
	 */
	private void disableActionSelect() {
		takeActionButton.setDisable(true);
		skipActionButton.setDisable(true);
		toggleRectDisable(fireButton, true, Color.TRANSPARENT);
		toggleRectDisable(iceButton, true, Color.TRANSPARENT);
		toggleRectDisable(doubleMoveButton, true, Color.TRANSPARENT);
		toggleRectDisable(backTrackButton, true, Color.TRANSPARENT);
	}

	/**
	 * Finds and highlights the tiles that fire/ice can be played on
	 */
	private void setSelectableTiles(String action) {
		ArrayList<Floor> nonInflictableTiles = new ArrayList<>();
		inflictableTiles = new ArrayList<>();
		// Finding tiles that can't be inflicted
		for (Player player : playerRoster) {
			Floor currTile = player.getCurrentFloor(gameBoard);
			nonInflictableTiles.addAll(gameBoard.getSurroundingTiles(currTile));
		}
		// Finding tiles that can be inflicted
		for (int row = 1; row < boardRows - 1; row++) {
			for (int col = 1; col < boardColumns - 1; col++) {
				Floor tempFloor = gameBoard.getTileAt(row - 1, col - 1);
				if (!nonInflictableTiles.contains(tempFloor)) {
					inflictableTiles.add(tempFloor);
					StackPane tempStack = boardImg[row][col];
					if (action.equalsIgnoreCase("fire")) {
						toggleRectDisable(tempStack, false, Color.ORANGERED);
					} else if (action.equalsIgnoreCase("ice")) {
						toggleRectDisable(tempStack, false, Color.LIGHTBLUE);
					}
				}
			}
		}
	}

	/**
	 * Checks if there are any inflictions that have worn out
	 */
	private void checkInflictions() {
		ArrayList<Floor> removed = new ArrayList<>();
		StackPane stack;
		Rectangle effect;
		boolean changed = false;
		for (Floor effected : fireInfectedTiles) {
			if (!effected.isOnBoard() || effected.getFireOver() < turn) {
				effected.setIsFire(false);
				removed.add(effected);
			} else if (effected.getFireOver() == turn) {
				stack = boardImg[effected.getRow() + 1][effected.getColumn() + 1];
				effect = (Rectangle) stack.getChildren().get(1);
				effect.setOpacity(0);
				effected.setIsFire(false);
				changed = true;
			}
		}
		fireInfectedTiles.removeAll(removed);
		if (changed) {
			gameLog.appendText("Fire, that were burning on some islands, have now died down.\n");
			changed = false;
		}
		removed = new ArrayList<>();
		for (Floor effected : iceInfectedTiles) {
			if (!effected.isOnBoard() || effected.getIceOver() < turn) {
				effected.setIsIce(false);
				removed.add(effected);
			} else if (effected.getIceOver() == turn) {
				stack = boardImg[effected.getRow() + 1][effected.getColumn() + 1];
				effect = (Rectangle) stack.getChildren().get(2);
				effect.setOpacity(0);
				effected.setIsIce(false);
				changed = true;
			}
		}
		if (changed) {
			gameLog.appendText("Some ice, that were freezing some islands still, have now melted.\n");
		}
		iceInfectedTiles.removeAll(removed);
//		System.out.println(fireInfectedTiles); // TODO - print
		System.out.println(iceInfectedTiles);
	}

	/**
	 * Loads in a new player and the 'take an tile' section of the game
	 */
	private void startNextTurn() {
		turn++;
//		System.out.println("Turn: " + turn); // TODO - print

		setupCurrPlayerDisplay();
		updatePlayerQueue(q1Img, q1Txt, playerRoster.get(1));
		if (playerRoster.size() >= 3) {
			updatePlayerQueue(q2Img, q2Txt, playerRoster.get(2));
		}
		if (playerRoster.size() >= 4) {
			updatePlayerQueue(q3Img, q3Txt, playerRoster.get(3));
		}

		actionTrackerMove.setFill(GREY);
		actionTrackerDraw.setFill(currPlayer.getColour());
		checkInflictions();

		takeSilkBagTileButton.setDisable(false);
		gameLog.appendText("Take a gift from Nesoi!\n");
	}

	/**
	 * Ends the 'Draw a tile' phase of the game when a floor tile has been slid into the board
	 *
	 * @param axis      - The axis the floor was slid on
	 * @param direction - The direction the floor was sliding on
	 * @param printNum  - The index of the axis
	 */
	private void endDrawTurn(String axis, String direction, int printNum) {
		silkBagTileImg.setFill(GREY);
		Floor tempFloor = (Floor) silkBagTile;
		tempFloor.setRotation(0);

		gameLog.appendText("Island slid at " + axis + " " + printNum + " from " + direction + ".\n");
		startPlayActionTurn();
	}

	/**
	 * Loads the 'play action tiles' section of the game
	 */
	private void startPlayActionTurn() {
		silkBagTileRotate.setDisable(true);
		setDisabledBoardArrows(true);
		takeActionButton.setDisable(false);
		skipActionButton.setDisable(false);

		actionTrackerDraw.setFill(GREY);
		actionTrackerPlay.setFill(currPlayer.getColour());
		gameLog.appendText("Cast an ability!\n");
	}

	/**
	 * Loads the movement section of the game
	 */
	private void startMoveActionTurn() {
		takeActionButton.setDisable(true);
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
			toggleRectDisable(boardImg[tile.getRow() + 1][tile.getColumn() + 1], true, Color.TRANSPARENT);
		}
		// Checks if move button should be disabled
		if (!isDoubleMoveUsed) {
			moveButton.setDisable(true);
		} else {
			gameLog.appendText("Double Move was cast upon you earlier, you can move again if you wish.\n");
			isDoubleMoveUsed = false;
		}
		endTurnButton.setDisable(false);
		isDoubleMoveUsed = false;
	}

	/**
	 * Method that runs when the game ends. Will announce the winner and exit from the game
	 */
	private void endGame() { // TODO - flesh out
		gameLog.appendText(currPlayer.getName() + " wins!!\nThank you for playing!!\n");
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
//		for (Player player : playerRoster) { TODO - print
//			System.out.println(player.getName() + "(" + player.getRowLoc() + ", " + player.getColumnLoc() + ")");
//		}

		silkBagTileImg.setRotate(0);
		silkBagTile = silkBag.drawTile();
		silkBagTileImg.setFill(new ImagePattern(silkBagTile.getImage()));
		// Sound effect when the button is clicked
		Media buttonSound = new Media(new File("resources/sounds/button.wav").toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
		mediaPlayer.play();

		// Check the type of the tile
		String tempTileType = silkBagTile.getTileType();
		if (tempTileType.equalsIgnoreCase("corner")
				|| tempTileType.equalsIgnoreCase("straight")
				|| tempTileType.equalsIgnoreCase("tee")) {
			// If is a floor type
			isNewTileAction = false;
			gameLog.appendText(currPlayer.getName() + " was gifted a new island!\n");
			takeSilkBagTileButton.setDisable(true);
			silkBagTileRotate.setDisable(false);
			setDisabledBoardArrows(false);
		} else if (tempTileType.equalsIgnoreCase("fire")
				|| tempTileType.equalsIgnoreCase("ice")
				|| tempTileType.equalsIgnoreCase("doubleMove")
				|| tempTileType.equalsIgnoreCase("backTrack")) {
			// If is action type
			isNewTileAction = true;
			gameLog.appendText(currPlayer.getName() + " was gifted a powerful ability!\n");
			takeSilkBagTileButton.setDisable(true);
			startPlayActionTurn();
		} else {
			gameLog.appendText("WARNING: Unknown gift has been drawn, please redraw a new tile.\n");
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
		Media buttonSound = new Media(new File("resources/sounds/button.wav").toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
		mediaPlayer.play();

		// Rotates the image since it is not 'locked' to tile
		silkBagTileImg.setRotate(tempFloor.getRotation());
		gameLog.appendText("Island rotated 90 degrees.\n");
	}

	/**
	 * Button that checks what action tiles the player has and enables the relevant buttons
	 */
	@FXML
	private void takeActionClick() {
		if (currPlayer.getHand().isEmpty()) {
			gameLog.appendText("You currently have no castable powers. Please skip, your time to shine will come.\n");
		} else {
			Paint colour = currPlayer.getColour();
			if (!currPlayerFireTxt.getText().equalsIgnoreCase("none")) {
				toggleRectDisable(fireButton, false, colour);
			}
			if (!currPlayerIceTxt.getText().equalsIgnoreCase("none")) {
				toggleRectDisable(iceButton, false, colour);
			}
			if (!currPlayerDoubleMoveTxt.getText().equalsIgnoreCase("none")) {
				toggleRectDisable(doubleMoveButton, false, colour);
			}
			if (!currPlayerBacktrackTxt.getText().equalsIgnoreCase("none")) {
				toggleRectDisable(backTrackButton, false, colour);
			}
			gameLog.appendText("Please select one of your available powers.\n");
			skipActionButton.setDisable(true);
		}
	}

	/**
	 * Button that will use a fire action tile
	 */
	@FXML
	private void fireClick() {
		try {
			currPlayer.playActionTile("fire");
			disableActionSelect();
			gameLog.appendText("Choose an island to cast FIRE on.\n");
			currPlayerBacktrackTxt.setText("Used");
			setSelectableTiles("fire");
			// Sound effect when the fire action has been played
			Media buttonSound = new Media(new File("resources/sounds/fire.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
			mediaPlayer.play();
		} catch(Exception e) {
			gameLog.appendText(e.getMessage());
		}

	}

	/**
	 * Button that will use a ice action tile
	 */
	@FXML
	private void iceClick() {
		try {
			currPlayer.playActionTile("ice");
			disableActionSelect();
			gameLog.appendText("Choose an island to cast ICE on.\n");
			currPlayerBacktrackTxt.setText("Used");
			setSelectableTiles("ice");
			//Sound effect for when ice action has been played
			Media buttonSound = new Media(new File("resources/sounds/ice.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
			mediaPlayer.play();
		} catch(Exception e) {
			gameLog.appendText(e.getMessage());
		}
	}

	/**
	 * Button that will use a double move action tile
	 */
	@FXML
	private void doubleMoveClick() {
		try {
			currPlayer.playActionTile("doubleMove");
			isDoubleMoveUsed = true;
			disableActionSelect();
			gameLog.appendText("Double Move was cast on " + currPlayer.getName()
					+ ". You can now move twice on this turn.\n");
			currPlayerBacktrackTxt.setText("Used");
			moveButton.setDisable(false);
		} catch(Exception e) {
			gameLog.appendText(e.getMessage());
		}
	}

	/**
	 * Button that will use a backtrack action tile
	 */
	@FXML
	private void backTrackClick() {
		try {
			currPlayer.playActionTile("backTrack");
			disableActionSelect();
			gameLog.appendText("Choose a fellow deity to cast BACKTRACK on.\n");
			currPlayerBacktrackTxt.setText("Used");

			for (Player player : playerRoster) {
				if (!player.isBacktracked()) {
					StackPane tempStack = boardImg[player.getRowLoc() + 1][player.getColumnLoc() + 1];
					toggleRectDisable(tempStack, false, currPlayer.getColour());
				}
			}
		} catch(Exception e) {
			gameLog.appendText(e.getMessage());
		}
	}

	/**
	 * Button to skip the play action turn and move onto the movement turn.
	 */
	@FXML
	private void skipActionClick() {
		gameLog.appendText(currPlayer.getName() + " skipped casting an ability.\n");
		startMoveActionTurn();
		Media buttonSound = new Media(new File("resources/sounds/button.wav").toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
		mediaPlayer.play();
	}

	/**
	 * Button that displays the movement options and allows player to choose
	 */
	@FXML
	private void moveClick() {
		try {
			playerMoves = currPlayer.possibleMoves(gameBoard);
			Media buttonSound = new Media(new File("resources/sounds/button.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
			mediaPlayer.play();
//			for (Floor floor : playerMoves) { TODO - print
//				System.out.println(floor.getRow() + "," + floor.getColumn() + ":"
//						+ floor.isNorth() + floor.isEast() + floor.isSouth() + floor.isWest());
//			}
//			System.out.println(currPlayer.getRowLoc() + "," + currPlayer.getColumnLoc() + ","
//					+ gameBoard.getTileAt(currPlayer.getRowLoc(), currPlayer.getColumnLoc()).isNorth() + ","
//					+ gameBoard.getTileAt(currPlayer.getRowLoc(), currPlayer.getColumnLoc()).isEast() + ","
//					+ gameBoard.getTileAt(currPlayer.getRowLoc(), currPlayer.getColumnLoc()).isSouth() + ","
//					+ gameBoard.getTileAt(currPlayer.getRowLoc(), currPlayer.getColumnLoc()).isWest() + ",");

			// Removing tiles with players on
			for (Player player : playerRoster) {
				playerMoves.remove(player.getCurrentFloor(gameBoard));
			}
			playerMoves.removeIf(Floor::getIsFire);

			if (!playerMoves.isEmpty()) {
				// Highlighting the available tiles
				for (Floor tile : playerMoves) {
					toggleRectDisable(boardImg[tile.getRow() + 1][tile.getColumn() + 1],
							false, currPlayer.getColour());
				}
			} else {
				gameLog.appendText("No movement possible, please end your turn.\n");
				endTurnButton.setDisable(false);
			}
		} catch (Exception e) {
			endTurnButton.setDisable(false);
		}
	}

	/**
	 * Button that will wraps up the current players turn and start the next player's turn
	 */
	@FXML
	private void endTurnClick() {
		if (currPlayer.getCurrentFloor(gameBoard).getIsGoal()) {
			endGame();
		}

		moveButton.setDisable(true); // Just in case it wasn't disabled earlier
		endTurnButton.setDisable(true);
		Media buttonSound = new Media(new File("resources/sounds/button.wav").toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
		mediaPlayer.play();

		// Add tile to hand if tile is an action tile and remove from screen
		if (isNewTileAction) {
			Action tempAction = (Action) silkBagTile;
			currPlayer.addActionTile(tempAction);
			silkBagTileImg.setFill(GREY);
			gameLog.appendText(currPlayer.getName() + "'s new ability has been added to their hand.\n");
		}

		playerRoster.remove(0);
		playerRoster.add(currPlayer);
		currPlayer = playerRoster.get(0);

		int round = (turn + playerRoster.size()) / playerRoster.size();
		gameLog.appendText("Round " + round + ": Next Deity - " + currPlayer.getName() + "!\n");
		startNextTurn();
	}

	public void openGameInstructions(ActionEvent actionEvent) {
	}

	public void quitGameFromMenu(ActionEvent actionEvent) {
	}
}
