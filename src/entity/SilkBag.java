package entity;

import java.util.ArrayList;

/**
 * The class that represents the silk bag
 *
 * @author Chris, Ryan, Junjie
 */
public class SilkBag {
	ArrayList<Tile> bag = new ArrayList<>();
	ArrayList<Tile> discardBag = new ArrayList<>();
	Tile removedTile;

	/**
	 * @return the silk bag
	 */
	public ArrayList<Tile> getBag() {
		return bag;
	}

	/**
	 * The method used to draw a tile out of silk bag and remove that tile.
	 *
	 * @return the tile taken from the silk bag
	 */
	public Tile drawTile() {
		Tile tile;
		int value;
		do {
			double index = Math.random() * (bag.size()) + 0;
			value = (int) index;
			tile = bag.get(value);
		} while (tile == null);
		bag.remove(value);
		return tile;
	}

	/**
	 * Method used to check which type of tile was taken from silk bag to see which bag it goes back into
	 *
	 * @param isRemoved if the tile is being removed from the board/game
	 * @param removedTile the tile type passed in to check where it goes.
	 */
	public void addTile(boolean isRemoved, Tile removedTile) {
		if (isRemoved) {
			if (removedTile.tileType.equals("corner")
					|| removedTile.tileType.equals("straight")
					|| removedTile.tileType.equals("tee")) {
				bag.add(removedTile);
			} else if (removedTile.tileType.equals("fire")
					|| removedTile.tileType.equals("ice")
					|| removedTile.tileType.equals("backTrack")
					|| removedTile.tileType.equals("doubleMove")) {
				discardBag.add(removedTile);
			} else {
				System.out.println("ERROR: Unknown tile trying to be added back into bag.");
			}
		} else {
			bag.add(removedTile);
		}
	}
}