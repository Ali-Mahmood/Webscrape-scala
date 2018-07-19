import com.datastax.driver.core.{ResultSet, Session}
import scala.collection.JavaConverters._

class NewsDBHandler(session: Session){

  def saveHeadlines(headlines: List[String], website: String): ResultSet ={
    //session.execute(s"INSERT INTO headlines (date_time, headlines , website) VALUES (toTimestamp(now()), $headlines, $website)")

    val preparedStatement = session.prepare(
      s"""
         | INSERT INTO headlines (date_time, headlines , website)
         | VALUES (toTimestamp(now()), ?, ?)
       """.stripMargin
    )

    val statement = preparedStatement.bind(headlines.asJava, website) // Bind values
    session.execute(statement)
  }

}