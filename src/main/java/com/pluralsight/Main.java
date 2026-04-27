package com.pluralsight;
import com.pluralsight.ui.Console;
import com.pluralsight.models.Transactions;
import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args){
        String option;
        Transactions t = new Transactions(" ", " ");
        LocalDate tester = t.getParseDate("2020-09-04");
        LocalTime timeTester = t.getParseTime("23:01");
        System.out.println(tester);
        System.out.println(timeTester);
        do {
            displayMainMenu();
            option = Console.promptForOptions("> ", "A","P","L","X");
            switch (option) {
                case "A":
                    //Add deposit method
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
    private static void displayMainMenu(){
        System.out.println("""
                \t\tMain Menu
                \t[A] Add Deposit
                \t[P] Make a Payment
                \t[L] Ledger
                \t[X] Exit""");
    }
    private static void displayLedgerMenu() {
        System.out.println("""
                \t\tLedger Menu
                \t[A] Display All Entries
                \t[D] Display Deposits
                \t[P] Display Payments
                \t[R] Reports""");
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
    //private static void addDeposit()

    //private static void makePayment()

    //private static void  displayLedgerMenu()

    //private static void exitApplication()
}
