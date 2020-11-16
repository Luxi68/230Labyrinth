import java.util.ArrayList;

public class SilkBag {
    ArrayList<Tile> bag = new ArrayList<Tile>();

    public ArrayList<Tile> getBag() {
        return bag;
    }

    public Tile drawTile(){
        double index = Math.random() * (bag.size()) + 0;
        int value = (int)index;
        return bag.get(value);
    }

    public void addTile(Tile discard){
        bag.add(discard);
    }

}
