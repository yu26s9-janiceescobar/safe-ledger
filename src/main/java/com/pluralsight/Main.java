package com.pluralsight;
import com.pluralsight.models.Transactions;
import com.pluralsight.ui.Console;
import com.pluralsight.data.DataManager;
import com.pluralsight.ui.LedgerView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Main {
    private final static ArrayList<Transactions> transactions = DataManager.loadTransactions();

    public static void main(String[] args){
            String option;
            do {
                LedgerView.displayMainMenu();
                option = Console.promptForOptions("> ", "A","P","L","X");
                switch (option) {
                    case "A":
                        handleDeposit();
                        break;
                    case "P":
                        handlePayment();
                    case "L":
                        // Ledger Menu
                        break;
                    case "X":
                        Console.exitApplication();
                        break;
                }
            }
            while(!option.equals("X"));
    }
    private static void handleDeposit(){
        LedgerView.depositDisplay();
        double amount = Console.promptForCurrency("Enter Deposit Amount: ");
        addTransaction(amount);
    }
    private static void handlePayment(){
        double amount = Console.promptForCurrency("Enter Payment Amount: ");
        double payment = -amount;
        addTransaction(payment);
    }
    private static void addTransaction(double amount){
        LedgerView.customDateMenu();
        LocalDateTime dateTime;
        int option = Console.promptForInt("> ", 1, 2);
        if (option == 1){
            dateTime = Console.parseDateTime();
        }
        else{
           dateTime = LocalDateTime.now();
        }
        String description = Console.promptForString("Enter Description: ");
        String vendor = Console.promptForString("Enter vendor: ");

        DataManager.addTransaction(dateTime, description, vendor, amount);

    }

}

    //private static void makePayment()

    //private static void  displayLedgerMenu()

    //private static void exitApplication()
