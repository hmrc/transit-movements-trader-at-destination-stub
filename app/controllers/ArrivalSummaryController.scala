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
import play.api.Logger
import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.ControllerComponents
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController
import utils.JsonUtils

class ArrivalSummaryController @Inject()(cc: ControllerComponents, jsonUtils: JsonUtils) extends BackendController(cc) {

  val logger = Logger(s"application.${this.getClass.getSimpleName}")

  private val ArrivalNotificationMessageId: Int                  = 1
  private val DuplicateMRN: Int                                  = 3
  private val DuplicateMRNMessageId: Int                         = 2
  private val GenericMRN: Int                                    = 7
  private val GenericArrivalMessageId: Int                       = 2
  private val NonFunctionalArrivalId: Int                        = 11
  private val NonFunctionalMessageId: Int                        = 2
  private val XMLSubmissionNegativeAcknowledgementArrivalId: Int = 12
  private val UnloadingXMLSubmissionNegativeAckArrivalId: Int    = 13
  private val MessageId1: Int                                    = 1
  private val MessageId2: Int                                    = 2

  private val UnloadingRemarksRejectionArrivalId         = 8
  private val UnloadingRemarksDateRejectionArrivalId     = 9
  private val UnloadingRemarksMultipleRejectionArrivalId = 10
  private val UnloadingPermissionMessageId: Int          = 1
  private val UnloadingRemarksMessageId: Int             = 2
  private val UnloadingRemarksNoSealsMessageId: Int      = 5
  private val UnloadingRejectionMessageId: Int           = 3
  private val UnloadingXMLRejectionMessageId: Int        = 4

  def getSummary(arrivalId: Int): Action[AnyContent] = Action {
    implicit request =>
      val json = arrivalId match {
        case DuplicateMRN                                  => jsonUtils.readJsonFromFile("conf/resources/arrival-summary-duplicate.json")
        case GenericMRN                                    => jsonUtils.readJsonFromFile("conf/resources/arrival-summary-generic.json")
        case NonFunctionalArrivalId                        => jsonUtils.readJsonFromFile("conf/resources/arrival-summary-non-functional.json")
        case UnloadingRemarksRejectionArrivalId            => jsonUtils.readJsonFromFile("conf/resources/unloading-remarks-summary-generic.json")
        case UnloadingRemarksDateRejectionArrivalId        => jsonUtils.readJsonFromFile("conf/resources/unloading-remarks-summary-date-error.json")
        case UnloadingRemarksMultipleRejectionArrivalId    => jsonUtils.readJsonFromFile("conf/resources/unloading-remarks-summary-multiple-error.json")
        case UnloadingRemarksMessageId                     => jsonUtils.readJsonFromFile("conf/resources/unloading-remarks-summary-with-seals.json")
        case UnloadingRemarksNoSealsMessageId              => jsonUtils.readJsonFromFile("conf/resources/unloading-remarks-summary-no-seals.json")
        case XMLSubmissionNegativeAcknowledgementArrivalId => jsonUtils.readJsonFromFile("conf/resources/arrival-summary-xml-negative-acknowledgement.json")
        case UnloadingXMLSubmissionNegativeAckArrivalId =>
          jsonUtils.readJsonFromFile("conf/resources/unloading-summary-unloading-xml-negative-acknowledgement.json")
        case _ => jsonUtils.readJsonFromFile("conf/resources/unloading-remarks-summary-with-seals.json")
      }
      Ok(json).as("application/json")
  }

  def get(arrivalId: Int, messageId: Int): Action[AnyContent] = Action {
    implicit request =>
      val json = (arrivalId, messageId) match {
        case (DuplicateMRN, DuplicateMRNMessageId)            => jsonUtils.readJsonFromFile("conf/resources/arrival-rejection-duplicate.json")
        case (GenericMRN, GenericArrivalMessageId)            => jsonUtils.readJsonFromFile("conf/resources/arrival-rejection-generic.json")
        case (NonFunctionalArrivalId, NonFunctionalMessageId) => jsonUtils.readJsonFromFile("conf/resources/arrival-rejection-non-functional.json")
        case (UnloadingRemarksRejectionArrivalId, UnloadingPermissionMessageId) =>
          jsonUtils.readJsonFromFile("conf/resources/unloading-response-with-seals.json")
        case (UnloadingRemarksRejectionArrivalId, UnloadingRemarksMessageId) => jsonUtils.readJsonFromFile("conf/resources/unloading-remarks.json")
        case (UnloadingRemarksRejectionArrivalId, UnloadingRejectionMessageId) =>
          jsonUtils.readJsonFromFile("conf/resources/unloading-remarks-rejection-generic.json")
        case (UnloadingRemarksDateRejectionArrivalId, 1) => jsonUtils.readJsonFromFile("conf/resources/unloading-permission-with-seals.json") // IE043
        case (UnloadingRemarksDateRejectionArrivalId, 2) => jsonUtils.readJsonFromFile("conf/resources/unloading-remarks.json")
        case (UnloadingRemarksDateRejectionArrivalId, UnloadingRejectionMessageId) =>
          jsonUtils.readJsonFromFile("conf/resources/unloading-remarks-rejection-date-error.json") // IE058
        case (UnloadingRemarksMultipleRejectionArrivalId, UnloadingPermissionMessageId) =>
          jsonUtils.readJsonFromFile("conf/resources/unloading-response-with-seals-single.json")
        case (UnloadingRemarksMultipleRejectionArrivalId, UnloadingRemarksMessageId) => jsonUtils.readJsonFromFile("conf/resources/unloading-remarks.json")
        case (UnloadingRemarksMultipleRejectionArrivalId, UnloadingRejectionMessageId) =>
          jsonUtils.readJsonFromFile("conf/resources/unloading-remarks-rejection-multiple-error.json")
        case (UnloadingRemarksMessageId, UnloadingPermissionMessageId) => jsonUtils.readJsonFromFile("conf/resources/unloading-response-with-seals-single.json")
        case (UnloadingRemarksNoSealsMessageId, UnloadingPermissionMessageId) =>
          jsonUtils.readJsonFromFile("conf/resources/unloading-response-no-seals-single.json")
        case (XMLSubmissionNegativeAcknowledgementArrivalId, MessageId2) =>
          jsonUtils.readJsonFromFile("conf/resources/arrival-xml-negative-acknowledgement.json")
        case (UnloadingXMLSubmissionNegativeAckArrivalId, MessageId2) => jsonUtils.readJsonFromFile("conf/resources/unloading-response-no-seals-single.json")
        case (UnloadingXMLSubmissionNegativeAckArrivalId, UnloadingXMLRejectionMessageId) =>
          jsonUtils.readJsonFromFile("conf/resources/arrival-xml-negative-acknowledgement.json")
        case (_, ArrivalNotificationMessageId) => jsonUtils.readJsonFromFile("conf/resources/arrival-notification-message.json")
        case _ => {
          logger.error(s"No match for ArrivalId=$arrivalId and MessageId=$messageId")
          throw new IllegalArgumentException(s"No match for ArrivalId=$arrivalId and MessageId=$messageId")
        }
      }
      Ok(json).as("application/json")
  }

}
