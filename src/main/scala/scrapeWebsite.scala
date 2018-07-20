import net.ruippeixotog.scalascraper.browser._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import com.datastax.driver.core.Cluster

object scrapeWebsite {

  def main(args: Array[String]) {

    // Cassandra DB session
    val cluster = Cluster.builder().addContactPoint("127.0.0.1").build()
    val session = cluster.connect("web_scrape")

    // instance of NewsDBHandler class
    val newsDBHandler = new NewsDBHandler(session)

    val browser = JsoupBrowser()

    val listOfSites = List("https://news.sky.com/", "https://news.sky.com/technology", "https://news.sky.com/uk", "http://www.bbc.co.uk/news", "http://www.bbc.co.uk/news/technology")

    listOfSites.foreach{ site =>
      val page = browser.get(site)

      val maybeHeadlines: Option[List[String]] = site match {

        case site if site.contains("news.sky.com") =>
          page tryExtract elementList("h3 a") map (titles => titles.map(_.text))

        case site if site.contains("www.bbc.co.uk") =>
          page tryExtract elementList("a h3") map (titles => titles.map(_.text))

        case _ =>
          println("Website not supported")
          None
      }

      maybeHeadlines.map(headlines => newsDBHandler.saveHeadlines(headlines.distinct, site))
      println(site)
    }
  }
}
