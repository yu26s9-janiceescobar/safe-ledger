package com.pluralsight;
import com.pluralsight.ui.Console;
public class Main {
    public static void main(String[] args){
        displayMenu();
        String option = Console.promptForOptions("> ", "A","P","L","X");
        System.out.println(option);
    }
    public static void displayMenu(){
        System.out.println("""
                \t\tMain Menu
                \t[A] Add Deposit
                \t[P] Make a Payment
                \t[L] Ledger
                \t[X] Exit""");
    }
}
