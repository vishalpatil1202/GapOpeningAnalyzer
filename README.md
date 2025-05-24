# 📈 Gap Opening Analyzer

**Gap Opening Analyzer** is a Java-based AI Agent that analyzes stock market gap openings, fetches and summarizes relevant financial news, and sends out concise reports via email — all automatically.

---

## ✨ Features

- 🔍 **Gap Detection**: Compares today's open price with the previous day's close to identify gap ups or gap downs.
- 📰 **News Fetching**: Automatically queries financial news from trusted sources like:
  - Moneycontrol
  - Economic Times
  - Business Standard
  - Financial Express
  - LiveMint
- 🧠 **AI Summarization**: Uses Hugging Face's BART model via their API to summarize top 3 news stories per stock.
- 📧 **Email Reporting**: Sends a formatted report with gap information and summarized news using Jakarta Mail.

---

## 🛠️ Tech Stack

| Component          | Technology                                 |
|--------------------|--------------------------------------------|
| Backend            | Java 17, Spring Boot, Maven                |
| Frontend           | HTML, CSS                                  |
| Database           | PostgreSQL
| Gap Detection      | Alpha Vantage API                          |
| News Aggregation   | NewsAPI.org (with domain filtering)        |
| AI Summarization   | Hugging Face `facebook/bart-large-cnn`     |
| Email Sending      | Jakarta Mail (with Gmail SMTP & App Pass)  |

---

## 🗂️ Project Structure

```
GapOpeningAnalyzer/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── vishal/
│   │   │           └── AppConfig.java
|   |   |           ├── GapOpeningAnalyzerApplication.java
|   |   |           ├── ScheduledTaskRunner.java
│   │   │               ├── controller/
│   │   │               │   └── EmailSubscriptionController.java
│   │   │               ├── dto/
|   |   |               |   └── EmailSubscriptionRequest.java
│   │   │               ├── exception/
|   |   |               |   └── ValidationExceptionHandler.java
│   │   │               ├── model/
│   │   │               │   ├── EmailSubscriber.java
│   │   │               │   └── StockData.java
│   │   │               ├── repository/
|   |   |               |   └── EmailSubscriberRepository.java
│   │   │               ├── service/
│   │   │               │   ├── AlphaVantageService.java
│   │   │               │   ├── EmailService.java
│   │   │               │   ├── NewsService.java
|   |   |               |   └── SummarizationService.java
│   │   │               ├── utils/
│   │   │               │   └── NewsSummarizer.java
│   │   └── resources/
|   |       ├── static
|   |       |   └── index.html
│   │       ├── application.properties
├── Dockerfile
└── pom.xml
```

---

## 🔑 How to Get the Required API Keys

| Service         | How to Register                                       |
|-----------------|-------------------------------------------------------|
| Alpha Vantage   | https://www.alphavantage.co/support/#api-key          |
| NewsAPI         | https://newsapi.org/register                          |
| Hugging Face    | https://huggingface.co/settings/tokens                |
| Gmail App Pass  | https://support.google.com/accounts/answer/185833     |

> Note: Enable 2FA on your Gmail to generate an App Password.

---

## 🧪 How to Run the Project

### Prerequisites:
- Java 17+ 
- Maven 3.6+
- Internet access (for APIs)

### Steps:
1. Clone the repository:
   ```bash
   git clone https://github.com/vishalpatil1202/GapOpeningAnalyzer.git
   cd GapOpeningAnalyzer
   ```

2. Compile and run:
   ```bash
   # Navigate to the project directory
   cd GapOpeningAnalyzer
   # Package the project (optional)
   mvn clean install
   # Run the application
   mvn spring-boot:run
   ```

---

## ❗ Disclaimer:

This is *not* a stock recommendation tool. It is designed purely to **inform traders/investors** about market gap openings and associated news, helping them stay aware and make their **own decisions**.

---

## 🤝 Contributing

Pull requests are welcome! Open an issue for feature requests, improvements, or bug reports.
