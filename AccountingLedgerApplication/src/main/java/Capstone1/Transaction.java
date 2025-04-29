package Capstone1;
import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {
    private  final LocalDate Date;
    private final LocalTime Time;
    private final String Description;
    private final String Vendor;
    private final double Amount;

    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        Date = date;
        Time = time;
        Description = description;
        Vendor = vendor;
        Amount = amount;
    }

    public LocalDate getDate() {
        return Date;
    }

    public LocalTime getTime() {
        return Time;
    }

    public String getDescription() {
        return Description;
    }

    public String getVendor() {
        return Vendor;
    }

    public double getAmount() {
        return Amount;
    }

    public String toString(){
        return String.format("%-12s %-10s %-25s %-20s %10.2f",Date.toString(),Time.toString(),capitalize(Description),capitalize(Vendor),Amount);

    }
 public static String capitalize(String s){
        StringBuilder resultString=new StringBuilder();
        String[]words=s.split( " ");
       for (String word:words){
          resultString.append(firstUpper(word)).append(" ");

       }
     return resultString.toString().trim();
}
 public static String firstUpper(String s){
        return s.substring(0,1).toUpperCase()+s.substring(1).toLowerCase();
 }
}


