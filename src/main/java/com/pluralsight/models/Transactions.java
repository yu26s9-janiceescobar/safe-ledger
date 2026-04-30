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

    public void setAmount(double amount){
        this.amount = amount;
    }
    public LocalDateTime getDateTime(){
        return dateTime;

    }
    public void setDateTime(LocalDateTime dateTime){
        this.dateTime = dateTime;
    }
    public LocalDate getDate(){
        return dateTime.toLocalDate();
    }
    public LocalDateTime setTime(LocalTime time){
        return time.atDate(dateTime.toLocalDate());
    }
    public LocalDateTime setDate(LocalDate date){
        return date.atTime(dateTime.toLocalTime());
    }

    public LocalTime getTime(){
        return dateTime.toLocalTime();
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public String getVendor(){
        return vendor;
    }
    public void setVendor(String vendor){
        this.vendor = vendor;
    }


    @Override
    public String toString(){
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm:ss");
        return String.format("%-20s %-20s %-45s %-35s $%,.2f", this.getDate(), this.getTime(), description, vendor, amount);
    }
}
