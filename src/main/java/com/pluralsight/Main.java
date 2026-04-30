package com.pluralsight;
import com.pluralsight.models.Transactions;
import com.pluralsight.ui.Console;
import com.pluralsight.data.DataManager;
import java.time.*;
import java.util.ArrayList;

public class Main {
    private static final ArrayList<Transactions> transaction = DataManager.loadTransactions();
    private static final int DESCRIPTION_MAX_CHARACTER_COUNT = 35;
    private static final int VENDOR_MAX_CHARACTER_COUNT = 25;
    private enum TransactionType {
        DEPOSIT, PAYMENT
    }
    private enum Report {
        CURRENT, PRIOR
    }

    /**
     * Entry point for program.
     * Prompts user to start or quit program.
     * @param args not used in program.
     */
    public static void main(String[] args) {
        System.out.printf("%35s %n%n %22s %9s %n","Welcome to Safe Ledger", "[S] Start", "[Q] Quit");
        String option = Console.promptForOptions("> ", "S", "Q");
        switch(option){
            case "S":
                displayMainMenu();
                break;
            case "Q":
                System.out.println("Exiting Application...");
                break;
        }
    }

    /**
     * Displays Main Menu and prompts user to select an option including
     * add deposit, add payment, go to ledger menu, or exit program.
     */
    private static void displayMainMenu(){
        System.out.println("Starting Program...");
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
                    transactionScreen(TransactionType.DEPOSIT);
                    break;
                case "P":
                    transactionScreen(TransactionType.PAYMENT);
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
                    displayAllTransactions();
                    break;
                case "D":
                    displayByTransactionType(TransactionType.DEPOSIT);
                    break;
                case "P":
                    displayByTransactionType(TransactionType.PAYMENT);
                    break;
                case "R":
                    reportMenu();
                    break;
                case "H":
                    break;
            }
        }while(!option.equals("H"));
    }

    /**
     * Displays all transactions.
     */
    private static void displayAllTransactions(){
        transactionExists("ALL TRANSACTIONS", transaction);
    }
    /**
     * Displays transaction either by deposit or payment type.
     * @param type the transaction type, deposit or payment.
     */
    private static void displayByTransactionType(TransactionType type){
        ArrayList<Transactions> filter = new ArrayList<>();
        for (Transactions t: transaction){
            if ( type == TransactionType.DEPOSIT ? t.getAmount() > 0: t.getAmount() < 0 ){
                filter.add(t);
            }
        }
        String header = type == TransactionType.DEPOSIT ? "DEPOSIT REPORT" : "PAYMENT REPORT";
        transactionExists(header, filter);

    }

    /**
     * Displays Transaction screen and prompts the user to enter information about transaction including
     * amount, custom or current date, description, and vendor of the transaction.
     * @param type the transaction type, deposit or payment.
     */
    private static void transactionScreen(TransactionType type){
        String transactionType = type == TransactionType.DEPOSIT ? "Deposit" : "Payment";
        System.out.println("\t\t" + transactionType + " Screen");

        double amount = Console.promptForAmount("Enter " + transactionType + " Amount: ");
        double parseAmount = (type == TransactionType.DEPOSIT) ? amount : -amount;

        System.out.println("""
                    \t\tEnter an Option:
                    \t[1] Custom Date and Time
                    \t[2] Current Date and Time""");

        int option = Console.promptForInt("> ", 1, 2);
        LocalDateTime dateTime = (option == 1) ? Console.promptForDateTime() : LocalDateTime.now();

        String description = Console.promptForStringWithCharacterLimit("Enter Description: ", DESCRIPTION_MAX_CHARACTER_COUNT);
        String formatDescription = Console.capitalizeFirstLetter(description);
        String vendor = Console.promptForStringWithCharacterLimit("Enter vendor: ", VENDOR_MAX_CHARACTER_COUNT);
        String formatVendor = Console.capitalizeFirstOfEveryWord(vendor);


        Transactions temp = new Transactions(dateTime, formatDescription, formatVendor, parseAmount);
        String options;
        do {
            System.out.println("[Y] To Confirm " + transactionType + " [E] Edit [X] To Cancel");
            options = Console.promptForOptions("> ", "Y", "E", "X");

            switch (options) {
                case "Y":
                    break;
                case "E":
                    temp = editTransaction(temp, type);
                    break;
                case "X":
                    break;
            }
        }while(options.equals("E"));
        DataManager.addTransaction(temp.getDateTime(), temp.getDescription(), temp.getVendor(), temp.getAmount());

    }
    private static Transactions editTransaction(Transactions temp, TransactionType type){
        int option;
        do {
            System.out.println("""
                    What Would You Like to Edit?
                    [1] Date
                    [2] Time
                    [3] Description
                    [4] Vendor
                    [5] Amount
                    [0] Done""");

            option = Console.promptForInt("> ", 0, 5);
            switch (option) {
                case 1:
                    while (true) {
                        LocalDate date = Console.promptForDate("Enter New Date: ");
                        LocalDateTime newDateTime = LocalDateTime.of(date, temp.getTime());

                        if (newDateTime.isAfter(LocalDateTime.now())) {
                            System.out.println("Error: Date cannot be in the future.");
                        } else {
                            temp.setDateTime(newDateTime);
                            break;
                        }
                    }
                    break;
                case 2:
                    while (true) {
                        LocalTime time = Console.promptForTime("Enter New Time: ");
                        LocalDateTime newDateTime = LocalDateTime.of(temp.getDate(), time);

                        if (newDateTime.isAfter(LocalDateTime.now())) {
                            System.out.println("Error: Time cannot be in the future.");
                        } else {
                            temp.setDateTime(newDateTime);
                            break;
                        }
                    }
                    break;
                case 3:
                    String description = Console.promptForStringWithCharacterLimit("Enter New Description: ", DESCRIPTION_MAX_CHARACTER_COUNT);
                    String formatDescription = Console.capitalizeFirstLetter(description);
                    temp.setDescription(formatDescription);
                    break;
                case 4:
                    String vendor = Console.promptForStringWithCharacterLimit("Enter New Vendor: ", VENDOR_MAX_CHARACTER_COUNT);
                    String formatVendor = Console.capitalizeFirstOfEveryWord(vendor);
                    temp.setVendor(formatVendor);
                    break;
                case 5:
                    double amount = Console.promptForAmount("Enter New Amount: ");
                    double parseAmount = (type == TransactionType.DEPOSIT) ? amount : -amount;
                    temp.setAmount(parseAmount);
                    break;
            }
        }while (option != 0);
        return temp;

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
                    break;
                case 1:
                    displayMonthReport(Report.CURRENT);
                    break;
                case 2:
                    displayMonthReport(Report.PRIOR);
                    break;
                case 3:
                    displayYearReport(Report.CURRENT);
                    break;
                case 4:
                    displayYearReport(Report.PRIOR);
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
        System.out.println("Leave Blank to Skip Field.");

        LocalDate startDateFilter = Console.promptCustomDate("Enter Start Date: ", true).minusDays(1); // true if start date filter, false if end date.
        LocalDate endDateFilter = Console.promptCustomDate("Enter End Date: ", false).plusDays(1); //Includes date the user entered.
        String descriptionFilter = Console.promptForStringWithCharacterLimit("Enter Description: ", DESCRIPTION_MAX_CHARACTER_COUNT).toLowerCase();
        String vendorFilter = Console.promptForStringWithCharacterLimit("Enter Vendor: ", VENDOR_MAX_CHARACTER_COUNT).toLowerCase();
        double parseMinFilter = Console.promptForCustomAmount("Enter Minimum Amount: ", true);
        double parseMaxFilter = Console.promptForCustomAmount("Enter Maximum Amount: ", false);


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

            }
        }

        transactionExists("CUSTOM SEARCH APPLIED", customSearch);
    }
    /**
     * Lets user search by vendor name.
     */
    private static void searchByVendor(){
        ArrayList<Transactions> filter = new ArrayList<>();
        String vendor = Console.promptForString("Enter Vendor: ").toLowerCase();

        for (Transactions t: transaction){
            if (t.getVendor().toLowerCase().contains(vendor)){
                filter.add(t);

            }
        }
        transactionExists("VENDOR: " + vendor.toUpperCase(), filter);
    }

    /**
     * Displays current or prior month transactions.
     * @param type the report type either current or prior.
     */
    private static void displayMonthReport(Report type){
        ArrayList<Transactions> filter = new ArrayList<>();
        String header = type == Report.CURRENT ? "CURRENT MONTH REPORT" : "PRIOR MONTH REPORT";
        YearMonth yearMonth = (type == Report.CURRENT) ? YearMonth.now() : YearMonth.now().minusMonths(1);
        for (Transactions t: transaction){
            YearMonth transactionMonth = YearMonth.from(t.getDateTime());
            if (yearMonth.equals(transactionMonth)){
                filter.add(t);
            }
        }
        transactionExists(header, filter);
    }

    /**
     * Displays Current or Prior Year transactions.
     * @param type type the report type either current or prior.
     */
    private static void displayYearReport(Report type){
        ArrayList<Transactions> filter = new ArrayList<>();
        String header = type == Report.CURRENT ? "CURRENT YEAR REPORT" : "PRIOR YEAR REPORT";
        Year year = (type == Report.CURRENT) ? Year.now() : Year.now().minusYears(1);
        for (Transactions t: transaction){
            Year transactionYear = Year.from(t.getDateTime());
            if (year.equals(transactionYear)){
                filter.add(t);
            }
        }
        transactionExists(header, filter);
    }

    /**
     * Displays transactions if transactions are found.
     * @param header the title of the page.
     * @param transaction the list of transactions.
     */
    private static void transactionExists(String header, ArrayList<Transactions> transaction){
        if (transaction.isEmpty()) {
            System.out.print("No Transactions Found.");
        } else {
            displayTransactionsMenu(header, transaction);
        }
    }

    /**
     * Displays transaction page with a maximum of 10 transactions.
     * @param currentPage the current page number.
     * @param header the title of the page.
     * @param transaction the list of transactions.
     * @return int the page number.
     */
    private static int displayPage(int currentPage, String header, ArrayList<Transactions> transaction){
        int previousTransactionsDisplayed = (currentPage * 10) - 10;
        int lastPage = (transaction.size() % 10 == 0 ) ?  transaction.size() / 10 : transaction.size() / 10 + 1;
        int transactionsLeft = transaction.size() - previousTransactionsDisplayed;
        if (currentPage < 1){
            System.out.println("You are on the first page.");
            return currentPage + 1;
        }
        else if (currentPage > lastPage ){
            System.out.println("You have reached the last page.");
            return currentPage - 1;
        }

        System.out.printf("%75s %d %n","PAGE", currentPage);
        System.out.printf("%81s %n%-20s %-20s %-45s %-35s %s %n", header, "DATE", "TIME", "DESCRIPTION", "VENDOR", "AMOUNT");

        System.out.println("=".repeat(145));
        for (int i = previousTransactionsDisplayed; i < previousTransactionsDisplayed + 10 ; i++){
            System.out.println(transaction.get(i));
            transactionsLeft--;
            if (transactionsLeft == 0){
                return lastPage;
            }
        }
        return currentPage;
    }

    /**
     * Displays transactions to user.
     * @param transaction the transactions being displayed.
     */
    private static void displayTransactionsMenu(String header, ArrayList<Transactions> transaction){
        String option;
        int pageNum = 1;

        displayPage(pageNum, header, transaction);
        do{
            System.out.print("=".repeat(51));
            System.out.print(" [P] PREVIOUS PAGE [N] NEXT PAGE [X] EXIT ");
            System.out.println("=".repeat(53));
            option = Console.promptForOptions("> ","P","N","X");
            switch(option){
                case "P":
                    pageNum--;
                    pageNum = displayPage(pageNum, header, transaction);
                    break;
                case "N":
                    pageNum++;
                    pageNum = displayPage(pageNum, header, transaction);
                    break;
                case "X":
                    break;
            }

        }while(!option.equals("X"));

    }


}




