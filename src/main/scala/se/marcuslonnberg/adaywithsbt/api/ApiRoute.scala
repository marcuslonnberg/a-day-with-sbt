package se.marcuslonnberg.adaywithsbt.api
import se.marcuslonnberg.adaywithsbt.{BuildInfo, Sorter}
import akka.http.scaladsl.server.Directives
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport

class ApiRoute extends Directives with PlayJsonSupport {
  val route = {
    path("buildinfo" | "build-info") {
      get {
        complete(BuildInfo.toString)
      }
    } ~
    path("sort") {
      post {
        entity(as[Seq[Int]]) { numbers =>
          complete(Sorter(numbers))
        }
      }
    }
  }
}
