import com.datastax.driver.core.{ResultSet, Session}
import scala.collection.JavaConverters._

class NewsDBHandler(session: Session){

  def saveHeadlines(headlines: List[String], website: String): ResultSet ={

    val preparedStatement = session.prepare(
      s"""
         | INSERT INTO headlines (date_time, headlines , website)
         | VALUES (toTimestamp(now()), ?, ?)
       """.stripMargin
    )

    val statement = preparedStatement.bind(headlines.asJava, website)
    session.execute(statement)
  }

}