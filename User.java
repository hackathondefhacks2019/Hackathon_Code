import java.util.*;

public class User {

    private Date startDate;
    private Date endDate;
    private  String firstName;
    private  String lastName;
    private  long accountNum;
    private  int cvv;
    public  ArrayList<Transaction> userTransactionHist;

    public User(){
        startDate = new Date(2015,1,1);
        endDate = new Date(2015,1,2);
        firstName = "John";
        lastName = "Smith";
        accountNum = 100000000001;
        cvv = 123;
        userTransactionHist = new ArrayList<Transaction>();
    }

    public User(Date start, Date end, String first, String last, long acc, int cv){
        startDate = new Date(start);
        endDate = new Date(end);
        firstName = first;
        lastName = last;
        accountNum = acc;
        cvv = cv;
        userTransactionHist = new ArrayList<Transaction>();
    }




}