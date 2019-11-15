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

import javax.inject.Inject
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import uk.gov.hmrc.play.bootstrap.controller.BackendController

import scala.xml.NodeSeq

class ArrivalNotificationController @Inject()(cc: ControllerComponents) extends BackendController(cc) {

  def post(): Action[AnyContent] = Action {
    implicit request =>
      request.body.asXml match {
        case Some(xml) if getValidMrn(xml) => Ok
        case _ => BadRequest
      }
  }

  private def getValidMrn(xml: NodeSeq): Boolean = {
    val mrnFormat: String = """^(\d{2})([A-Z]{2})([A-Z0-9]{13})(\d)$"""
    val mrn: NodeSeq = xml \\ "CC007A" \\ "HEAHEA" \\ "DocNumHEA5"
    mrn.text.matches(mrnFormat)
  }

}
