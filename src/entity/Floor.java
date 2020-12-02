package entity;

import javafx.scene.image.Image;

/**
 * Floor.java
 *
 * Class which created different types of floor tiles and to rotate the tiles.
 * @author Chris, Ryan, Junjie
 */
public class Floor extends Tile {
	private final Type FLOOR_TYPE;
	private final boolean IS_FIXED;
	private final boolean IS_GOAL;
	private int x;



	private int y;



	private boolean north;
	private boolean south;
	private boolean east;
	private boolean west;
	private boolean isFire;
	private boolean isIce;
	private int rotation;
	//private String floorType;

	// TODO - finish javadoc

	/**
	 * Constructor initialising the the floor tile and checking if it has been affected by anything.
	 * @param tileType what type of floor tile it is
	 * @param image what the tile looks like
	 * @param isFixed true if tile is fixed, false if not
	 */
	public Floor(String tileType, Image image, boolean isFixed, int x, int y)	{
		super(tileType, image);
		this.IS_FIXED = isFixed;
		this.isFire = false;
		this.isIce = false;
		this.rotation = 0;
		this.x = x;
		this.y = y;
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

			case ("goal"):
				this.FLOOR_TYPE = Type.goal;
				this.north = true;
				this.south = true;
				this.east = true;
				this.west = true;
				this.IS_GOAL = true;
				break;

			default: // TODO - Maybe throw an exception?
				this.FLOOR_TYPE = null;
				this.IS_GOAL = false;
				System.out.println("Error: Tried to create floor tile with invalid type");
				break;
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	public boolean isNorth() {
		return north;
	}

	public void setNorth(boolean north) {
		this.north = north;
	}

	public boolean isSouth() {
		return south;
	}

	public void setSouth(boolean south) {
		this.south = south;
	}

	public boolean isEast() {
		return east;
	}

	public void setEast(boolean east) {
		this.east = east;
	}

	public boolean isWest() {
		return west;
	}

	public void setWest(boolean west) {
		this.west = west;
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
	 * @return - The rotation in degrees
	 */
	public int getRotation() {
		int realRotation;

		if (rotation == 1) {
			realRotation = 90;
		} else if (rotation == 2) {
			realRotation = 180;
		} else if (rotation == 3) {
			realRotation = 270;
		} else {
			realRotation = 0;
		}

		return realRotation;
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
		north = east;
		east = south;
		south = west;
		west = tempN;

		if (rotation == 0) {
			this.rotation = 1;
		} else if (rotation == 1) {
			this.rotation = 2;
		} else if (rotation == 2) {
			this.rotation = 3;
		} else {
			this.rotation = 0;
		}
	}
	
	//Method that returns the exits points of a tile
	public boolean[] exitPoints() {
		return new boolean [] {north, east, south, west};
	}
}
