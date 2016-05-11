package se.marcuslonnberg.adaywithsbt

import java.nio.file.{Files, Paths}

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import com.typesafe.scalalogging.StrictLogging
import se.marcuslonnberg.adaywithsbt.api.ApiRoute

import scala.io.Source
import scala.util.{Failure, Success}

object Main extends App with StrictLogging {
  implicit val system = ActorSystem()

  import system.dispatcher

  implicit val mat = ActorMaterializer(ActorMaterializerSettings(system))

  logger.info(s"Starting a day with sbt: $BuildInfo")

  val apiRoute = new ApiRoute()

  Http().bindAndHandle(apiRoute.route, "0.0.0.0", 8080).onComplete {
    case Success(binding) =>
      logger.info(s"Bound to ${binding.localAddress}")
    case Failure(ex) =>
      logger.error("Failed to bind", ex)
  }

  val superhero = Source.fromURL(getClass.getClassLoader.getResource("superhero")).mkString
  logger.info(s"Superhero: $superhero")
}
