import com.datastax.driver.core.{ResultSet, Session}
import scala.collection.JavaConverters._

class NewsDBHandler(session: Session){

  def saveHeadlines(headlinesWithHrefs: List[(String, String)], website: String): ResultSet ={

    val preparedStatement = session.prepare(
      s"""
         | INSERT INTO headlines (date_time, headlines , website)
         | VALUES (toTimestamp(now()), ?, ?)
       """.stripMargin
    )

    val statement = preparedStatement.bind(headlinesWithHrefs.asJava, website)
    session.execute(statement)
  }

}