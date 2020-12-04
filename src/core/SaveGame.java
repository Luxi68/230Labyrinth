package core;
import entity.*;
import javafx.scene.image.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Salim Zaidi
 * class that saves to a text file the imformation necessary to resume playing a game in progress
 */

public class SaveGame {

    public static void inputs(Board board, Floor floor, ArrayList<Player> players, SilkBag bag) throws IOException { //add hashmaps

        String filename = "/resources/users/savedgame.txt";
        File createdFile = new File(filename);

        BufferedReader buff = new BufferedReader(new FileReader("/resources/users/savedgame.txt"));
        // if first line is empty i.e empty file, then write to it
        if (buff.readLine() == null) {
            FileWriter writing = new FileWriter(filename);

            writing.write(board.getLength() + "," + board.getHeight()); //board dimensions x y
            writing.write("\n");

            int x = 0;
            int y = 0;
            while(x < board.getLength() && y < board.getHeight()) {  //loop through board tiles

                if (x - y == 1) {
                    y++;
                }
                    if (floor.equals("corner")){
                        writing.write(floor.getX() + "," + floor.getY() + "," + floor.getRotation() + "," + floor.getFLOOR_TYPE() + "," + floor.getIsFixed()); //write x,y,n,corner,true/false
                        writing.write("\n");
                    } else if (floor.equals("straight")) {
                        writing.write(floor.getX() + "," + floor.getY() + "," + floor.getRotation() + "," + floor.getFLOOR_TYPE() + "," + floor.getIsFixed()); //write x,y,n,straight,true/false
                        writing.write("\n");
                    }  else if (floor.equals("tee")){
                        writing.write(floor.getX() + "," + floor.getY() + "," + floor.getRotation() + "," + floor.getFLOOR_TYPE() + "," + floor.getIsFixed()); //write x,y,n,tee,true/false
                        writing.write("\n");
                    } else (floor.equals("goal")){
                        writing.write(floor.getX() + "," + floor.getY() + "," + floor.getRotation() + "," + floor.getFLOOR_TYPE() + "," + floor.getIsFixed()); //write x,y,n,goal,true/false
                        writing.write("\n");
                    }

                ++x;
                y++; @// TODO:  need to find a way to increment through coordinates (xy) 01  11  12  22 instead of 00 11 22 33

            } //end while loop

            writing.write(board.toString());
            writing.write("\n");

            writing.write(players.toString());

            writing.write("\n");

            writing.write(bag.toString());

            writing.close();


         // Otherwise delete existing file
        } else {
            createdFile.delete(); @// TODO: 04/12/2020 delete is ignored apparently.
            //  SaveGame.inputs();
        }
    }

    public static void main(String[]args) throws IOException {

        Board board1 = new Board(5,5);
        Floor floor = new Floor("corner","/assets/corner.png", true); @// TODO: 04/12/2020 image needed not url.

        Player player1 = new Player(new Image("/assets/corner.png"), "#fdd14b", 5, 5, board1, new Profile("Rhys"));
        SilkBag bag1 = new SilkBag();
        ArrayList<Player> player2 = null;
        player2.add(player1);

        SaveGame.inputs(board1, floor,player2, bag1);
    }
    //public void FileAlreadyExistsException(File createdFile){
    //    createdFile.delete();
    //}

}

