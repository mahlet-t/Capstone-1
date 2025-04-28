package Capstone1;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {
    private LocalDate Date;
    private LocalTime Time;
    private String Description;
    private String Vendor;
    private double Amount;

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
        return String.format("%-12s %-10s %-10s  %-10s %10.2f",Date.toString(),Time.toString(),Description,Vendor,Amount,"\n");
    }

}


