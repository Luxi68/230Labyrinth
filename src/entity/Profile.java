package entity;

import java.io.*;
import java.util.Scanner;

/**
 * Profile.java
 * This class defines the player profile object.
 * @author - Alberto Ortenzi
 */
public class Profile implements Comparable<Profile>, Serializable {
    String playerName;
    int numberOfWins = 0;
    int numberOfGamesPlayed = 0;

    /**
     * This constructor creates the profile object using a string which is then assigned to be the profile name
     * @param name - The name of the profile.
     */
    public Profile(String name){
        this.playerName = name;
    }

    /**
     * this method creates a file with the string name as filename inside the users folder
     * it also writes a file with a specific format
     * @param name - the profile name which is created
     * @throws IOException - in case the folder doesn't exist.
     */
    public void createProfileFile(String name) throws IOException {
        String filename = "resources/users/"+name+".txt";
        File createdFile = new File(filename);
        FileWriter writing = new FileWriter(filename);
        writing.write(name);
        writing.write(System.lineSeparator());
        for (int i=1; i<=3 ; i++){
            writing.write(Integer.toString(i));
            writing.write(",");
            writing.write("0");
            writing.write(",");
            writing.write("0");
            writing.write(System.lineSeparator());
        }
        writing.close();
    }

    /**
     * This method overrides the compareTo method making it so that two profile objects are sorted
     * based on the number of wins
     * @param profile - the profile to be compared to
     * @return - true if the compared player has more wins.
     */
    @Override
    public int compareTo(Profile profile){
        return profile.getNumberOfWins() - getNumberOfWins();
    }

    /**
     * getter method for games played.
     * @return - the number of games played attribuite for the current profile object
     */
    public int getNumberOfGamesPlayed() {
        return numberOfGamesPlayed;
    }

    /**
     * getter method for games won.
     * @return - the number of games won attribuite for the current profile object
     */
    public int getNumberOfWins() {
        return numberOfWins;
    }

    /**
     * getter method for player name.
     * @return - the player name attribuite for the current profile object
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * setter method for number of games played. sets the number of games played for the current profile
     * @param numberOfGamesPlayed - the new value for games played attribuite
     */
    public void setNumberOfGamesPlayed(int numberOfGamesPlayed) {
        this.numberOfGamesPlayed = numberOfGamesPlayed;
    }

    /**
     * setter method for number of gwins. sets the number of wins for the current profile
     * @param numberOfWins - the new value for games won attribuite
     */
    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    /**
     * setter method for player name. sets the player name for the current profile
     * @param playerName - the new value for player name attribuite
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

}
