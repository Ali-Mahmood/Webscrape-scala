package servers

import com.datastax.driver.core.Cluster
import db.NewsDBHandler
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import scrapers.scrapeWebsites

object main extends App {

    val cluster = Cluster.builder().addContactPoint("127.0.0.1").build()
    val session = cluster.connect("web_scrape")
    val newsDBHandler = new NewsDBHandler(session)

    val browser = JsoupBrowser()

    val listOfSites = List("https://news.sky.com/", "https://news.sky.com/technology", "https://news.sky.com/uk", "http://www.bbc.co.uk/news", "http://www.bbc.co.uk/news/technology")

    scrapeWebsites.scrape(browser, newsDBHandler, listOfSites)

    cluster.close()
  }
