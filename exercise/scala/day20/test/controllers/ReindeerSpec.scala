package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._

import java.util.UUID

class ReindeerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {
  "ReindeerController GET" should {
    "existing reindeer" in {
      val request = FakeRequest(GET, "/reindeer/40f9d24d-d3e0-4596-adc5-b4936ff84b19")
      status(route(app, request).get) mustBe OK
    }

    "NOT FOUND for a non-existing reindeer" in {
      val nonExistingReindeer = UUID.randomUUID().toString
      val request = FakeRequest(GET, s"/reindeer/$nonExistingReindeer")

      status(route(app, request).get) mustBe NOT_FOUND
    }
  }

  "ReindeerController POST" should {
    "return CONFLICT when trying to create an existing reindeer" in {
      val json = Json.obj(
        "name" -> "Petar",
        "color" -> "Purple"
      )
      val request = FakeRequest(POST, "/reindeer").withJsonBody(json)
      val result = route(app, request).get
      status(result) mustBe CONFLICT
    }
  }
}
