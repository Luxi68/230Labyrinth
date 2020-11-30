package entity;

import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import java.util.ArrayList;

/**
 * Class that represents the players and their tokens within the game
 *
 * @author Junjie
 */
public class Player { // TODO - finish class + javadoc
	private final String NAME;
	private final Image IMAGE;
	private final Paint COLOUR;
	private final ArrayList<Action> HAND;
	private int xLoc;
	private int yLoc;

	/**
	 * Constructor to create new player object
	 *
	 * @param name  - In-game name of the player token
	 * @param image - Image representing the player token
	 */
	public Player(String name, Image image, String hexColour, int xStart, int yStart) {
		this.NAME = name;
		this.IMAGE = image;
		this.COLOUR = Paint.valueOf(hexColour);
		HAND = new ArrayList<>();
		this.xLoc = xStart;
		this.yLoc = yStart;
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
	public Paint getCOLOUR() {
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

	public void moveN(Board board) throws Exception {
		if (yLoc == board.getHEIGHT() -1) {
			throw new IndexOutOfBoundsException(this.NAME + " is already at the top of the board.");
		} else {
			this.yLoc++;
		}
	}

	public void moveE(Board board) throws Exception {
		if (xLoc == board.getLENGTH() -1) {
			throw new IndexOutOfBoundsException(this.NAME + " is already at the edge of the board.");
		} else {
			this.xLoc++;
		}
	}

	public void moveS() throws Exception {
		if (yLoc == 0) {
			throw new IndexOutOfBoundsException(this.NAME + " is already at the bottom of the board.");
		} else {
			this.yLoc--;
		}
	}

	public void moveW() throws Exception {
		if (xLoc == 0) {
			throw new IndexOutOfBoundsException(this.NAME + " is already at the edge of the board.");
		} else {
			this.xLoc--;
		}
	}
}
