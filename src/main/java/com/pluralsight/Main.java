package com.pluralsight;
import com.pluralsight.models.Transactions;
import com.pluralsight.ui.Console;
import com.pluralsight.data.DataManager;
import java.time.*;
import java.util.ArrayList;

public class Main {
    private static final ArrayList<Transactions> transaction = DataManager.loadTransactions();
    private enum TransactionType {
        DEPOSIT, PAYMENT
    }
    private enum Report {
        CURRENT, PRIOR
    }
    /**
     * Entry point for program.
     * Displays Main Menu and prompts user to select an option including
     * add deposit, add payment, go to ledger menu, or exit program.
     * @param args not used in program.
     */
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
                        transactionScreen("Deposit Screen", TransactionType.DEPOSIT);
                        break;
                    case "P":
                        transactionScreen("Payment Screen", TransactionType.PAYMENT);
                        break;
                    case "L":
                        ledgerMenu();
                        break;
                    case "X":
                        System.out.println("Exiting Application...");
                        break;
                }
            }
            while(!option.equals("X"));
    }

    /**
     * Displays ledger menu and prompts user to select an option including
     * display all entries, display deposits, display payments, reports, and return to home screen.
     */
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
                    displayTransactionsMenu(transaction);
                    break;
                case "D":
                    displayByTransactionType("Deposits", TransactionType.DEPOSIT);
                    break;
                case "P":
                    displayByTransactionType("Payments", TransactionType.PAYMENT);
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
     * Displays transaction either by deposit or payment type.
     * @param header the header displayed to the user.
     * @param type the transaction type, deposit or payment.
     */
    private static void displayByTransactionType(String header, TransactionType type){
        ArrayList<Transactions> filter = new ArrayList<>();
        for (Transactions t: transaction){
            if ( type == TransactionType.DEPOSIT ? t.getAmount() > 0: t.getAmount() < 0 ){
                filter.add(t);
            }
        }
        System.out.printf("%70s %n", header);
        displayTransactionsMenu(filter);
    }

    /**
     * Displays Transaction screen and prompts the user to enter information about transaction including
     * amount, custom or current date, description, and vendor of the transaction.
     * @param prompt the message displayed to the user.
     * @param type the transaction type, deposit or payment.
     */
    private static void transactionScreen(String prompt, TransactionType type){
        System.out.println("\t\t" + prompt);
        double parseAmount = Console.promptForAmount("Enter " + (type == TransactionType.DEPOSIT ? "Deposit" : "Payment") + " Amount: ");
        double amountType = (type == TransactionType.DEPOSIT) ? parseAmount : -parseAmount;

        System.out.println("""
                    Enter an Option:
                    \t[1] Custom Date and Time
                    \t[2] Current Date and Time""");

        int option = Console.promptForInt("> ", 1, 2);
        LocalDateTime dateTime = (option == 1) ? Console.promptForDateTime() : LocalDateTime.now();

        String description = Console.promptForString("Enter Description: ");
        String vendor = Console.promptForString("Enter vendor: ");

        DataManager.addTransaction(dateTime, description, vendor, amountType);
    }

    /**
     * Displays Reports menu and prompts user to choose an option including
     * Month to date transactions, Previous month transactions, Year to date transactions,
     * search by vendor option, a custom search option, or return to ledger menu option.
     */
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
                    displayMonthReport("Month to Date Report", Report.CURRENT);
                    break;
                case 2:
                    displayMonthReport("Previous Month Report", Report.PRIOR);
                    break;
                case 3:
                    displayYearReport("Current Year Report", Report.CURRENT);
                    break;
                case 4:
                    displayYearReport("Prior Year Report", Report.PRIOR);
                    break;
                case 5:
                    searchByVendor();
                    break;
                case 6:
                    customSearchScreen();
                    break;
            }
        }while(option != 0);
    }

    /**
     * Displays a custom search screen and prompts user to enter
     * custom filters for their transactions.
     */
    private static void customSearchScreen(){
        ArrayList<Transactions> customSearch = new ArrayList<>();
        System.out.println("\t\tCustom Search Filter");
        System.out.println("Press ENTER to skip field");

        LocalDate startDateFilter = Console.promptCustomDate("Enter Start Date: ", true).minusDays(1); // true if start date filter, false if end date.
        LocalDate endDateFilter = Console.promptCustomDate("Enter End Date: ", false).plusDays(1); //Includes date the user entered.
        String descriptionFilter = Console.promptForString("Enter Description: ").toLowerCase();
        String vendorFilter = Console.promptForString("Enter Vendor: ").toLowerCase();
        double parseMinFilter = Console.customAmount("Enter Minimum Amount: ", true);
        double parseMaxFilter = Console.customAmount("Enter Maximum Amount: ", false);
        boolean isFound = false;

        for (Transactions t: transaction){

            LocalDate transactionDate = t.getDate();
            String transactionDescription = t.getDescription().toLowerCase();
            String transactionVendor = t.getVendor().toLowerCase();
            double transactionAmount = Math.abs(t.getAmount()); // For negative transaction amounts.


            if (transactionDate.isAfter(startDateFilter) &&
            transactionDate.isBefore(endDateFilter) &&
                    transactionDescription.contains(descriptionFilter) &&
            transactionVendor.contains(vendorFilter) &&
                    transactionAmount >= parseMinFilter &&
                    transactionAmount <= parseMaxFilter){

                customSearch.add(t);
                isFound = true;
            }
        }

        if (!isFound){
            System.out.println("No Matches Found.");
        }else{
            displayTransactionsMenu(customSearch);
        }
    }
    /**
     * Lets user search by vendor name.
     */
    private static void searchByVendor(){
        ArrayList<Transactions> transactions = new ArrayList<>();
        String vendor = Console.promptForString("Enter Vendor: ").toLowerCase();
        boolean isFound = false;
        for (Transactions t: transaction){
            if (t.getVendor().toLowerCase().contains(vendor)){
                transactions.add(t);
                isFound = true;
            }
        }
        if (!isFound){
            System.out.println("No Matching Vendors.");
        }
        else{
            displayTransactionsMenu(transactions);
        }
    }

    /**
     * Displays current or prior month transactions.
     * @param prompt the header title displayed to the user.
     * @param type the report type either current or prior.
     */
    private static void displayMonthReport(String prompt, Report type){
        System.out.printf("%70s %n", prompt);
        transactionHeader();
        YearMonth filter = (type == Report.CURRENT) ? YearMonth.now() : YearMonth.now().minusMonths(1);
        for (Transactions t: transaction){
            YearMonth transactionMonth = YearMonth.from(t.getDateTime());
            if (filter.equals(transactionMonth)){
                System.out.println(t);
            }
        }
    }

    /**
     * Displays Current or Prior Year transactions.
     * @param prompt the header title displayed to the user.
     * @param type type the report type either current or prior.
     */
    private static void displayYearReport(String prompt, Report type){
        System.out.printf("%70s %n", prompt);
        transactionHeader();
        Year year = (type == Report.CURRENT) ? Year.now() : Year.now().minusYears(1);
        for (Transactions t: transaction){
            Year transactionYear = Year.from(t.getDateTime());
            if (year.equals(transactionYear)){
                System.out.println(t);
            }
        }
    }

    private static int incrementPage(int currentPage){
        // page = 1, page = 2, page = 3
        int numOfPages = transaction.size() / 10;
        int lastPage = (transaction.size() % 10 == 0 ) ?  numOfPages : numOfPages + 1;
        if (currentPage < 1){
            System.out.println("You are on the first page.");
            return currentPage + 1;
        }
        if (currentPage > lastPage ){
            System.out.println("You have reached the last page.");
            return currentPage - 1;
        }

        System.out.println("Page " + currentPage);
        int itemIndex = currentPage * 10;
        if (transaction.size() <= 10){
            for (Transactions t: transaction){
                System.out.println(t);
            }

        }else{
            for (int i = itemIndex - 10; i < itemIndex; i++){
                System.out.println(transaction.get(i));
            }
        }
        return currentPage;
    }

    /**
     * Displays transactions to user.
     * @param transaction the transactions being displayed.
     */
    private static void displayTransactionsMenu(ArrayList<Transactions> transaction){
        String option;
        int pageNum = 1;
        incrementPage(pageNum);
        pageNum++;
        do{
            option = Console.promptForOptions("[P] Previous Page [N] Next Page [X] Exit","P","N","X");
            switch(option){
                case "P":
                    pageNum--;
                    pageNum = incrementPage(pageNum);
                    break;
                case "N":
                    pageNum++;
                    pageNum = incrementPage(pageNum);
                    break;
                case "X":
                    System.out.println("Returning back to Ledger Menu...");
                    break;
            }

        }while(!option.equals("X"));

    }

    private static void transactionHeader(){
        System.out.printf("%-20s %-20s %-45s %-30s %s %n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-".repeat(140));
    }
}




