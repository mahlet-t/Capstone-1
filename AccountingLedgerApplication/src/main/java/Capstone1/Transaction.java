package Capstone1;

public class Transaction {
    private String Date;
    private String Time;
    private String Description;
    private String Vendor;
    private double Amount;

    public Transaction(String date, String time, String description, String vendor, double amount) {
        Date = date;
        Time = time;
        Description = description;
        Vendor = vendor;
        Amount = amount;
    }

    public String getDate() {
        return Date;
    }

    public String getTime() {
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
}
