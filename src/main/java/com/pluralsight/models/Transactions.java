package com.pluralsight.models;
import java.time.LocalDate;
import java.time.LocalTime;

public class Transactions {
    private String description;
    private String vendor;
    private int amount;

    public Transactions(String description, String vendor){
        this.description = description;
        this.vendor = vendor;
    }

    public LocalDate getParseDate(String date){
        return LocalDate.parse(date);
    }

    public LocalTime getParseTime(String time){
        return LocalTime.parse(time);
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
    public void setVendor(){
        this.vendor = vendor;
    }

}
