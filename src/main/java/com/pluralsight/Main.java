package com.pluralsight;
import com.pluralsight.models.Transactions;
import com.pluralsight.ui.Console;
import com.pluralsight.data.DataManager;
import com.pluralsight.ui.LedgerView;
import java.time.LocalDate;
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
                        addTransaction();
                        LedgerView.displayAllTransactions(transactions);
                        break;
                    case "P":
                        // Make a payment
                        break;
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
    private static void addTransaction(){
        LedgerView.customDateMenu();
        LocalDate date;
        LocalTime time;
        int option = Console.promptForInt("> ", 1, 2);
        if (option == 1){
            date = Console.parseDateInput("Enter Date of Transaction(YYYY-MM-DD): ");
            time = Console.parseTimeInput("Enter Time of Transaction(24:00): ", date);
        }
        else{
            date = LocalDate.now();
            time = LocalTime.now().withNano(0);
        }
        String description = Console.promptForString("Enter Description: ");
        String vendor = Console.promptForString("Enter vendor: ");
        double amount = Console.promptForCurrency("Enter amount: ");

        DataManager.addTransaction(date, time, description, vendor, amount);

    }

}

    //private static void makePayment()

    //private static void  displayLedgerMenu()

    //private static void exitApplication()
