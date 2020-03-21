package com.zo

import akka.actor.{Actor, ActorLogging}
import org.joda.time._
import org.joda.time.format._
import spray.json._

case class Timestamp(unix: String, datetime: DateTime)

trait TimestampJsonProtocol extends DefaultJsonProtocol {
    
    implicit object DateJsonFormat extends RootJsonFormat[DateTime] {
        
        private val parserISO: format.DateTimeFormatter = ISODateTimeFormat.dateTimeNoMillis()
        
        override def write(obj: DateTime): JsValue = JsString(parserISO.print(obj))
        
        override def read(json: JsValue): DateTime = json match {
            case JsString(s) => parserISO.parseDateTime(s)
            case _           => throw DeserializationException("Invalid date format: " + json)
        }
    }
    
    implicit val timestampFormat: RootJsonFormat[Timestamp] = jsonFormat2(Timestamp)
}

object TimestampActor {
    case object Now
    case class ConvertToUnix(date: DateTime)
    case class ConvertToDateTime(unix: Long)
}

class TimestampActor extends Actor with ActorLogging {
    
    import com.zo.TimestampActor._
    
    override def receive: Receive = {
        case Now                     =>
            log.info("Fetching the current Date and Time.")
            sender() ! Timestamp(DateTime.now().getMillis.toString.slice(0, 10), DateTime.now())
        case ConvertToUnix(date)     =>
            log.info("Converting Date to Unix timestamp.")
            sender() ! Timestamp(date.getMillis.toString.slice(0, 10), date.toDateTime)
        case ConvertToDateTime(unix) =>
            log.info("Converting Unix timestamp to DateTime.")
            sender() ! Timestamp(unix.toString, new DateTime(unix * 1000))
    }
}
