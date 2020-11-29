import javafx.scene.image.Image;

public class Player { // TODO - finish class
	private final String NAME;
	private final int ORDER;
	private final Image IMAGE;

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
}
