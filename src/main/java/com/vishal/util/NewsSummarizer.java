package com.vishal.util;

import org.apache.http.client.HttpResponseException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class NewsSummarizer {

    private static final String API_URL = "https://api-inference.huggingface.co/models/facebook/bart-large-cnn";
    private static final String API_TOKEN = "**************"; // Update your Hagging Face API token

    public static String summarizeNews(List<String> newsList, int maxSummaryCount) throws Exception {
        if (newsList == null || newsList.isEmpty()) {
            return "No news available.";
        }

        String fullText = newsList.stream()
                                  .limit(maxSummaryCount)
                                  .collect(Collectors.joining(". "));
        
        if (fullText.isEmpty()) {
            return "No valid news content to summarize.";
        }

        return summarize(fullText);
    }

    // Summarizes long text using HuggingFace inference API
    public static String summarize(String text) throws Exception {
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + API_TOKEN);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        // Set timeouts for the connection
        conn.setConnectTimeout(5000);  
        conn.setReadTimeout(5000);   

        JSONObject input = new JSONObject();
        input.put("inputs", text);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(input.toString().getBytes());
        }

        if (conn.getResponseCode() != 200) {
            throw new HttpResponseException(conn.getResponseCode(), "Failed to summarize text");
        }

        // Read the response
        Scanner scanner = new Scanner(conn.getInputStream());
        StringBuilder jsonStr = new StringBuilder();
        while (scanner.hasNext()) {
            jsonStr.append(scanner.nextLine());
        }
        scanner.close();

        String response = jsonStr.toString();
        JSONArray summaryArr = new JSONArray(response);
        return summaryArr.getJSONObject(0).getString("summary_text");
    }
}