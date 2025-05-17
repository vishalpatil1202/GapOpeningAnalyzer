package com.vishal.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {

    @Value("${newsapi.apiKey}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public NewsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String fetchStockNews(String stockSymbol) {
        try {
            String companyName = mapSymbolToCompany(stockSymbol);
            String query = companyName != null ? companyName : stockSymbol;
            String url = String.format(
            	    "https://newsapi.org/v2/everything?qInTitle=%s&domains=moneycontrol.com,livemint.com,financialexpress.com,economictimes.indiatimes.com,business-standard.com&sortBy=publishedAt&language=en&apiKey=%s",
            	    URLEncoder.encode(companyName, StandardCharsets.UTF_8), apiKey
            	);
            
            String response = restTemplate.getForObject(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            List<String> newsList = new ArrayList<>();

            for (int i = 0; i < Math.min(3, root.path("articles").size()); i++) {
                JsonNode article = root.path("articles").get(i);
                String title = article.get("title").asText();
                String description = article.has("description") ? article.get("description").asText() : "";
                newsList.add(title + ". " + description);
            }

            return summarizeNews(newsList, 3); // Top 3 short summaries

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to fetch news for " + stockSymbol;
        }
    }

    private String mapSymbolToCompany(String symbol) {
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

    private String summarizeNews(List<String> newsList, int limit) {
        StringBuilder summary = new StringBuilder();
        for (int i = 0; i < Math.min(limit, newsList.size()); i++) {
            summary.append("• ").append(newsList.get(i)).append("\n");
        }
        return summary.toString();
    }
}
