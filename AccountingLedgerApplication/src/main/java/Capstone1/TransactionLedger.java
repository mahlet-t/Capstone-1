package Capstone1;
import java.io.*;
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
        Scanner input = new Scanner(System.in);
        ArrayList<Transaction> transactionList = getTransactionList();
        monthToDateReport(transactionList);


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
                LocalTime Time = LocalTime.parse(parts[1]);
                String Description = parts[2];
                String Vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);
                transactionList.add(new Transaction(Date, Time, Description, Vendor, amount));
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
     * ....................................................................................................................
     */
    public static void addDeposit(ArrayList<Transaction> transactionList, Scanner input) {
        System.out.println("Enter a description: ");
        String description = input.nextLine();
        System.out.println("Enter the vendor name: ");
        String name = input.nextLine();
        System.out.println("Enter the amount to deposit:");
        double amount = input.nextDouble();
        input.nextLine();
        try {
            Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now().withNano(0), description, name, amount);
            File file = new File("transaction.csv");
            boolean fileExists = file.exists();
            FileWriter writer = new FileWriter(file, true);
            if (!fileExists) {
                writer.write("date" + "|" + "time" + "|" + "description" + "|" + "vendor" + "|" + "amount\n");

            }
            writer.write(transaction.getDate() + "|" + transaction.getTime() + "|" + transaction.getDescription() + "|" + transaction.getVendor() + "|" + transaction.getAmount() + "\n");
            writer.close();
            transactionList.add(transaction);

        } catch (Exception e) {
            System.out.println("Invalid: " + e.getMessage());
        }
    }

    /**
     * prompts the user to enter payment information, creates a new transaction,
     * and appends it to the transactions.csv file.
     * ........................................................................................................
     */
    public static void makePayment(ArrayList<Transaction> transactionsList, Scanner input) {
        System.out.println("Enter a description: ");
        String description = input.nextLine();
        System.out.println("Enter the vendor name: ");
        String name = input.nextLine();
        System.out.println("Enter the amount to pay:");
        double amount = input.nextDouble();
        input.nextLine();
        amount = amount * -1;
        try {
            Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now().withNano(0), description, name, amount);
            File file = new File("transaction.csv");
            boolean fileExists = file.exists();
            FileWriter writer = new FileWriter(file, true);
            if (!fileExists) {
                writer.write("date" + "|" + "time" + "|" + "description" + "|" + "vendor" + "|" + "amount\n");

            }
            writer.write(transaction.getDate() + "|" + transaction.getTime() + "|" + transaction.getDescription() + "|" + transaction.getVendor() + "|" + transaction.getAmount() + "\n");
            writer.close();
            transactionsList.add(transaction);

        } catch (Exception e) {
            System.out.println("Invalid: " + e.getMessage());
        }

    }

    /**
     * Display all transactions in the ledger,
     * including a header row and formatted transaction details.
     */
//...................................
    public static void displayAll(ArrayList<Transaction> transactionsList) {
        System.out.println("here is your complete transaction history: ");
        System.out.println("..............................................................................................");
        System.out.println(String.format("%-12s%-10s%-10s  %10s  %-10s", "Date", "Time", "Description", "Vendor", "Amount"));
        System.out.println("..............................................................................................");
        for (Transaction transaction : transactionsList) {
            System.out.println(transaction);
        }
    }

    /**
     * Displays all deposit transactions from the transaction list,
     * including a formatted header
     */

    public static void displayDeposits(ArrayList<Transaction> transactionsList) {
        System.out.println("Here is your complete deposit transaction history:");
        System.out.println(".........................................................");
        System.out.println(String.format("%-12s%-10s%-10s  %10s  %-10s", "Date", "Time", "Description", "Vendor", "Amount"));
        System.out.println("............................................................................");
        for (Transaction transaction : transactionsList) {
            if (transaction.getAmount() >= 0) {
                System.out.println(transaction);

            }
        }

    }

    /**
     * Displays all deposit transactions from the transaction list,
     * including a formatted header.
     */


    public static void displayPayment(ArrayList<Transaction> transactionsList) {
        System.out.println("Here is your complete payment history:");
        System.out.println("................................................................");
        System.out.println(String.format("%-12s%-10s%-10s  %10s  %-10s", "Date", "Time", "Description", "Vendor", "Amount"));
        System.out.println("............................................................................");
        for (Transaction transaction : transactionsList) {
            if (transaction.getAmount() < 0) {
                System.out.println(transaction);


            }
        }
    }

    /**
     * Displays all transactions from the beginning of the current month until today.
     * Only transactions with dates between the first day of the month and the current date are shown.
     *Includes a formatted header for clarity.
     */
    public static void monthToDateReport(ArrayList<Transaction>transactionsList){
        System.out.println("Here is your current month report");
        System.out.println("................................................................");
        System.out.println(String.format("%-12s%-10s%-10s  %10s  %-10s", "Date", "Time", "Description", "Vendor", "Amount"));
        System.out.println("............................................................................");
        LocalDate firstDayOfMonth=LocalDate.now().withDayOfMonth(1);
        LocalDate today=LocalDate.now();
        for (Transaction transaction : transactionsList) {
            LocalDate transactionDate=transaction.getDate();
            if ((transactionDate.isEqual(firstDayOfMonth)||transactionDate.isAfter(firstDayOfMonth))&&
                    (transactionDate.isEqual(today)||transactionDate.isBefore(today))){
                System.out.println(transaction);
            }
        }



    }
    /**
     * Display a report of all transaction from the previous month.
     * Filter transactions between the first and last day of the previous month.
     * Included a formatted header for better readability.
     */
    public static void previousMonthReport(ArrayList<Transaction>transactionsList){
        System.out.println("Here is your Previous Month Report");
        System.out.println("................................................................");
        System.out.println(String.format("%-12s%-10s%-10s  %10s  %-10s", "Date", "Time", "Description", "Vendor", "Amount"));
        System.out.println("............................................................................");
        // Get the first day of the previous month
        LocalDate firstDayOfPreviousMonth=LocalDate.now().minusMonths(1).withDayOfMonth(1);
        //Get the last day of the previous month
        LocalDate lastDayOfPreviousMonth=LocalDate.now().minusMonths(1).withDayOfMonth(LocalDate.now().minusMonths(1).lengthOfMonth());
       for (Transaction transaction:transactionsList){
           LocalDate transactionDate=transaction.getDate();
           if ((transactionDate.isEqual(firstDayOfPreviousMonth)||transactionDate.isAfter(firstDayOfPreviousMonth))&&
                   (transactionDate.isEqual(lastDayOfPreviousMonth)||transactionDate.isBefore(lastDayOfPreviousMonth))){
               System.out.println(transaction);
           }
       }

    }


}