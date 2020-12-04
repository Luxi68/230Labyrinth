package entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Profile implements Comparable<Profile> {
    String playerName;
    int numberOfWins = 0;
    int numberOfGamesPlayed = 0;

    public Profile(String name){
        this.playerName = name;
    }

    public void createProfileFile(String name) throws IOException {
        String filename = "/Users/albertoortenzi/IdeaProjects/230Labyrinth/resources/users/"+name+".txt";
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

    @Override
    public int compareTo(Profile profile){
        return profile.getNumberOfWins() - getNumberOfWins();
    }

    public int getNumberOfGamesPlayed() {
        return numberOfGamesPlayed;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setNumberOfGamesPlayed(int numberOfGamesPlayed) {
        this.numberOfGamesPlayed = numberOfGamesPlayed;
    }

    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

}
