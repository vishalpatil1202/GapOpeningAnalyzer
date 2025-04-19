package com.vishal.service;

import com.vishal.util.NewsSummarizer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StockNewsService {

    private static final String NEWS_API_KEY = "************"; // Update your News Org API key
    private static final String NEWS_API_URL = 
    	    "https://newsapi.org/v2/everything?q=%s&domains=moneycontrol.com,livemint.com,financialexpress.com,economictimes.indiatimes.com,business-standard.com&sortBy=publishedAt&language=en&apiKey=" + NEWS_API_KEY;

    public static String getNewsSummaryForStock(String stockSymbol) {
        try {
        	String query = mapSymbolToCompany(stockSymbol);
        	URL url = new URL(String.format(NEWS_API_URL, query));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            Scanner scanner = new Scanner(conn.getInputStream());
            StringBuilder jsonStr = new StringBuilder();
            while (scanner.hasNext()) {
                jsonStr.append(scanner.nextLine());
            }
            scanner.close();

            JSONObject response = new JSONObject(jsonStr.toString());
            JSONArray articles = response.getJSONArray("articles");

            List<String> newsList = new ArrayList<>();
            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                String title = article.getString("title");
                String description = article.optString("description", "");
                newsList.add(title + ". " + description);
            }

            // Summary logic added here
            return NewsSummarizer.summarizeNews(newsList, 3); // Top 3 short summaries

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to fetch news for " + stockSymbol;
        }
    }
    
    private static String mapSymbolToCompany(String symbol) {
        switch (symbol) {
            case "RELIANCE.BSE": return "Reliance Industries";
            case "TATASTEEL.BSE": return "Tata Steel";
            case "AXISBANK.BSE": return "Axis Bank";
            case "ASIANPAINT.BSE": return "Asian Paints";
            case "SBIN.BSE": return "State Bank of India";
            case "HDFCBANK.BSE": return "HDFC Bank";
            case "BAJFINANCE.BSE": return "Bajaj Finance";
            case "INFY.BSE": return "Infosys";
            case "TCS.BSE": return "TCS";
            case "NESTLEIND.BSE": return "Nestle India";
            case "WIPRO.BSE": return "Wipro";
            case "ICICIBANK.BSE": return "ICICI Bank";
            default: return symbol;
        }
    }
}
