package com.company;

public class Date {

    private int year;
    private int month;
    private int day;

    public Date() {

    }

    public Date (int year, int month, int date) {
        this.year = year;
        this.month = month;
        this.day = date;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDate() {
        return day;
    }

    public void setDate(int date) {
        this.day = date;
    }

    @Override
    public String toString() {
        return year + "-" + month + "-" + day;
    }
}

