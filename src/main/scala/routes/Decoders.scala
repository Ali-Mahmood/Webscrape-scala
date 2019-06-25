package routes

import java.sql.Timestamp
import java.time.{LocalDate, LocalDateTime}
import java.time.format.DateTimeFormatter

import cats.data.Validated.{invalidNel, validNel}
import org.http4s.dsl.io.QueryParamDecoderMatcher
import org.http4s.{ParseFailure, QueryParamDecoder, QueryParameterValue}

import scala.util.{Failure, Success, Try}


object Decoders {
  val urlFriendlyFormatterWithoutHours: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")

  object TimestampQueryParamDecoderMatcher
    extends QueryParamDecoderMatcher[Timestamp]("timestamp")(dateQueryParamDecoder(urlFriendlyFormatterWithoutHours))

  def dateQueryParamDecoder(format: DateTimeFormatter): QueryParamDecoder[Timestamp] =
    (queryParam: QueryParameterValue) => {

      val maybeDate: Try[Timestamp] = Try {
        val localDateTime: LocalDateTime = LocalDate.parse(queryParam.value, urlFriendlyFormatterWithoutHours).atStartOfDay()
        Timestamp.valueOf(localDateTime)
      }

      maybeDate match {
        case Success(date) => validNel(date)
        case Failure(e) =>
          val msg = s"Failure parsing date '${queryParam.value}', \n${e.getStackTrace.mkString("\n")}"
          invalidNel(ParseFailure(msg, msg))
      }

    }
}

sealed case class NewsData(website: String, headLinesWithLinks: Option[(List[String], List[String])])