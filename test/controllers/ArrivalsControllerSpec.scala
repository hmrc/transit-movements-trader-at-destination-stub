/*
 * Copyright 2022 HM Revenue & Customs
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
import play.api.test.FakeRequest
import play.api.test.Helpers._

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers

class ArrivalsControllerSpec extends AnyFreeSpec with Matchers with GuiceOneAppPerSuite with OptionValues {

  "ArrivalsControllerSpec" - {

    "GET" - {

      "return arrivals submitted" in {

        val request = FakeRequest(GET, routes.ArrivalsController.get.url)
        val result  = route(app, request).value

        status(result) mustEqual OK
        contentType(result).get mustEqual "application/json"
      }

      "return single arrival submitted" in {

        val request = FakeRequest(GET, routes.ArrivalsController.getArrival(1).url)
        val result  = route(app, request).value

        status(result) mustEqual OK
        contentType(result).get mustEqual "application/json"
      }

    }

    "POST" - {

      "submit arrival" in {

        val request = FakeRequest(POST, routes.ArrivalsController.post.url)
        val result  = route(app, request).value

        status(result) mustEqual ACCEPTED
      }

    }

    "PUT" - {

      "update arrival" in {

        val request = FakeRequest(PUT, routes.ArrivalsController.put(3).url)
        val result  = route(app, request).value

        status(result) mustEqual ACCEPTED
      }

    }

  }

}
