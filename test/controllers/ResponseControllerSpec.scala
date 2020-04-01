package controllers

import org.scalatest.{FreeSpec, MustMatchers, OptionValues}
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.test.FakeRequest
import play.api.test.Helpers.{POST, route, _}

class ResponseControllerSpec extends FreeSpec with GuiceOneAppPerSuite with MustMatchers with OptionValues {

  "Response Controller tests" - {
    "post" in {

      val result = route(app, FakeRequest(POST, routes.ResponseController.post().url)).value

      status(result) mustBe OK

    }

    "onPageLoad" in {

    }

  }
}
