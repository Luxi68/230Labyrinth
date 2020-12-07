package controller;

import entity.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Class that holds the actions and logic behind GameScreen.fxml
 *
 * @author Junjie, Rhys
 */
public class GameScreenController implements Initializable {
	private final Paint GREY = Paint.valueOf("#3f443e");
	private MediaPlayer mediaPlayer1;
	// Game components
	private int boardNum; // Which board is being played
	private Board gameBoard;
	private SilkBag silkBag;
	private Tile silkBagTile;
	private ArrayList<Player> playerRoster;
	private Player currPlayer;
	// Game info
	private Player playerWon;
	private int turn;
	private ArrayList<Floor> playerMoves;
	private int boardRows; // Board height + 2 for arrow buttons
	private int boardColumns; // Board length + 2 for arrow buttons
	private StackPane[][] boardImg; // A 2D array for referencing gridPane contents. Is one step bigger all the way around
	private ArrayList<Floor> inflictableTiles;
	private ArrayList<Floor> fireInfectedTiles;
	private ArrayList<Floor> iceInfectedTiles;
	private ArrayList<Integer> rowNoFixed;
	private ArrayList<Integer> columnNoFixed;
	// Game checks
	private boolean isNewTileAction;
	private boolean isDoubleMoveUsed;

	@FXML
	private BorderPane borderPane;
	@FXML
	private AnchorPane anchorPaneCentre;
	@FXML
	private Slider volumeSlider;
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
			borderPane.setStyle("-fx-background-image: url('assets/mount.png');" + "-fx-background-size: cover");
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
		backgroundMusic("gameScreenBackground");
		volumeSlider.setShowTickLabels(true);
		volumeSlider.setShowTickMarks(true);
		volumeSlider.setValue(mediaPlayer1.getVolume() * 100);
		volumeSlider.valueProperty().addListener(
				observable -> mediaPlayer1.setVolume(volumeSlider.getValue() / 100));
	}

	/**
	 * Method to play background music
	 *
	 * @param filename the name of the music file that requested
	 */
	public void backgroundMusic(String filename) {
		Media backgroundSound = new Media(
				new File("resources/sounds/" + filename + ".wav").toURI().toString());
		mediaPlayer1 = new MediaPlayer(backgroundSound);
		mediaPlayer1.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer1.setVolume(0.1);
		mediaPlayer1.setAutoPlay(true);
	}

	/**
	 * Method to play a chosen sound effect
	 *
	 * @param filename - Name of the sound effect file to be played
	 * @param volume   - Volume the sound is to be played at
	 */
	public void playSoundEffect(String filename, double volume) {
		Media effect = new Media(new File("resources/sounds/" + filename + ".wav").toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(effect);
		mediaPlayer.play();
		mediaPlayer.setVolume(volume);
	}

	/**
	 * Initialises data necessary to setup game
	 */
	public void initData(ArrayList<Object> data) {
		gameBoard = (Board) data.get(0);
		silkBag = (SilkBag) data.get(1);
		boardNum = (int) data.get(5);

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

						playSoundEffect("footstep", 3);
						gameLog.appendText(currPlayer.getName() + " moved to island ("
								+ (finalJ - 1) + ", " + (finalI - 1) + ").\n");
						checkEndMove();

					} else if (actionTrackerPlay.getFill() == currPlayer.getColour()) {
						if (colour == currPlayer.getColour()) {
							Player victim = checkPlayerLoc(finalI - 1, finalJ - 1);
							// Erase buttons highlights
							for (Player player : playerRoster) {
								toggleRectDisable(boardImg[player.getRowLoc() + 1][player.getColumnLoc() + 1]
										, true, Color.TRANSPARENT);
							}
							if (victim != null) {
								try {
									setPlayerImg(null,
											victim.getRowLoc() + 1, victim.getColumnLoc() + 1);
									victim.backtrack(gameBoard);
									setPlayerImg(victim.getImage(),
											victim.getRowLoc() + 1, victim.getColumnLoc() + 1);
									victim.toggleBacktracked();
									gameLog.appendText(victim.getName() + " was forcibly moved back in time.\n");
									playSoundEffect("backtrack", 0.6);
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
								playSoundEffect("fire", 0.5);

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
								playSoundEffect("ice", 1);

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
		insertTileButton.setFill(Color.BLACK);
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

		// What happens if the arrow tile buttons are clicked
		stack.setOnMouseClicked(event -> {
			// The index of the gridpane referencing the images and arrows
			int rowImg = GridPane.getRowIndex(stack);
			int colImg = GridPane.getColumnIndex(stack);
			// The index of the board referencing the floor tiles
			int rowPhy = rowImg - 1;
			int colPhy = colImg - 1;
			Floor ejectedTile;
			Player ejectedPlayer;
			Floor insertedTile = (Floor) silkBagTile;
			String axis;
			String direction;

			if (row == 0 || row == boardRows - 1) {
				axis = "longitude";
				// Move all the tiles along and return the ejected tile
				if (row == 0) {
					try {
						direction = "north";
						ejectedTile = gameBoard.insertFromTop(insertedTile, colPhy);
						silkBag.addTile(true, ejectedTile); //tile is removed
						ejectedPlayer = checkPlayerLoc(boardRows - 3, colPhy);

						// Loop up through the column to update all images
						for (int i = boardRows - 2; i > 0; i--) {
							updateTileImgs(i, colImg);
							Player token = checkPlayerLoc(i - 1, colPhy);
							if (token != null) {
								setPlayerImg(null, i, colImg);
								if (i != boardRows - 2) {
									// set image of tile below
									setPlayerImg(token.getImage(), i + 1, colImg);
									token.movePlayer(gameBoard, gameBoard.getTileAt(i, colPhy));
								}
							}
						}
						if (ejectedPlayer != null) {
							ejectedPlayer.movePlayer(gameBoard, gameBoard.getTileAt(0, colPhy));
							setPlayerImg(ejectedPlayer.getImage(), 1, colImg);
						}
						playSoundEffect("wind", 1);
						endDrawTurn(axis, direction, colPhy);
					} catch (Exception e) {
						gameLog.appendText(e.getMessage());
					}
				} else {
					try {
						direction = "south";
						ejectedTile = gameBoard.insertFromBottom(insertedTile, colPhy);
						silkBag.addTile(true, ejectedTile); //tile is removed
						ejectedPlayer = checkPlayerLoc(0, colPhy);

						// Loop down through the column to update all images
						for (int i = 1; i < boardRows - 1; i++) {
							updateTileImgs(i, colImg);
							Player token = checkPlayerLoc(i - 1, colPhy);
							if (token != null) {
								setPlayerImg(null, i, colImg);
								if (i != 1) {
									// set image of tile above
									setPlayerImg(token.getImage(), i - 1, colImg);
									token.movePlayer(gameBoard, gameBoard.getTileAt(i - 2, colPhy));
								}
							}
						}
						if (ejectedPlayer != null) {
							ejectedPlayer.movePlayer(gameBoard, gameBoard.getTileAt(boardRows - 3, colPhy));
							setPlayerImg(ejectedPlayer.getImage(), boardRows - 2, colImg);

						}
						playSoundEffect("wind", 1);
						endDrawTurn(axis, direction, colPhy);
					} catch (Exception e) {
						gameLog.appendText(e.getMessage());
					}
				}
			} else if (column == 0 || column == boardColumns - 1) { // Left column
				axis = "latitude";
				// Move all the tiles along and return the ejected tile
				if (column == 0) {
					try {
						direction = "west";
						ejectedTile = gameBoard.insertFromLeft(insertedTile, rowPhy);
						silkBag.addTile(true, ejectedTile);
						ejectedPlayer = checkPlayerLoc(rowPhy, boardColumns - 3);

						// Loop right through the row to update all images
						for (int i = boardColumns - 2; i > 0; i--) {
							updateTileImgs(rowImg, i);
							Player token = checkPlayerLoc(rowPhy, i - 1);
							if (token != null) {
								setPlayerImg(null, rowImg, i);
								if (i != boardColumns - 2) {
									// set image of tile right
									setPlayerImg(token.getImage(), rowImg, i + 1);
									token.movePlayer(gameBoard, gameBoard.getTileAt(rowPhy, i));
								}
							}
						}
						if (ejectedPlayer != null) {
							ejectedPlayer.movePlayer(gameBoard, gameBoard.getTileAt(rowPhy, 0));
							setPlayerImg(ejectedPlayer.getImage(), rowImg, 1);
						}
						playSoundEffect("wind", 1);
						endDrawTurn(axis, direction, rowPhy);
					} catch (Exception e) {
						gameLog.appendText(e.getMessage());
					}
				} else {
					try {
						direction = "east";
						ejectedTile = gameBoard.insertFromRight(insertedTile, rowPhy);
						silkBag.addTile(true, ejectedTile);
						ejectedPlayer = checkPlayerLoc(rowPhy, 0);

						// Loop left through the row to update all images
						for (int i = 1; i < boardColumns - 1; i++) {
							updateTileImgs(rowImg, i);
							Player token = checkPlayerLoc(rowPhy, i - 1);
							if (token != null) {
								setPlayerImg(null, rowImg, i);
								if (i != 1) {
									// set image of tile left
									setPlayerImg(token.getImage(), rowImg, i - 1);
									token.movePlayer(gameBoard, gameBoard.getTileAt(rowPhy, i - 2));
								}
							}
						}
						if (ejectedPlayer != null) {
							ejectedPlayer.movePlayer(gameBoard, gameBoard.getTileAt(rowPhy, boardColumns - 3));
							setPlayerImg(ejectedPlayer.getImage(), rowImg, boardColumns - 2);
						}
						playSoundEffect("wind", 1);
						endDrawTurn(axis, direction, rowPhy);
					} catch (Exception e) {
						gameLog.appendText(e.getMessage());
					}
				}
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
		Rectangle tempFire = (Rectangle) tempStack.getChildren().get(1);
		if (tempFloor.getIsFire()) {
			tempFire.setOpacity(1);
		} else {
			tempFire.setOpacity(0);
		}
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
	}

	/**
	 * Loads in a new player and the 'take an tile' section of the game
	 */
	private void startNextTurn() {
		turn++;

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
	private void endGame() throws IOException { // TODO - flesh out
		playerWon = currPlayer;
		updateProfiles();
		Alert errorInfo = new Alert(Alert.AlertType.INFORMATION);
		errorInfo.setTitle("Game Over");
		errorInfo.setHeaderText("GAME WON!!!");
		errorInfo.setContentText(playerWon.getName() + " wins!!\nThank you for playing!!\n");
		errorInfo.show();

		returnToMain();
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
		silkBagTile = silkBag.drawTile();
		silkBagTileImg.setRotate(0);
		silkBagTileImg.setFill(new ImagePattern(silkBagTile.getImage()));
		playSoundEffect("button", 1);

		// Check the type of the tile
		String tempTileType = silkBagTile.getTileType();
		if (tempTileType.equalsIgnoreCase("corner")
				|| tempTileType.equalsIgnoreCase("straight")
				|| tempTileType.equalsIgnoreCase("tee")) {
			// If is a floor type
			Floor tempFloor = (Floor) silkBagTile;
			tempFloor.setRotation(0);
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
		playSoundEffect("button", 1);

		// Rotates the image since it is not 'locked' to tile
		silkBagTileImg.setRotate(tempFloor.getRotation());
		gameLog.appendText("Island rotated 90 degrees.\n");
	}

	/**
	 * Button that checks what action tiles the player has and enables the relevant buttons
	 */
	@FXML
	private void takeActionClick() {
		playSoundEffect("button", 1);
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
			Action playedAction = currPlayer.playActionTile("fire");
			silkBag.addTile(true, playedAction);
			disableActionSelect();
			gameLog.appendText("Choose an island to cast FIRE on.\n");
			currPlayerBacktrackTxt.setText("Used");
			playSoundEffect("magic", 2);
			setSelectableTiles("fire");
		} catch (Exception e) {
			gameLog.appendText(e.getMessage());
		}

	}

	/**
	 * Button that will use a ice action tile
	 */
	@FXML
	private void iceClick() {
		try {
			Action playedAction = currPlayer.playActionTile("ice");
			silkBag.addTile(true, playedAction);
			disableActionSelect();
			gameLog.appendText("Choose an island to cast ICE on.\n");
			currPlayerBacktrackTxt.setText("Used");
			setSelectableTiles("ice");
			playSoundEffect("magic", 2);
		} catch (Exception e) {
			gameLog.appendText(e.getMessage());
		}
	}

	/**
	 * Button that will use a double move action tile
	 */
	@FXML
	private void doubleMoveClick() {
		try {
			Action playedAction = currPlayer.playActionTile("doubleMove");
			silkBag.addTile(true, playedAction);
			isDoubleMoveUsed = true;
			disableActionSelect();
			gameLog.appendText("Double Move was cast on " + currPlayer.getName()
					+ ". You can now move twice on this turn.\n");
			currPlayerBacktrackTxt.setText("Used");
			startMoveActionTurn();
			playSoundEffect("magic", 2);
		} catch (Exception e) {
			gameLog.appendText(e.getMessage());
		}
	}

	/**
	 * Button that will use a backtrack action tile
	 */
	@FXML
	private void backTrackClick() {
		try {
			Action playedAction = currPlayer.playActionTile("backTrack");
			silkBag.addTile(true, playedAction);
			disableActionSelect();
			gameLog.appendText("Choose a fellow deity to cast BACKTRACK on.\n");
			currPlayerBacktrackTxt.setText("Used");
			playSoundEffect("magic", 2);

			for (Player player : playerRoster) {
				if (!player.isBacktracked()) {
					StackPane tempStack = boardImg[player.getRowLoc() + 1][player.getColumnLoc() + 1];
					toggleRectDisable(tempStack, false, currPlayer.getColour());
				}
			}
		} catch (Exception e) {
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
		playSoundEffect("button", 1);
	}

	/**
	 * Button that displays the movement options and allows player to choose
	 */
	@FXML
	private void moveClick() {
		try {
			playerMoves = currPlayer.possibleMoves(gameBoard);
			playSoundEffect("button", 1);

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
			playSoundEffect("yay", 0.8);
		} else {
			moveButton.setDisable(true); // Just in case it wasn't disabled earlier
			endTurnButton.setDisable(true);
			playSoundEffect("button", 1);

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
	}

	/**
	 * Setup a pane to allow game saving.
	 */
	@FXML
	private void saveGameButton() {
		// Setup the popup
		VBox saveGameScreen = new VBox(10);
		saveGameScreen.setPadding(new Insets(10, 10, 10, 10));
		saveGameScreen.setAlignment(Pos.CENTER);
		Label header = new Label("Save Game");
		Text text = new Text("Please name your savefile: ");
		TextField filename = new TextField();
		Button saveAndQuitButton = new Button("Save and Quit");
		Button returnButton = new Button("Return to Game");
		saveGameScreen.getChildren().addAll(header, filename, saveAndQuitButton, returnButton);
		// Open the popup
		borderPane.setEffect(new GaussianBlur());
		Stage popupStage = new Stage(StageStyle.TRANSPARENT);
		popupStage.setScene(new Scene(saveGameScreen, Color.TRANSPARENT));
		popupStage.setAlwaysOnTop(true);
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.show();

		saveAndQuitButton.setOnAction(e -> {
			if (filename.getText().isEmpty()) {
				// Sound effect for an error pop up
				playSoundEffect("nope", 3);
			} else {
				// Code to save file
				Platform.exit();
			}
		});

		returnButton.setOnAction(e -> {
			playSoundEffect("button", 1);
			borderPane.setEffect(null);
			popupStage.hide();
		});
	}

	/**
	 * Button to return to the main menu
	 */
	@FXML
	private void returnToMain() {
		try {
			URL url = getClass().getResource("/scene/StartScreen.fxml");
			FXMLLoader loader = new FXMLLoader(url);
			Parent startScreenParent = loader.load();
			startScreenParent.setStyle("-fx-background-image: url('assets/mount.png');" + "-fx-background-size: cover");
			Stage startScreenScene = (Stage) endTurnButton.getScene().getWindow();
			startScreenScene.setTitle("The First Olympian");
			startScreenScene.setScene(new Scene(startScreenParent, 640, 371));
			startScreenScene.show();
		} catch (IOException e) {
			System.out.println("Error starting the Game Screen from new game screen.");
			e.printStackTrace();
		}
		mediaPlayer1.stop();
	}

	/**
	 * Button that quits the game when pressed
	 */
	@FXML
	private void quitGameFromMenuButton() {
		playSoundEffect("button", 1);
		Platform.exit();
	}

	/**
	 * Opens the game instructions
	 */
	@FXML
	private void openGameInstructionsButton() throws FileNotFoundException {
		playSoundEffect("button", 1);
		// Setup text
		File instructions = new File("src/Instructions.txt");
		StringBuilder outputText = new StringBuilder();
		Scanner in;
		in = new Scanner(instructions);
		while (in.hasNextLine()) {
			outputText.append(in.nextLine()).append(System.lineSeparator());
		}

		// Setup pane
		VBox helpPage = new VBox(10);
		helpPage.setPadding(new Insets(10, 10, 10, 10));
		helpPage.setAlignment(Pos.CENTER);
		Label header = new Label("Instructions");
		TextArea textArea = new TextArea(outputText.toString());
		Button returnButton = new Button("Return to Game");
		helpPage.getChildren().addAll(header, textArea, returnButton);

		// Open the popup
		borderPane.setEffect(new GaussianBlur());
		Stage popupStage = new Stage(StageStyle.TRANSPARENT);
		popupStage.setScene(new Scene(helpPage, Color.TRANSPARENT));
		popupStage.setAlwaysOnTop(true);
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.show();

		returnButton.setOnAction(e -> {
			playSoundEffect("button", 1);
			borderPane.setEffect(null);
			popupStage.hide();
		});
	}

	/**
	 * Updates the profile statistics
	 *
	 * @throws IOException - If improper data has been added
	 */
	public void updateProfiles() throws IOException {
		ArrayList<String> updatedFileContent = new ArrayList<>();
		Scanner in;
		for(int i=0; i<playerRoster.size(); i++){
			File currentProfileFile = new File("resources/users/"+playerRoster.get(i).getName()+".txt");
			FileWriter wr = new FileWriter(currentProfileFile);
			in = new Scanner(currentProfileFile);
			while(in.hasNextLine()){
				String currentLine = in.nextLine();
				updatedFileContent.add(currentLine);
			}
			switch (boardNum){
				case 1:
					String knossosData = updatedFileContent.get(1);
					String[] knossosSplitted = knossosData.split(",");
					if(playerRoster.get(i)==playerWon){
						knossosSplitted[1] += 1;
						knossosSplitted[2] += 1;
					}else{
						knossosSplitted[2] += 1;
					}
					String knossosLineToBeWritten = String.join(",",knossosSplitted);
					updatedFileContent.set(1,knossosLineToBeWritten);
					for (String s : updatedFileContent) {
						wr.write(s);
						wr.write(System.lineSeparator());
					}
					break;
				case 2:
					String marathonData= updatedFileContent.get(2);
					String[] marathonSplitted = marathonData.split(",");
					if(playerRoster.get(i)==playerWon){
						marathonSplitted[1] += 1;
						marathonSplitted[2] += 1;
					}else{
						marathonSplitted[2] += 1;
					}
					String marathonLineToBeWritten = String.join(",",marathonSplitted);
					updatedFileContent.set(1,marathonLineToBeWritten);
					for (String s : updatedFileContent) {
						wr.write(s);
						wr.write(System.lineSeparator());
					}
					break;
				case 3:
					String spartaData= updatedFileContent.get(3);
					String[] spartaSplitted = spartaData.split(",");
					if(playerRoster.get(i)==playerWon){
						spartaSplitted[1] += 1;
						spartaSplitted[2] += 1;
					}else{
						spartaSplitted[2] += 1;
					}
					String spartaLineToBeWritten = String.join(",",spartaSplitted);
					updatedFileContent.set(1,spartaLineToBeWritten);
					for (String s : updatedFileContent) {
						wr.write(s);
						wr.write(System.lineSeparator());
					}
					break;
			}
		}
	}
}
