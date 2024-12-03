import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class StatTracker {

    protected static int player1Wins = 0;
    protected static int player2Wins = 0;
    protected static int comWins = 0;
    protected static long timeStart = 0;
    protected static long timeEnd = 0;
    protected static ArrayList<String> winHistory = new ArrayList<String>();

    public static void startTime(){
        timeStart = System.currentTimeMillis();
    }

    public static void endTime(){
        timeEnd = System.currentTimeMillis();
    }

    public static int calcTime(){
        long timeMS = timeEnd - timeStart;
        int timeS = (int)(timeMS / 1000);
        return timeS;
    }

    public static void p1Win(){
        player1Wins++;
    }

    public static void p2Win(){
        player2Wins++;
    }

    public static void comWin(){
        comWins++;
    }

    public static void addWin(String player, String game){
        int time = calcTime();
        String winInfo = player + " won " + game +  " in " + time + " seconds.";
        winHistory.add(winInfo);
        writeToFile(winInfo);
    }

    private static void writeToFile(String winInfo){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("winhistory.txt", true))){
            writer.write(winInfo);
            writer.newLine();
        }catch(IOException e){
            e.printStackTrace();
        }
    
    }

    public static Integer getP1Wins(){
        return player1Wins;
    }

    public static Integer getP2Wins(){
        return player2Wins;
    }

    public static Integer getComWins(){
        return comWins;
    }

    public static ArrayList<String> getWinHistory(){
        return winHistory;
    }
}