import net.ruippeixotog.scalascraper.browser._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._

object scrapeWebsite {

  def main(args: Array[String]) {

    val browser = JsoupBrowser()

    val listOfSites = List("https://news.sky.com/", "https://news.sky.com/technology", "https://news.sky.com/uk")

    listOfSites.foreach{ site =>
      val page = browser.get(site)
      for {
        pageTitle <- page >> extractor("h1", texts)
        headlines <- page >?> elementList("h3 a")
      } (println(pageTitle), headlines.foreach(title => println("-- " + title.text + " --")))
    }
  }
}
