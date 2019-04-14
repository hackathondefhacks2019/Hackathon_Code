import java.util.*;

public class User {

    private Date startDate;
    private Date endDate;
    private String firstName;
    private String lastName;
    private double accountNum;
    private int cvv;
    private Owe owe;
    public  ArrayList<Transaction> userTransactionHist;

    public User(){
        startDate = new Date(2015,1,1);
        endDate = new Date(2015,1,2);
        firstName = "John";
        lastName = "Smith";
        accountNum = 1000001;
        cvv = 123;
        owe = new Owe(0.00,new Date(2015,1,1));
        userTransactionHist = new ArrayList<Transaction>();
    }

    public User(Date start, Date end, String first, String last, double acc, int cv, Owe value){
        startDate = new Date(start.getYear(),start.getMonth(), start.getDay());
        endDate = new Date(end);
        firstName = first;
        lastName = last;
        accountNum = acc;
        cvv = cv;
        owe = new Owe(value.getOwedAmt(), value.getOweAmtSince());
        userTransactionHist = new ArrayList<Transaction>();
    }

    //add transaction object
    public boolean addTransaction(Transaction transactionVal){
        //owe += transactionVal.getTranAmt();
        double temp = transactionVal.getTranAmt();
        double curOweAmt = owe.getOwedAmt();

        //updates owe with amt
        //the date of owe object gets updated only if
        //the amount goes from positive to negatiev or
        //vice versa
        //in order
        if (temp + curOweAmt > 0)
            owe.updateAmt(transactionVal.getTranAmt());
        else
            owe.updateAmt(transactionVal.getTranAmt(), transactionVal.getTranDate());

        return userTransactionHist.add(transactionVal);
    }

    //add the transaction object in parts: date, amount, details
    public boolean addTransaction(Date transactionDate, double transactionAmt, String details){
        owe.updateAmt(transactionAmt);
        return userTransactionHist.add(transactionDate, transactionAmt, details);
    }

    public double getOweAmt(){
        return Owe.getOwedAmt();
    }

    //owe class that keeps track of the owed amount and the date since the
    //owed amount started accumulating
    public class Owe {
        double owedAmt;
        Date oweAmtSince;

        public Owe(){
            owedAmt = 0.00;
            oweAmtSince = new Date(2015,1,1);
        }

        public Owe(double oweValue, Date sinceDate){
            owedAmt = oweValue;
            oweAmtSince = new Date(sinceDate);
        }

        public double getOwedAmt(){
            return owedAmt;
        }

        public Date getOweAmtSince(){
            return new Date(oweAmtSince);
        }

        public void setOwedAmt(double value){
            owedAmt = value;
        }

        public void setOweAmtSince(Date value){
            oweAmtSince= new Date(value);
        }

        public void updateAmt(double value){
            owedAmt += value;
        }

        public void updateValues(double amt, Date owedDate){
            owedAmt += amt;
            oweAmtSince = new Date(owedDate);
        }
    }




}