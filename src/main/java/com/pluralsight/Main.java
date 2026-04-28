package com.pluralsight;
import com.pluralsight.models.Transactions;
import com.pluralsight.ui.Console;
import com.pluralsight.data.DataManager;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {
    private final static ArrayList<Transactions> transactions = DataManager.loadTransactions();

    public static void main(String[] args){
            String option;
            do {
                displayMainMenu();
                option = Console.promptForOptions("> ", "A","P","L","X");
                switch (option) {
                    case "A":
                        handleDeposit();
                        break;
                    case "P":
                        handlePayment();
                        break;
                    case "L":
                        handleLedger();
                        break;
                    case "X":
                        Console.exitApplication();
                        break;
                }
            }
            while(!option.equals("X"));
    }
    private static void handleLedger() {
        String option;
        do {
            displayLedgerMenu();
            option = Console.promptForOptions("> ", "A", "D", "P", "R", "H");
            switch (option) {
                case "A":
                    displayTransactions(transactions);
                    break;
                case "D":
                    handleDepositDisplay();
                    break;
                case "P":
                    handlePaymentDisplay();
                    break;
                case "R":
                    handleReports();
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
    private static void handleDepositDisplay(){
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
    private static void handlePaymentDisplay(){
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
    private static void handleDeposit(){
        System.out.println("\tDeposit Screen");

        double amount = Console.promptForCurrency("Enter Deposit Amount:\n> ");
        addTransaction(amount);
        System.out.println("You have successfully made a deposit.");
    }

    /**
     * Allows user to make a payment.
     */
    private static void handlePayment(){
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
        customDateMenu();
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

    private static void handleReports(){
        int option;
        do {
            displayReportsMenu();
            option = Console.promptForInt("> ", 0, 5);
            switch(option){
                case 0:
                    System.out.println("Loading Ledger Menu...");
                    break;
                case 1:
                    //Month to date
                    break;
                case 3:
                    //Previous Month
                    break;
                case 4:
                    //Year to date
                    break;
                case 5:
                    //Search by vendor
                    break;
            }
        }while(option != 0);
    }
    private static void displayTransactions(ArrayList<Transactions> transactions){
        System.out.printf("%-20s %-20s %-45s %-30s %s %n", "Date", "Time", "Description", "Vendor", "Amount");
        for (Transactions t: transactions){
            System.out.println(t);
        }
    }
    private static void customDateMenu(){
        System.out.println("""
                    Enter an Option:
                    \t[1] Custom Date and Time
                    \t[2] Current Date and Time""");
    }
    private static void displayMainMenu(){
        System.out.println("""
                \t\tMain Menu
                \t[A] Add Deposit
                \t[P] Make a Payment
                \t[L] Ledger
                \t[X] Exit""");
    }
    private static void displayReportsMenu(){
        System.out.println("""
                \t\tReports Menu
                \t[1] Month to Date
                \t[2] Previous Month
                \t[3] Year to Date
                \t[4] Previous Year
                \t[5] Search by Vendor
                \t[0] Back to Ledger Menu""");
    }
    private static void displayLedgerMenu() {
        System.out.println("""
                \t\tLedger Menu
                \t[A] Display All Entries
                \t[D] Display Deposits
                \t[P] Display Payments
                \t[R] Reports""");
    }
}




