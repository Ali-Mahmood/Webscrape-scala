import net.ruippeixotog.scalascraper.browser.Browser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._

object scrapeWebsites {

  def scrape(browser: Browser, dBHandler: NewsDBHandler, sites: List[String]) = {
    sites.foreach { site =>
      val page = browser.get(site)

      val maybeHeadlines: Option[List[(String, String)]] = site match {

        case site if site.contains("news.sky.com") =>
          val pageExtractionTags = page tryExtract elementList("h3 a")
          val headers = pageExtractionTags map (titles => titles.map(_.text))
          val hrefs = pageExtractionTags tryExtract attr("href")("a")
          Option(for {
            (header, href) <- headers.get zip hrefs.get.flatten
          } yield (header, href.toString))

        //        case site if site.contains("www.bbc.co.uk") =>
        //          val headers: Option[List[String]] = page tryExtract elementList("a h3") map (titles => titles.map(_.text))
        //          val hrefs = page tryExtract elementList("a h3") tryExtract attr("href")("a")
        //          hrefs.foreach(h => println(h))
        //          for {
        //            (header, href) <- headers.toList.flatten zip hrefs.toList.flatten
        //          } yield (header, href)

        case _ =>
          println("Website not supported")
          None
      }
      dBHandler.saveHeadlines(maybeHeadlines.get, site)
    }

  }

}
