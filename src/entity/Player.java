package entity;

import javafx.scene.image.Image;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

/**
 * Class that represents the players and their tokens within the game
 *
 * @author Nouran, Chris, Junjie.
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
	private boolean backtracked;

	// TODO - implement backtrack + bool check


	/**
	 * Constructor to create new player object
	 * @param name  - In-game name of the player token
	 * @param image - Image representing the player token
	 */
	public Player(String name, Image image, String hexColour, int xStart, int yStart, Board board, Profile profile) {
		this.NAME = profile.getPlayerName();
		this.IMAGE = image;
		this.COLOUR = Paint.valueOf(hexColour);
		this.HAND = new ArrayList<>();
		this.xLoc = xStart;
		this.yLoc = yStart;
		this.board = board;
		this.PROFILE = profile;
		this.backtracked = false;
	}


	/**
	 * Returns the in-game name of the player token
	 * @return - The name of the player token
	 */
	public String getName() {
		return NAME;
	}

	/**
	 * Returns the image associated with this player
	 * @return - This player's image
	 */
	public Image getImage() {
		return IMAGE;
	}

	/**
	 * Returns the colour associated with this player
	 * @return - The colour as a paint value
	 */
	public Paint getColour() {
		return COLOUR;
	}

	/**
	 * Return all the cards this player is holding
	 * @return - The player's hand of tiles
	 */
	public ArrayList<Action> getHand() {
		return HAND;
	}

	/**
	 * Returns the player's x coordinate
	 * @return - The player's x coordinate
	 */
	public int getXLoc() {
		return xLoc;
	}

	/**
	 * Returns the player's y coordinate
	 * @return - The player's y coordinate
	 */
	public int getYLoc() {
		return yLoc;
	}

	/**
	 * sets backtracked to true
	 */
	public void toggleBacktracked(){
		this.backtracked  = true;
	}
	/**
	 * Adds a action tile to this player's hand
	 * @param action - The action tile to be added to the hand
	 */
	public void addActionTile(Action action) {
		HAND.add(action);
	}

	/**
	 * Moves the player to the tile he selects if possible
	 * @param moveTo - The tile the player wants to move to
	 */
	public void movePlayer(Floor moveTo) {
		if (possibleMoves().contains(moveTo)) {
			storePosi();
			this.currentTile = moveTo;
		} else {
			throw new IndexOutOfBoundsException("ERROR: " + this.NAME + " cannot make this move");
		}
	}

	/**
	 * Stores the last 3 positions, used to backtrack the player
	 */
	public void storePosi(){
		lastPosiX[2] = lastPosiX[1];
		lastPosiY[2] = lastPosiX[1];
		lastPosiX[1] = lastPosiX[0];
		lastPosiY[1] = lastPosiY[0];
		lastPosiX[0] = xLoc;
		lastPosiY[0] = yLoc;
	}

	/**
	 * Returns all the possible moves a player can male from his current location
	 * @return an arrayList of all possible tiles he can move to
	 */
	public ArrayList<Floor> possibleMoves() {
		ArrayList<Floor> possibleMoves = new ArrayList<>();
		if (isEastPossible()){
			possibleMoves.add(board.getTileAt(xLoc+1, yLoc));
		}
		if (isWestPossible()) {
			possibleMoves.add(board.getTileAt(xLoc-1, yLoc));
		}
		if (isNorthPossible()){
			possibleMoves.add(board.getTileAt(xLoc, yLoc-1));
		}
		if (isSouthPossible()){
			possibleMoves.add(board.getTileAt(xLoc, yLoc+1));
		}
		return possibleMoves;
	}

	/**
	 * Checks if it is possible to move to the tile on the right
	 * @return true if it is possible to move east, false otherwise
	 */
	public boolean isEastPossible() { //right
		if (xLoc < board.getLength() - 1) {
			return board.getTileAt(xLoc,yLoc).isWest() == true && board.getTileAt(xLoc + 1, yLoc).isEast() == true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if it is possible to move to the tile on the left
	 * @return true if it is possible to move west, false otherwise
	 */
	public boolean isWestPossible(){
		if (xLoc > 0) {
			return board.getTileAt(xLoc,yLoc).isEast() == true && board.getTileAt(xLoc - 1, yLoc).isWest() == true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if it is possible to move to the tile above
	 * @return true if it is possible to move upwards, false otherwise
	 */
	public boolean isNorthPossible(){
		if (yLoc > 0) {
			return board.getTileAt(xLoc,yLoc).isSouth() == true && board.getTileAt(xLoc, yLoc - 1).isNorth() == true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if it is possible to move to the tile below
	 * @return true if it is possible to move down, false otherwise
	 */
	public boolean isSouthPossible(){
		if (yLoc < board.getHeight() - 1) {
			return board.getTileAt(xLoc,yLoc).isNorth() == true && board.getTileAt(xLoc, yLoc + 1).isSouth() == true;
		} else {
			return false;
		}
	}


	/**
	 *
	 * @param type - The type of the action he wants to play, if it is available
	 * @return tempAction - The action card that is being played
	 */
	public Action playActionTile(String type) throws NullPointerException {
		Action tempAction = null;
		for (Action actionTile: HAND) {
			if (actionTile.tileType.equalsIgnoreCase(type)) { //TODO fix this
				tempAction = actionTile;
			}
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
	 * This is going to be used when a backtrack tile is being played on them
	 * @throws NullPointerException if the player cannot be backtracked
	 */
	public void backtrack() throws NullPointerException{
		if (!backtracked) {
			if (!board.getTileAt(lastPosiX[1],lastPosiY[1]).getIsFire()) {
				xLoc = lastPosiX[1];
				yLoc = lastPosiY[1];
			} else if (board.getTileAt(lastPosiX[1],lastPosiY[1]).getIsFire()) {
				if (!board.getTileAt(lastPosiX[2],lastPosiY[2]).getIsFire()) {
					xLoc = lastPosiX[2];
					yLoc = lastPosiY[2];
				} else if  (board.getTileAt(lastPosiX[2],lastPosiY[2]).getIsFire()) {
					throw new NullPointerException("ERROR: " + this.NAME + " cannot be backtracked");
				}
			}
		} else {
			throw new NullPointerException("ERROR: " + this.NAME + " cannot be backtracked because " +
					"player has been backtracked");
		}
	}

}
