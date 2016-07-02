package common

import org.joda.time.DateTime

import scala.concurrent.duration.Duration

object DateTimeExtensions {

  implicit class ExtendedDateTime(dateTime: DateTime) extends scala.AnyRef {
    def +(duration: Duration) = {
      dateTime.plusMillis(duration.toMillis.toInt)
    }
  }
}

