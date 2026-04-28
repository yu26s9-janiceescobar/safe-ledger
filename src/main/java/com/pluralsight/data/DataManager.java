package com.pluralsight.data;
import com.pluralsight.models.Transactions;

import java.time.LocalDate;
import java.time.LocalTime;
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
                String description = t[2];
                String vendor = t[3];
                double amount = Double.parseDouble(t[4]);
                Transactions transaction = new Transactions(date, time, description, vendor, amount);
                transactions.add(transaction);
            }
            bufReader.close();
        }
        catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
        return transactions;
    }

    public static void addTransaction(LocalDate date, LocalTime time, String description, String vendor, double amount){
        Transactions t = new Transactions(date, time, description, vendor, amount);
        transactions.add(t);
        try {
            FileWriter fileWriter = new FileWriter(transactionFile, true);
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);
            String line = String.format("%s|%s|%s|%s|%.2f", date, time, description, vendor, amount);
            bufWriter.write(line);
            bufWriter.close();
        }catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }



}
