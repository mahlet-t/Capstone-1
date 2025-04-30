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
        boolean keepChoosing = true;
        while (keepChoosing) {
            printTitle("Transaction Ledger Home screen");
            System.out.println("Enter from the following option");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment(Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            String choose = input.nextLine().toUpperCase();

            switch (choose) {
                case "D":
                    addDeposit(transactionList, input);
                    break;
                case "P":
                    makePayment(transactionList, input);
                    break;
                case "L":
                    ledgerMenu(transactionList, input);
                    break;
                case "X":
                    System.out.println("Good Bye!");
                    keepChoosing = false;
                    break;

                default:
                    System.out.println("Invalid Choice. Please Try again.");
            }

        }

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
        String description = Transaction.capitalize(input.nextLine());

        System.out.println("Enter the vendor name: ");
        String name = Transaction.capitalize(input.nextLine());
        double amount = 0;
        boolean validAmount = false;
        while (!validAmount) {
            System.out.println("Enter the amount to deposit:");
            try {
                amount = input.nextDouble();
                input.nextLine();
                if (amount <= 0) {
                    System.out.println("Please enter a valid amount greater than 0.");
                } else {
                    validAmount = true;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. please enter a valid number ");

            }


        }

        Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now().withNano(0), description, name, amount);
        try {
            File file = new File("transaction.csv");
            boolean fileExists = file.exists();
            FileWriter writer = new FileWriter(file, true);
            if (!fileExists) {
                writer.write("date" + "|" + "time" + "|" + "description" + "|" + "vendor" + "|" + "amount\n");

            }
            writer.write(transaction.getDate() + "|" + transaction.getTime() + "|" + transaction.getDescription() + "|" + transaction.getVendor() + "|" + transaction.getAmount() + "\n");
            writer.close();
            transactionList.add(transaction);
            loadingMessage("Processing your deposit");
            System.out.println("Your deposit was add successfully.");

        } catch (Exception e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * prompts the user to enter payment information, creates a new transaction,
     * and appends it to the transactions.csv file.
     * ........................................................................................................
     */
    public static void makePayment(ArrayList<Transaction> transactionsList, Scanner input) {
        System.out.println("Enter a description: ");
        String description = Transaction.capitalize(input.nextLine());
        System.out.println("Enter the vendor name: ");
        String name = Transaction.capitalize(input.nextLine());
        double amount = 0;
        boolean validAmount = false;
        while (!validAmount) {
            System.out.println("Enter the amount to pay:");
            try {
                amount = input.nextDouble();
                input.nextLine();
                validAmount = true;
            } catch (Exception e) {
                System.out.println("Invalid input please enter a valid number");

            }
        }
        amount = -Math.abs(amount);
        Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now().withNano(0), description, name, amount);
        try {
            File file = new File("transaction.csv");
            boolean fileExists = file.exists();
            FileWriter writer = new FileWriter(file, true);
            if (!fileExists) {
                writer.write("date" + "|" + "time" + "|" + "description" + "|" + "vendor" + "|" + "amount\n");

            }
            writer.write(transaction.getDate() + "|" + transaction.getTime() + "|" + transaction.getDescription() + "|" + transaction.getVendor() + "|" + transaction.getAmount() + "\n");
            writer.close();
            transactionsList.add(transaction);

            loadingMessage("Processing your deposit");
            System.out.println("Your payment was made Successfully ");


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
        printTitle("here is your complete transaction history: ");
        System.out.println("..............................................................................................");
        System.out.printf("%-12s %-10s %-25s  %-20s  %10s\n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("..............................................................................................");
        transactionsList.sort((t1, t2) -> {
            int dateCompare = t2.getDate().compareTo(t1.getDate());
            if (dateCompare != 0) {
                return dateCompare;
            }
            return t2.getTime().compareTo(t1.getTime());
        });
        for (Transaction transaction : transactionsList) {
            System.out.println(transaction);
        }
    }

    /**
     * Displays all deposit transactions from the transaction list,
     * including a formatted header
     */

    public static void displayDeposits(ArrayList<Transaction> transactionsList) {
        loadingMessage("Loading");
        printTitle("Here is your complete deposit transaction history:");
        System.out.println("..................................................................................");
        System.out.printf("%-12s %-10s %-25s  %-20s  %10s\n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("..................................................................................");
        transactionsList.sort((t1, t2) -> {
            int dateCompare = t2.getDate().compareTo(t1.getDate());
            if (dateCompare != 0) {
                return dateCompare;
            }
            return t2.getTime().compareTo(t1.getTime());
        });
        for (Transaction transaction : transactionsList) {
            if (transaction.getAmount() > 0) {
                System.out.println(transaction);

            }
        }

    }

    /**
     * Displays all deposit transactions from the transaction list,
     * including a formatted header.
     */


    public static void displayPayment(ArrayList<Transaction> transactionsList) {
        loadingMessage("Loading");
        printTitle("Here is your complete payment history:");
        System.out.println("..................................................................................");
        System.out.printf("%-12s %-10s %-25s  %-20s  %10s\n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("..................................................................................");
        transactionsList.sort((t1, t2) -> {
            int dateCompare = t2.getDate().compareTo(t1.getDate());
            if (dateCompare != 0) {
                return dateCompare;
            }
            return t2.getTime().compareTo(t1.getTime());
        });
        for (Transaction transaction : transactionsList) {
            if (transaction.getAmount() < 0) {
                System.out.println(transaction);


            }
        }
    }

    /**
     * Displays all transactions from the beginning of the current month until today.
     * Only transactions with dates between the first day of the month and the current date are shown.
     * Includes a formatted header for clarity.
     */
    public static void monthToDateReport(ArrayList<Transaction> transactionsList) {
        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate today = LocalDate.now();
        boolean found = false;
        for (Transaction transaction : transactionsList) {
            LocalDate transactionDate = transaction.getDate();
            if ((transactionDate.isEqual(firstDayOfMonth) || transactionDate.isAfter(firstDayOfMonth)) &&
                    (transactionDate.isEqual(today) || transactionDate.isBefore(today))) {
                if (!found) {
                    loadingMessage("Loading");
                    System.out.println("Here is your current month report");
                    System.out.println("..................................................................................");
                    System.out.printf("%-12s %-10s %-25s  %-20s  %10s\n", "Date", "Time", "Description", "Vendor", "Amount");
                    System.out.println("..................................................................................");
                    found = true;

                }
                System.out.println(transaction);
            }
        }
        if (!found) {
            System.out.println("No current month report found");
        }


    }

    /**
     * Display a report of all transaction from the previous month.
     * Filter transactions between the first and last day of the previous month.
     * Included a formatted header for better readability.
     */
    public static void previousMonthReport(ArrayList<Transaction> transactionsList) {
        // Get the first day of the previous month
        LocalDate firstDayOfPreviousMonth = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        //Get the last day of the previous month
        LocalDate lastDayOfPreviousMonth = LocalDate.now().minusMonths(1).withDayOfMonth(LocalDate.now().minusMonths(1).lengthOfMonth());
        boolean found = false;
        for (Transaction transaction : transactionsList) {
            LocalDate transactionDate = transaction.getDate();
            if ((transactionDate.isEqual(firstDayOfPreviousMonth) || transactionDate.isAfter(firstDayOfPreviousMonth)) &&
                    (transactionDate.isEqual(lastDayOfPreviousMonth) || transactionDate.isBefore(lastDayOfPreviousMonth))) {
                if (!found) {
                    loadingMessage("Loading");
                    printTitle("Here is your Previous Month Report");
                    System.out.println("..................................................................................");
                    System.out.printf("%-12s %-10s %-25s  %-20s  %10s\n", "Date", "Time", "Description", "Vendor", "Amount");
                    System.out.println("..................................................................................");
                    found = true;

                }
                System.out.println(transaction);
            }


        }
        if (!found) {
            System.out.println("No previous month report found .");
        }


    }

    /**
     * Display a report of all transaction from the beginning of the current year up to today.
     * Filter transactions between the first day of the year and the current date.
     */
    public static void yearToDateReport(ArrayList<Transaction> transactionsList) {

        LocalDate firstDayOfYear = LocalDate.now().withDayOfYear(1);
        LocalDate today = LocalDate.now();
        boolean found = false;
        for (Transaction transaction : transactionsList) {
            LocalDate transactionDate = transaction.getDate();
            if ((transactionDate.isEqual(firstDayOfYear) || transactionDate.isAfter(firstDayOfYear))
                    && (transactionDate.isEqual(today) || transactionDate.isBefore(today))) {
                if (!found) {
                    loadingMessage("Loading");
                    printTitle("Here is your current year Report");
                    System.out.println("..................................................................................");
                    System.out.printf("%-12s %-10s %-25s  %-20s  %10s\n", "Date", "Time", "Description", "Vendor", "Amount");
                    System.out.println("..................................................................................");
                    found = true;
                }
                System.out.println(transaction);

            }
        }
        if (!found) {
            System.out.println("no current year report found");

        }
    }


    /**
     * Displays all transaction from the previous calendar year.
     * Filter transactions between January 1st and december 31st of the previous year.
     */
    public static void previousYearReport(ArrayList<Transaction> transactionsList) {
        LocalDate firstDayOfPreviousYear = LocalDate.now().minusYears(1).withDayOfYear(1);
        LocalDate LastDayOfPreviousYear = LocalDate.now().minusYears(1).withDayOfYear(LocalDate.now().minusYears(1).lengthOfYear());
        boolean found = false;
        for (Transaction transaction : transactionsList) {
            LocalDate transactionDate = transaction.getDate();
            if ((transactionDate.isEqual(firstDayOfPreviousYear) || transactionDate.isAfter(firstDayOfPreviousYear))
                    && (transactionDate.isEqual(LastDayOfPreviousYear) || transactionDate.isBefore(LastDayOfPreviousYear))) {
                if (!found) {
                    loadingMessage("Loading");
                    printTitle("Here is your previous year Report");
                    System.out.println("..................................................................................");
                    System.out.printf("%-12s %-10s %-25s  %-20s  %10s\n", "Date", "Time", "Description", "Vendor", "Amount");
                    System.out.println("..................................................................................");
                    found = true;
                }
                System.out.println(transaction);

            }
        }
        if (!found) {
            System.out.println("No previous year report found");
        }

    }

    /**
     * Allows the user to search for transaction by vendor name.
     * If found, display all matching transactions with formatted header.
     * User can keep searching or type "Exit" to leave the search
     */
    public static void searchByVendor(ArrayList<Transaction> transactionsList, Scanner input) {
        while (true) {
            System.out.println("Enter the vendor");
            System.out.println("or type 'Exit' To Exit");
            String vendor = input.nextLine();
            transactionsList.sort((t1, t2) -> {
                int dateCompare = t2.getDate().compareTo(t1.getDate());
                if (dateCompare != 0) {
                    return dateCompare;
                }
                return t2.getTime().compareTo(t1.getTime());
            });
            if (vendor.equalsIgnoreCase("exit")) {
                break;
            }
            boolean found = false;
            for (Transaction transaction : transactionsList) {
                if (vendor.equalsIgnoreCase(transaction.getVendor())) {
                    if (!found) {
                        loadingMessage("Loading");
                        printTitle("Found\n");
                        System.out.println("...................................................................................");
                        System.out.printf("%-12s %-10s %-25s  %-20s  %10s\n", "Date", "Time", "Description", "Vendor", "Amount");
                        System.out.println("...................................................................................");
                        found = true;
                    }
                    System.out.println(transaction);
                }
            }
            if (!found) {
                System.out.println("No transaction found for vendor: " + vendor);
            } else {
                break;
            }

        }
    }

    public static void ledgerMenu(ArrayList<Transaction> transactionsList, Scanner input) {
        boolean keepChoosing = true;
        while (keepChoosing) {
            printTitle("Ledger Menu");
            System.out.println("Enter from the following option ");
            System.out.println("A) Display ALL");
            System.out.println("D) Display Deposits only");
            System.out.println("P) Display payment only");
            System.out.println("R) Report");
            System.out.println("H) Go back To Home Page");
            String choose = input.nextLine().toUpperCase();
            switch (choose) {
                case "A":
                    displayAll(transactionsList);
                    break;
                case "D":
                    displayDeposits(transactionsList);
                    break;
                case "P":
                    displayPayment(transactionsList);
                    break;
                case "R":
                    reports(transactionsList, input);
                    break;
                case "H":
                    keepChoosing = false;
                    break;
                default:
                    System.out.println("Invalid input try again");
            }

        }


    }

    public static void reports(ArrayList<Transaction> transactionsList, Scanner input) {
        boolean keepChoosing = true;
        while (keepChoosing) {
            printTitle("Report Menu");
            System.out.println("Enter From The following option");
            System.out.println("1) Month to Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year to Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("6) Custom Search");
            System.out.println("0) Go back to the report page");
            int choose = input.nextInt();
            input.nextLine();
            switch (choose) {
                case 1:
                    monthToDateReport(transactionsList);
                    break;
                case 2:
                    previousMonthReport(transactionsList);
                    break;
                case 3:
                    yearToDateReport(transactionsList);
                    break;
                case 4:
                    previousYearReport(transactionsList);
                    break;
                case 5:
                    searchByVendor(transactionsList, input);
                    break;
                case 6:
                    customSearch(transactionsList, input);
                    break;
                case 0:
                    keepChoosing = false;
                    break;
            }

        }
    }

    public static void customSearch(ArrayList<Transaction> transactionsList, Scanner input) {
        boolean keepSearching = true;

        while (keepSearching) {
            System.out.println("Enter Start date (yyyy-mm-dd) or press Enter to skip:");
            String startDateInput = input.nextLine();
            System.out.println("Enter End date (yyyy-mm-dd) or press Enter to skip:");
            String endDateInput = input.nextLine();
            System.out.println("Enter Description or press Enter to skip:");
            String description = input.nextLine();
            System.out.println("Enter Vendor or press Enter to skip:");
            String vendor = input.nextLine();
            System.out.println("Enter Amount or press Enter to skip:");
            String amountInput = input.nextLine();

            // Sort by newest first
            transactionsList.sort((t1, t2) -> {
                int dateCompare = t2.getDate().compareTo(t1.getDate());
                if (dateCompare != 0) return dateCompare;
                return t2.getTime().compareTo(t1.getTime());
            });

            LocalDate startDate = null;
            LocalDate endDate = null;
            Double amount = null;
            try {
                if (!startDateInput.isEmpty()) {
                    startDate = LocalDate.parse(startDateInput);
                }
                if (!endDateInput.isEmpty()) {
                    endDate = LocalDate.parse(endDateInput);
                }
                if (!amountInput.isEmpty()) {
                    amount = Double.parseDouble(amountInput);
                }
            } catch (Exception e) {
                System.out.println("Invalid date or amount format. Please try again.");
                continue; // skip this search round
            }

            boolean anyMatch = false;
            for (Transaction transaction : transactionsList) {
                boolean match = true;


                if (startDate != null && transaction.getDate().isBefore(startDate)) {
                    match = false;
                }
                if (endDate != null && transaction.getDate().isAfter(endDate)) {
                    match = false;
                }
                if (!description.isEmpty() &&
                        !transaction.getDescription().equalsIgnoreCase(description)) {
                    match = false;
                }
                if (!vendor.isEmpty() &&
                        !transaction.getVendor().equalsIgnoreCase(vendor)) {
                    match = false;
                }
                if (amount != null && transaction.getAmount() != amount) {
                    match = false;
                }

                if (match) {
                    if (!anyMatch) {
                        loadingMessage("Loading");
                        System.out.println("................................................................................");
                        System.out.printf("%-12s %-10s %-25s %-20s %10s\n", "Date", "Time", "Description", "Vendor", "Amount");
                        System.out.println("................................................................................");
                        anyMatch = true;
                    }
                    System.out.println(transaction);
                }
            }

            if (!anyMatch) {
                System.out.println("No transaction matched your search.");
            }

            System.out.println("\nDo you want to search again? (yes/no):");
            String again = input.nextLine();
            if (!again.equalsIgnoreCase("yes")) {
                keepSearching = false;
            }
        }


    }

    public static void printTitle(String title) {
        System.out.println("\n=================================================================");
        System.out.println("               " + title);
        System.out.println("==================================================================\n");
    }

   public static void loadingMessage(String message) {
//        System.out.println(message);
//        for (int i = 0; i < 3; i++) {
//        Thread.sleep(500);//wait 0.5 second
//        }
//        System.out.println(".");

    }
  }