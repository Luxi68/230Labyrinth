package entity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Profile {
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
        writing.write(",");
        writing.write("0");
        writing.write(",");
        writing.write("0");
        writing.close();
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
