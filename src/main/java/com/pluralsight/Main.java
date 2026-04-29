package com.pluralsight;
import com.pluralsight.models.Transactions;
import com.pluralsight.ui.Console;
import com.pluralsight.data.DataManager;
import java.time.*;
import java.util.ArrayList;

public class Main {
    private static final ArrayList<Transactions> transactions = DataManager.loadTransactions();

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
                        addDeposit();
                        break;
                    case "P":
                        addPayment();
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
                    displayTransactions(transactions);
                    break;
                case "D":
                    displayDeposits();
                    break;
                case "P":
                    displayPayments();
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
     * Displays all deposits to user.
     */
    private static void displayDeposits(){
        ArrayList<Transactions> deposits = new ArrayList<>();
        for (Transactions t: transactions){
            if (t.getAmount() > 0){
                deposits.add(t);
            }
        }
        System.out.printf("%70s %n", "Deposits");
        displayTransactions(deposits);
    }

    /**
     * Displays all payments to user.
     */
    private static void displayPayments(){
        ArrayList<Transactions> payments = new ArrayList<>();
        for (Transactions t: transactions){
            if (t.getAmount() < 0){
                payments.add(t);
            }
        }
        System.out.printf("%70s %n", "Payments");
        displayTransactions(payments);
    }

    /**
     * Allows user to make a deposit.
     */
    private static void addDeposit(){
        System.out.println("\tDeposit Screen");
        String amount;
        do{
            amount = Console.promptForString("Enter Deposit Amount: ");
        }while(!Console.isValidCurrency(amount) && !amount.isBlank());

        addTransaction(Double.parseDouble(amount));

        System.out.println("You have successfully made a deposit.");
    }

    /**
     * Allows user to make a payment.
     */
    private static void addPayment(){
        System.out.println("\tPayment Screen");
        String amount;
        do {
            amount = Console.promptForString("Enter Payment Amount: ");
        }while(!Console.isValidCurrency(amount) && !amount.isBlank());

        double parseAmount = -Double.parseDouble(amount); // Converts amount to negative to indicate payment.
        addTransaction(parseAmount);
        System.out.println("You have successfully made a payment.");
    }

    /**
     * Allows user to make a transaction.
     * @param amount the deposit or payment amount.
     */
    private static void addTransaction(double amount){
        System.out.println("""
                    Enter an Option:
                    \t[1] Custom Date and Time
                    \t[2] Current Date and Time""");
        LocalDateTime dateTime;
        int option = Console.promptForInt("> ", 1, 2);
        if (option == 1){
            dateTime = Console.promptForDateTime();
        }
        else{
           dateTime = LocalDateTime.now();
        }
        String description = Console.promptForString("Enter Description:\n> ");
        String vendor = Console.promptForString("Enter vendor:\n> ");

        DataManager.addTransaction(dateTime, description, vendor, amount);

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
                    customSearch();
                    break;
            }
        }while(option != 0);
    }
    private static void customSearch(){
        System.out.println("\t\tCustom Search Filter");
        System.out.println("Press ENTER to skip field");
        LocalDate startDate;
        LocalDate endDate;

        String date = Console.promptForString("Enter Start Date: ");
        if (!date.isBlank()) {
            startDate = Console.parseDate(date);
        }
        else{
            startDate = LocalDate.parse("1970-01-01");
        }

        String date2 = Console.promptForString("Enter End Date: ");
        if (!date2.isBlank()) {
            endDate = Console.parseDate(date2);
        }
        else{
            endDate = LocalDate.now();
        }

        String description = Console.promptForString("Enter Description: ").toLowerCase();
        String vendor = Console.promptForString("Enter Vendor: ");

        String min;
        double parseMin;
        do {
            min = Console.promptForString("Enter Minimum Amount: ");
        }
        while(!Console.isValidCurrency(min));

        if (min.isBlank()){
            parseMin = 0;
        }
        else{
            parseMin = Double.parseDouble(min);
        }


        String max;
        double parseMax;

        do {
            max = Console.promptForString("Enter Maximum Amount: ");
        }while(!Console.isValidCurrency(max));

        
        if (max.isBlank()){
            parseMax = 999999999.99;
        }
        else{
            parseMax = Double.parseDouble(max);
        }

        boolean isFound = false;

        for (Transactions t: transactions){
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
                System.out.println(t);
                isFound = true;
            }
        }

        if (!isFound){
            System.out.println("No Matches Found.");
        }



    }
    /**
     * Lets user search by vendor name.
     */
    private static void searchByVendor(){
        System.out.println("\t\tSearch By Vendor:");
        String vendor = Console.promptForString("Enter Vendor\n> ").toLowerCase();
        boolean isFound = false;
        for (Transactions t: transactions){
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
        for (Transactions t: transactions){
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
        for (Transactions t: transactions){
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
        for (Transactions t: transactions){
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
        for (Transactions t: transactions){
            YearMonth transactionMonth = YearMonth.from(t.getDateTime());
            if (transactionMonth.equals(priorMonth)){
                System.out.println(t);
            }
        }
    }

    private static void displayTransactions(ArrayList<Transactions> transactions){
        transactionHeader();
        for (Transactions t: transactions){
            System.out.println(t);
        }
    }
    private static void transactionHeader(){
        System.out.printf("%-20s %-20s %-45s %-30s %s %n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-".repeat(140));
    }
}




