package entity;

import javafx.scene.image.Image;

/**
 * Tile.java
 * An abstract class which defines a variable to be used by its sub classes.
 *
 * @author Chris and Ryan
 */
public abstract class Tile {
	String tileType;
	Image image;

	/**
	 * Constructor initialising the type of tile.
	 *
	 * @param tileType defines the type of tile.
	 * @param image    the image associated with the tile
	 */
	protected Tile(String tileType, Image image) {
		this.tileType = tileType;
		this.image = image;
	}

	/**
	 * @return the tile type
	 */
	public String getTileType() {
		return tileType;
	}

	/**
	 * @return the image associated with the tile
	 */
	public Image getImage() {
		return image;
	}

}
