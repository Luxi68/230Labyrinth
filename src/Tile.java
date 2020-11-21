
public abstract class Tile 
{
	String tileType;
	
	protected Tile(String tileType)
	{
		this.tileType = tileType;
	}
	
	public void setTileType (String tileType)
	{
		this.tileType = tileType;
	}
	
	public String getTileType()
	{
		return tileType;
	}
}
