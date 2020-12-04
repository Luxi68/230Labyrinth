package entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Leaderboard{
    ArrayList<Profile> Leaderboard;

    public Leaderboard() throws FileNotFoundException {
        this.Leaderboard = createLeaderboard();
    }

    private ArrayList<Profile> createLeaderboard() throws FileNotFoundException {
        ArrayList<Profile> listOfUsers = new ArrayList<Profile>();
        File directoryPath = new File("resources/users");
        File listOfFiles[] = directoryPath.listFiles();
        Scanner sc = null;
        for (File currentFile : listOfFiles){
            Profile user = new Profile("");
            sc = new Scanner(currentFile);
            while (sc.hasNextLine()){
                String currentLine = sc.nextLine();
                String[] splitted = currentLine.split(",");
                user.setPlayerName(splitted[0]);
                user.setNumberOfGamesPlayed(Integer.parseInt(splitted[1]));
                user.setNumberOfWins(Integer.parseInt(splitted[2]));
            }
            listOfUsers.add(user);
        }
        Collections.sort(listOfUsers);
        return listOfUsers;
    }

    public void setLeaderboard(ArrayList<Profile> leaderboard) {
        Leaderboard = leaderboard;
    }

    public ArrayList<Profile> getLeaderboard() {
        return Leaderboard;
    }

}
