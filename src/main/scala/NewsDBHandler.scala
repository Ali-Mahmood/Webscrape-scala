import com.datastax.driver.core.{ResultSet, Session}
import scala.collection.JavaConverters._

class NewsDBHandler(session: Session){

  def saveHeadlines(headlines: List[String], hrefs: List[String], website: String): ResultSet ={

    val preparedStatement = session.prepare(
      s"""
         | INSERT INTO headlines (date_time, headlines, hrefs, website)
         | VALUES (toTimestamp(now()), ?, ?, ?)
       """.stripMargin
    )

    val statement = preparedStatement.bind(headlines.asJava, hrefs.asJava, website)
    session.execute(statement)
  }

}