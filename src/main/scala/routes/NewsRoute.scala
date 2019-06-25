package routes

import cats.effect.Effect
import com.typesafe.scalalogging.LazyLogging
import org.http4s.HttpService
import org.http4s.dsl.Http4sDsl
import org.slf4j.{Logger, LoggerFactory}
import routes.Decoders.TimestampQueryParamDecoderMatcher

class NewsRoute[F[_]: Effect] extends Http4sDsl[F] with LazyLogging {
  val log: Logger = LoggerFactory.getLogger(getClass)

  val service: HttpService[F] = {
    HttpService[F] {

      case req@GET -> Root / "newshistory" :?
        website +&
        TimestampQueryParamDecoderMatcher(timestamp) =>


    }
  }
}

