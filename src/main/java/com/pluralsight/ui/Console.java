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
        System.out.println(prompt);
        String userInput = scanner.nextLine().strip();
        return parseCurrency(userInput);
    }
    public static Double parseCurrency(String input){
        while(true){
            try {
                double parseDouble = Double.parseDouble(input);

                if (parseDouble <= 0){
                    System.out.println("Error: Amount cannot be less than 0.01");
                }
                if (parseDouble > 999999999.99){
                    System.out.println("Error: Amount exceeds Maximum Allowed Value.");
                }
                else if(input.contains(".")){
                    String[] decimalPlaces = input.split("\\.");
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
            System.out.println("Invalid Input. Please Try Again.");
        }

    }


    public static LocalDateTime promptForDateTime(){
        while(true) {
            LocalDateTime today = LocalDateTime.now();
            System.out.print("Enter Date: ");
            String dateInput = scanner.nextLine().strip();
            LocalDate date = parseDate(dateInput);
            System.out.println("Enter Time: ");
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
            LocalDate today = LocalDate.now();
            try {
                LocalDate parseDate = LocalDate.parse(input, fmt);
                if (parseDate.isAfter(today)){
                    System.out.println("Error: No future dates allowed. Please Try Again.");
                    continue;
                }
                if (parseDate.isBefore(LocalDate.parse("1970-01-01"))){
                    System.out.println("Error: Date cannot be older than 1970-01-01");
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
            } catch (DateTimeParseException e) {
                System.out.println("Invalid Input. Please Try Again.");
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
