import java.util.ArrayList;

public class Player {
    private Profile profile;
    private Tile currentTile;
    private boolean isGoal;
    private ArrayList<Action> actionTiles;
    private ArrayList<Tile> possibleMoves;

    public Player(Profile profile, Tile startingTile /*,goal?*/) {
        this.profile = profile;
        this.currentTile = startingTile;
    }

    public void movePlayer(Tile moveTo) {
        if (possibleMoves.contains(moveTo)) {
            this.currentTile = moveTo;
            possibleMoves = null;
        }
    }

    public ArrayList<Tile> possibleMoves() {
        //add stuff to possibleMoves
        return possibleMoves;
    }


}

