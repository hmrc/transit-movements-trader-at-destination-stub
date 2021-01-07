/*
 * Copyright 2021 HM Revenue & Customs
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

import javax.inject.Inject
import models.MovementReferenceNumber
import play.api.libs.json.Json
import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.MessagesControllerComponents
import play.twirl.api.Html
import renderer.Renderer
import uk.gov.hmrc.play.bootstrap.controller.FrontendBaseController
import services.MrnGeneratorService._

import scala.concurrent.ExecutionContext

class MrnGeneratorController @Inject()(renderer: Renderer, val controllerComponents: MessagesControllerComponents)(implicit ec: ExecutionContext)
    extends FrontendBaseController {

  def onPageLoad: Action[AnyContent] = Action.async {
    implicit request =>
      val generatedMrn = MovementReferenceNumber(randomNumericString(2), randomAlphaString(2), randomAlphaNumericString(13))
      val json         = Json.obj("generatedMrn" -> generatedMrn)
      renderer
        .render("generatedMrn.njk", json)
        .map((x: Html) => {
          Ok(x)
        })
  }
}
