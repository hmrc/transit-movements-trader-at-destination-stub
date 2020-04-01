/*
 * Copyright 2020 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers

import org.mockito.ArgumentCaptor
import org.mockito.Mockito._
import org.mockito.Matchers._
import org.scalatest.FreeSpec
import org.scalatest.MustMatchers
import org.scalatest.OptionValues
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.Configuration
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.FakeRequest
import play.api.test.Helpers.POST
import play.api.test.Helpers.route
import play.api.test.Helpers._
import uk.gov.hmrc.nunjucks.NunjucksRenderer

class ResponseControllerSpec extends FreeSpec with MockitoSugar with GuiceOneAppPerSuite with MustMatchers with OptionValues {

  val mockRenderer: NunjucksRenderer = mock[NunjucksRenderer]

  def application: Application =
    new GuiceApplicationBuilder()
      .configure(Configuration("metrics.enabled" -> "true"))
      .overrides(
        bind[NunjucksRenderer].toInstance(mockRenderer)
      )
      .build()

  "Response Controller tests" - {
    "post" ignore {

      val result = route(application, FakeRequest(POST, routes.ResponseController.post().url)).value

      status(result) mustBe OK

    }

    "onPageLoad" in {

      val result = route(application, FakeRequest(GET, routes.ResponseController.onPageLoad().url)).value

      val templateCaptor = ArgumentCaptor.forClass(classOf[String])

      verify(mockRenderer, times(1)).render(templateCaptor.capture())(any())

      status(result) mustBe OK

      templateCaptor.getValue mustEqual "response.njk"
    }

  }
}
