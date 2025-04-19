package com.vishal;

import com.vishal.model.StockData;
import com.vishal.service.AlphaVantageService;
import com.vishal.service.StockNewsService;
import com.vishal.service.EmailService;

public class App {

    private static final String ALPHA_VANTAGE_API_KEY = "*************"; // Update your Alpha Vantage API key
    private static final String USER_EMAIL = "*********@gmail.com"; // Update your email address

    public static void main(String[] args) {
        String[] stockSymbols = {
        		"RELIANCE.BSE", "TCS.BSE", "HDFCBANK.BSE"
        };

        AlphaVantageService service = new AlphaVantageService(ALPHA_VANTAGE_API_KEY);

        for (String symbol : stockSymbols) {
            System.out.println("=> Analyzing: " + symbol);
            StockData stockData = service.getStockData(symbol);

            if (stockData != null) {
                System.out.println("  Previous Close: " + stockData.getPreviousClose());
                System.out.println("  Current Open:   " + stockData.getOpen());

                if (stockData.getOpen() > stockData.getPreviousClose()) {
                    System.out.println("=>>Gap Up Detected");
                    String summarizedNews = StockNewsService.getNewsSummaryForStock(symbol);
                    System.out.println("=>>Latest Summarized News:\n" + summarizedNews);
                    EmailService.sendEmail(USER_EMAIL, "Stock Gap Up: " + symbol, summarizedNews);

                } else if (stockData.getOpen() < stockData.getPreviousClose()) {
                    System.out.println("=>>Gap Down Detected");
                    String summarizedNews = StockNewsService.getNewsSummaryForStock(symbol);
                    System.out.println("=>>Latest Summarized News:\n" + summarizedNews);
                    EmailService.sendEmail(USER_EMAIL, "Stock Gap Down: " + symbol, summarizedNews);

                } else {
                    System.out.println("=>No Gap");
                }
            } else {
                System.out.println("=>Failed to fetch data.");
            }
        }
    }
}