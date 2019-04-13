import java.io.FileNotFoundException;
import java.io.File;

public class Client {
    public static void main(String[] args) throws FileNotFoundException {
//        Score temp = new Score(new File("/Users/shimadaharuki/Desktop/transaction_data.txt"));
//        System.out.println("demo-0");
//        System.out.println(temp.getScoreNumerical());
//        System.out.println();
//
//        Score stat = new Score(new File("/Users/shimadaharuki/Desktop/demo-1.txt"));
//        System.out.println("demo-1");
//        System.out.println(stat.getScoreNumerical());
//        System.out.println();

        Score got = new Score(new File("/Users/shimadaharuki/Desktop/demo-2.txt"));
        System.out.println("demo-2");
        System.out.println(got.getScoreNumerical());
        System.out.println();
    }
}
