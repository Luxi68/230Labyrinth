package entity;

import javafx.scene.image.Image;

/**
 * Floor.java
 * <p>
 * Class which creates different types of floor tiles and to rotate the tiles.
 *
 * @author Chris, Nouran, Ryan, Junjie
 */
public class Floor extends Tile {
	private final Type FLOOR_TYPE;
	private final boolean IS_FIXED;
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


	/**
	 * Constructor initialising the the floor tile and checking if it has been affected by anything.
	 *
	 * @param tileType what type of floor tile it is
	 * @param image    what the tile looks like
	 * @param isFixed  true if tile is fixed, false if not
	 */
	public Floor(String tileType, Image image, boolean isFixed) throws IllegalStateException {
		super(tileType, image);
		this.IS_FIXED = isFixed;
		this.isOnBoard = false;
		this.isFire = false;
		this.isIce = false;
		this.rotation = 0;

		switch (tileType) {
			case ("corner"):
				this.FLOOR_TYPE = Type.corner;
				this.north = false;
				this.south = true;
				this.east = true;
				this.west = false;
				break;

			case ("straight"):
				this.FLOOR_TYPE = Type.straight;
				this.north = true;
				this.south = true;
				this.east = false;
				this.west = false;
				break;

			case ("tee"):
				this.FLOOR_TYPE = Type.tee;
				this.north = true;
				this.south = true;
				this.east = true;
				this.west = false;
				break;

			case ("goal"):
				this.FLOOR_TYPE = Type.goal;
				this.north = true;
				this.south = true;
				this.east = true;
				this.west = true;
				break;

			default:
				this.FLOOR_TYPE = null;
				throw new IllegalStateException("Floor tile type does not exist");
		}

	}

	/**
	 * Sets the row and column coordinates of the floor tile once set on the board
	 *
	 * @param row    - the row coordinate of the tile
	 * @param column - the column coordinate of the tile
	 */
	public void updateCoords(int row, int column) {
		this.isOnBoard = true;
		this.row = row;
		this.column = column;
	}

	/**
	 * Gets the row coordinate of the tile
	 *
	 * @return The row coordinate of the tile
	 */
	public int getRow() {

		return row;
	}

	/**
	 * Sets the row coordinate of the tile relatively to board
	 *
	 * @param row - the row coordinate of the tile
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * Gets the column coordinate of the tile
	 *
	 * @return The column coordinate of the tile
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Sets the column coordinate of the tile relatively to board
	 *
	 * @param column - the column coordinate of the tile
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * Checks if there's a path connecting to the top tile
	 *
	 * @return true if there is a path connecting to the top tile, false otherwise
	 */
	public boolean isNorth() {
		return north;
	}

	/**
	 * Checks if there's a path connecting to the bottom tile
	 *
	 * @return true if there is a path connecting to the bottom tile, false otherwise
	 */
	public boolean isSouth() {
		return south;
	}

	/**
	 * Checks if there's a path connecting to the east tile
	 *
	 * @return true if there is a path connecting to the east tile, false otherwise
	 */
	public boolean isEast() {
		return east;
	}

	/**
	 * Checks if there's a path connecting to the west tile
	 *
	 * @return true if there is a path connecting to the west tile, false otherwise
	 */
	public boolean isWest() {
		return west;
	}

	/**
	 * Checks if the floor tile is on the board
	 *
	 * @return true if floor is on the board
	 */
	public boolean isOnBoard() {
		return isOnBoard;
	}

	/**
	 * Check if the tile is a goal tile
	 *
	 * @return whether the tile is goal or not
	 */
	public boolean getIsGoal() {
		return this.FLOOR_TYPE == Type.goal;
	}

	/**
	 * Checks if the tile is fixed
	 *
	 * @return whether the tile is fixed or not
	 */
	public boolean getIsFixed() {
		return IS_FIXED;
	}

	/**
	 * Checks if the tile is on fire
	 *
	 * @return whether the tile is on fire or not
	 */
	public boolean getIsFire() {
		return isFire;
	}

	/**
	 * Sets the tile on fire
	 *
	 * @param isFire tells if the tile is on fire
	 */
	public void setIsFire(boolean isFire) {
		this.isFire = isFire;
	}

	/**
	 * Checks if the tile is on ice
	 *
	 * @return true if floor is covered in ice
	 */
	public boolean getIsIce() {
		return isIce;
	}

	/**
	 * Sets the tile on ice
	 *
	 * @param isIce tells if the tile is on ice
	 */
	public void setIsIce(boolean isIce) {
		this.isIce = isIce;
	}

	/**
	 * Get when the fire infliction is over
	 *
	 * @return turn number when fire effect is over
	 */
	public int getIceOver() {
		return iceOver;
	}

	/**
	 * Sets when the ice infliction is over
	 *
	 * @param iceOver - turn when ice effect is over
	 */
	public void setIceOver(int iceOver) {
		this.iceOver = iceOver;
	}

	/**
	 * Gets when the ice effect is over
	 *
	 * @return turn number when ice effect is over
	 */
	public int getFireOver() {
		return fireOver;
	}

	/**
	 * Sets when the fire infliction is over
	 *
	 * @param fireOver - turn when ice effect is over
	 */
	public void setFireOver(int fireOver) {
		this.fireOver = fireOver;
	}

	/**
	 * Gets the rotation of the tile
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
	 * Sets the rotation of the board
	 *
	 * @param rotation - The rotation of the board
	 */
	public void setRotation(int rotation) {

		this.rotation = rotation;
	}

	/**
	 * Get the floor's type
	 *
	 * @return the floor type
	 */
	public Type getFloorType() {
		return FLOOR_TYPE;
	}

	/**
	 * Sets a boolean value to check if the tile is one the board or not
	 *
	 * @param onBoard - if the tile is on the board
	 */
	public void setIsOnBoard(boolean onBoard) {
		isOnBoard = onBoard;
	}

	/**
	 * Method that rotates the tile 90 degrees clockwise once
	 */
	public void rotate() {
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

	/**
	 * The different types of floor tiles.
	 */
	enum Type {
		corner, straight, tee, goal
	}
}
