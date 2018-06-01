import net.ruippeixotog.scalascraper.browser._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._

object scrapeWebsite {

  def main(args: Array[String]) {

    val browser = JsoupBrowser()

    val listOfSites = List("https://news.sky.com/", "https://news.sky.com/technology", "https://news.sky.com/uk", "http://www.bbc.co.uk/news", "http://www.bbc.co.uk/news/technology")

    listOfSites.foreach{ site =>
      val page = browser.get(site)
      site match {
        case site if site.contains("sky") =>
        for {
          headlines <- page tryExtract elementList("h3 a")
        } (println(site), headlines.foreach(title => println("-- " + title.text + " --")))
        case site if site.contains("bbc") =>
          for {
            headlines <- page tryExtract elementList("a h3")
          } (println(site), headlines.foreach(title => println("-- " + title.text + " --")))
        case _ => println("No website matched.")
      }
    }
  }
}
