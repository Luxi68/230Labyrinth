package entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Leaderboard{
    ArrayList<Profile> Leaderboard;

    public Leaderboard(int boardNumber) throws FileNotFoundException {
        this.Leaderboard = createLeaderboard(boardNumber);
    }

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

    public void setLeaderboard(ArrayList<Profile> leaderboard) {
        Leaderboard = leaderboard;
    }

    public ArrayList<Profile> getLeaderboard() {
        return Leaderboard;
    }

}
