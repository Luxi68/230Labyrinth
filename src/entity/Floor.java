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
	private int row;
	private int column;
	private boolean north;
	private boolean south;
	private boolean east;
	private boolean west;
	private boolean isOnBoard;
	private boolean isFire;
	private boolean isIce;
	private int fireOver;
	private int iceOver;
	private int rotation;
	//private String floorType;

	// TODO - finish javadoc

	/**
	 * Constructor initialising the the floor tile and checking if it has been affected by anything.
	 * @param tileType what type of floor tile it is
	 * @param image what the tile looks like
	 * @param isFixed true if tile is fixed, false if not
	 */
	public Floor(String tileType, Image image, boolean isFixed)	{
		super(tileType, image);
		this.IS_FIXED = isFixed;
		this.isOnBoard = false;
		this.isFire = false;
		this.isIce = false;
		this.rotation = 0;
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

	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public void updateCoords(int row, int column) {
		this.isOnBoard = true;
		this.row = row;
		this.column = column;
	}

	/**
	 * Checks if the floor tile is on the board
	 *
	 * @return true if floor is on the board
	 */
	public boolean isOnBoard() {
		return isOnBoard;
	}

	public int getRow() {

		return row;
	}

	public int getColumn() {
		return column;
	}

	public boolean isNorth() {
		return north;
	}


	public boolean isSouth() {
		return south;
	}


	public boolean isEast() {
		return east;
	}


	public boolean isWest() {
		return west;
	}

	public void setIsOnBoard(boolean onBoard) {
		isOnBoard = onBoard;
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
	 * @param fireOver - turn when ice effect is over
	 */
	public void setFireOver(int fireOver) {
		this.fireOver = fireOver;
	}

	/**
	 *
	 * @param iceOver - turn when ice effect is over
	 */
	public void setIceOver(int iceOver) {
		this.iceOver = iceOver;
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
	 * @return true if floor is covered in ice
	 */
	public boolean getIsIce() {
		return isIce;
	}

	/**
	 *
	 * @return turn number when fire effect is over
	 */
	public int getIceOver() {
		return iceOver;
	}

	/**
	 *
	 * @return turn number when ice effect is over
	 */
	public int getFireOver() {
		return fireOver;
	}

	/**
	 *
	 * @return - The rotation in degrees
	 */
	public int getRotation() {
		if (rotation == 1) {
			return 90;
		} else if (rotation == 2) {
			return 180;
		} else if (rotation == 3) {
			return 270;
		} else {
			return 0;
		}
	}

	/**
	 *
	 * @param rotation
	 */
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	/**
	 * 
	 * @return the floor type
	 */
	public Type getFLOOR_TYPE() {
		return FLOOR_TYPE;
	}


	/**
	 * Method that rotates the tile 90 degrees clockwise
	 */
	public void  rotate () {
		boolean tempN = north;
		north = west;
		west = south;
		south = east;
		east = tempN;

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
