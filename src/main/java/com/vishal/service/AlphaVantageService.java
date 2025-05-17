package com.vishal.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vishal.model.StockData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

@Service
public class AlphaVantageService {

    private static final String BASE_URL = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY";

    @Value("${alphavantage.apiKey}")  
    private String apiKey;

    public AlphaVantageService() {
        
    }

    public StockData getStockData(String symbol) {
        try {
            String requestUrl = BASE_URL + "&symbol=" + symbol + "&apikey=" + apiKey;
            System.out.println("Requesting data from Alpha Vantage with URL: " + requestUrl);  // Debug statement
            URL url = new URL(requestUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.toString());

            if (rootNode.has("Note")) {
                System.out.println("Rate limit reached or usage note from Alpha Vantage:");
                System.out.println(rootNode.get("Note").asText());
                return null;
            }

            if (rootNode.has("Error Message")) {
                System.out.println("Error from Alpha Vantage:");
                System.out.println(rootNode.get("Error Message").asText());
                return null;
            }

            JsonNode timeSeries = rootNode.path("Time Series (Daily)");

            if (timeSeries.isMissingNode() || !timeSeries.fields().hasNext()) {
                System.out.println("'Time Series (Daily)' data not found. Full response:");
                System.out.println(response.toString());
                return null;
            }

            Iterator<Map.Entry<String, JsonNode>> dates = timeSeries.fields();

            if (!dates.hasNext()) {
                System.out.println("No date entries found in time series.");
                return null;
            }

            Map.Entry<String, JsonNode> latestEntry = dates.next();
            double open = latestEntry.getValue().get("1. open").asDouble();

            if (!dates.hasNext()) {
                System.out.println("Only one day of data available. Cannot compare.");
                return null;
            }

            Map.Entry<String, JsonNode> previousEntry = dates.next();
            double previousClose = previousEntry.getValue().get("4. close").asDouble();

            return new StockData(open, previousClose);

        } catch (Exception e) {
            System.out.println("Exception occurred while calling Alpha Vantage API:");
            e.printStackTrace();
            return null;
        }
    }
}
