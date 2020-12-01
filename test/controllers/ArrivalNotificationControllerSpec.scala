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

import org.scalatest.OptionValues
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.mvc.AnyContentAsXml
import play.api.test.Helpers._
import play.api.test.FakeHeaders
import play.api.test.FakeRequest

import scala.xml.NodeSeq

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers

class ArrivalNotificationControllerSpec extends AnyFreeSpec with Matchers with GuiceOneAppPerSuite with OptionValues {

  val validHeaders: Seq[(String, String)] = Seq(
    ("Content-Type", "application/xml"),
    ("Accept", "application/xml"),
    ("X-Message-Type", "IO007"),
    ("X-Correlation-ID", "1234567890"),
    ("CustomProcessHost", "Digital")
  )

  private def fakePostRequest(content: NodeSeq, headers: Seq[(String, String)]): FakeRequest[AnyContentAsXml] =
    FakeRequest(POST, routes.ArrivalNotificationController.post().url, FakeHeaders(headers), AnyContentAsXml(content))

  private def buildXml(mrn: String): NodeSeq =
    <CC007A>
      <HEAHEA>
        <DocNumHEA5>{mrn}</DocNumHEA5>
      </HEAHEA>
    </CC007A>

  "post must return" - {

    "OK for valid input" in {

      val xml    = buildXml("19GB00000000000001")
      val result = route(app, fakePostRequest(xml, validHeaders)).value

      status(result) mustEqual ACCEPTED
    }

    "must return status bad request when there is no data" in {

      val request = FakeRequest(POST, routes.ArrivalNotificationController.post().url)
      val result  = route(app, request).value

      status(result) mustEqual BAD_REQUEST
    }

    "BAD_REQUEST when Content-Type is missing" in {

      val xml = buildXml("19GB00000000000001")

      def postRequest =
        FakeRequest(POST, routes.ArrivalNotificationController.post().url)
          .withTextBody(xml.toString())
          .withHeaders(("Accept", "application/xml"), ("X-Message-Type", "IO007"), ("X-Correlation-ID", "1234567890"), ("CustomProcessHost", "Digital"))

      val result = route(app, postRequest).value

      status(result) mustEqual BAD_REQUEST
    }

    "BAD_REQUEST when MessageCode is missing" in {

      val invalidHeaders: Seq[(String, String)] = Seq(
        ("Content-Type", "application/xml"),
        ("Accept", "application/xml"),
        ("X-Correlation-ID", "1234567890"),
        ("CustomProcessHost", "Digital")
      )

      val xml    = buildXml("19GB00000000000001")
      val result = route(app, fakePostRequest(xml, headers = invalidHeaders)).value

      status(result) mustEqual BAD_REQUEST
    }

    "BAD_REQUEST when X-Correlation-ID is missing" in {

      val invalidHeaders: Seq[(String, String)] = Seq(
        ("Content-Type", "application/xml"),
        ("Accept", "application/xml"),
        ("X-Message-Type", "IO007"),
        ("CustomProcessHost", "Digital")
      )

      val xml    = buildXml("19GB00000000000001")
      val result = route(app, fakePostRequest(xml, headers = invalidHeaders)).value

      status(result) mustEqual BAD_REQUEST
    }

    "BAD_REQUEST when CustomProcessHost is missing" in {

      val invalidHeaders: Seq[(String, String)] = Seq(
        ("Content-Type", "application/xml"),
        ("Accept", "application/xml"),
        ("X-Message-Type", "IO007"),
        ("X-Correlation-ID", "1234567890")
      )

      val xml    = buildXml("19GB00000000000001")
      val result = route(app, fakePostRequest(xml, headers = invalidHeaders)).value

      status(result) mustEqual BAD_REQUEST
    }

  }

}
