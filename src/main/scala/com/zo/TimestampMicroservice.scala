package com.zo

import java.text.SimpleDateFormat
import java.util.Date

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.pattern.ask
import akka.stream.SystemMaterializer
import akka.util.Timeout
import com.zo.TimestampActor._
import org.joda.time.DateTime
import spray.json._

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

object TimestampMicroservice extends App
                             with TimestampJsonProtocol
                             with SprayJsonSupport {
    
    implicit val system: ActorSystem = ActorSystem("timestamp-microservice")
    implicit val materializer: SystemMaterializer = SystemMaterializer(system)
    implicit val ec: ExecutionContextExecutor = system.dispatcher
    implicit val timeout: Timeout = Timeout(3 seconds)
    
    val timestampActor = system.actorOf(Props[TimestampActor])
    
    val DATE_FORMAT = "yyyy-MM-dd"
    def stringToDate(s: String): DateTime = {
        val dateFormat = new SimpleDateFormat(DATE_FORMAT)
        new DateTime(dateFormat.parse(s))
    }
    
    val timestampRoute =
        pathPrefix("api" / "timestamp") {
            path(LongNumber) { unix =>
                val responseFuture: Future[Timestamp] = (timestampActor ? ConvertToDateTime(unix)).mapTo[Timestamp]
                
                onComplete(responseFuture) {
                    case Success(response) => complete(response.toJson.prettyPrint)
                    case Failure(ex)       => complete(HttpResponse(StatusCodes.BadRequest, entity = s"$ex"))
                }
            } ~
            path(Segment) { dateString =>
                val date: DateTime = stringToDate(dateString)
                val responseFuture: Future[Timestamp] = (timestampActor ? ConvertToUnix(date)).mapTo[Timestamp]
                
                onComplete(responseFuture) {
                    case Success(response) => complete(response.toJson.prettyPrint)
                    case Failure(ex)       => complete(HttpResponse(StatusCodes.BadRequest, entity = s"$ex"))
                }
            } ~
            pathEndOrSingleSlash {
                val responseFuture: Future[Timestamp] = (timestampActor ? Now).mapTo[Timestamp]
                
                onComplete(responseFuture) {
                    case Success(response) => complete(response.toJson.prettyPrint)
                    case Failure(ex)       => complete(HttpResponse(StatusCodes.BadRequest, entity = s"$ex"))
                }
            }
        }
    
    Http().bindAndHandle(timestampRoute, "localhost", 9999)
    
}
