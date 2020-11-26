/**
 * Motd.java
 * This class issues a HTTP get request and obtains the message of the day
 * @author Alberto Ortenzi
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Motd {
    /**
     * This method takes in a string which is a url and issues a HTTP get request using said url
     * @param url - the url to be used for the get request
     * @return the contents of the get request in a string.
     * @throws IOException
     */
   private static String request(String url) throws IOException {
       URL link = new URL(url);
       HttpURLConnection con = (HttpURLConnection) link.openConnection();
       con.setRequestMethod("GET");
       BufferedReader in = new BufferedReader(
               new InputStreamReader(con.getInputStream()));
       String inputLine;
       StringBuffer content = new StringBuffer();
       inputLine = in.readLine();
       content.append(inputLine);
       in.close();
       con.disconnect();
       return inputLine;
   }

    /**
     * This method given a string encrypts using a ceaser cipher with a given shift. I.e encrypt(A,1) will return B
     * @param text - the string to be encrypted
     * @param shift - the number of positions it will move. If negative it will move backwards
     * @return a string with the encrypted text
     */
    private static String encrypt(String text, int shift) {
        String encryptedMsg = "";
        char letter;
        for (int i = 0; i < text.length(); i++) {
            letter = text.charAt(i);

            if (letter >= 'a' && letter <= 'z') {
                letter = (char) (letter + shift);

                if (letter > 'z') {
                    letter = (char) (letter - 'z' + 'a' - 1);
                }
                if (letter < 'a') {
                    letter = (char) (Math.abs(letter) - 'a' + 1 + 'z');
                }
                encryptedMsg += letter;
            } else if (letter >= 'A' && letter <= 'Z') {
                letter = (char) (letter + shift);

                if (letter > 'Z') {
                    letter = (char) (letter - 'Z' + 'A' - 1);
                }
                if (letter < 'A') {
                    letter = (char) (Math.abs(letter) - 'A' + 1 + 'Z');
                }
                encryptedMsg += letter;
            } else {
                encryptedMsg += letter;
            }
        }
        return encryptedMsg;
    }
    
    public static void main(String args[]) throws IOException {
        /*
        Brief summary of what happens:
        1. a get request is done at a predetermined url
        2. the result of said request is encrypted using a ceaser cipher using alternate shift.
           i.e first letter 1 character backwards. second letter 2 chracter forwards ecc.
        3. The result is appended to a given string "CS-230" and with the character count
        4. Result of said operations is appended to a predetermined url (solutionUrl)
        5. A new get request is done using said url and the result is the MOTD
         */
        int charCount;
        String solutionUrl = "http://cswebcat.swansea.ac.uk/message?solution=";
        String url = "http://cswebcat.swansea.ac.uk/puzzle";
        String puzzle = request(url);
        String solvedPuzzle = "";
        char c;
        int count = 1;
        for(int i=0; i < puzzle.length(); i++){
            if(count < 0){
                count = Math.abs(count);
                c = puzzle.charAt(i);
                solvedPuzzle += encrypt(Character.toString(c), count);
                count++;
            }
            else{
                count = -count;
                c = puzzle.charAt(i);
                solvedPuzzle += encrypt(Character.toString(c), count);
                count--;

            }
        }
        solvedPuzzle = "CS-230" + solvedPuzzle;
        charCount = solvedPuzzle.length();
        solvedPuzzle = solvedPuzzle + charCount;
        solutionUrl = solutionUrl + solvedPuzzle;
        String actualMessage = request(solutionUrl);
        System.out.println(actualMessage);
    }
}