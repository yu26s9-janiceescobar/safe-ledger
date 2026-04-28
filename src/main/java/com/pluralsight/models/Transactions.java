package com.pluralsight.models;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Transactions {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;

    public Transactions(LocalDate date, LocalTime time, String description, String vendor, double amount){
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;

    }

    public double getAmount(){
        return amount;
    }

    public LocalDate getDate(){
        return date;

    }
    public LocalTime getTime(){
        return time;

    }

    public String getDescription(){
        return description;
    }

    public String getVendor(){
        return vendor;
    }


    @Override
    public String toString(){
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm:ss");
        return String.format("%-20s %-20s %-45s %-30s $%.2f", date, time.format(timeFmt), description, vendor, amount);
    }
}
