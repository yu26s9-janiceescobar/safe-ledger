package com.pluralsight.models;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Creates a transaction including information of date, time, description, vendor, and amount.
 */
public class Transactions {
    private LocalDateTime dateTime;
    private String description;
    private String vendor;
    private double amount;

    /**
     * Creates Transaction with information about the transaction.
     * @param dateTime the date and time of transaction.
     * @param description the description of the transaction.
     * @param vendor the vendor of transaction.
     * @param amount the amount of the transaction.
     */
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

    /**
     * The date, time, description, vendor, and amount of transaction in a formatted string.
     * @return String of transaction information.
     */
    @Override
    public String toString(){
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm:ss");
        return String.format("%-20s %-20s %-45s %-35s $%,.2f", this.getDate().format(dateFmt), this.getTime().format(timeFmt), description, vendor, amount);
    }
}
