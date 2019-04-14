import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import java.util.*;

public class testReadingJson {

    public static void main(String[] args){


        testReadingJson a;
        a.read("transaction_data.json");

    }

    public void read(String fileNm){
        ArrayList<TransactionNode> list = new ArrayList<TransactionNode>();

        try {
            Object obj = new JSONParser().parse(new FileReader(fileNm));

            JSONObject fileRead = (JSONObject) obj;

            JSONArray tranData = (JSONArray) fileRead.get("transactions");

            Iterator itr1 = fileRead.iterator();
            while (itr1.hasNext()) {
                Iterator itr1 = ((Map) itr2.next()).entrySet().iterator();
                String temp = "";
                int count = 0;
                while (itr1.hasNext()) {
                    Map.Entry pair = itr1.next();
                    count++;
                    if (count != 2) {
                        temp += pair.getValue() + " ";
                        count++;
                    } else if (count == 2) {
                        temp += pair.getValue();
                        count = 0;
                        TransactionNode parsedData = parse(temp);
                        System.out.println(temp);
                        list.add(parsedData);
                        temp = "";
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error while reading");
            System.exit(0);
        }

        this.list = list;
    }
}