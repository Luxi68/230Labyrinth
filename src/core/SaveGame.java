package core;
import entity.*;
import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
public class SaveGame {

    public static void inputs(Board board, ArrayList<Player> players, SilkBag bag) throws IOException { //add hashmaps

        String filename = "/resources/users/savedgame.txt";
        File createdFile = new File(filename);

        BufferedReader buff = new BufferedReader(new FileReader("/resources/users/savedgame.txt"));
        // if first line is empty i.e empty file, then write to it
        if (buff.readLine() == null) {
            FileWriter writing = new FileWriter(filename);
            writing.write(String.valueOf(board));
            writing.write("\n");
            writing.write(String.valueOf(players));
            writing.write("\n");
            writing.write(String.valueOf(bag));
            writing.close();
         // Otherwise delete existing file
        } else {
            createdFile.delete();
            //  SaveGame.inputs();
        }
    }
    public static void main(String[]args) throws IOException {

        Board board1 = new Board(5,5);
        Player player1 = new Player(new Image("/assets/corner.png"), "#fdd14b", 5, 5, board1, new Profile("Rhys"));
        SilkBag bag1 = new SilkBag();
        ArrayList<Player> player2 = null;
        player2.add(player1);

        SaveGame.inputs(board1,player2, bag1);
    }
    //public void FileAlreadyExistsException(File createdFile){
    //    createdFile.delete();
    //}

}

