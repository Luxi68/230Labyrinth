import java.util.ArrayList;

/**
 * Floor.java
 * Class which created different types of floor tiles and to rotate the tiles.
 * @author Chris and Ryan
 *
 */
public class Floor extends Tile
{
	private ArrayList<Direction> directions;
	private boolean isGoal;
	private boolean isFixed;
	private boolean isFire;
	private boolean isIce;
	//private String floorType;
	private Type floorType;
	
	/**
	 * Constructor initialising the the floor tile and checking if it has been affected by anything.
	 * @param tileType what type of floor tile it is
	 * @param north the exit points of the tile
	 * @param south the exit points of the tile
	 * @param east the exit points of the tile
	 * @param west the exit points of the tile
	 * @param isGoal true if tile is goal tile, false if not
	 * @param isFixed true if tile is fixed, false if not
	 * @param isFire true if tile is on fire, false if not
	 * @param isIce true if tile is frozen, false if not
	 */
	public Floor(String tileType, String north, String south, String east, String west, boolean isGoal, boolean isFixed,
				boolean isFire, boolean isIce)
	{
		super(tileType);

		if (north.equalsIgnoreCase("true")) {
			directions.add(Direction.north);
		} else if (south.equalsIgnoreCase("true")) {
			directions.add(Direction.south);
		} else if (east.equalsIgnoreCase("true")) {
			directions.add(Direction.east);
		} else if (west.equalsIgnoreCase("true")) {
			directions.add(Direction.west);
		}

		this.isGoal = isGoal;
		this.isFixed = isFixed;
		this.isFire = isFire;
		this.isIce = isIce;
		
		if (tileType.equalsIgnoreCase("corner")) {
			this.floorType = Type.corner;
		} else if (tileType.equalsIgnoreCase("straight")) {
			this.floorType = Type.straight;
		} else if (tileType.equalsIgnoreCase("lShape")) {
			this.floorType = Type.lShape;
		} else if (tileType.equalsIgnoreCase("tShape")) {
			this.floorType = Type.tShape;
		}
		
	}

	public enum Direction
	{
		north,south,east,west;
	}
	
	/**
	 * 
	 * The different types of floor tiles.
	 *
	 */
	enum Type 
	{
		corner, straight, lShape, tShape;
	}
	

	/**
	 * 
	 * @param isGoal set to itself
	 */
	public void setIsGoal (boolean isGoal)
	{
		this.isGoal = isGoal;
	}
	
	/**
	 * 
	 * @param isFixed set it to itself
	 */
	public void setIsFixed (boolean isFixed)
	{
		this.isFixed = isFixed;
	}
	
	/**
	 * 
	 * @param isFire set to itself
	 */
	public void setIsFire (boolean isFire)
	{
		this.isFire = isFire;
	}
	
	/**
	 * 
	 * @param isIce set to itself
	 */
	public void setIsIce (boolean isIce)
	{
		this.isIce = isIce;
	}
	
	/**
	 * 
	 * @param floorType set to itself
	 */
	public void setFloorType (Type floorType)
	{
		this.floorType = floorType;
	}
	

	/**
	 * 
	 * @return whether the tile is goal or not
	 */
	public boolean getIsGoal()
	{
		return isGoal;
	}
	
	/**
	 * 
	 * @return whether the tile is fixed or not
	 */
	public boolean getIsFixed()
	{
		return isFixed;
	}
	
	/**
	 * 
	 * @return whether the tile is on fire or not
	 */
	public boolean getIsFire()
	{
		return isFire;
	}
	
	/**
	 * 
	 * @return whether the tile is frozen or not
	 */
	public boolean getIsIce()
	{
		return isIce;
	}
	
	/**
	 * 
	 * @return the floor type
	 */
	public Type getFloorType()
	{
		return floorType;
	}

	public ArrayList<Direction> getDirections(){
		return directions;
	}

	public void setDirections(ArrayList<Direction> directions){
		this.directions = directions;
	}

	/*
	// Method that rotates the tile
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
	
	//Method that returns the exits points of a tile
	public boolean[] exitPoints()
	{
		boolean[] exitPoints = new boolean [] {north, east, south, west};
		return exitPoints;
	}*/
}
