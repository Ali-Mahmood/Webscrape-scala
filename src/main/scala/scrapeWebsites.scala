import net.ruippeixotog.scalascraper.browser.Browser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._

object scrapeWebsites {

  def scrape(browser: Browser,dBHandler: NewsDBHandler, sites: List[String]) = {
    sites.foreach { site =>
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
      maybeHeadlines.map(headlines => dBHandler.saveHeadlines(headlines.distinct, site))
      println(site)
    }
  }
}
