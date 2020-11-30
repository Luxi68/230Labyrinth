package core;
/*
Right now File reader will work as soon as other classes are implemented I will update it using the appropriate method
names and fully implement it as soon as we decide how to integrate it with the ui and the rest of the java program.
 */
import entity.Action;
import entity.Board;
import entity.Player;
import entity.SilkBag;
import entity.Floor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.image.Image;

/**
 * FileReader.java
 * This class reads a level file.  For the format of this file, see A1 design (slight modification have been done to it)
 * @author Alberto Ortenzi, Rhys
 */
public class FileReader {
    /**
     * Reads the data file used by the program and returns something not sure atm. Based on the first character of each line
     * of the file the method calls a specific method and creates specific objects which are part of the program
     * @param in the scanner containing the file
     * @return gameData an array of different objects(boards w/ tiles placed, silk bag with tiles in and then four players) used to create the game
     */
    private static ArrayList<Object> readDataFile(Scanner in) {
        ArrayList<Object> gameData = null;
        Board gBoard = null;
        SilkBag bag = null;
        Player p1 = null;
        Player p2 = null;
        Player p3 = null;
        Player p4 = null;
        while (in.hasNextLine()) {
            gameData = new ArrayList<>();
            bag = new SilkBag();


            String data = in.nextLine();
            String[] splitted = data.split(",");// splits the line of the program using the comma delimiter
            if (splitted[0].matches("-?\\d+")) { //checks if the character is a integer and returns true and false based on it
                switch (splitted.length) { //checks length of array and does action based on it as it uses the file format.
                    case 2:
                        //generates a blank board
                        int height = Integer.parseInt(splitted[0]);
                        int width = Integer.parseInt(splitted[1]);
                        gBoard = new Board(height, width);
                        break;
                    case 3:
                        //create player object and set starting position to px,py and player number to pn
                        int pn = Integer.parseInt(splitted[0]);
                        int px = Integer.parseInt(splitted[1]);
                        int py = Integer.parseInt(splitted[2]);
                        switch (pn) {
                            case 1:
                                p1 = new Player("Aries", new Image("/assets/aries.png"), "#b53232", px, py);
                                break;
                            case 2:
                                p2 = new Player("Aphrodite", new Image("/assets/Aphrodite.png"), "#c677b3", px, py);
                                break;
                            case 3:
                                p3 = new Player("Artemis", new Image("/assets/Artemis.png"), "#55b54c", px, py);
                                break;
                            case 4:
                                p4 = new Player("Apollo", new Image("/assets/Apollo.png"), "#fdd14b", px, py);
                                break;
                        }
                        break;
                }

            } else {
                switch (splitted[0].toLowerCase()) { //checks the first position of the array as it contains a specific character
                    case "f":
                        //create a tile object with type x, and rotation j and insert it into the board at position y,z
                        String type = splitted[1];
                        int rot = Integer.parseInt(splitted[2]);
                        int tx = Integer.parseInt(splitted[3]);
                        int ty = Integer.parseInt(splitted[4]);
                        
                        switch(type){
                            case "corner":
                                gBoard.insertTileAt(tx,ty, new Floor(type, new Image("/assets/fixedcorner.png"),true));
                                break;
                            case "straight":
                                gBoard.insertTileAt(tx,ty,new Floor(type,new Image("/assets/fixedstraight.png"),true));
                                break;
                            case "tee":
                                gBoard.insertTileAt(tx,ty,(new Floor(type,new Image("/assets/fixedtee.png"),true)));
                                break;
                            case "goal":
                                gBoard.insertTileAt(tx,ty,new Floor(type,new Image("/assets/goal.png"),true));
                                break;
                        }
                        for (int i = 0; i < rot; ++i) {
                            gBoard.getTileAt(tx, ty).rotate();
                        }


                        break;
                    case "b":
                        //reads in the name of the tile then adds the num quantity of this tile to the bag
                        String name = splitted[1];
                        int num = Integer.parseInt(splitted[2]);

                        switch (name) {
                            case "corner":
                                for (int i = 0; i < num; ++i) {
                                    bag.addTile(new Floor(name, new Image("/assets/corner.png"), false));
                                }
                                break;
                            case "straight":
                                for (int i = 0; i < num; ++i) {
                                    bag.addTile(new Floor(name, new Image("/assets/straight.png"), false));
                                }
                                break;
                            case "tee":
                                for (int i = 0; i < num; ++i) {
                                    bag.addTile(new Floor(name, new Image("/assets/tee.png"), false));
                                }
                                break;
                            case "fire":
                                for (int i = 0; i < num; ++i) {
                                    bag.addTile(new Action(name, new Image("/assets/fire.png")));
                                }
                                break;
                            case "ice":
                                for (int i = 0; i < num; ++i) {
                                    bag.addTile(new Action(name, new Image("/assets/ice.png")));
                                }
                                break;
                            case "doubleMove":
                                for (int i = 0; i < num; ++i) {
                                    bag.addTile(new Action(name, new Image("/assets/doublemove.png")));
                                }
                                break;
                            case "backTrack":
                                for (int i = 0; i < num; ++i) {
                                    bag.addTile(new Action(name, new Image("/assets/backtrack.png")));
                                }
                                break;
                            default:
                                System.out.println("What is this?");
                                break;
                        }

                        break;
                }
            }
        }

        gameData.add(gBoard);
        gameData.add(bag);
        gameData.add(p1);
        gameData.add(p2);
        gameData.add(p3);
        gameData.add(p4);

        in.close();
        return gameData;//spit out board bag and then players//not defined atm but will be as soon as we decide how to make it work with UI
    }

    /**
     * Method to read the file name which is asked in the main method. initialised a scanner and checks if the file exists
     * @param filename the name of the file
     * @return method call to readDatafile(in) where in contains the file
     */
    public static ArrayList<Object> readDataFile(String filename) {
        Scanner in = null;
        try {
            File input = new File(filename);
            in = new Scanner(input);
        } catch (FileNotFoundException e) {
            System.out.println("Error file not found");
        }
        return FileReader.readDataFile(in);
    }
}
