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

import java.util.List;

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

    // @Scheduled(fixedRate = 300000) 
    @Scheduled(cron = "0 30 9 * * ?")
    public void executeDailyTask() {
        String[] symbols = {"HDFCBANK.BSE", "RELIANCE.BSE", "TCS.BSE", "INFY.BSE", "SBIN.BSE", "WIPRO.BSE", "ICICIBANK.BSE", "TATASTEEL.BSE", "AXISBANK.BSE", "ASIANPAINT.BSE"};
        StringBuilder finalReport = new StringBuilder();
        boolean anySignificantGap = false;

        for (String symbol : symbols) {
            System.out.println("\nAnalyzing stock: " + symbol);
            StockData stockData = alphaVantageService.getStockData(symbol);

            if (stockData != null) {
                System.out.println("Stock Data Fetched:");
                System.out.println(stockData);

                double gap = ((stockData.getOpen() - stockData.getPreviousClose()) / stockData.getPreviousClose()) * 100;
                System.out.printf("Gap: %.2f%%\n", gap);

                if (Math.abs(gap) > 0.5) {
                    anySignificantGap = true;

                    String rawNews = newsService.fetchStockNews(symbol);
                    System.out.println("rawNews >>> " + rawNews);
                    String summary;
                    if (rawNews == null || rawNews.trim().isEmpty()) {
                        summary = "No relevant news found.";
                    } else {
                        try {
                            summary = summarizationService.summarize(rawNews);
                        } catch (Exception e) {
                            System.err.println("Summarization failed for " + symbol + ": " + e.getMessage());
                            summary = "Could not generate summary.";
                        }
                    }

                    finalReport.append("Gap Opening Detected for ").append(symbol).append(":\n")
                            .append("Open Price: ").append(stockData.getOpen()).append("\n")
                            .append("Previous Close: ").append(stockData.getPreviousClose()).append("\n")
                            .append(String.format("Gap: %.2f%%\n", gap))
                            .append("News Summary:\n").append(summary).append("\n\n")
                            .append("----------------------------------------------------\n\n");
                } else {
                    System.out.println("No significant gap for " + symbol);
                }
            } else {
                System.out.println("Failed to fetch stock data for " + symbol);
            }
        }

        if (anySignificantGap) {
            System.out.println("Sending consolidated email...");

            List<EmailSubscriber> subscribers = subscriberRepository.findAll();

            for (EmailSubscriber subscriber : subscribers) {
                String recipientEmail = subscriber.getEmail();
                emailService.sendEmail(recipientEmail, "Gap Opening Report", finalReport.toString());
            }

        } else {
            System.out.println("No significant gaps found in any stock. Email not sent.");
        }
    }
}
