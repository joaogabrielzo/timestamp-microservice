package com.zo

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import TimestampMicroservice.timestampRoute

object TimestampServer extends App {
    
    implicit val system: ActorSystem = ActorSystem("timestamp-server")
    
    Http().bindAndHandle(timestampRoute, "localhost", 9999)
    
}
