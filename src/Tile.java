/**
 * Tile.java
 * An abstract class which defines a variable to be used by its sub classes.
 * @author Chris and Ryan
 *
 */
public abstract class Tile 
{
	String tileType;
	/**
	 * Constructor initialising the type of tile.
	 * @param tileType defines the type of tile.
	 */
	protected Tile(String tileType)
	{
		this.tileType = tileType;
	}
	/**
	 * 
	 * @param tileType sets the tile type
	 */
	public void setTileType (String tileType)
	{
		this.tileType = tileType;
	}
	
	/**
	 * 
	 * @return the tile type
	 */
	public String getTileType()
	{
		return tileType;
	}
}
