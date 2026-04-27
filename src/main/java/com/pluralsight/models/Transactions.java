package com.pluralsight.models;
import java.time.LocalDate;
import java.time.LocalTime;

public class Transactions {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private long pennyAmount;

    public Transactions(LocalDate date, LocalTime time, String description, String vendor, double amount){
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.pennyAmount = (long)(amount*100);

    }
    public double getDollarAmount(){
        return pennyAmount * 100.0;
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
      return String.format("""
              Date: %s,
              Time: %s
              Description: %s
              Vendor: %s
              Amount: $%.2f""", date, time, description, vendor, getDollarAmount());
       }

}
