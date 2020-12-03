package entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Leaderboard{
    ArrayList<Tuple> Leaderboard;

    public Leaderboard() throws FileNotFoundException {
        this.Leaderboard = createLeaderboard();
    }

    private ArrayList<Tuple> createLeaderboard() throws FileNotFoundException {
        ArrayList<Tuple> listOfUsers = new ArrayList<Tuple>();
        File directoryPath = new File("resources/users");
        File listOfFiles[] = directoryPath.listFiles();
        Scanner sc = null;
        for (File currentFile : listOfFiles){
            int count = 0;
            Profile user = new Profile("");
            sc = new Scanner(currentFile);
            while (sc.hasNextLine()){
                String currentLine = sc.nextLine();
                String[] splitted = currentLine.split(",");
                user.setPlayerName(splitted[0]);
                user.setNumberOfGamesPlayed(Integer.parseInt(splitted[1]));
                user.setNumberOfWins(Integer.parseInt(splitted[2]));
            }
            count++;
            Tuple entry = new Tuple(count,user);
            listOfUsers.add(entry);
        }
        Collections.sort(listOfUsers);
        return  listOfUsers;
    }

    public void setLeaderboard(ArrayList<Tuple> leaderboard) {
        Leaderboard = leaderboard;
    }

    public ArrayList<Tuple> getLeaderboard() {
        return Leaderboard;
    }

}
