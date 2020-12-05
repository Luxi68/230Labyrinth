package entity;	

import java.util.ArrayList;

import javax.sound.sampled.AudioFileFormat.Type;
/**
 * The class that represents the silk bag
 * @author Chris, Ryan 
 *
 */

public class SilkBag {
    ArrayList<Tile> bag = new ArrayList<>();
    ArrayList<Tile> discardBag = new ArrayList<>();
    Tile removedTile;

    /**
     * 
     * @return the silk bag
     */
    public ArrayList<Tile> getBag() {
        return bag;
    }
    /**
     *The method used to draw a tile out of silk bag and remove that tile.	 
     * @return the tile taken from the silk bag
     */
    public Tile drawTile(){
        Tile tile;
        do {
            double index = Math.random() * (bag.size()) + 0;
            int value = (int)index;
            tile = bag.get(value);
            removedTile = bag.get(value);
            bag.remove(value);
        } while (tile == null);
        return tile;
    }
    /**
     * Method used to check which type of tile was taken from silk bag to see which bag it goes back into
     * @param removedTile the tile type passed in to check where it goes.
     */
    public void addTile(Tile removedTile){
     if( removedTile.toString().equalsIgnoreCase("corner")
    	 			|| removedTile.toString().equalsIgnoreCase("straight")
    	 			|| removedTile.toString().equalsIgnoreCase("tee")) {
        bag.add(removedTile);
     }
     else if (removedTile.toString().equalsIgnoreCase("fire")
    		 		|| removedTile.toString().equalsIgnoreCase("ice")
    		 		|| removedTile.toString().equalsIgnoreCase("backTrack")
    		 		|| removedTile.toString().equalsIgnoreCase("doubleMove")) {
    	 discardBag.add(removedTile);
     }
     else {
    	System.out.println("ERROR: Unknown tile trying to be added back into bag.");
     }
    }
}