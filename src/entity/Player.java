package entity;

import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Class that represents the players and their tokens within the game
 *
 * @author Junjie
 */
public class Player { // TODO - finish class
	private final String NAME;
	private final int ORDER;
	private final Image IMAGE;
	private ArrayList<Action> hand;

	/**
	 * Constructor to create new player object
	 *
	 * @param name  - In-game name of the player token
	 * @param order - Order of play for this player token
	 * @param image - Image representing the player token
	 */
	public Player(String name, int order, Image image) {
		this.NAME = name;
		this.ORDER = order;
		this.IMAGE = image;
		hand = new ArrayList<>();
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
	 * Returns the order that this player token acts at
	 *
	 * @return - The order of play for the player
	 */
	public int getOrder() {
		return ORDER;
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
	 * Return all the cards this player is holding
	 *
	 * @return - The player's hand of tiles
	 */
	public ArrayList<Action> getHand() {
		return hand;
	}

	/**
	 * Adds a action tile to this player's hand
	 *
	 * @param action - The action tile to be added to the hand
	 */
	public void addActionTile(Action action) {
		hand.add(action);
	}
}
