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
    private final static double MAX_AMOUNT = 999_999_999.99;
    private final static double MIN_AMOUNT = 0.01;

    /**
     * Prompts user to enter a string.
     * @param prompt the message displayed to the user.
     * @return String the user entered.
     */
    public static String promptForString(String prompt){
        System.out.print(prompt);
        String userInput = scanner.nextLine().strip();
        return userInput.replaceAll("\\s+"," ");
    }

    /**
     * Prompts user to enter an input with a minimum and maximum character limit.
     * @param prompt the message displayed to the user.
     * @param minCharacterCount the minimum characters allowed to be in input.
     * @param maxCharacterCount the maximum characters allowed to be in input.
     * @return String the user entered.
     */
    public static String promptForStringWithCharacterLimit(String prompt, int minCharacterCount, int maxCharacterCount){
        while(true){
            String input = promptForString(prompt);
            try{
                return characterCountLimit(input, minCharacterCount, maxCharacterCount);
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Checks if user input is between minimum and maximum character count.
     * @param input the string the user entered.
     * @param minCharacterCount the minimum characters allowed to be in input.
     * @param maxCharacterCount the maximum characters allowed to be in input.
     * @return String the user entered.
     */
    public static String characterCountLimit(String input, int minCharacterCount, int maxCharacterCount){
        if (input.length() < minCharacterCount){
            throw new IllegalArgumentException("Error: Character Count Cannot be less than " + minCharacterCount);
        }
        if (input.length() > maxCharacterCount){
            throw new IllegalArgumentException("Error: Character Count Cannot Exceed " + maxCharacterCount);
        }
        return input;
    }

    /**
     * Capitalizes the first letter of the first word of a string.
     * @param input the string entered.
     * @return String the string with the first letter of the word capitalized.
     */
    public static String capitalizeFirstLetter(String input){
        if (input.isEmpty()){
            throw new IllegalArgumentException("Error: Input cannot be empty.");
        }
        return input.substring(0,1).toUpperCase() + input.substring(1);
    }

    /**
     * Capitalizes every first letter of a word in a string.
     * @param input the string entered.
     * @return String the string with the first letter of every word capitalized.
     */
    public static String capitalizeFirstOfEveryWord(String input){
        String[] words = input.split(" ");
        StringBuilder formatSentence = new StringBuilder();
        for (String w: words){
            String newWord = capitalizeFirstLetter(w) + " ";
            formatSentence.append(newWord);
        }
        return formatSentence.toString().strip();
    }
    /**
     * Prompts the user for currency amount and returns parsed double.
     * @param prompt the message displayed to the user.
     * @return double the amount the user entered.
     */
    public static double promptForAmount(String prompt) {
        while(true){
            String userInput = promptForString(prompt);
            try {
                return parseAmount(userInput);
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Validates and parses user input to a valid currency amount.
     * @param amountInput the number the user entered.
     * @return double the parsed currency amount.
     */

    public static double parseAmount(String amountInput) {
            try {
                double parseAmount = Double.parseDouble(amountInput);

                if (parseAmount < MIN_AMOUNT || parseAmount > MAX_AMOUNT ){
                    throw new IllegalArgumentException(String.format("Error: Amount must be between $%.2f and $%.2f", MIN_AMOUNT, MAX_AMOUNT));
                }
                if (amountInput.contains(".")){
                    String[] decimalPlaces = amountInput.split("\\.");
                    if (decimalPlaces.length > 1) {
                        if (decimalPlaces[1].length() > 2) {
                            throw new IllegalArgumentException("Error: Amount cannot be more than two decimal places.");
                        }
                    }
                }
                return parseAmount;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Error: Invalid Amount.");
            }
    }

    /**
     * Prompts the user to enter a custom amount filter.
     * @param prompt the message displayed to the user.
     * @param isMinAmount true if the custom filter is the minimum amount or false if it is the maximum amount filter.
     * @return double the amount used for the filter.
     */
    public static double promptForCustomAmount(String prompt, boolean isMinAmount){
        double defaultAmount = isMinAmount ? MIN_AMOUNT : MAX_AMOUNT;
        while(true) {
            try {
                String userInput = promptForString(prompt);
                return userInput.isBlank() ? defaultAmount : parseAmount(userInput);
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
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
                if (userInput.equals(option.toUpperCase())){//In case I forget to make options uppercase
                    return userInput;
                }
            }
            System.out.println("Error: Invalid Input. Please Try Again.");
        }
    }

    /**
     * Prompts the user for a time.
     * @param prompt the message displayed to the user.
     * @return LocalTime the validated and parsed time the user entered.
     */
    public static LocalTime promptForTime(String prompt){
        while(true){
            try{
                String userInput = promptForString(prompt);
                return parseTime(userInput);
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Prompts the user for a date and time.
     * @return LocalDateTime the date and time parsed and validated.
     */
    public static LocalDateTime promptForDateTime(){
            boolean isFuture;
            LocalDateTime dateTime;

            LocalDate date = promptForDate("Enter Date (YYYY-MM-DD): ");
            do {
                LocalTime time = promptForTime("Enter Time (24:00): ");
                dateTime = LocalDateTime.of(date, time);
                isFuture = dateTime.isAfter(LocalDateTime.now());

                if (isFuture){
                    System.out.println("Error: Date and Time cannot be in the future.");
                }

            }while(isFuture);
            return dateTime;
    }

    /**
     * Prompts the user for a date.
     * @param prompt the message displayed to the user.
     * @return LocalDate the parsed and validated date the user entered.
     */
    public static LocalDate promptForDate(String prompt){
        while(true) {
            try {
                String userInput = promptForString(prompt);
                return parseDate(userInput);
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Parses and validates date in format YYYY-M-D or YYYY-MM-DD not exceeding today's date.
     * @param input the date being parsed and validated.
     * @return LocalDate the date user enters.
     */
    public static LocalDate parseDate(String input){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-M-d");
        try {
            LocalDate parseDate = LocalDate.parse(input, fmt);
            if (parseDate.isAfter(LocalDate.now()) || parseDate.isBefore(MIN_DATE)){
                throw new IllegalArgumentException("Error: Date has to be between 1970-01-01 and today");
            }
            return parseDate;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Error: Invalid Date. Please Try Again.");
        }
    }

    /**
     * Parses and validates time in HH:MM format.
     * @param input the time being parsed and validated.
     * @return LocalTime, the time the user entered.
     */
    public static LocalTime parseTime(String input){
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            return LocalTime.parse(input, timeFormatter);
        }catch (DateTimeParseException e){
            throw new IllegalArgumentException("Error: Invalid Time. Please Try Again.");
        }
    }


    /**
     * Prompts the user for a custom filter date, either start or end date.
     * @return LocalDate the date the user entered or a default date if left blank.
     */
    public static LocalDate promptCustomDate(String prompt, boolean isStartDate){
        while(true){
            LocalDate defaultDate = isStartDate ? MIN_DATE : LocalDate.now();
            try {
                String date = promptForString(prompt);
                if (date.isBlank()) {
                    return defaultDate;
                } else {
                    return isStartDate ? parseDate(date).minusDays(1) : parseDate(date).plusDays(1); // Makes 1970-01-01 and today's date inclusive.
                }
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
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
                System.out.println("Please enter an option between " + min + "-" + max);
            }catch(NumberFormatException e){
                System.out.println("Error: Invalid Character. Please Try Again!");
            }
        }
    }
}
