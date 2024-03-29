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

package controllers.enrolmentStore

import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.ControllerComponents
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController
import utils.JsonUtils

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class EnrolmentStoreProxyController @Inject()(val executionContext: ExecutionContext, cc: ControllerComponents, jsonUtils: JsonUtils)
    extends BackendController(cc) {

  val basePath: String = "conf/resources/groupEnrolments"

  def checkNCTSGroup(groupId: String, `type`: String = "principal", service: String): Action[AnyContent] = Action {
    val json = jsonUtils.readJsonFromFile(s"$basePath/$groupId.json")
    Ok(json).as("application/json")
  }
}
