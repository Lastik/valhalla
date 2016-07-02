package common

import common.DateTimeExtensions.ExtendedDateTime
import org.joda.time.DateTime

import scala.concurrent.duration._

case class TimeSpan(millis: Long)  {

  lazy val duration = millis.millis

  def seconds = duration.toSeconds

  def +(other: TimeSpan) =
    TimeSpan(this.millis + other.millis)

  def +(otherOpt: Option[TimeSpan]): TimeSpan =
    otherOpt match {
      case Some(other) => this + other
      case None => this
    }

  def /(timeSpan: TimeSpan) = {
    millis.toDouble / timeSpan.millis.toDouble
  }
}

object TimeSpan {

  implicit def apply(duration: Duration): TimeSpan = {
    TimeSpan(millis = duration.toMillis)
  }

  implicit class ExtendedDateTimeForTimeSpan(dateTime: DateTime) extends ExtendedDateTime(dateTime){
    def -(other: DateTime): TimeSpan = {
      TimeSpan(dateTime.getMillis - other.getMillis)
    }

    def +(timeSpan: TimeSpan): DateTime = {
      new DateTime(dateTime.getMillis + timeSpan.millis)
    }

    def -(timeSpan: TimeSpan): DateTime = {
      new DateTime(dateTime.getMillis - timeSpan.millis)
    }
  }
}
