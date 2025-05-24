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
│   │   │           └── AppConfig.java                                # Spring configuration class
|   |   |           ├── GapOpeningAnalyzerApplication.java            # Main entry point of the Spring Boot application
|   |   |           ├── ScheduledTaskRunner.java                      # Scheduler to trigger stock analysis at intervals
│   │   │               ├── controller/            
│   │   │               │   └── EmailSubscriptionController.java      # Handles subscription form requests
│   │   │               ├── dto/
|   |   |               |   └── EmailSubscriptionRequest.java         # DTO for receiving email input from subscription form
│   │   │               ├── exception/
|   |   |               |   └── ValidationExceptionHandler.java       # Handles validation and custom exceptions globally
│   │   │               ├── model/
│   │   │               │   ├── EmailSubscriber.java                  # Entity model representing an email subscriber
│   │   │               │   └── StockData.java                        # Model to store structured stock data 
│   │   │               ├── repository/
|   |   |               |   └── EmailSubscriberRepository.java        # JPA repository for email subscriptions
│   │   │               ├── service/
│   │   │               │   ├── AlphaVantageService.java              # Fetches gap-up/down stock data from Alpha Vantage API
│   │   │               │   ├── EmailService.java                     # Sends formatted reports to subscriber emails
│   │   │               │   ├── NewsService.java                      # Fetches related news using NewsAPI
|   |   |               |   └── SummarizationService.java             # Summarises fetched news using Hugging Face model
│   │   └── resources/
|   |       ├── static
|   |       |   └── index.html                                        # Front-end form for email subscription
│   │       ├── application.properties                                # Environment configuration 
├── Dockerfile                                                        # Docker setup for containerised deployment
└── pom.xml                                                           # Maven dependencies and build configuration
```

---

## ⚙️ Configuration
This application uses environment variables for sensitive credentials and configuration. Ensure these are properly set in your local environment, .env file, or cloud deployment settings.
```
spring.datasource.url=<UPDATE_YOUR_DATASOURCE_URL>
spring.datasource.username=<UPDATE_DATASOURCE_USERNAME>
spring.datasource.password=<UPDATE_DATASOURCE_PASSWORD>
alphavantage.apiKey=<UPDATE_ALPHA_VANTAGE_API_KEY>
newsapi.apiKey=<UPDATE_NEWSORG_API_KEY>
mail.username=<UPDATE_SENDER_EMAIL_ID>
mail.password=<UPDATE_SENDER_GMAIL_APP_PASSWORD>
huggingface.apiToken=<UPDATE_HUGGING_FACE_API_TOKEN>
```
> Note: Replace the values with your actual credentials. Never commit real secrets to source code.

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
