package com.pluralsight.ui;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;
public class Console {
    private final static Scanner scanner = new Scanner(System.in);

    public static String promptForString(String prompt){
        System.out.println(prompt);
        return scanner.nextLine().strip();
    }

    public static Double promptForDouble(String prompt){
        do {
            try {
                System.out.println(prompt);
                String userInput = scanner.nextLine().strip();
                return Double.parseDouble(userInput);
            } catch (Exception e) {
                System.out.println("Invalid Input. Please Try again.");
            }
        }while(true);
    }

    /**
     * Prompts user for a menu option input.
     * @param prompt the message displayed to the user.
     * @param options the options user has to choose from.
     * @return the String the user entered.
     */
    public static String promptForOptions(String prompt, String ...options){
        do {
            System.out.print(prompt);
            String userInput = scanner.nextLine().toUpperCase();
            for (String option : options) {
                if (userInput.equalsIgnoreCase(option)) {
                    return userInput;
                }
            }
            System.out.println("Invalid Input. Try Again.");
        }
        while(true);
    }

    public static LocalDate parseDateInput(String prompt){
        do{
            System.out.println(prompt);
            try {
                String userInput = scanner.nextLine().strip();
                return LocalDate.parse(userInput);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid Input. Try Again.");
            }
        }while(true);
    }
    public static LocalTime parseTimeInput(String prompt){
        do{
            System.out.println(prompt);
            try {
                String userInput = scanner.nextLine().strip() + ":00";
                return LocalTime.parse(userInput);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid Input. Try Again.");
            }
        }while(true);

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
        do{
            try {
                System.out.print(prompt);
                parseInt = Integer.parseInt(scanner.nextLine());
                if (parseInt >= min && parseInt <= max){
                    return parseInt;
                }

            }catch(Exception e){
                System.out.println("Invalid input. Please Try Again!");
            }
            System.out.println("Please enter an option between " + min + "-" + max);
        }while(true);
    }
}
