package com.vishal.model;

public class StockData {

    private double open;
    private double previousClose;
    private String symbol;

    public StockData(double open, double previousClose) {
        this.open = open;
        this.previousClose = previousClose;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getPreviousClose() {
        return previousClose;
    }

    public void setPreviousClose(double previousClose) {
        this.previousClose = previousClose;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "StockData{" +
                "open=" + open +
                ", previousClose=" + previousClose +
                '}';
    }
}
