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

import com.google.inject.Inject
import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.ControllerComponents
import uk.gov.hmrc.play.bootstrap.controller.BackendController

import scala.io.Source

class UnloadingPermissionController @Inject()(cc: ControllerComponents) extends BackendController(cc) {

  private val WithSeals: Int    = 2
  private val WithoutSeals: Int = 5

  def get(arrivalId: Int): Action[AnyContent] = Action {
    implicit request =>
      val json = arrivalId match {
        case WithSeals    => Source.fromFile("conf/resources/unloading-response-with-seals.json").getLines.mkString
        case WithoutSeals => Source.fromFile("conf/resources/unloading-response-without-seals.json").getLines.mkString
      }
      Ok(json).as("application/json")
  }

}
