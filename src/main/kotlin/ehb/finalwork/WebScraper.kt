package ehb.finalwork

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class WebScraper {

    // Function to scrape the website and return a list of div contents
    fun scrapeWebsite(url: String): List<String> {
        val scrapedData = mutableListOf<String>()
        try {
            // Fetch the HTML content of the website
            val doc: Document = Jsoup.connect(url).get()

            // Use CSS selector to target the specific main element with ID "content"
            val contentElement = doc.select("main#content")

            // Find all div elements inside the main element
            val divElements = contentElement.select("div")

            // Extract the text content of each div element and add it to the list
            for (divElement in divElements) {
                val divContent = divElement.text()
                scrapedData.add(divContent)
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }

        return scrapedData
    }

    // Scheduled method to run the scraping process every day at midnight
    @Scheduled(cron = "0 0 0 * * ?") // Run at midnight every day
    fun performDailyScraping() {
        val url = "https://www.sneakerjagers.com/releases" // Replace with the URL of the website you want to scrape
        val scrapedData = scrapeWebsite(url)

        // Process the scraped data as needed (e.g., store it in a database, send it via email, etc.)
        // For demonstration purposes, let's just print the scraped data for now
        println("Scraped Data for Today:")
        for (data in scrapedData) {
            println(data)
        }
    }
}