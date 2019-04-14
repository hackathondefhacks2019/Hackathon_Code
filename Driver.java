import java.util.*;
import java.lang.*;

public class Driver{

    //public static String fileName = "transaction_data.txt";

    public static void main(String[] args){

        User rithik = new User();

        //System.out.println("Current amount owed: " + rithik.getOweAmt());

        Date tranDate = new Date(2016,5,4);
        Date tranDateA = new Date(2016,5,10);
        Transaction newTran = new Transaction(tranDate,24.50,"Bought a watch");
        Transaction newTranA = new Transaction(tranDateA,12.60,"Grocery shopping");

        rithik.addTransaction(newTran,fileName);
        rithik.addTransaction(newTranA,fileName);

        //System.out.println("Current amount owed: " + rithik.getOweAmt());
        testReadingJson a;
        a.read("transaction_data.json");

    }
}