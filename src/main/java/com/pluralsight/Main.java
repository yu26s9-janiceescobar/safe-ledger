package com.pluralsight;
import com.pluralsight.models.Transactions;
import com.pluralsight.ui.Console;
import com.pluralsight.data.DataManager;
import com.pluralsight.ui.LedgerView;
import java.time.LocalDateTime;
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
                        handleLedger();
                        break;
                    case "X":
                        Console.exitApplication();
                        break;
                }
            }
            while(!option.equals("X"));
    }
    private static void handleLedger(){
        LedgerView.displayLedgerMenu();
        int option = Console.promptForInt("> ", 0, 5);
        switch(option){
            case 0:
                //Month to date
                break;
            case 1:
                //Previous Month
                break;
            case 2:
                //Year to date
                break;
            case 3;
                //
        }
    }
    private static void handleDeposit(){
        LedgerView.depositDisplay();
        double amount = Console.promptForCurrency("Enter Deposit Amount:\n> ");
        addTransaction(amount);
    }
    private static void handlePayment(){
        LedgerView.paymentDisplay();
        double amount = Console.promptForCurrency("Enter Payment Amount:\n> ");
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
        String description = Console.promptForString("Enter Description:\n> ");
        String vendor = Console.promptForString("Enter vendor:\n> ");

        DataManager.addTransaction(dateTime, description, vendor, amount);

    }


}



    //private static void  displayLedgerMenu()

    //private static void exitApplication()
