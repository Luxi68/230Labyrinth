/**
 * FileReader.java
 * This class reads a level file.  For the format of this file, see A1 design (slight modification have been done to it)
 * @author Alberto Ortenzi
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
Right now File reader will work as soon as other classes are implemented I will update it using the appropriate method
names and fully implement it as soon as we decide how to integrate it with the ui and the rest of the java program.
 */
public class FileReader {
    /**
     * Reads the data file used by the program and returns something not sure atm. Based on the first character of each line
     * of the file the method calls a specific method and creates specific objects which are part of the program
     * @param in the scanner containing the file
     * @return Unkown atm
     */
    private static Game readDataFile(Scanner in) {
        while (in.hasNextLine()) {
            String data = in.nextLine();
            String[] splitted = data.split(",");// splits the line of the program using the comma delimiter
            if (splitted[0].matches("-?\\d+")) { //checks if the character is a integer and returns true and false based on it
                switch (splitted.length) { //checks length of array and does action based on it as it uses the file format.
                    case 2:
                        //create board object using x,y paramters as board declaration uses 2 numbers
                        break;
                    case 3:
                        //create player object and set starting position to y,z and player number to x
                        break;
                }
            } else {
                switch (splitted[0].toLowerCase()) { //checks the first position of the array as it contains a specific character
                    case "f":
                        //create a tile object with type x, and rotation j and insert it into the board at position y,z
                        break;
                    case "b":
                        //create Y amount of tile object of type x with default values and insert them into silk bag
                        break;
                }
            }
        }
        in.close();
        return //not defined atm but will be as soon as we decide how to make it work with UI
    }

    /**
     * Method to read the file name which is asked in the main method. initialised a scanner and checks if the file exists
     * @param filename the name of the file
     * @return method call to readDatafile(in) where in contains the file
     */
    public static Game readDataFile(String filename) {
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
