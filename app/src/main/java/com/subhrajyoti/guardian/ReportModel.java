package com.subhrajyoti.guardian;


import java.io.Serializable;

public class ReportModel implements Serializable{

    private String date;
    private String time;
    private String location;
    private String crime;

    public ReportModel(){

    }

    public ReportModel(String date, String time, String location, String crime) {
        this.date = date;
        this.time = time;
        this.location = location;
        this.crime = crime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCrime() {
        return crime;
    }

    public void setCrime(String crime) {
        this.crime = crime;
    }
}
