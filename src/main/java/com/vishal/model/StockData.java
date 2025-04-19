package com.vishal.model;

public class StockData {
    private double open;
    private double previousClose;

    public StockData(double open, double previousClose) {
        this.open = open;
        this.previousClose = previousClose;
    }

    public double getOpen() {
        return open;
    }

    public double getPreviousClose() {
        return previousClose;
    }
}