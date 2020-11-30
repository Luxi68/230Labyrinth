package entity;

import javafx.scene.image.Image;

/**
 * Action.java
 * This class defines the action tiles and declares what type of action tile is being used.
 * @author Chris, Ryan and Nouran
 *
 */
public class Action extends Tile {
	
	private final Type ACTION_TYPE;

	/**
	 * The constructor initialising the type of action tile it is.
	 * @param tileType inherited from Tile.java to see which action tile it is.
	 */
	public Action(String tileType, Image image) {
		super(tileType, image);

		switch (tileType) {
			case ("fire"):
				this.ACTION_TYPE = Type.fire;
				break;
			case ("ice"):
				this.ACTION_TYPE = Type.ice;
				break;
			case ("doubleMove"):
				this.ACTION_TYPE = Type.doubleMove;
				break;
			case ("backTrack"):
				this.ACTION_TYPE = Type.backTrack;
				break;
			default: // TODO - Maybe throw an exception?
				this.ACTION_TYPE = null;
				System.out.println("Error: Tried to create action tile with invalid type");
				break;
		}
	}
	
	/**
	 * 
	 * The different types of action that can be used
	 *
	 */
	enum Type {
		fire, ice, doubleMove, backTrack
	}
	
	/**
	 * Method that uses action tiles which affect the floor tiles.
	 * @param tile - The tile which is being acted upon.
	 */
	public void useFireIce(Floor tile) {
		if (ACTION_TYPE.equals(Type.fire)) {
			tile.setIsFire(true);
		} else if (ACTION_TYPE.equals(Type.ice)) {
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