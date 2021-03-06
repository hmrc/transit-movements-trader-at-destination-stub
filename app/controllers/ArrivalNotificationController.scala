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
import play.api.Logger
import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.ControllerComponents
import services.HeaderValidatorService
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

class ArrivalNotificationController @Inject()(cc: ControllerComponents, headerValidatorService: HeaderValidatorService) extends BackendController(cc) {

  def post(): Action[AnyContent] = Action {
    implicit request =>
      if (headerValidatorService.validate(request.headers)) {
        request.body.asXml match {
          case Some(xml) =>
            Logger.warn(s"validated XML $xml")
            Accepted
              .withHeaders("Location" -> s"/arrivals/5")
          case e =>
            Logger.warn(s"FAILED VALIDATING XML $e")
            BadRequest
        }
      } else {
        Logger.warn("FAILED VALIDATING headers")
        BadRequest
      }

  }

  def put(arrivalId: String): Action[AnyContent] = Action {
    implicit request =>
      if (headerValidatorService.validate(request.headers)) {
        request.body.asXml match {
          case Some(xml) =>
            Logger.warn(s"validated XML $xml")
            NoContent
              .withHeaders("Location" -> s"/arrivals/$arrivalId/messages/5")
          case e =>
            Logger.warn(s"FAILED VALIDATING XML $e")
            BadRequest
        }
      } else {
        Logger.warn("FAILED VALIDATING headers")
        BadRequest
      }

  }
}
