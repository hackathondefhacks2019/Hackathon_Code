import java.util.*;

public class Transaction{

    private Date tranDate;
    double tranAmt;
    private String description;

    public Transaction(){
        tranDate = new Date(2015,1,1);
        tranAmt = 0.00;
        description = "";
    }

    public Transaction(Date dateTran, double amount, String detail ){
        tranDate = new Date(dateTran);
        tranAmt = amount;
        description = detail;
    }

    public double getTranAmt(){
        return (double)tranAmt;
    }

    public Date getTranDate(){
        return new Date(tranDate);
    }


}