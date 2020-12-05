package core;
import entity.*;
import javafx.scene.image.Image;

import java.io.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

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
     * @throws IOException because it was being awkward //todo fix the throws
     */

    //throughout the file i commented out the hashmaps as i was unsure as to whether we were using them
    //but to make it work with them simply uncomment and add the following line to the end of the save params
    // ,HashMap hash1, HashMap hash2

    public static void save(String name, Board board, ArrayList<Player> players, SilkBag bag, ArrayList<Integer> rowNoFixed, ArrayList<Integer> columnNoFixed) throws IOException { //add hashmaps

    String fileName = "./reources/save/"+name+".txt";
    File file = new File(fileName);
    if(file.exists()){
        PrintWriter clear = new PrintWriter(file);
        clear.print("");
        clear.close();
    }else{
        file.createNewFile();
    }

        FileOutputStream fFile = new FileOutputStream(file);
        ObjectOutputStream oFile = new ObjectOutputStream(fFile);

        oFile.writeObject(board);
        oFile.writeObject(players);
        oFile.writeObject(bag);
        oFile.writeObject(rowNoFixed);
        oFile.writeObject(columnNoFixed);

        fFile.close();
        oFile.close();
    }

    /**
     *
     * @param name the name of the file to be loaded from not the path to it
     * @return a list of information which will allow the game to be recreated to the point it was saved at
     * @throws IOException
     * @throws ClassNotFoundException // todo fix the throws
     */

    public static ArrayList<Object> loadSave (String name) throws IOException, ClassNotFoundException {

        String fileName = "./reources/save/"+name+".txt";
        File file = new File(fileName);
        ArrayList<Object> fileData = new ArrayList<>();

        FileInputStream fiFile = new FileInputStream(file);
        ObjectInputStream oiFile = new ObjectInputStream(fiFile);

        Board board = (Board)oiFile.readObject();
        ArrayList<Player> players = (ArrayList<Player>)oiFile.readObject();
        SilkBag bag = (SilkBag) oiFile.readObject();
        ArrayList<Integer> rowNoFixed = (ArrayList<Integer>)oiFile.readObject();
        ArrayList<Integer> columnNoFixed = (ArrayList<Integer>)oiFile.readObject();

        fileData.add(board);
        fileData.add(players);
        fileData.add(bag);
        fileData.add(rowNoFixed);
        fileData.add(columnNoFixed);

        fiFile.close();
        oiFile.close();

        return fileData;
    }
}

