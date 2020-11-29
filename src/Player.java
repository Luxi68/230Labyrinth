/**
 * Class that represents the players and their tokens within the game
 *
 * @author Junjie
 */

import javafx.scene.image.Image;
import java.util.ArrayList;

public class Player { // TODO - finish class
	private final String NAME;
	private final int ORDER;
	private final Image IMAGE;
	private ArrayList<Action> hand;

	/**
	 * Constructor to create new player object
	 * @param name - In-game name of the player token
	 * @param order - Order of play for this player token
	 * @param image - Image representing the player token
	 */
	public Player(String name, int order, Image image) {
		this.NAME = name;
		this.ORDER = order;
		this.IMAGE = image;
		hand = new ArrayList<>();
	}

	public String getName() {
		return NAME;
	}

	public int getOrder() {
		return ORDER;
	}

	public Image getImage() {
		return IMAGE;
	}

	public ArrayList<Action> getHand() {
		return hand;
	}

	public void addHand(ArrayList<Action> hand) {
		this.hand = hand;
	}
}
