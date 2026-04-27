package com.pluralsight.data;
import com.pluralsight.models.Transactions;
import java.util.ArrayList;
import com.pluralsight.models.Transactions;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class DataManager {
    private String fileName;
    private ArrayList<Transactions> transactions;

    public DataManager(){
        String fileName = "data/transactions.csv";
    }
//    public void loadTransactions(){
//        try {
//            FileReader fileReader = new FileReader(fileName);
//            BufferedReader bufReader = new BufferedReader(fileReader);
//            bufReader.readLine();
//            String line;
//            while((line = bufReader.readLine() != null){
//                String[] transaction = line.split("\\|");
//
//            }
//        }
//        catch (IOException e){
//            System.out.println("Error: " + e.getMessage());
//        }
//
//
//    }
}
