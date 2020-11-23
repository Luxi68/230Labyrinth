
public abstract class Action extends Tile 
{
	public Action(String tileType)
	{
		super(tileType);
		if (tileType.equalsIgnoreCase("fire"))
		{
			Type = fire;
		}
	}
	enum Type
	{
		fire, ice, doubleMove, backTrack;
	}
	
	public void use()
	{
		
	}
}
