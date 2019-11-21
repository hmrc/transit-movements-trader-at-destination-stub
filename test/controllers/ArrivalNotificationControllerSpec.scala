/*
 * Copyright 2019 HM Revenue & Customs
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

import org.scalatest.{FreeSpec, MustMatchers, OptionValues}
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.mvc.AnyContentAsXml
import play.api.test.Helpers._
import play.api.test.{FakeHeaders, FakeRequest}

import scala.xml.NodeSeq

class ArrivalNotificationControllerSpec extends FreeSpec with MustMatchers with GuiceOneAppPerSuite with OptionValues {

  private def fakePostRequest(content: NodeSeq): FakeRequest[AnyContentAsXml] = {
    FakeRequest(POST, routes.ArrivalNotificationController.post().url, FakeHeaders(Seq.empty), AnyContentAsXml(content))
  }

  private def buildXml(mrn: String): NodeSeq = {
    <CC007A>
      <HEAHEA>
        <DocNumHEA5>{mrn}</DocNumHEA5>
      </HEAHEA>
    </CC007A>
  }

  private val invalidXml: NodeSeq = {
    <CC007A>
      <DocNumHEA5>19IT02110010007827</DocNumHEA5>
    </CC007A>
  }

  "post" - {

    "must return status ok for valid input" in {

      val xml = buildXml("19GB00000000000001")
      val result = route(app, fakePostRequest(xml)).value

      status(result) mustEqual OK
    }

    "must return status bad request when there is no data" in {

      val request = FakeRequest(POST, routes.ArrivalNotificationController.post().url)
      val result = route(app, request).value

      status(result) mustEqual BAD_REQUEST
    }
  }

}
