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

    public String getDescription(){
        return description;
    }

    public void setTranDate(Date dateOfTran){
        tranDate = new Date(dateOfTran);
    }

    public void setTranAmt(double value){

        //i dont want anyone else setting the tranAmt
        // to 0. value of 0 on transaction is reserved
        //for a dummy transaction object
        //- value means user credited money
        //+ vaue means user debited money
        tranAmt = (value != 0) ? value : 0.00;
    }

    public void setDescription(String details){
        description = details != NULL? details : "";
    }
}