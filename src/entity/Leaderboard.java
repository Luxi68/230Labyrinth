package entity;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import javafx.print.PageOrientation;
import javafx.util.Pair;

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
        int count = 0;
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
            count++;
            Tuple entry = new Tuple(count,user);
            listOfUsers.add(entry);
        }
        Collections.sort(listOfUsers);
        for (int i=0; i<listOfUsers.size(); i++){
            if (listOfUsers.get(i).rank != i+1){
                listOfUsers.get(i).setRank(i+1);
            }
        }
        return listOfUsers;
    }

    public void setLeaderboard(ArrayList<Tuple> leaderboard) {
        Leaderboard = leaderboard;
    }

    public ArrayList<Tuple> getLeaderboard() {
        return Leaderboard;
    }

}
