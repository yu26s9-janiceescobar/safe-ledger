package com.pluralsight.ui;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
public class Console {
    private final static Scanner scanner = new Scanner(System.in);
    private final static LocalDate MIN_DATE = LocalDate.parse("1970-01-01");
    private final static LocalDate MAX_DATE = LocalDate.now();
    private final static double MAX_AMOUNT = 999999999.99;
    private final static double MIN_AMOUNT = 0.0;

    /**
     * Prompts user to enter a string.
     * @param prompt the message displayed to the user.
     * @return String the user entered.
     */
    public static String promptForString(String prompt){
        System.out.print(prompt);
        return scanner.nextLine().strip();
    }
    public static void exitApplication(){
        System.out.println("Exiting Application...");
    }

    /**
     * Prompts the user for a currency input.
     * @return Double the user entered.
     */

    public static boolean isValidAmount(String amount) {
        double parseDouble;
        if (amount.isBlank()){
            return true;
        }
        try {
            parseDouble = Double.parseDouble(amount);
            if (parseDouble <= MIN_AMOUNT) {
                System.out.println("Error: Amount cannot be less than 0.01");
                return false;
            }


            if (parseDouble > MAX_AMOUNT) {
                System.out.println("Error: Amount exceeds Maximum Allowed Value.");
                return false;
            }


            if (amount.contains(".")) {
                String[] decimalPlaces = amount.split("\\.");
                if (decimalPlaces[1].length() > 2) {
                    System.out.println("Error: Amount cannot have more than two decimal places.");
                    return false;
                }
            }

            return true;

        } catch (Exception e) {
            System.out.println("Invalid Input. Please Try again.");
        }
        return false;
    }
    public static double customAmount(String prompt, boolean isMinAmount){
        String amount;
        do {
            amount = Console.promptForString(prompt);
        }while(!Console.isValidAmount(amount));
        double defaultAmount = isMinAmount ? MIN_AMOUNT : MAX_AMOUNT;

        return amount.isBlank() ? defaultAmount : Double.parseDouble(amount);
    }

    /**
     * Prompts user for a menu option input.
     * @param prompt the message displayed to the user.
     * @param options the options user has to choose from.
     * @return the String the user entered.
     */
    public static String promptForOptions(String prompt, String ...options){
        while(true){
            System.out.print(prompt);
            String userInput = scanner.nextLine().strip().toUpperCase();
            for (String option : options) {
                if (userInput.equals(option)) {
                    return userInput;
                }
            }
            System.out.println("Invalid Input. Please Try Again.");
        }

    }


    public static LocalDateTime promptForDateTime(){
        while(true) {
            LocalDateTime today = LocalDateTime.now();
            System.out.print("Enter Date: ");
            String dateInput = scanner.nextLine().strip();
            LocalDate date = parseDate(dateInput);
            System.out.print("Enter Time: ");
            String timeInput = scanner.nextLine().strip();
            LocalTime time = parseTime(timeInput);
            LocalDateTime dateTime = LocalDateTime.of(date, time);
            if (dateTime.isAfter(today)) {
                System.out.println("Error: No Future time allowed. Please Try Again.");
            } else {
                return dateTime;
            }
        }
    }

    /**
     * Prompts user to enter a valid date in YYYY-MM-DD format not exceeding today's date.
     * @return LocalDate the date user enters.
     */
    public static LocalDate parseDate(String input){
        while(true){
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-M-d");
            try {
                LocalDate parseDate = LocalDate.parse(input, fmt);
                if (parseDate.isAfter(MAX_DATE) || parseDate.isBefore(MIN_DATE)){
                    System.out.println("Error: Date has to be between 1970-01-01 and today");
                    continue;
                }
                return parseDate;
            } catch (DateTimeParseException e) {
                System.out.println("Error: Enter Valid Date. Please Try Again.");
            }
        }
    }

    /**
     * Prompts the user to enter a valid time in HH:MM format.
     * @return LocalTime, the time user entered.
     */
    public static LocalTime parseTime(String input){
        while(true) {
            try {
                return LocalTime.parse(input);
            }catch (DateTimeParseException e){
                System.out.println("Invalid Input. Please Try Again.");
            }
        }
    }

    public static LocalDate customDate(String prompt, boolean isStartDate){
        String date = Console.promptForString(prompt);
        LocalDate defaultDate = isStartDate ? MIN_DATE : MAX_DATE;
        return date.isBlank() ? defaultDate : parseDate(date);
    }

    /**
     * Prompts user for an integer between a given range.
     * @param prompt the message displayed to the user.
     * @param min the minimum number user is allowed to enter.
     * @param max the maximum number user is allowed to enter.
     * @return the integer user entered.
     */
    public static int promptForInt(String prompt, int min, int max){
        int parseInt;
        while(true){
            try {
                System.out.print(prompt);
                parseInt = Integer.parseInt(scanner.nextLine().strip());
                if (parseInt >= min && parseInt <= max){
                    return parseInt;
                }
                System.out.println("Please enter an option between " + min + "-" + max);
            }catch(NumberFormatException e){
                System.out.println("Invalid input. Please Try Again!");
            }
        }
    }
}
