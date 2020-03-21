package com.zo

import java.text.SimpleDateFormat

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.stream.SystemMaterializer
import akka.util.Timeout
import com.zo.TimestampActor._
import org.joda.time.DateTime
import spray.json._

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.{Failure, Success}

object TimestampMicroservice extends TimestampJsonProtocol
                             with SprayJsonSupport {
    
    implicit val system: ActorSystem = ActorSystem("timestamp-microservice")
    implicit val materializer: SystemMaterializer = SystemMaterializer(system)
    implicit val timeout: Timeout = Timeout(3 seconds)
    
    val timestampActor: ActorRef = system.actorOf(Props[TimestampActor])
    
    val DATE_FORMAT = "yyyy-MM-dd"
    def stringToDate(s: String): DateTime = {
        val dateFormat = new SimpleDateFormat(DATE_FORMAT)
        new DateTime(dateFormat.parse(s))
    }
    
    val timestampRoute: Route =
        pathPrefix("api" / "timestamp") {
            path(LongNumber) { unix =>
                val responseFuture: Future[Timestamp] = (timestampActor ? ConvertToDateTime(unix)).mapTo[Timestamp]
                
                complete(responseFuture)
            } ~
            path(Segment) { dateString =>
                val date: DateTime = stringToDate(dateString)
                val responseFuture: Future[Timestamp] = (timestampActor ? ConvertToUnix(date)).mapTo[Timestamp]
                
                complete(responseFuture)
            } ~
            pathEndOrSingleSlash {
                val responseFuture: Future[Timestamp] = (timestampActor ? Now).mapTo[Timestamp]
                
                complete(responseFuture)
            }
        }
}
