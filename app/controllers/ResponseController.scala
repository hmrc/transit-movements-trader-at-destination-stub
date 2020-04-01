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

import javax.inject.Inject
import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.MessagesAbstractController
import play.api.mvc.MessagesControllerComponents

import scala.concurrent.Future

class ResponseController @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  def post(): Action[AnyContent] = Action.async {
    implicit request =>
      ResponseForm.form
        .bindFromRequest()
        .fold(
          hasErrors => Future.successful(BadRequest(views.html.response(hasErrors))),
          value => {
            Future.successful(Redirect(routes.ResponseController.onPageLoad()))
          }
        )
  }

  def onPageLoad(): Action[AnyContent] = Action.async {
    implicit request =>
      Future.successful(Ok(views.html.response(ResponseForm.form)))
  }
}
