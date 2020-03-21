name := "timestamp-microservice"

version := "0.1"

scalaVersion := "2.12.10"

val akkaVersion = "2.6.4"
val akkaHttpVersion = "10.1.11"
val scalaTestVersion = "3.1.1"

libraryDependencies ++= Seq(
    // akka streams
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    // akka http
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion,
    // testing
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
    "org.scalatest" %% "scalatest" % scalaTestVersion,
    
    // JWT
    "com.pauldijou" %% "jwt-spray-json" % "2.1.0",
    
    "joda-time" % "joda-time" % "2.10.5"

)
