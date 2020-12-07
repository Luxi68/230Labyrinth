package core;
import entity.*;

import java.io.*;
import java.util.ArrayList;


/**
 * @author Rhys
 * class that saves to a text file the imformation necessary to resume playing a game in progress
 * also loads a game from a prior save
 */

public class SaveGame {

    /**
     *
     * @param name the name of the file to be saved to not the full path
     * @param board the board to be saved
     * @param players a list of players to be saved
     * @param bag the silkbag to be saved
     * @param rowNoFixed a list of rows indexs with no fixed tiles
     * @param columnNoFixed a list of column indexs with no fixed tile
     * @throws IOException needed to run but has never been an issue
     */

    public static void save(String name, Board board, SilkBag bag, ArrayList<Player> players, ArrayList<Integer> rowNoFixed, ArrayList<Integer> columnNoFixed) throws IOException { //add hashmaps

        String fileName = "resources/saves/" + name + ".ser";
        File file = new File(fileName);

        FileOutputStream fFile = new FileOutputStream(file);
        ObjectOutputStream oFile = new ObjectOutputStream(fFile);

        /*
        oFile.writeObject(board.getHeight());
        oFile.write('\n');
        oFile.writeObject(board.getLength());
        oFile.write('\n');

        int i = board.getHeight() * board.getLength();
        oFile.writeObject(i);
        oFile.write('\n');
        for (int x = 0; x < board.getHeight(); x++) {
            for (int y = 0; y < board.getLength(); y++) {
                oFile.writeObject(board.getTileAt(x, y));
                oFile.write('\n');
            }
        }

        int size = bag.getBagSize();
        for (int x = 0; x < size; x++){
            oFile.writeObject(bag.drawTile());
            oFile.write('\n');
        }*/

        oFile.writeObject(board);
        oFile.write('\n');
        oFile.writeObject(bag);
        oFile.write('\n');
        oFile.writeObject(players);
        oFile.write('\n');
        oFile.writeObject(rowNoFixed);
        oFile.write('\n');
        oFile.writeObject(columnNoFixed);

        fFile.close();
        oFile.close();
    }

    /**
     *
     * @param name the name of the file to be loaded from not the path to it
     * @return a list of information which will allow the game to be recreated to the point it was saved at
     * @throws IOException needed to run but has never been an issue
     * @throws ClassNotFoundException needed to run but has never been an issue
     */

    //couldn't get this to work but the save works so that's something
    public static ArrayList<Object> loadSave (String name) throws IOException, ClassNotFoundException {

        String fileName = "resources/saves/"+name+".ser";
        File file = new File(fileName);
        ArrayList<Object> fileData = new ArrayList<>();

        FileInputStream fiFile = new FileInputStream(file);
        ObjectInputStream oiFile = new ObjectInputStream(fiFile);


        ArrayList<Player> players = new ArrayList<>();
        SilkBag bag = new SilkBag();
        ArrayList<Integer> rowNoFixed = new ArrayList<>();
        ArrayList<Integer> columnNoFixed = new ArrayList<>();

        Tile tile = (Tile)oiFile.readObject();
        players = (ArrayList<Player>)oiFile.readObject();
        bag = (SilkBag) oiFile.readObject();
        rowNoFixed = (ArrayList<Integer>)oiFile.readObject();
        columnNoFixed = (ArrayList<Integer>)oiFile.readObject();

        fileData.add(tile);
        fileData.add(bag);
        fileData.add(players);
        fileData.add(rowNoFixed);
        fileData.add(columnNoFixed);

        fiFile.close();
        oiFile.close();

        return fileData;
    }
}

