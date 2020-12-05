package entity;

import java.util.ArrayList;

public class SilkBag {
    ArrayList<Tile> bag = new ArrayList<>();

    public ArrayList<Tile> getBag() {
        return bag;
    }

    public Tile drawTile() {
        Tile tile;
        double index;
        int value;
        do {
            index = Math.random() * (bag.size()) + 0;
            value = (int) index;
            tile = bag.get(value);
        } while (tile == null);
        bag.remove(value);
        return tile;
    }// TODO - Tiles need to be removed as they are taken out

    public void addTile(Tile discard){
        bag.add(discard);
    }
}
