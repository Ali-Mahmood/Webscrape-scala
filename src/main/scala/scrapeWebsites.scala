import net.ruippeixotog.scalascraper.browser.Browser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import scala.collection.breakOut

object scrapeWebsites {

  def scrape(browser: Browser,dBHandler: NewsDBHandler, sites: List[String]) = {
    sites.foreach { site =>
      val page = browser.get(site)

      val maybeHeadlines = site match {
        case site if site.contains("news.sky.com") =>
          val headers: Option[List[String]] = page tryExtract elementList("h3 a") map (titles => titles.map(_.text))
          val hrefs: Option[List[Option[String]]] = page tryExtract elementList("h3 a") tryExtract attr("href")("a")

          for {
            head <- headers
            hre <- hrefs
            mapping <- Map(head -> hre)
          }

        case site if site.contains("www.bbc.co.uk") =>
          val headlines = page tryExtract elementList("a h3") map (titles => titles.map(_.text))
          val href = page tryExtract elementList("a h3") tryExtract attr("href")("a")
          Map(headlines -> href)

        case _ =>
          println("Website not supported")
          None
      }
      println(maybeHeadlines)
      //maybeHeadlines.map(headlines => dBHandler.saveHeadlines(headlines.distinct, site))
      println(site)
    }
  }
}
