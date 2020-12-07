package entity;

import javafx.scene.image.Image;
import javafx.scene.paint.Paint;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class that represents the players and their tokens within the game
 *
 * @author Nouran, Chris, Junjie.
 */
public class Player implements Serializable {
	private final String NAME;
	private final Profile PROFILE;
	private final ArrayList<Action> HAND;
	private int rowLoc;
	private int columnLoc;
	private int[] lastPosiRow = new int[3];
	private int[] lastPosiColumn = new int[3];
	private boolean backtracked;
	private String hexColour;

	// TODO - implement backtrack + bool check


	/**
	 * Constructor to create new player object
	 *
	 * @param hexColour - Image representing the player token
	 */
	public Player(String hexColour, int rowStart, int columnStart, Board board, Profile profile) {
		this.NAME = profile.getPlayerName();
		this.HAND = new ArrayList<>();
		this.rowLoc = rowStart;
		this.columnLoc = columnStart;
		this.PROFILE = profile;
		this.backtracked = false;
		this.hexColour = hexColour;
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
		Image img;
		switch(hexColour) {
			case "#b53232":
				img = new Image("/assets/aries.png");
				break;
			case "#c677b3":
				img = new Image("/assets/aphrodite.png");
				break;
			case "#55b54c":
				img = new Image("/assets/apollo.png");
				break;
			default:
				img = new Image("/assets/artemis.png");
		}
		return img;
	}

	/**
	 * Returns the colour associated with this player
	 *
	 * @return - The colour as a paint value
	 */
	public Paint getColour() {
		return Paint.valueOf(hexColour);
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
	 * Returns the row the player is on
	 *
	 * @return -  the row the player is on
	 */
	public int getRowLoc() {
		return rowLoc;
	}

	/**
	 * Returns the column the player is on
	 *
	 * @return - the column the player is on
	 */
	public int getColumnLoc() {
		return columnLoc;
	}

	/**
	 * @param board the board where we are looking
	 * @return the co ords of the player on the board
	 */
	public Floor getCurrentFloor(Board board) {
		return board.getTileAt(this.rowLoc, this.columnLoc);
	}

	/**
	 *
	 * @return whether the player has been backtracked or not
	 */
	public boolean isBacktracked() {
		return backtracked;
	}

	/**
	 * sets backtracked to true
	 */
	public void toggleBacktracked() {
		this.backtracked = true;
	}

	/**
	 * Adds a action tile to this player's hand
	 *
	 * @param action - The action tile to be added to the hand
	 */
	public void addActionTile(Action action) {
		HAND.add(action);
	}

	/**
	 * Moves the player to the tile he selects if possible
	 *
	 * @param moveTo - The tile the player wants to move to
	 */
	public void movePlayer(Board board, Floor moveTo) {
		this.rowLoc = moveTo.getRow();
		this.columnLoc = moveTo.getColumn();
		storePosi();
	}

	/**
	 * Returns all the possible moves a player can male from his current location
	 *
	 * @return an arrayList of all possible tiles he can move to
	 */
	public ArrayList<Floor> possibleMoves(Board board) {
		ArrayList<Floor> possibleMoves = new ArrayList<>();

		if (isEastPossible(board)) {
			possibleMoves.add(board.getTileAt(rowLoc, columnLoc + 1));
		}
		if (isWestPossible(board)) {
			possibleMoves.add(board.getTileAt(rowLoc, columnLoc - 1));
		}
		if (isNorthPossible(board)) {
			possibleMoves.add(board.getTileAt(rowLoc - 1, columnLoc));
		}
		if (isSouthPossible(board)) {
			possibleMoves.add(board.getTileAt(rowLoc + 1, columnLoc));
		}
		return possibleMoves;
	}

	/**
	 * Stores the last 3 positions, used to backtrack the player
	 */
	private void storePosi() {
		lastPosiRow[2] = lastPosiRow[1];
		lastPosiColumn[2] = lastPosiRow[1];
		lastPosiRow[1] = lastPosiRow[0];
		lastPosiColumn[1] = lastPosiColumn[0];
		lastPosiRow[0] = rowLoc;
		lastPosiColumn[0] = columnLoc;
	}

	/**
	 * Checks if it is possible to move to the tile on the right
	 *
	 * @return true if it is possible to move east, false otherwise
	 */
	public boolean isEastPossible(Board board) { //right
		if (columnLoc != board.getLength() - 1) {
			return board.getTileAt(rowLoc, columnLoc).isEast()
					&& board.getTileAt(rowLoc, columnLoc + 1).isWest();
		} else {
			return false;
		}
	}

	/**
	 * Checks if it is possible to move to the tile on the left
	 *
	 * @return true if it is possible to move west, false otherwise
	 */
	public boolean isWestPossible(Board board) {
		if (columnLoc != 0) {
			return board.getTileAt(rowLoc, columnLoc).isWest()
					&& board.getTileAt(rowLoc, columnLoc - 1).isEast();
		} else {
			return false;
		}
	}

	/**
	 * Checks if it is possible to move to the tile above
	 *
	 * @return true if it is possible to move upwards, false otherwise
	 */
	public boolean isNorthPossible(Board board) {
		if (rowLoc != 0) {
			return board.getTileAt(rowLoc, columnLoc).isNorth()
					&& board.getTileAt(rowLoc - 1, columnLoc).isSouth();
		} else {
			return false;
		}
	}

	/**
	 * Checks if it is possible to move to the tile below
	 *
	 * @return true if it is possible to move down, false otherwise
	 */
	public boolean isSouthPossible(Board board) {
		if (rowLoc != board.getHeight() - 1) {
			return board.getTileAt(rowLoc, columnLoc).isSouth()
					&& board.getTileAt(rowLoc + 1, columnLoc).isNorth();
		} else {
			return false;
		}
	}


	/**
	 * @param type - The type of the action he wants to play, if it is available
	 * @return tempAction - The action card that is being played
	 */
	public Action playActionTile(String type) throws NullPointerException {
		Action tempAction = null;
		for (Action actionTile : HAND) {
			if (actionTile.tileType.equalsIgnoreCase(type)) { //TODO fix this
				tempAction = actionTile;
			}
		}
		if (tempAction == null) {
			throw new NullPointerException(
					"WARNING: " + this.NAME + " does not hold any " + type + " abilities.\n");
		} else {
			HAND.remove(tempAction);
			//add tempAction to discarded section in silkbag
		}
		return tempAction;
	}

	/**
	 * This is going to be used when a backtrack tile is being played on them
	 *
	 * @throws NullPointerException if the player cannot be backtracked
	 */
	public void backtrack(Board board) throws NullPointerException {
		if (!backtracked) {
			if (!board.getTileAt(lastPosiRow[1], lastPosiColumn[1]).getIsFire()) {
				rowLoc = lastPosiRow[1];
				columnLoc = lastPosiColumn[1];
				if (!board.getTileAt(lastPosiRow[2], lastPosiColumn[2]).getIsFire()) {
					rowLoc = lastPosiRow[2];
					columnLoc = lastPosiColumn[2];
				} else {
					throw new NullPointerException("WARNING: " + this.NAME + " can only be backtracked once.\n");
				}
			} else {
				throw new NullPointerException("WARNING: " + this.NAME
						+ "'s previous tile is on fire and thus cannot be moved.\n");
			}
		} else {
			throw new NullPointerException(
					"WARNING: Backtrack cannot be used on " + this.NAME + " as they already been backtracked.\n");
		}
	}

}
