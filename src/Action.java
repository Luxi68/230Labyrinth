import javafx.scene.image.Image;

/**
 * Action.java
 * This class defines the action tiles and declares what type of action tile is being used.
 * @author Chris, Ryan and Nouran
 *
 */
public class Action extends Tile {
	
	private Type actionType;
	private Image actionImage;
	
	/**
	 * The constructor initialising the type of action tile it is.
	 * @param tileType inherited from Tile.java to see which action tile it is.
	 */
	public Action(String tileType, Image image) {
		super(tileType, image);

		this.actionImage = image;
		if (tileType.equalsIgnoreCase("fire")) {
			this.actionType = Type.fire;
		} else if (tileType.equalsIgnoreCase("ice")) {
			this.actionType = Type.ice;
		} else if (tileType.equalsIgnoreCase("doubleMove")) {
			this.actionType = Type.doubleMove;
		} else if (tileType.equalsIgnoreCase("backTrack")) {
			this.actionType = Type.backTrack;
		}
	}
	
	/**
	 * 
	 * The different types of action that can be used
	 *
	 */
	enum Type {
		fire, ice, doubleMove, backTrack;
	}
	
	/**
	 * Method that uses action tiles which affect the floor tiles.
	 * @param tile
	 */
	public void useFireIce(Floor tile) {
		if (actionType.equals(Type.fire)) {
			tile.setIsFire(true);
		} else if (actionType.equals(Type.ice)) {
			tile.setIsIce(true);
		}
	}
	
//	/**
//	 * Method that uses action tiles which affect the player.
//	 * @param player
//	 */
//	public void useBackDouble(Player player) {
//		if (actionType.equals(Type.doubleMove)) {
//			//player.doubleMove();
//		} else if (actionType.equals(Type.backTrack)){
//			//player.backTrack();
//		}
//	}
}