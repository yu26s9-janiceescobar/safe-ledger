package com.pluralsight.ui;
import java.util.*;
public class Console {
    private final static Scanner scanner = new Scanner(System.in);

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
                if (parseInt > min && parseInt < max){
                    return parseInt;
                }

            }catch(Exception e){
                System.out.println("Invalid input. Please Try Again!");
            }
            System.out.println("Please enter an option between " + min + "-" + max);
        }while(true);
    }
}
