package Capstone1;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;


public class TransactionLedger {
    /**
     * Accounting Ledger Application
     * This Java CLI application allows users to track financial transactions,
     * including deposits and payments, and generate financial reports.
     * All transactions are saved to and read from a transaction.csv file.
      */
    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        ArrayList<Transaction>transactionList = getTransactionList();
        addDeposit(transactionList,input);


    }

    /**
     * Reads all transactions from the transactions.csv file.
     * Each line is parsed into a Transaction object and added to an ArrayList.
     * Returns the full list of transactions.
     */
    public static ArrayList<Transaction> getTransactionList() {
        ArrayList<Transaction> transactionList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("transaction.csv"));
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                LocalDate Date = LocalDate.parse(parts[0]);
                LocalTime Time =LocalTime.parse( parts[1]);
                String Description = parts[2];
                String Vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);
                transactionList.add(new Transaction(Date,Time, Description, Vendor, amount));
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("invalid input" + e.getMessage());
        }
        return transactionList;
    }
    /**
     * prompts the user to enter deposit information.
     * creates a new Transaction and appends it to the transactions.csv file.
     */
    public static void addDeposit(ArrayList<Transaction>transactionList,Scanner input){
        System.out.println("Enter a description: ");
        String description=input.nextLine();
        System.out.println("Enter the vendor name: ");
        String name=input.nextLine();
        System.out.println("Enter the amount to deposit:");
        double amount= input.nextDouble();
        input.nextLine();
        try {
            Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now().withNano(0), description, name, amount);
            File file = new File("transaction.csv");
            boolean fileExists = file.exists();
            FileWriter writer = new FileWriter(file, true);
            if (!fileExists){
                writer.write("date"+"|"+"time"+"|"+"description"+"|"+"vendor"+"|"+"amount\n");

            }
            writer.write(transaction.getDate()+"|"+transaction.getTime()+"|"+transaction.getDescription()+"|"+transaction.getVendor()+"|"+transaction.getAmount()+"\n");
            writer.close();
            transactionList.add(transaction);

        }catch (Exception e){
            System.out.println("Invalid: "+e.getMessage());
        }
    }


}