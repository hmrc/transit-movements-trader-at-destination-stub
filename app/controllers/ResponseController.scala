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

import connectors.DestinationConnector
import javax.inject.Inject
import play.api.data.Form
import play.api.i18n.I18nSupport
import play.api.i18n.MessagesApi
import play.api.libs.json.Json
import play.api.libs.json._
import play.api.mvc._
import renderer.Renderer
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.viewmodels.NunjucksSupport

import scala.xml.Elem
import scala.xml.{XML => xmlFile}
import scala.concurrent.ExecutionContext
import scala.concurrent.Future

class ResponseController @Inject()(
  override val messagesApi: MessagesApi,
  cc: MessagesControllerComponents,
  destinationConnector: DestinationConnector,
  formProvider: ResponseForm,
  renderer: Renderer
)(implicit ec: ExecutionContext)
    extends MessagesAbstractController(cc)
    with I18nSupport
    with NunjucksSupport {

  private val form: Form[ResponseModel] = formProvider()

  private val goodsReleasedXml: Elem                   = xmlFile.load(getClass.getResourceAsStream("/resources/goodsReleased.xml"))
  private val unloadingPermissionWithSealsXml: Elem    = xmlFile.load(getClass.getResourceAsStream("/resources/unloadingPermissionWithSeals.xml"))
  private val unloadingPermissionWithoutSealsXml: Elem = xmlFile.load(getClass.getResourceAsStream("/resources/unloadingPermissionWithoutSeals.xml"))

  def post(): Action[AnyContent] = Action.async {
    implicit request =>
      implicit val hc: HeaderCarrier = HeaderCarrier()
      form
        .bindFromRequest()
        .fold(
          hasErrors => renderer.render("response.njk", json(hasErrors)).map(BadRequest(_)),
          (value: ResponseModel) => {
            val xmlToSend = value.messageType match {
              case "goodsReleased"                   => (goodsReleasedXml, "IE025")
              case "unloadingPermissionWithSeals"    => (unloadingPermissionWithSealsXml, "IE043")
              case "unloadingPermissionWithoutSeals" => (unloadingPermissionWithoutSealsXml, "IE043")
              case _ =>
                ??? //todo: error out
            }
            destinationConnector.sendMessage(xmlToSend._1, value.arrivalId, value.version, xmlToSend._2)
            Future.successful(Redirect(routes.ResponseController.onPageLoad()))
          }
        )
  }

  def onPageLoad(): Action[AnyContent] = Action.async {
    implicit request =>
      renderer.render("response.njk", json(form)).map(Ok(_))
  }

  def json(form: Form[ResponseModel])(implicit request: MessagesRequest[AnyContent]): JsObject =
    Json.obj(
      "form" -> form,
      "messageType" -> Json.arr(
        Json.obj(
          "text"     -> "Goods Released",
          "value"    -> "goodsReleased",
          "selected" -> true
        ),
        //todo: add in goods rejected CTCTRADERS-423
        /*Json.obj(
          "text"     -> "Goods Rejected",
          "value"    -> "goodsRejected",
          "selected" -> false
        ),*/
        Json.obj(
          "text"     -> "Unloading Permission with seals",
          "value"    -> "unloadingPermissionWithSeals",
          "selected" -> false
        ),
        Json.obj(
          "text"     -> "Unloading Permission without seals",
          "value"    -> "unloadingPermissionWithoutSeals",
          "selected" -> false
        )
      )
    )
}
