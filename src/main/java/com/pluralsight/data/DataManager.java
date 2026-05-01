package com.pluralsight.data;
import com.pluralsight.models.Transactions;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    /**
     * loads transactions from a preloaded file and adds it to a list of Transactions.
     * @return the list of transactions from the file.
     */
    public static ArrayList<Transactions> loadTransactions(){
        try {
            FileReader fileReader = new FileReader(transactionFile);
            BufferedReader bufReader = new BufferedReader(fileReader);

            bufReader.readLine();

            String line;
            while((line = bufReader.readLine()) != null){
                if (line.isBlank()){
                    continue; // avoids blank line errors
                }
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
        transactions.sort((t1, t2)->t2.getDateTime().compareTo(t1.getDateTime())); // Sorts transactions by newest transaction to oldest transaction.
        return transactions;
    }

    /**
     * Creates a new Transaction and saves it to a file.
     * @param dateTime the date and time of transaction.
     * @param description the description of the transaction.
     * @param vendor the vendor of the transaction.
     * @param amount the amount of transaction.
     */
    public static void addTransaction(LocalDateTime dateTime, String description, String vendor, double amount){
        Transactions t = new Transactions(dateTime, description, vendor, amount);

        try {
            String line;
            FileWriter fileWriter = new FileWriter(transactionFile, true);
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);
            line = String.format("%s|%s|%s|%s|%.2f%n", t.getDate(), t.getTime(), description, vendor, amount);
            bufWriter.write(line);
            bufWriter.close();
            transactions.add(t); // Will not add to arrayList if IOException is thrown.
        }catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }

        transactions.sort((t1, t2)->t2.getDateTime().compareTo(t1.getDateTime()));

    }



}
