
public class Action extends Tile {
	
	private Type actionType;
	
	public Action(String tileType) {
		super(tileType);
		
		if (tileType.equalsIgnoreCase("fire")) {
			this.actionType = Type.fire;
		} else if (tileType.equalsIgnoreCase("ice")) {
			this.actionType = Type.ice;
		} else if (tileType.equalsIgnoreCase("doubleMove")) {
			this.actionType = Type.doubleMove;
		} else if (tileType.equalsIgnoreCase("backTrack")) {
			this.actionType = Type.backTrack;
		}
	}
	
	enum Type {
		fire, ice, doubleMove, backTrack
	}
	
	public void useFireIce(Floor tile) {
		if (actionType.equals(Type.fire)) {
			tile.setIsFire(true);
		} else if (actionType.equals(Type.ice)) {
			tile.setIsIce(true);
		}
	}
	
//	public void useBackDouble(Player player) {
//		if (actionType.equals(Type.doubleMove)) {
//			//player.doubleMove();
//		} else if (actionType.equals(Type.backTrack)){
//			//player.backTrack();
//		}
//	}

}
