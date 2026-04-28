package com.pluralsight.ui;
import com.pluralsight.models.Transactions;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.*;
public class Console {
    private final static Scanner scanner = new Scanner(System.in);

    public static String promptForString(String prompt){
        System.out.println(prompt);
        return scanner.nextLine().strip();
    }
    public static void exitApplication(){
        System.out.println("Exiting Application...");
    }

    public static Double promptForCurrency(String prompt){
        do {
            try {
                System.out.println(prompt);
                String userInput = scanner.nextLine().strip();
                double parseDouble = Double.parseDouble(userInput);

                if (parseDouble == 0){
                    System.out.println("Dollar Amount cannot be 0.");
                }
                else{
                    return parseDouble;
                }

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
            LocalDate today = LocalDate.now();
            System.out.println(prompt);
            try {
                String userInput = scanner.nextLine().strip();
                LocalDate parseDate = LocalDate.parse(userInput);
                if (parseDate.isAfter(today)){
                    System.out.println("Error: No future dates allowed.");
                }
                else{
                    return parseDate;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Error: Enter Valid Date.");
            }
        }while(true);
    }
    public static LocalTime parseTimeInput(String prompt, LocalDate date){
        do{
            LocalDate today = LocalDate.now();
            LocalTime currentTime = LocalTime.now();
            System.out.println(prompt);
            try {
                String userInput = scanner.nextLine().strip() + ":00";
                LocalTime parseTime = LocalTime.parse(userInput);
                if (date.isEqual(today) && parseTime.isAfter(currentTime)){
                    System.out.println("Error: No Future time allowed.");
                }
                else{
                    return parseTime;
                }

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
