import javafx.scene.image.Image;

public class Floor extends Tile
{
	private boolean north;
	private boolean south;
	private boolean east;
	private boolean west;
	private boolean isGoal;
	private boolean isFixed;
	private boolean isFire;
	private boolean isIce;
	private String floorType;
	private Image image;
	//private int rotation;
	
	public Floor(String tileType, boolean north, boolean south, boolean east, boolean west, boolean isGoal, boolean isFixed, 
				boolean isFire, boolean isIce, String floorType /*,int rotation*/)
	{
		super(tileType);
		this.north = north;
		this.south = south;
		this.east = east;
		this.west = west;
		this.isGoal = isGoal;
		this.isFixed = isFixed;
		this.isFire = isFire;
		this.isIce = isIce;	
		this.floorType = floorType;
		//this.rotation = rotation;
	
	}
	
	public void setNorth (boolean north)
	{
		this.north = north;
	}
	
	public void setSouth (boolean south)
	{
		this.south = south;
	}
	
	public void setEast (boolean east)
	{
		this.east = east;
	}
	
	public void setWest (boolean west)
	{
		this.west = west;
	}
	
	public void setIsGoal (boolean isGoal)
	{
		this.isGoal = isGoal;
	}
	
	public void setIsFixed (boolean isFixed)
	{
		this.isFixed = isFixed;
	}
	
	public void setIsFire (boolean isFire)
	{
		this.isFire = isFire;
	}
	
	public void setIsIce (boolean isIce)
	{
		this.isIce = isIce;
	}

	public void setFloorType (String floorType)
	{
		this.floorType = floorType;
	}

	/*public void setRotation(int rotation)
	{
		this.rotation = rotation;
	}*/

	public boolean getNorth()
	{
		return north;
	}

	public boolean getSouth()
	{
		return south;
	}

	public boolean getEast()
	{
		return east;
	}

	public boolean getWest()
	{
		return west;
	}

	public boolean getIsGoal()
	{
		return isGoal;
	}

	public boolean getIsFixed()
	{
		return isFixed;
	}

	public boolean getIsFire()
	{
		return isFire;
	}

	public boolean getIsIce()
	{
		return isIce;
	}

	public String getFloorType()
	{
		return floorType;
	}

	/*public int getRotation()
	{
		return rotation;
	}*/

	public void rotate ()
	{
		boolean tempN = north;
		boolean tempS = south;
		boolean tempE = east;
		boolean tempW = west;

		north = tempW;
		south = tempE;
		east = tempN;
		west = tempS;
	}

	public boolean[] exitPoints()
	{
		boolean[] exitPoints = new boolean [] {north, east, south, west};
		return exitPoints;
	}
}
