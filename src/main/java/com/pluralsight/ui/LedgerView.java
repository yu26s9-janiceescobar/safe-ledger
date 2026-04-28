package com.pluralsight.ui;
import com.pluralsight.models.Transactions;
import java.util.ArrayList;

public class LedgerView {
    public static void displayAllTransactions(ArrayList<Transactions> transaction){
        for (Transactions t: transaction){
            System.out.println(t);
        }
    }
    public static void customDateMenu(){
        System.out.println("""
                    \t\tSelect an option:
                    \t[1] Custom Date and Time
                    \t[2] Current Date and Time""");
    }
    public static void displayMainMenu(){
        System.out.println("""
                \t\tMain Menu
                \t[A] Add Deposit
                \t[P] Make a Payment
                \t[L] Ledger
                \t[X] Exit""");
    }
    public static void displayReportsMenu(){
        System.out.println("""
                \t\tReports Menu
                \t[1] Month to Date
                \t[2] Previous Month
                \t[3] Year to Date
                \t[4] Previous Year
                \t[5] Search by Vendor
                \t[0] Back to Ledger Menu""");
    }
    public static void displayLedgerMenu() {
        System.out.println("""
                \t\tLedger Menu
                \t[A] Display All Entries
                \t[D] Display Deposits
                \t[P] Display Payments
                \t[R] Reports""");
    }
}
