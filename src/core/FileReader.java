package core;
/*
Right now File reader will work as soon as other classes are implemented I will update it using the appropriate method
names and fully implement it as soon as we decide how to integrate it with the ui and the rest of the java program.
 */

import entity.*;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * FileReader.java
 * This class reads a level file.
 * For the format of this file, see A1 design (slight modification have been done to it)
 *
 * @author Alberto Ortenzi, Rhys
 */
public class FileReader {
    /**
     * Method to read the file name which is asked in the main method.
     * Initialised a scanner and checks if the file exists.
     *
     * @param filename the name of the file
     * @param a        is a list of profiles to be used in creating players for the game
     * @return method call to readDatafile(in) where in contains the file
     */
    public static ArrayList<Object> readDataFile(String filename, ArrayList<Profile> a) {
        //Scanner in = new Scanner();
        Scanner in = null;
        try {
            File input = new File(filename);
            in = new Scanner(input);
        } catch (FileNotFoundException e) {
            System.out.println("File " + filename + " does not exist.");
            e.printStackTrace();
            System.exit(0);
        }
        return dataFile(in, a);
    }

    /**
     * Reads the data file used by the program and returns something not sure atm.
     * Based on the first character of each line of the file the method calls a specific method
     * and creates specific objects which are part of the program
     *
     * @param in the scanner containing the file
     * @param a  a list of profiles to be used for creating players
     * @return gameData an array of different objects
     * (boards w/ tiles placed, silk bag with tiles in and then four players) used to create the game
     */
    private static ArrayList<Object> dataFile(Scanner in, ArrayList<Profile> a) {
        ArrayList<Object> gameData = new ArrayList<>();
        ArrayList<Player> players = new ArrayList<>();
        Board gBoard = new Board(0, 0);
        SilkBag bag = new SilkBag();
        while (in.hasNextLine()) {
            String data = in.nextLine();
            // splits the line of the program using the comma delimiter
            String[] splitted = data.split(",");
            //checks if the character is a integer and returns true and false based on it
            if (splitted[0].matches("-?\\d+")) {
                //checks length of array and does action based on it as it uses the file format.
                switch (splitted.length) {
                    case 2:
                        //generates a blank board
                        int rows = Integer.parseInt(splitted[0]);
                        int columns = Integer.parseInt(splitted[1]);
                        gBoard = new Board(columns, rows);
                        break;
                    case 3:
                        //create player object and set starting position to px,py and player number to pn

                        int pn = Integer.parseInt(splitted[0]);
                        int pRows = Integer.parseInt(splitted[1]);
                        int pColumns = Integer.parseInt(splitted[2]);

                        Image img = null;
                        String hex = "";
                        switch (pn) {
                            case 1:
                                img = new Image("/assets/aries.png");
                                hex = "#b53232";
                                break;
                            case 2:
                                img = new Image("/assets/aphrodite.png");
                                hex = "#c677b3";
                                break;
                            case 3:
                                img = new Image("/assets/apollo.png");
                                hex = "#55b54c";
                                break;
                            case 4:
                                img = new Image("/assets/artemis.png");
                                hex = "#fdd14b";
                                break;
                        }
                        if (a.size() > pn - 1) {
                            players.add(new Player(img, hex, pColumns, pRows, gBoard, a.get(pn - 1)));
                        }
                        break;
                }

            } else {
                //checks the first position of the array as it contains a specific character
                switch (splitted[0].toLowerCase()) {
                    case "f":
                        //create a tile object with type x, and rotation j and insert it into the board at position y,z
                        String type = splitted[1];
                        int rot = Integer.parseInt(splitted[2]);
                        int tRows = Integer.parseInt(splitted[3]);
                        int tColumns = Integer.parseInt(splitted[4]);

                        switch (type) {
                            case "corner":
                                gBoard.insertTileAt(tRows, tColumns, new Floor(
                                        "corner", new Image("/assets/fixedcorner.png"), true));
                                break;
                            case "straight":
                                gBoard.insertTileAt(tRows, tColumns, new Floor(
                                        "straight", new Image("/assets/fixedstraight.png"), true));
                                break;
                            case "tee":
                                gBoard.insertTileAt(tRows, tColumns, (new Floor(
                                        "tee", new Image("/assets/fixedtee.png"), true)));
                                break;
                            case "goal":
                                gBoard.insertTileAt(tRows, tColumns, new Floor(
                                        "goal", new Image("/assets/goal.png"), true));
                                break;
                        }
                        for (int i = 0; i < rot; ++i) {
                            gBoard.getTileAt(tRows, tColumns).rotate();
                        }


                        break;
                    case "b":
                        //reads in the name of the tile then adds the num quantity of this tile to the bag
                        String name = splitted[1];
                        int num = Integer.parseInt(splitted[2]);

                        switch (name) {
                            case "corner":
                                for (int i = 0; i < num; ++i) {
                                    bag.addTile(new Floor("corner",
                                            new Image("/assets/corner.png"), false));
                                }
                                break;
                            case "straight":
                                for (int i = 0; i < num; ++i) {
                                    bag.addTile(new Floor("straight",
                                            new Image("/assets/straight.png"), false));
                                }
                                break;
                            case "tee":
                                for (int i = 0; i < num; ++i) {
                                    bag.addTile(new Floor("tee",
                                            new Image("/assets/tee.png"), false));
                                }
                                break;
                            case "fire":
                                for (int i = 0; i < num; ++i) {
                                    bag.addTile(new Action("fire",
                                            new Image("/assets/fire.png")));
                                }
                                break;
                            case "ice":
                                for (int i = 0; i < num; ++i) {
                                    bag.addTile(new Action("ice",
                                            new Image("/assets/ice.png")));
                                }
                                break;
                            case "doubleMove":
                                for (int i = 0; i < num; ++i) {
                                    bag.addTile(new Action("doubleMove",
                                            new Image("/assets/doublemove.png")));
                                }
                                break;
                            case "backTrack":
                                for (int i = 0; i < num; ++i) {
                                    bag.addTile(new Action("backTrack",
                                            new Image("/assets/backtrack.png")));
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


        //get non fixed rows and coloumns

        ArrayList<Integer> rowNoFixed = new ArrayList<>();
        ArrayList<Integer> columnNoFixed = new ArrayList<>();

        for (int i = 0; i < gBoard.getHeight(); i++) {
            boolean empty = true;
            for (int x = 0; x < gBoard.getLength(); x++) {
                if (gBoard.getTileAt(x, i) != null) {
                    empty = false;
                }
            }
            if (empty) {

                rowNoFixed.add(i);
            }
        }

        for (int i = 0; i < gBoard.getLength(); i++) {
            boolean empty = true;
            for (int x = 0; x < gBoard.getHeight(); x++) {
                if (gBoard.getTileAt(x, i) != null) {
                    empty = false;
                }
            }
            if (empty) {

                columnNoFixed.add(i);
            }
        }

        //fill empty slots with floor tiles

        for (int i = 0; i < gBoard.getLength(); i++) {
            for (int y = 0; y < gBoard.getHeight(); y++) {
                if (gBoard.getTileAt(i, y) == null) {
                    Tile silkBagTile = bag.drawTile();
                    String tempTileType = silkBagTile.getTileType();
                    while (tempTileType.equalsIgnoreCase("fire")
                            || tempTileType.equalsIgnoreCase("ice")
                            || tempTileType.equalsIgnoreCase("doubleMove")
                            || tempTileType.equalsIgnoreCase("backTrack")){
                        silkBagTile = bag.drawTile();
                        tempTileType = silkBagTile.getTileType();
                    }
                    gBoard.insertTileAt(y,i,(Floor) silkBagTile);
                }
            }
        }

        gameData.add(gBoard);
        gameData.add(bag);
        gameData.add(players);
        gameData.add(rowNoFixed);
        gameData.add(columnNoFixed);

        //need list of rows and columns for not fixed rows
        // make into a list of players so make new player a loop in a list


        in.close();
        return gameData;//spit out board bag and then players
        // not defined atm but will be as soon as we decide how to make it work with UI
    }

}


