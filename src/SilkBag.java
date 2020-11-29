import java.util.ArrayList;

public class SilkBag {
    ArrayList<Tile> bag = new ArrayList<>();

    public ArrayList<Tile> getBag() {
        return bag;
    }

    public Tile drawTile(){
        double index = Math.random() * (bag.size()) + 0;
        int value = (int)index;
        return bag.get(value);
    }// TODO - Tiles need to be removed as they are taken out

    public void addTile(Tile discard){
        bag.add(discard);
    }
}
