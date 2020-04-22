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

import connectors.DestinationConnector
import org.mockito.Matchers.any
import org.mockito.Matchers.{eq => eqTo}
import org.mockito.Mockito.when
import org.mockito.Mockito._
import org.scalatest.FreeSpec
import org.scalatest.MustMatchers
import org.scalatest.OptionValues
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Configuration
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.FakeRequest
import play.api.test.Helpers.POST
import play.api.test.Helpers.route
import play.api.test.Helpers._
import uk.gov.hmrc.http.HttpResponse

import scala.concurrent.Future

class ResponseControllerSpec extends FreeSpec with GuiceOneAppPerSuite with MustMatchers with MockitoSugar with OptionValues {

  protected def applicationBuilder: GuiceApplicationBuilder =
    new GuiceApplicationBuilder().configure(Configuration("metrics.enabled" -> "false"))

  "Response Controller tests" - {
    "post" - {

      "should post goods released message" in {

        val mockDestinationConnector = mock[DestinationConnector]
        when(mockDestinationConnector.sendMessage(any(), any(), any(), eqTo("IE025"))(any())).thenReturn(Future.successful(HttpResponse(OK)))

        val application = applicationBuilder.overrides(bind[DestinationConnector].toInstance(mockDestinationConnector)).build()

        val result = route(application,
                           FakeRequest(POST, routes.ResponseController.post().url)
                             .withFormUrlEncodedBody("arrivalId" -> "12", "version" -> "1", "messageType" -> "1")).value

        status(result) mustBe SEE_OTHER

        verify(mockDestinationConnector, times(1)).sendMessage(any(), any(), any(), any())(any())

      }

      "should post unloading permission message" in {

        val result = route(app,
                           FakeRequest(POST, routes.ResponseController.post().url)
                             .withFormUrlEncodedBody("arrivalId" -> "12", "version" -> "1", "messageType" -> "3")).value

        status(result) mustBe SEE_OTHER

      }
    }

    "onPageLoad" in {

      val result = route(app, FakeRequest(GET, routes.ResponseController.onPageLoad().url)).value

      status(result) mustBe OK
    }

  }
}
