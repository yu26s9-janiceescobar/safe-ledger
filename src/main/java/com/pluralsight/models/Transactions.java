package com.pluralsight.models;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Transactions {
    private LocalDateTime dateTime;
    private String description;
    private String vendor;
    private double amount;

    public Transactions(LocalDateTime dateTime, String description, String vendor, double amount){
        this.dateTime = dateTime;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;

    }

    public double getAmount(){
        return amount;
    }

    public LocalDateTime getDateTime(){
        return dateTime;

    }

    public LocalDate getDate(){
        return dateTime.toLocalDate();
    }

    public LocalTime getTime(){
        return dateTime.toLocalTime();
    }

    public String getDescription(){
        return description;
    }

    public String getVendor(){
        return vendor;
    }


    @Override
    public String toString(){
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm:ss");
        return String.format("%-20s %-20s %-45s %-30s $%,.2f", dateTime.format(dateFmt), dateTime.format(timeFmt), description, vendor, amount);
    }
}
