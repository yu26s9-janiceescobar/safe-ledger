package com.pluralsight;
import com.pluralsight.models.Transactions;
import com.pluralsight.ui.Console;
import com.pluralsight.data.DataManager;
import java.time.*;
import java.util.ArrayList;

public class Main {
    private static final ArrayList<Transactions> transaction = DataManager.loadTransactions();

    public static void main(String[] args){
            String option;
            do {
                System.out.println("""
                \t\tMain Menu
                \t[A] Add Deposit
                \t[P] Make a Payment
                \t[L] Ledger
                \t[X] Exit""");

                option = Console.promptForOptions("> ", "A","P","L","X");
                switch (option) {
                    case "A":
                        transactionScreen("Deposit Screen", true);
                        break;
                    case "P":
                        transactionScreen("Payment Screen", false);
                        break;
                    case "L":
                        ledgerMenu();
                        break;
                    case "X":
                        Console.exitApplication();
                        break;
                }
            }
            while(!option.equals("X"));
    }

    private static void ledgerMenu() {
        String option;
        do {
            System.out.println("""
                \t\tLedger Menu
                \t[A] Display All Entries
                \t[D] Display Deposits
                \t[P] Display Payments
                \t[R] Reports
                \t[H] Home Screen""");
            option = Console.promptForOptions("> ", "A", "D", "P", "R", "H");
            switch (option) {
                case "A":
                    displayTransactions(transaction);
                    break;
                case "D":
                    displayByTransactionType("Deposits", true);
                    break;
                case "P":
                    displayByTransactionType("Payments", false);
                    break;
                case "R":
                    reportMenu();
                    break;
                case "H":
                    System.out.println("Loading Home Screen...");
                    break;
            }
        }while(!option.equals("H"));
    }

    /**
     * Displays transaction either by deposit or payment type.
     * @param header the header displayed to the user.
     * @param isDeposit true if the transactions are deposits, false if they are payments.
     */
    private static void displayByTransactionType(String header, boolean isDeposit){
        ArrayList<Transactions> filter = new ArrayList<>();
        for (Transactions t: transaction){
            if (isDeposit ? t.getAmount() > 0: t.getAmount() < 0 ){
                filter.add(t);
            }
        }
        System.out.printf("%70s %n", header);
        displayTransactions(filter);
    }

    private static void transactionScreen(String prompt, boolean isDeposit){
        System.out.println("\t\t" + prompt);
        String amount;

        do {
            amount = Console.promptForString("Enter "  + (isDeposit ? "Deposit" : "Payment") + " Amount: ");
        }while(!Console.isValidAmount(amount) || amount.isBlank());

        double parseAmount = Double.parseDouble(amount);
        double amountType = isDeposit ? parseAmount : -parseAmount;

        System.out.println("""
                    Enter an Option:
                    \t[1] Custom Date and Time
                    \t[2] Current Date and Time""");

        int option = Console.promptForInt("> ", 1, 2);
        LocalDateTime dateTime = (option == 1) ? Console.promptForDateTime() : LocalDateTime.now();

        String description = Console.promptForString("Enter Description: ");
        String vendor = Console.promptForString("Enter vendor: ");

        DataManager.addTransaction(dateTime, description, vendor, amountType);
    }


    private static void reportMenu(){
        int option;
        do{
            System.out.println("""
                \t\tReports Menu
                \t[1] Month to Date
                \t[2] Previous Month
                \t[3] Year to Date
                \t[4] Previous Year
                \t[5] Search by Vendor
                \t[6] Custom Search
                \t[0] Back to Ledger Menu""");

            option = Console.promptForInt("> ", 0, 6);
            switch(option){
                case 0:
                    System.out.println("Loading Ledger Menu...");
                    break;
                case 1:
                    displayMonthToDate();
                    break;
                case 2:
                    displayPreviousMonth();
                    break;
                case 3:
                    displayYearToDate();
                    break;
                case 4:
                    displayPriorYear();
                    break;
                case 5:
                    searchByVendor();
                    break;
                case 6:
                    customSearchScreen();
                    break;
            }
        }while(option != 0);
    }


