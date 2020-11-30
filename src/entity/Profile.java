package entity;

import java.io.File;

public class Profile {
    String playerName;
    int numberOfWins = 0;
    int numberOfLosses = 0;
    int numberOfGamesPlayed = 0;

    public Profile(String name){
        this.playerName = name;
    }

    public void createProfileFile(String name){
        File createdFile = new File()
    }

    public int getNumberOfGamesPlayed() {
        return numberOfGamesPlayed;
    }

    public int getNumberOfLosses() {
        return numberOfLosses;
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

    public void setNumberOfLosses(int numberOfLosses) {
        this.numberOfLosses = numberOfLosses;
    }

    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

}
