package com.pluralsight.data;
import com.pluralsight.models.Transactions;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class DataManager {
    private static final String transactionFile = "data/transactions.csv";
    private static final ArrayList<Transactions> transactions = new ArrayList<>();


    public static ArrayList<Transactions> loadTransactions(){
        try {
            FileReader fileReader = new FileReader(transactionFile);
            BufferedReader bufReader = new BufferedReader(fileReader);

            bufReader.readLine();

            String line;
            while((line = bufReader.readLine()) != null){
                String[] t = line.split("\\|");
                LocalDate date = LocalDate.parse(t[0]);
                LocalTime time = LocalTime.parse(t[1]);
                LocalDateTime dateTime = LocalDateTime.of(date,time); // Combines the date and time objects to create a DateTime object.
                String description = t[2];
                String vendor = t[3];
                double amount = Double.parseDouble(t[4]);
                Transactions transaction = new Transactions(dateTime, description, vendor, amount);
                transactions.add(transaction);
            }
            bufReader.close();
        }
        catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
        return transactions;
    }

    public static void addTransaction(LocalDateTime dateTime, String description, String vendor, double amount){
        Transactions t = new Transactions(dateTime, description, vendor, amount);

        DateTimeFormatter dateFormat =  DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Takes the date part of the DateTime object
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss"); // Takes the time part of the DateTime object.
        try {
            String line;
            FileWriter fileWriter = new FileWriter(transactionFile, true);
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);
            line = String.format("%s|%s|%s|%s|%.2f", dateTime.format(dateFormat), dateTime.format(timeFormat), description, vendor, amount);
            bufWriter.newLine();
            bufWriter.write(line);
            bufWriter.close();
            transactions.add(t);
        }catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }



}
