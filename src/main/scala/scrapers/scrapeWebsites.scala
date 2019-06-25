package scrapers

import db.NewsDBHandler
import net.ruippeixotog.scalascraper.browser.Browser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import routes.NewsData

object scrapeWebsites {

  def scrape(browser: Browser, dBHandler: NewsDBHandler, sites: List[String]) = {
    sites.foreach { site =>
      val page = browser.get(site)

      def maybeHeadlines: Option[(List[String], List[String])] = site match {
        case site if site.contains("news.sky.com") =>
          val pageExtractionTags = page tryExtract elementList("h3 a")
          val headers: Option[List[String]] = pageExtractionTags map (titles => titles.map(_.text))
          val hrefs: Option[List[Option[String]]] = pageExtractionTags tryExtract attr("href")("a")
          Option(Tuple2(headers.get, hrefs.get.flatten[String]))

        case _ =>
          println("Website not supported")
          None
      }

      maybeHeadlines.map { t =>
        dBHandler.saveHeadlines(t._1, t._2, site)}
      }
    }
}
