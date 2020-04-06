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
import forms.mappings.Mappings
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.libs.json.OFormat

class ResponseForm @Inject() extends Mappings {

  def apply(): Form[ResponseModel] =
    Form(
      mapping(
        "arrivalId"   -> text("required"),
        "version"     -> text("required"),
        "messageType" -> text("required")
      )(ResponseModel.apply)(ResponseModel.unapply)
    )
}

case class ResponseModel(arrivalId: String, version: String, messageType: String)

object ResponseModel {
  implicit val format: OFormat[ResponseModel] = Json.format[ResponseModel]
}
