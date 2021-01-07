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

import com.google.inject.Inject
import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.ControllerComponents
import uk.gov.hmrc.play.bootstrap.controller.BackendController
import utils.JsonUtils

class ArrivalSummaryController @Inject()(cc: ControllerComponents, jsonUtils: JsonUtils) extends BackendController(cc) {

  private val ArrivalNotificationMessageId: Int = 1
  private val DuplicateMRN: Int                 = 3
  private val DuplicateMRNMessageId: Int        = 2
  private val GenericMRN: Int                   = 7
  private val GenericArrivalMessageId: Int      = 2
  private val NonFunctionalArrivalId: Int       = 11
  private val NonFunctionalMessageId: Int       = 2

  private val UnloadingRemarksRejectionArrivalId = 8
  private val UnloadingRemarksDateRejectionArrivalId = 9
  private val UnloadingRemarksMultipleRejectionArrivalId = 10
  private val UnloadingPermissionMessageId: Int  = 1
  private val UnloadingRemarksMessageId: Int     = 2
  private val UnloadingRemarksNoSealsMessageId: Int     = 5
  private val UnloadingRejectionMessageId: Int   = 3

  def getSummary(arrivalId: Int): Action[AnyContent] = Action {
    implicit request =>
      val json = arrivalId match {
        case DuplicateMRN                       => jsonUtils.readJsonFromFile("conf/resources/arrival-summary-duplicate.json")
        case GenericMRN                         => jsonUtils.readJsonFromFile("conf/resources/arrival-summary-generic.json")
        case NonFunctionalArrivalId             => jsonUtils.readJsonFromFile("conf/resources/arrival-summary-non-functional.json")
        case UnloadingRemarksRejectionArrivalId => jsonUtils.readJsonFromFile("conf/resources/unloading-remarks-summary-generic.json")
        case UnloadingRemarksDateRejectionArrivalId => jsonUtils.readJsonFromFile("conf/resources/unloading-remarks-summary-date-error.json")
        case UnloadingRemarksMultipleRejectionArrivalId => jsonUtils.readJsonFromFile("conf/resources/unloading-remarks-summary-multiple-error.json")
        case UnloadingRemarksMessageId => jsonUtils.readJsonFromFile("conf/resources/unloading-remarks-summary-with-seals.json")
        case UnloadingRemarksNoSealsMessageId => jsonUtils.readJsonFromFile("conf/resources/unloading-remarks-summary-no-seals.json")
        case _ => jsonUtils.readJsonFromFile("conf/resources/unloading-remarks-summary-with-seals.json")
      }
      Ok(json).as("application/json")
  }

  def get(arrivalId: Int, messageId: Int): Action[AnyContent] = Action {
    implicit request =>
      val json = (arrivalId, messageId) match {
        case (DuplicateMRN, DuplicateMRNMessageId) => jsonUtils.readJsonFromFile("conf/resources/arrival-rejection-duplicate.json")
        case (GenericMRN, GenericArrivalMessageId) => jsonUtils.readJsonFromFile("conf/resources/arrival-rejection-generic.json")
        case (NonFunctionalArrivalId, NonFunctionalMessageId) => jsonUtils.readJsonFromFile("conf/resources/arrival-rejection-non-functional.json")
        case (UnloadingRemarksRejectionArrivalId, UnloadingPermissionMessageId) =>
          jsonUtils.readJsonFromFile("conf/resources/unloading-response-with-seals.json")
        case (UnloadingRemarksRejectionArrivalId, UnloadingRemarksMessageId) => jsonUtils.readJsonFromFile("conf/resources/unloading-remarks.json")
        case (UnloadingRemarksRejectionArrivalId, UnloadingRejectionMessageId) =>
          jsonUtils.readJsonFromFile("conf/resources/unloading-remarks-rejection-generic.json")
        case (UnloadingRemarksDateRejectionArrivalId, UnloadingRemarksMessageId) => jsonUtils.readJsonFromFile("conf/resources/unloading-remarks.json")
        case (UnloadingRemarksDateRejectionArrivalId, UnloadingRejectionMessageId) =>
          jsonUtils.readJsonFromFile("conf/resources/unloading-remarks-rejection-date-error.json")
        case (UnloadingRemarksMultipleRejectionArrivalId, UnloadingPermissionMessageId) => jsonUtils.readJsonFromFile("conf/resources/unloading-response-with-seals-single.json")
        case (UnloadingRemarksMultipleRejectionArrivalId, UnloadingRemarksMessageId) => jsonUtils.readJsonFromFile("conf/resources/unloading-remarks.json")
        case (UnloadingRemarksMultipleRejectionArrivalId, UnloadingRejectionMessageId) =>
          jsonUtils.readJsonFromFile("conf/resources/unloading-remarks-rejection-multiple-error.json")
        case (UnloadingRemarksMessageId, UnloadingPermissionMessageId) => jsonUtils.readJsonFromFile("conf/resources/unloading-response-with-seals-single.json")
        case (UnloadingRemarksNoSealsMessageId, UnloadingPermissionMessageId) => jsonUtils.readJsonFromFile("conf/resources/unloading-response-no-seals-single.json")
        case (_, ArrivalNotificationMessageId) => jsonUtils.readJsonFromFile("conf/resources/arrival-notification-message.json")
      }
      Ok(json).as("application/json")
  }

}
