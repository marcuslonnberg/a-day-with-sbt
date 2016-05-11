package se.marcuslonnberg.adaywithsbt

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import org.scalatest.{FlatSpec, Matchers}
import se.marcuslonnberg.adaywithsbt.api.ApiRoute

class ApiSpec extends FlatSpec with Matchers with ScalatestRouteTest with PlayJsonSupport {
  val apiRoute = new ApiRoute()

  "API /sort" should "sort an empty list" in {
    Post("/sort", Seq.empty[Int]) ~> apiRoute.route ~> check {
      status shouldEqual StatusCodes.OK
      val out = responseAs[Seq[Int]]

      out shouldBe empty
    }
  }

  it should "sort a list with numbers" in {
    Post("/sort", Seq(2, 1, 3)) ~> apiRoute.route ~> check {
      status shouldEqual StatusCodes.OK
      val out = responseAs[Seq[Int]]

      out should contain theSameElementsInOrderAs Seq(1, 2, 3)
    }
  }
}
