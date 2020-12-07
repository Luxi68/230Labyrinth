package entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Leaderboard.java
 * This class defines the leaderboard object used in the application.
 * @author - Alberto Ortenzi
 */
public class Leaderboard{
    ArrayList<Profile> Leaderboard;

    /**
     * This constructor creates a board object using an int paramter. It instantly creates an arraylist using the passed in integer
     * @param boardNumber - the integer representing one of the three avaialble boards
     * @throws FileNotFoundException - in case the desired file doesn't exist.
     */
    public Leaderboard(int boardNumber) throws FileNotFoundException {
        this.Leaderboard = createLeaderboard(boardNumber);
    }

    /**
     * This method creates a leaderboard object for the appropriate board. each board is associated to a specific
     * integer which is why an int paramter is passed. It also sorts the output arraylist based on the profile's number of wins
     * @param boardNumber - the integer associated to the desired board
     * @return - a sorted arraylist of profile objects.
     * @throws FileNotFoundException - in case there are errors accessing files.
     */
    private ArrayList<Profile> createLeaderboard(int boardNumber) throws FileNotFoundException {
        ArrayList<Profile> listOfUsers = new ArrayList<Profile>();
        File directoryPath = new File("resources/users");
        File listOfFiles[] = directoryPath.listFiles();
        Scanner sc;
        for (File currentFile : listOfFiles){
            sc = new Scanner(currentFile);
            String profileName = sc.nextLine();
            Profile user = new Profile(profileName);
            while (sc.hasNextLine()){
                String currentLine = sc.nextLine();
                String[] splitted = currentLine.split(",");
                if (Integer.parseInt(splitted[0])==boardNumber){
                    user.setNumberOfGamesPlayed(Integer.parseInt(splitted[1]));
                    user.setNumberOfWins(Integer.parseInt(splitted[2]));
                }
            }
            listOfUsers.add(user);
        }
        Collections.sort(listOfUsers);
        return listOfUsers;
    }

    /**
     * This method sets the leaderboard arraylist
     * @param leaderboard - the new leaderboard to be set
     */
    public void setLeaderboard(ArrayList<Profile> leaderboard) {
        Leaderboard = leaderboard;
    }

    /**
     * This method is to get the leaderboard arraylist for the current leaderboard object
     * @return
     */
    public ArrayList<Profile> getLeaderboard() {
        return Leaderboard;
    }

}