    private static void customSearchScreen(){
        ArrayList<Transactions> customSearch = new ArrayList<>();
        System.out.println("\t\tCustom Search Filter");
        System.out.println("Press ENTER to skip field");

        LocalDate startDate = Console.customDate("Enter Start Date: ", true);
        LocalDate endDate = Console.customDate("Enter End Date: ", false);
        String description = Console.promptForString("Enter Description: ").toLowerCase();
        String vendor = Console.promptForString("Enter Vendor: ").toLowerCase();
        double parseMin = Console.customAmount("Enter Minimum Amount: ", true);
        double parseMax = Console.customAmount("Enter Maximum Amount: ", false);
        boolean isFound = false;

        for (Transactions t: transaction){

            LocalDate transactionDate = t.getDate();
            String transactionDescription = t.getDescription().toLowerCase();
            String transactionVendor = t.getVendor().toLowerCase();
            double transactionAmount = Math.abs(t.getAmount()); // For negative transaction amounts.


            if (transactionDate.isAfter(startDate) &&
            transactionDate.isBefore(endDate) &&
                    transactionDescription.contains(description) &&
            transactionVendor.contains(vendor) &&
                    transactionAmount >= parseMin &&
                    transactionAmount <= parseMax){

                customSearch.add(t);
                isFound = true;
            }
        }
        displayTransactions(customSearch);
        if (!isFound){
            System.out.println("No Matches Found.");
        }
    }
    /**
     * Lets user search by vendor name.
     */
    private static void searchByVendor(){
        String vendor = Console.promptForString("Enter Vendor: ").toLowerCase();
        boolean isFound = false;
        for (Transactions t: transaction){
            if (t.getVendor().toLowerCase().contains(vendor)){
                System.out.println(t);
                isFound = true;
            }
        }
        if (!isFound){
            System.out.println("No Matching Vendors.");
        }
    }
    /**
     * Displays Month to date transactions to user.
     */
    private static void displayMonthToDate(){
        System.out.printf("%70s", "Month to Date Report");
        transactionHeader();
        YearMonth currentYearMonth = YearMonth.now();
        for (Transactions t: transaction){
            YearMonth transactionMonth = YearMonth.from(t.getDateTime());
            if (currentYearMonth.equals(transactionMonth)){
                System.out.println(t);
            }
        }
    }

    /**
     * Displays Year to date transactions to user.
     */
    private static void displayYearToDate(){
        System.out.printf("%70s %n", "Year to Date Report");
        transactionHeader();
        Year currentYear = Year.now();
        for (Transactions t: transaction){
            Year transactionYear = Year.from(t.getDateTime());
            if (currentYear.equals(transactionYear)) {
                System.out.println(t);
            }
        }
    }
    private static void displayPriorYear(){
        System.out.printf("%70s %n", "Last Year Report");
        transactionHeader();
        Year priorYear = Year.now().minusYears(1);
        for (Transactions t: transaction){
            Year transactionYear = Year.from(t.getDateTime()); // Returns yyyy
            if (priorYear.equals(transactionYear)){
                System.out.println(t);
            }
        }
    }

    /**
     * Displays previous month transactions to user.
     */
    private static void displayPreviousMonth(){
        System.out.printf("%70s", "Previous Month Report");
        transactionHeader();
        YearMonth priorMonth = YearMonth.now().minusMonths(1);
        for (Transactions t: transaction){
            YearMonth transactionMonth = YearMonth.from(t.getDateTime());
            if (transactionMonth.equals(priorMonth)){
                System.out.println(t);
            }
        }
    }

    private static void displayTransactions(ArrayList<Transactions> transaction){
        transactionHeader();
        for (Transactions t: transaction){
            System.out.println(t);
        }
    }
    private static void transactionHeader(){
        System.out.printf("%-20s %-20s %-45s %-30s %s %n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-".repeat(140));
    }
}




