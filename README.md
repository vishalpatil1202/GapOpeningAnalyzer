
# 📈 Gap Opening Analyzer – Your AI-Powered Stock Gap Assistant

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

| Component          | Technology                                |
|--------------------|--------------------------------------------|
| Language           | Java                                       |
| Gap Detection      | Alpha Vantage API (`TIME_SERIES_DAILY`)    |
| News Aggregation   | NewsAPI.org (with domain filtering)        |
| AI Summarization   | Hugging Face `facebook/bart-large-cnn`     |
| Email Sending      | Jakarta Mail (with Gmail SMTP & App Pass)  |
| JSON Handling      | Jackson / org.json                         |

---

## 🗂️ Project Structure

```
com/
├── vishal/
│   ├── App.java                     # Main entry point
│   ├── model/
│   │   └── StockData.java           # Data model for stock prices
│   ├── service/
│   │   ├── AlphaVantageService.java # Fetches stock data
│   │   ├── StockNewsService.java    # Fetches and summarizes news
│   │   └── EmailService.java        # Sends reports via email
│   └── util/
│       └── NewsSummarizer.java      # Uses Hugging Face API to summarize news
```

---

## ⚙️ Configuration

You must update the following values in the code:

- In `App.java`:
  ```java
  private static final String ALPHA_VANTAGE_API_KEY = "<your_alpha_vantage_key>";
  private static final String USER_EMAIL = "<recipient_email@example.com>";
  ```

- In `StockNewsService.java`:
  ```java
  private static final String NEWS_API_KEY = "<your_newsapi_key>";
  ```

- In `NewsSummarizer.java`:
  ```java
  private static final String API_TOKEN = "<your_huggingface_api_token>";
  ```

- In `EmailService.java`:
  ```java
  final String USER_EMAIL = "<your_email>";
  final String EMAIL_PASSWORD = "<your_app_password>";
  ```

---

## 🔑 How to Get the Required API Keys

| Service         | How to Register                                       |
|-----------------|--------------------------------------------------------|
| Alpha Vantage   | https://www.alphavantage.co/support/#api-key          |
| NewsAPI         | https://newsapi.org/register                          |
| Hugging Face    | https://huggingface.co/settings/tokens                |
| Gmail App Pass  | https://support.google.com/accounts/answer/185833     |

> Note: Enable 2FA on your Gmail to generate an App Password.

---

## 🧪 How to Run the Project

### Prerequisites:
- Java 11+ installed
- Internet access (for APIs)
- Maven (for dependencies)

### Steps:
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/gap-opening-analyzer.git
   cd gap-opening-analyzer
   ```

2. Compile and run:
   ```bash
   javac com/vishal/App.java
   java com.vishal.App
   ```

---

## 📌 Future Enhancements

- 📅 Scheduled execution with configurable time
- 📬 Support multiple email recipients
- 📉 Gap threshold filter (e.g., ignore changes <1%)

---

## 🤝 Contributing

Pull requests are welcome! Open an issue for feature requests, improvements, or bug reports.