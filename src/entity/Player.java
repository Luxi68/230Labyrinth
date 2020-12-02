package entity;

import javafx.scene.image.Image;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

/**
 * Class that represents the players and their tokens within the game
 *
 * @author Junjie
 */
public class Player {
	private final String NAME;
	private final Image IMAGE;
	private final Paint COLOUR;
	private final Profile PROFILE;
	private final ArrayList<Action> HAND;
	private int xLoc;
	private int yLoc;
	private Tile currentTile;
	private boolean isGoal;
	private Board board;
	private int[] lastPosiX = new int[3];
	private int[] lastPosiY = new int[3];

	// TODO - implement backtrack + bool check


	/**
	 * Constructor to create new player object
	 *
	 * @param image - Image representing the player token
	 */
	public Player(Image image, String hexColour, int xStart, int yStart, Board board, Profile profile) {
		this.NAME = profile.getPlayerName();
		this.IMAGE = image;
		this.COLOUR = Paint.valueOf(hexColour);
		this.HAND = new ArrayList<>();
		this.xLoc = xStart;
		this.yLoc = yStart;
		this.board = board;
		this.PROFILE = profile;
	}


	/**
	 * Returns the in-game name of the player token
	 *
	 * @return - The name of the player token
	 */
	public String getName() {
		return NAME;
	}

	/**
	 * Returns the image associated with this player
	 *
	 * @return - This player's image
	 */
	public Image getImage() {
		return IMAGE;
	}

	/**
	 * Returns the colour associated with this player
	 *
	 * @return - The colour as a paint value
	 */
	public Paint getColour() {
		return COLOUR;
	}

	/**
	 * Return all the cards this player is holding
	 *
	 * @return - The player's hand of tiles
	 */
	public ArrayList<Action> getHand() {
		return HAND;
	}

	/**
	 * @return
	 */
	public int getXLoc() {
		return xLoc;
	}

	/**
	 * @return
	 */
	public int getYLoc() {
		return yLoc;
	}

	/**
	 * Adds a action tile to this player's hand
	 *
	 * @param action - The action tile to be added to the hand
	 */
	public void addActionTile(Action action) {
		HAND.add(action);
	}


	public void movePlayer(Floor moveTo) {
		if (possibleMoves().contains(moveTo)) {
			this.currentTile = moveTo;
			storePosi();
		} else {
			throw new IndexOutOfBoundsException("ERROR: " + this.NAME + " cannot make this move");
		}
	}

	public void storePosi() {
		lastPosiX[2] = lastPosiX[1];
		lastPosiY[2] = lastPosiX[1];
	}

	public ArrayList<Floor> possibleMoves() {
		ArrayList<Floor> possibleMoves = new ArrayList<>();
		if (isEastPossible()) {
			possibleMoves.add(board.getTileAt(xLoc + 1, yLoc));
		}
		if (isWestPossible()) {
			possibleMoves.add(board.getTileAt(xLoc - 1, yLoc));
		}
		if (isNorthPossible()) {
			possibleMoves.add(board.getTileAt(xLoc, yLoc - 1));
		}
		if (isSouthPossible()) {
			possibleMoves.add(board.getTileAt(xLoc, yLoc + 1));
		}
		return possibleMoves;
	}

	public boolean isEastPossible() { //right
		if (xLoc < board.getLength() - 1) {
			return board.getTileAt(xLoc, yLoc).isWest() == true && board.getTileAt(xLoc + 1, yLoc).isEast() == true;
		} else {
			return false;
		}
	}

	public boolean isWestPossible() { //left
		if (xLoc > 0) {
			return board.getTileAt(xLoc, yLoc).isEast() == true && board.getTileAt(xLoc - 1, yLoc).isWest() == true;
		} else {
			return false;
		}
	}

	public boolean isNorthPossible() {
		if (yLoc > 0) {
			return board.getTileAt(xLoc, yLoc).isSouth() == true && board.getTileAt(xLoc, yLoc - 1).isNorth() == true;
		} else {
			return false;
		}
	}

	public boolean isSouthPossible() {
		if (yLoc < board.getHeight() - 1) {
			return board.getTileAt(xLoc, yLoc).isNorth() == true && board.getTileAt(xLoc, yLoc + 1).isSouth() == true;
		} else {
			return false;
		}
	}


	/**
	 * @param type
	 * @return
	 */
	public Action playActionTile(String type) {
		Action tempAction = null;
		for (Action actionTile : HAND) {
			if (actionTile.tileType.equalsIgnoreCase(type)) {
				tempAction = actionTile;
			}
			break;
		}

		if (tempAction == null) {
			throw new NullPointerException("ERROR: " + this.NAME + " does not hold any " + type + " action tiles.");
		} else {
			HAND.remove(tempAction);
			//add tempAction to discarded section in silkbag
		}
		return tempAction;
	}


	/**
	 * @param board
	 */
	public void moveN(Board board) {
		if (yLoc == board.getHeight() - 1) {
			throw new IndexOutOfBoundsException("ERROR: " + this.NAME + " is already at the top of the board.");
		} else {
			this.yLoc--;
		}
	}

	/**
	 * @param board
	 */
	public void moveE(Board board) {
		if (xLoc == board.getLength() - 1) {
			throw new IndexOutOfBoundsException("ERROR: " + this.NAME + " is already at the edge of the board.");
		} else {
			this.xLoc++;
		}
	}

	/**
	 *
	 */
	public void moveS() {
		if (yLoc == 0) {
			throw new IndexOutOfBoundsException("ERROR: " + this.NAME + " is already at the bottom of the board.");
		} else {
			this.yLoc++;
		}
	}

	/**
	 *
	 */
	public void moveW() {
		if (xLoc == 0) {
			throw new IndexOutOfBoundsException("ERROR: " + this.NAME + " is already at the edge of the board.");
		} else {
			this.xLoc--;
		}
	}

}
