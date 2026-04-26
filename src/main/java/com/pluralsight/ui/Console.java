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
            String userInput = scanner.nextLine();
            for (String option : options) {
                if (userInput.equalsIgnoreCase(option)) {
                    return userInput;
                }
            }
            System.out.println("Invalid Input. Try Again.");
        }
        while(true);
    }
}
