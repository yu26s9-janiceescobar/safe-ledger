package com.pluralsight.ui;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
public class Console {
    private final static Scanner scanner = new Scanner(System.in);

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
     * Prompts the user to enter a dollar amount.
     * @param prompt the message displayed to the user.
     * @return double returns the amount user entered.
     */

    public static Double promptForCurrency(String prompt){
        while(true){
            try {
                System.out.print(prompt);
                String userInput = scanner.nextLine().strip();
                double parseDouble = Double.parseDouble(userInput);

                if (parseDouble <= 0){
                    System.out.println("Error: Amount cannot be less than 0.01");
                    continue;
                }
                else if(userInput.contains(".")){
                    String[] decimalPlaces = userInput.split("\\.");
                    if (decimalPlaces[1].length() > 2){
                        System.out.println("Error: Amount cannot have more than two decimal places.");
                        continue;
                    }
                }
                return parseDouble;
            } catch (Exception e) {
                System.out.println("Invalid Input. Please Try again.");
            }
        }
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
            System.out.println("Invalid Input. Try Again.");
        }

    }


    public static LocalDateTime parseDateTime(){
        while(true) {
            LocalDateTime today = LocalDateTime.now();
            LocalDate date = promptForDate("Enter Date: ");
            LocalTime time = promptForTime("Enter Time: ");
            LocalDateTime dateTime = LocalDateTime.of(date, time);
            if (dateTime.isAfter(today)) {
                System.out.println("Error: No Future time allowed.");
            } else {
                return dateTime;
            }
        }
    }

    /**
     * Prompts user to enter a valid date in YYYY-MM-DD format not exceeding today's date.
     * @param prompt the message displayed to user.
     * @return LocalDate the date user enters.
     */
    public static LocalDate promptForDate(String prompt){
        while(true){
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-M-d");
            LocalDate today = LocalDate.now();
            System.out.print(prompt);
            try {
                String userInput = scanner.nextLine().strip();
                LocalDate parseDate = LocalDate.parse(userInput, fmt);
                if (parseDate.isAfter(today)){
                    System.out.println("Error: No future dates allowed.");
                    continue;
                }
                return parseDate;

            } catch (DateTimeParseException e) {
                System.out.println("Error: Enter Valid Date.");
            }
        }
    }

    /**
     * Prompts the user to enter a valid time in HH:MM format.
     * @param prompt the message displayed to user.
     * @return LocalTime, the time user entered.
     */
    public static LocalTime promptForTime(String prompt){
    while(true) {
            System.out.print(prompt);
            try {
                String userInput = scanner.nextLine().strip();
                return LocalTime.parse(userInput);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid Input. Try Again.");
            }
        }

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
            }catch(Exception e){
                System.out.println("Invalid input. Please Try Again!");
            }
            System.out.println("Please enter an option between " + min + "-" + max);
        }
    }
}
