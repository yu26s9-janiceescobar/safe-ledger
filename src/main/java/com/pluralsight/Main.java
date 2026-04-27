package com.pluralsight;
import com.pluralsight.ui.Console;
import com.pluralsight.data.DataManager;
import com.pluralsight.models.Transactions;
import com.pluralsight.ui.LedgerApp;
import java.util.ArrayList;


public class Main {
    private final ArrayList<Transactions> transactions = DataManager.loadTransactions();

    public static void main(String[] args){
        String option;
        do {
            LedgerApp.displayMainMenu();
            option = Console.promptForOptions("> ", "A","P","L","X");
            switch (option) {
                case "A":
                    addTransaction();
                    break;
                case "P":
                    // Make a payment
                    break;
                case "L":
                    // Ledger Menu
                    break;
                case "X":
                    //Exit Menu
                    break;
            }
        }
        while(!option.equals("X"));
    }

    private static void addTransaction (){
        LedgerApp.dateTimePrompt();
        String dateInput;
        int option = Console.promptForInt("> ", 1, 2);
        dateInput = Console.promptForString("Enter Date: ");
        Console.parseDate(dateInput);
        String description = Console.promptForString("Description: ");
        String vendor = Console.promptForString("Vendor: ");
        double amount = Console.promptForDouble("Amount: ");

    }

    //private static void makePayment()

    //private static void  displayLedgerMenu()

    //private static void exitApplication()
}
