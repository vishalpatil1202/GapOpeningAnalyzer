package com.vishal;

import com.vishal.model.EmailSubscriber;
import com.vishal.model.StockData;
import com.vishal.repository.EmailSubscriberRepository;
import com.vishal.service.AlphaVantageService;
import com.vishal.service.EmailService;
import com.vishal.service.NewsService;
import com.vishal.service.SummarizationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ScheduledTaskRunner {

    private final AlphaVantageService alphaVantageService;
    private final NewsService newsService;
    private final SummarizationService summarizationService;
    private final EmailService emailService;
    private final EmailSubscriberRepository subscriberRepository;

    @Autowired
    public ScheduledTaskRunner(
            AlphaVantageService alphaVantageService,
            NewsService newsService,
            SummarizationService summarizationService,
            EmailService emailService,
            EmailSubscriberRepository subscriberRepository
    ) {
        this.alphaVantageService = alphaVantageService;
        this.newsService = newsService;
        this.summarizationService = summarizationService;
        this.emailService = emailService;
        this.subscriberRepository = subscriberRepository;
    }

//    @Scheduled(fixedRate = 300000)
    @Scheduled(cron = "0 15 23 * * ?", zone = "Asia/Kolkata")
    public void executeDailyTask() {
        String[] symbols = {"HDFCBANK.BSE", "RELIANCE.BSE"};
        List<StockData> gappedStocks = new ArrayList<>();
        Map<String, String> summaries = new HashMap<>();
        boolean anySignificantGap = false;

        for (String symbol : symbols) {
            System.out.println("\nAnalyzing stock: " + symbol);
            StockData stockData = alphaVantageService.getStockData(symbol);

            if (stockData != null) {
                double gap = ((stockData.getOpen() - stockData.getPreviousClose()) / stockData.getPreviousClose()) * 100;

                if (Math.abs(gap) > 0.1) {
                    anySignificantGap = true;
                    String rawNews = newsService.fetchStockNews(symbol);
                    String summary;

                    if (rawNews == null || rawNews.trim().isEmpty()) {
                        summary = "No relevant news found.";
                    } else {
                        try {
                            summary = summarizationService.summarize(rawNews);
                        } catch (Exception e) {
                            summary = "Could not generate summary.";
                        }
                    }

                    stockData.setSymbol(symbol);
                    gappedStocks.add(stockData);
                    summaries.put(symbol, summary);
                }
            }
        }

        if (anySignificantGap) {
            List<EmailSubscriber> subscribers = subscriberRepository.findAll();
            String htmlReport = buildHtmlReport(gappedStocks, summaries);

            for (EmailSubscriber subscriber : subscribers) {
                emailService.sendEmail(subscriber.getEmail(), "📊 Gap Opening Report", htmlReport);
            }

            System.out.println("Email sent to subscribers.");
        } else {
            System.out.println("No significant gaps found. Email not sent.");
        }
    }

    private String buildHtmlReport(List<StockData> gappedStocks, Map<String, String> summaries) {
        StringBuilder html = new StringBuilder();
        html.append("<html><body>");
        html.append("<div style='font-family:Arial,sans-serif;'>");

        for (StockData stock : gappedStocks) {
            String symbol = stock.getSymbol();
            double gap = ((stock.getOpen() - stock.getPreviousClose()) / stock.getPreviousClose()) * 100;
            String summary = summaries.getOrDefault(symbol, "No summary available.");

            html.append("<div style='border:1px solid #ddd;padding:15px;margin-top:15px;border-radius:8px;'>")
                    .append("<h3>").append(symbol).append("</h3>")
                    .append("<p><strong>Open Price:</strong> ₹").append(stock.getOpen()).append("</p>")
                    .append("<p><strong>Previous Close:</strong> ₹").append(stock.getPreviousClose()).append("</p>")
                    .append(String.format("<p><strong>Gap:</strong> <span style='color:red;'>%.2f%%</span></p>", gap))
                    .append("<p><strong>News Summary:</strong></p>")
                    .append("<div style='background:#f4f4f4;padding:10px;border-left:4px solid #0053a0;'>")
                    .append(summary)
                    .append("</div>")
                    .append("</div>");
        }

        html.append("</div></body></html>");
        return html.toString();
    }
}
