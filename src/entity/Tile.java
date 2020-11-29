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
	 * @param tileType sets the tile type
	 */
	public void setTileType(String tileType) {
		this.tileType = tileType;
	}

	/**
	 * @return the image associated with the tile
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @param image the image that reflects the tile type
	 */
	public void setImage(Image image) {
		this.image = image;
	}
}
