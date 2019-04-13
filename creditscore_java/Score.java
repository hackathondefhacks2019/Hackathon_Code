import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

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
    private final double NEARFUTURE_BASEWEIGHT = 10.0;

    private final double SCORE_PER_GRADE = 65.0;
    private final double PASTSCORE_MULTIPLIER_TINKER = 0.4;
    private final double RECENTSCORE_MULTIPLIER_TINKER = 0.4;


    public Score(File file) throws FileNotFoundException {
        read(file);
        fullScoreCalculator();
    }

    // full score calculator
    public void fullScoreCalculator() {
        double result = 0.0;
        // total score for pastScore
        result += pastScore * pastScoreCalculator()/100.0;
        System.out.println(pastScoreCalculator()/100.0);
        System.out.println();
        // total score for recentScore
        result += recentScore * recentScoreCalculator()/100.0;
        System.out.println(recentScore);

        // total score for nearFutureSore
        result += nearFutureScore * nearFutureScoreCalculator()/100.0;
        System.out.println(nearFutureScore);

        // total score for farFutureScore
        result += farFutureScore * farFutureScoreCalculator()/100.0;
        System.out.println(farFutureScore);

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
            pastAcum += Math.abs(timeSpan)/30.0 * totalAcum * PASTSCORE_MULTIPLIER_TINKER;
            //System.out.println(pastAcum);
        }
        pastScore = pastAcum;

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
        int nagasa = 20;
        if (list.size() < 20) nagasa = list.size();

        // calculation for pastScore
        double recentAcum = 0.0;
        double totalAcum = 0.0;
        int currentDate = currentDate();
        for (int i = 0; i < nagasa; i++) {
            totalAcum += list.get(i).getAmount();

            int timeSpan = compareDate(currentDate, list.get(i).getDate());
            // multiplier applied
            recentAcum += Math.abs(timeSpan)/30.0 * totalAcum * RECENTSCORE_MULTIPLIER_TINKER;
            //System.out.println(recentAcum);
        }
        recentScore = recentAcum;

        // weight according to number of transactions
        if (recentScore >= 250.0) percentage = RECENTSCORE_BASEWEIGHT+4.0;
        else if (recentScore >= 150.0) percentage = RECENTSCORE_BASEWEIGHT+2.0;
        else percentage = RECENTSCORE_BASEWEIGHT;

        // final statements in method
        this.recentScore = recentScore;
        return percentage;
    }

    // future score calculator
    public double farFutureScoreCalculator() {
        double percentage;
        double[] temp = linearTrendLine(list.size());
        this.farFutureScore = (temp[0]*list.size())/(double)list.size() + temp[1];

        return percentageFinder(farFutureScore, FARFUTURE_BASEWEIGHT);
    }

    public double nearFutureScoreCalculator() {
        double percentage;
        double[] temp = linearTrendLine((list.size()>=20)?20:list.size());
        this.nearFutureScore = (temp[0]*((list.size()>=10)?20:list.size()))/10.0 + temp[1];

        return percentageFinder(nearFutureScore, NEARFUTURE_BASEWEIGHT);
    }

    private double percentageFinder(double first, double second) {
        double percentage;
        if (first <= 80) percentage = second;
        else if (first <= 100) percentage = second+1.0;
        else if (first <= 120) percentage = second+3.0;
        else percentage = second+5.0;
        return percentage;
    }

    private double[] linearTrendLine(int length) {
        double a = 0.0, b1 = 0.0, b2 = 0, c = 0.0, y = 0.0;

        int initialDate = list.get(0).getDate();
        for (int i = 0; i < length; i++) {
            y += list.get(i).getAmount();
            int x = compareDate(initialDate, list.get(i).getDate());

            a += x * y;
            b1 += x;
            b2 += y;
            c += x*x;

        }
        a *= 3;
        c *= 3;

        double slope = (a - b1*b2) / (c - b1*b1);
        double yIntercept = (b2 - slope*b1) / (double)list.size();
        return new double[] {slope, yIntercept};
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

    // parse line
    public TransactionNode parse(String line) {
        String[] temp = line.split(" ");
        double amount = Double.parseDouble(temp[0]);


        String[] stat = temp[1].split("/");
        String day = String.format(stat[0].length() == 1 ? "0"+stat[0] : stat[0]);
        String mon = String.format(stat[1].length() == 1 ? "0"+stat[1] : stat[1]);
        String year = String.format(stat[2]);
        int date = Integer.parseInt(year+mon+day);

        return new TransactionNode(amount, date);
    }

    // read file
    public void read(File file) throws FileNotFoundException {
        ArrayList<TransactionNode> list = new ArrayList<>();

        Scanner scn = new Scanner(file);
        while (scn.hasNextLine()) {
            String line = scn.nextLine();
            TransactionNode node = parse(line);
            list.add(node);
        }

        this.list = list;
    }
}
