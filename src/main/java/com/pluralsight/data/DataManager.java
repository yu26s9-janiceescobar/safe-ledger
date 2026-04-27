package com.pluralsight.data;
import com.pluralsight.models.Transactions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class DataManager {
    private static final String transactionFile = "data/transactions.csv";

    public static ArrayList<Transactions> loadTransactions(){
        ArrayList<Transactions> transactions = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(transactionFile);
            BufferedReader bufReader = new BufferedReader(fileReader);

            bufReader.readLine();

            String line;
            while((line = bufReader.readLine()) != null){
                String[] singleTransaction = line.split("\\|");
                LocalDate date = parseDate(singleTransaction[0]);
                LocalTime time = parseTime(singleTransaction[1]);
                String description = singleTransaction[2];
                String vendor = singleTransaction[3];
                double amount = Double.parseDouble(singleTransaction[4]);

                Transactions t = new Transactions(date, time, description, vendor, amount);
                transactions.add(t);
            }
        }
        catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
        return transactions;
    }
    public static LocalDate parseDate(String date){
        return LocalDate.parse(date);
    }
    public static LocalTime parseTime(String time){
        return LocalTime.parse(time);
    }
}
