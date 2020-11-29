import javafx.scene.image.Image;

/**
 * Floor.java
 * Class which created different types of floor tiles and to rotate the tiles.
 * @author Chris and Ryan
 *
 */
public class Floor extends Tile {
	private final Type FLOOR_TYPE;
	private final boolean IS_FIXED;
	private final boolean IS_GOAL;
	private boolean north;
	private boolean south;
	private boolean east;
	private boolean west;
	private boolean isFire;
	private boolean isIce;
	//private String floorType;

	/**
	 * Constructor initialising the the floor tile and checking if it has been affected by anything.
	 * @param tileType what type of floor tile it is
	 * @param image what the tile looks like
	 * @param isFixed true if tile is fixed, false if not
	 */
	public Floor(String tileType, Image image, boolean isFixed)	{
		super(tileType, image);
		this.IS_FIXED = isFixed;
		this.isFire = false;
		this.isIce = false;
		//this.floorType = floorType;

		switch (tileType) {
			case ("corner"):
				this.FLOOR_TYPE = Type.corner;
				this.north = false;
				this.south = true;
				this.east = true;
				this.west = false;
				this.IS_GOAL = false;
				break;

			case ("straight"):
				this.FLOOR_TYPE = Type.straight;
				this.north = true;
				this.south = true;
				this.east = false;
				this.west = false;
				this.IS_GOAL = false;
				break;

			case ("tee"):
				this.FLOOR_TYPE = Type.tee;
				this.north = true;
				this.south = true;
				this.east = true;
				this.west = false;
				this.IS_GOAL = false;
				break;

			default:
				this.FLOOR_TYPE = Type.goal;
				this.north = true;
				this.south = true;
				this.east = true;
				this.west = true;
				this.IS_GOAL = true;
		}
		
	}
	
	/**
	 * 
	 * The different types of floor tiles.
	 *
	 */
	enum Type {
		corner, straight, tee, goal
	}

	/**
	 * 
	 * @param isFire set to itself
	 */
	public void setIsFire (boolean isFire) {
		this.isFire = isFire;
	}
	
	/**
	 * 
	 * @param isIce set to itself
	 */
	public void setIsIce (boolean isIce) {
		this.isIce = isIce;
	}

	/**
	 * 
	 * @return returns north
	 */
	public boolean getNorth() {
		return north;
	}
	
	/**
	 * 
	 * @return returns south
	 */
	public boolean getSouth() {
		return south;
	}
	
	/**
	 * 
	 * @return returns east
	 */
	public boolean getEast() {
		return east;
	}
	
	/**
	 * 
	 * @return returns west
	 */
	public boolean getWest() {
		return west;
	}
	
	/**
	 * 
	 * @return whether the tile is goal or not
	 */
	public boolean getIsGoal() {
		return IS_GOAL;
	}
	
	/**
	 * 
	 * @return whether the tile is fixed or not
	 */
	public boolean getIsFixed() {
		return IS_FIXED;
	}
	
	/**
	 * 
	 * @return whether the tile is on fire or not
	 */
	public boolean getIsFire() {
		return isFire;
	}
	
	/**
	 * 
	 * @return whether the tile is frozen or not
	 */
	public boolean getIsIce() {
		return isIce;
	}
	
	/**
	 * 
	 * @return the floor type
	 */
	public Type getFLOOR_TYPE() {
		return FLOOR_TYPE;
	}

	// Method that rotates the tile
	public void rotate () {
		boolean tempN = north;
		boolean tempS = south;
		boolean tempE = east;

		north = west;
		south = tempE;
		east = tempN;
		west = tempS;		
	}
	
	//Method that returns the exits points of a tile
	public boolean[] exitPoints() {
		return new boolean [] {north, east, south, west};
	}
}
