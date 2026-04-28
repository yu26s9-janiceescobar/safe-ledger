package com.pluralsight;
import com.pluralsight.models.Transactions;
import com.pluralsight.ui.Console;
import com.pluralsight.data.DataManager;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

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
        double amount = Console.promptForCurrency("Enter Deposit Amount:\n> ");
        addTransaction(amount);
        System.out.println("You have successfully made a deposit.");
    }

    /**
     * Allows user to make a payment.
     */
    private static void addPayment(){
        System.out.println("\tPayment Screen");

        double amount = Console.promptForCurrency("Enter Payment Amount:\n> ");
        double payment = -amount; // Converts amount to negative to indicate payment.
        addTransaction(payment);
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
            dateTime = Console.parseDateTime();
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
                \t[0] Back to Ledger Menu""");

            option = Console.promptForInt("> ", 0, 5);
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
                    //Search by vendor
                    break;
            }
        }while(option != 0);
    }
    private static void displayMonthToDate(){
        transactionHeader();
        YearMonth currentYearMonth = YearMonth.now();
        for (Transactions t: transactions){
            YearMonth transactionMonth = YearMonth.from(t.getDateTime());
            if (currentYearMonth.equals(transactionMonth)){
                System.out.println(t);
            }
        }
    }
    private static void displayYearToDate(){
        transactionHeader();
        Year currentYear = Year.now();
        for (Transactions t: transactions){
            Year transactionYear = Year.from(t.getDateTime());
            if (currentYear.equals(transactionYear)) {
                System.out.println(t);
            }
        }
    }
    private static void displayPreviousMonth(){
        transactionHeader();
        YearMonth priorMonth = YearMonth.now().minusMonths(1);
        for (Transactions t: transactions){
            LocalDateTime transactionMonth = t.getDateTime().minusMonths(1);
            YearMonth transactionPriorMonth = YearMonth.from(transactionMonth);
            if (transactionPriorMonth.equals(priorMonth)){
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




