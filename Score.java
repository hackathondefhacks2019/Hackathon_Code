// transaction_data.txt

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
//import java.text.SimpleDateFormat;
//import java.text.DateFormat;
import java.util.Date;

public class Score {
    private double score;
    private double pastScore;
    private double nearFutureScore;
    private double farFutureScore;
    private double recentScore;
    private String currentScoreGrade;
    private File file;
    private ArrayList<TransactionNode> list;

    private final double INITIAL_CREDITSCORE = 70;
    private final double INITIAL_SCORE = 100;

    private final double PASTSCORE_BASEWEIGHT = 20.0;
    private final double RECENTSCORE_BASEWEIGHT = 10.0;
    private final double FARFUTURE_BASEWEIGHT = 20.0;

    private final double SCORE_PER_GRADE = 65.0;
    private final double PASTSCORE_MULTIPLIER_TINKER = 0.4;
    private final double RECENTSCORE_MULTIPLIER_TINKER = 0.4;


    public Score() throws FileNotFoundException {
        File file = new File("Users/shimadaharuki/Downloads/transactions_data.txt");
        read(file);
        fullScoreCalculator();
    }

    // full score calculator
    public void fullScoreCalculator() {
        double result = 0.0;
        // total score for pastScore
        result += pastScore * pastScoreCalculator()/100.0;

        // total score for recentScore
        result += recentScore * recentScoreCalculator()/100.0;

        // total score for nearFutureSore


        // total score for farFutureScore

        setScore(result);
    }

    // past score (all past transactions)
    public double pastScoreCalculator() {
        // initializing variables
        double percentage;
        double pastScore;
        int numsOfData = list.size();

        // weight according to number of transactions
        if (numsOfData <= 10) percentage = PASTSCORE_BASEWEIGHT;
        else if (numsOfData <= 20) percentage = PASTSCORE_BASEWEIGHT+1.0;
        else if (numsOfData <= 30) percentage = PASTSCORE_BASEWEIGHT+4.0;
        else if (numsOfData <= 50) percentage = PASTSCORE_BASEWEIGHT+6.0;
        else percentage = PASTSCORE_BASEWEIGHT+8.0;

        // calculation for pastScore
        double pastAcum = 0.0;
        double totalAcum = 0.0;
        int currentDate = currentDate();
        for (int i = 0; i < list.size(); i++) {
            totalAcum += list.get(i).getAmount();

            // length of time money was due
            int timeSpan = compareDate(currentDate, list.get(i).getDate());
            // multiplier applied
            pastAcum += Math.abs(timeSpan) * totalAcum * PASTSCORE_MULTIPLIER_TINKER;
        }
        pastScore = INITIAL_SCORE - pastAcum;

        // final statements in method
        this.pastScore = pastScore;
        return percentage;
    }

    // recent score calculator
    // the score of the most recent month
    public double recentScoreCalculator() {
        // initializing variables
        double percentage;
        double recentScore;

        // calculation for pastScore
        double recentAcum = 0.0;
        double totalAcum = 0.0;
        int currentDate = currentDate();
        for (int i = 0; i < 20; i++) {
            totalAcum += list.get(i).getAmount();

            int timeSpan = compareDate(currentDate, list.get(i).getDate());
            // multiplier applied
            recentAcum += Math.abs(timeSpan) * totalAcum * RECENTSCORE_MULTIPLIER_TINKER;
        }
        recentScore = INITIAL_SCORE - recentAcum;

        // weight according to number of transactions
        if (recentScore >= 80.0) percentage = RECENTSCORE_BASEWEIGHT+4.0;
        else if (recentScore >= 70.0) percentage = RECENTSCORE_BASEWEIGHT+2.0;
        else percentage = RECENTSCORE_BASEWEIGHT;

        // final statements in method
        this.recentScore = recentScore;
        return percentage;
    }

    // future score calculator
    public double farFutureScoreCalculator() {
        double a = 0.0, b1 = 0.0, b2 = 0, c = 0.0, y = 0.0;
        double slope, yintercept, percentage;
        int x;

        int initialDate = list.get(0).getDate();
        for (int i = 0; i < list.size(); i++) {
            y += list.get(i).getAmount();
            x = compareDate(initialDate, list.get(i).getDate());

            a += x * y;
            b1 += x;
            b2 += y;
            c += x*x;

        }
        a *= 3;
        c *= 3;

        slope = (a - b1*b2) / (c - b1*b1);
        yintercept = (b2 - slope*b1) / (double)list.size();
        this.farFutureScore = slope * 30 + yintercept;

        if (farFutureScore <= 80) percentage = FARFUTURE_BASEWEIGHT;
        else if (farFutureScore <= 100) percentage = FARFUTURE_BASEWEIGHT+1.0;
        else if (farFutureScore <= 120) percentage = FARFUTURE_BASEWEIGHT+3.0;
        else percentage = FARFUTURE_BASEWEIGHT+5.0;
        return percentage;
    }

    // near future score calculator
    public double nearFutureScoreCalculator() {

    }

    // artificial compareTo method for two dates
    private int compareDate(int first, int second) {
        Date firstDay = intToDate(first);
        Date secondDay = intToDate(second);
        return firstDay.compareTo(secondDay);
    }

    // int to date converter
    private Date intToDate(int date) {
        String temp = date + "";

        int year = Integer.parseInt(temp.substring(0, 3));
        int month = Integer.parseInt(temp.substring(4, 5));
        int day = Integer.parseInt(temp.substring(6, 7));
        return new Date(year, month, day);
    }

    // get methods
    private int currentDate() {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//        Date date = new Date();
//        return Integer.parseInt((dateFormat.format(date)).replaceAll("/", ""));
        return 20160914;
    }

    public double getScoreNumerical() {
        return this.score;
    }

    public String toString() {
        String result;
        if (score >= SCORE_PER_GRADE+20.0) result = "AAA";
        else if (score >= SCORE_PER_GRADE+15.0) result = "AA";
        else if (score >= SCORE_PER_GRADE+10.0) result = "A";
        else if (score >= SCORE_PER_GRADE+5.0) result = "B";
        else if (score >= SCORE_PER_GRADE) result = "BB";
        else result = "BBB";

        return result;
    }

    // set methods
    private void setScore(double score) {
        this.score = score;
    }

    // projection


    // parse line
    public TransactionNode parse(String line) {
        String[] temp = line.split(" ");
        double amount = Double.parseDouble(temp[0]);

        String[] stat = temp[1].split("/");
        String day = String.format("%td", stat[0]);
        String mon = String.format("%tm", stat[1]);
        String year =String.format("%ty", stat[2]);
        int date = Integer.parseInt(year+mon+day);

        return new TransactionNode(amount, date);
    }

    // read file
    public void read(File file) throws FileNotFoundException {

        Scanner scn = new Scanner(file);
        ArrayList<TransactionNode> list = new ArrayList<>();

        while (scn.hasNextLine()) {
            TransactionNode temp = parse(scn.nextLine());
            list.add(temp);
        }

        this.list = list;
    }
}
