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

import models.FakeResponse
import connectors.DestinationConnector
import forms.FakeResponseForm
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

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.xml.Elem
import scala.xml.{XML => xmlFile}

class FakeResponseController @Inject()(
  override val messagesApi: MessagesApi,
  cc: MessagesControllerComponents,
  destinationConnector: DestinationConnector,
  formProvider: FakeResponseForm,
  renderer: Renderer
)(implicit ec: ExecutionContext)
    extends MessagesAbstractController(cc)
    with I18nSupport
    with NunjucksSupport {

  private val form: Form[FakeResponse] = formProvider()

  private val goodsReleasedXml: Elem                   = xmlFile.load(getClass.getResourceAsStream("/resources/goodsReleased.xml"))
  private val unloadingPermissionWithSealsXml: Elem    = xmlFile.load(getClass.getResourceAsStream("/resources/unloadingPermissionWithSeals.xml"))
  private val unloadingPermissionWithoutSealsXml: Elem = xmlFile.load(getClass.getResourceAsStream("/resources/unloadingPermissionWithoutSeals.xml"))

  def post(): Action[AnyContent] = Action.async {
    implicit request =>
      implicit val hc: HeaderCarrier = HeaderCarrier()
      form
        .bindFromRequest()
        .fold(
          hasErrors => renderer.render("fakeResponse.njk", json(hasErrors)).map(BadRequest(_)),
          (value: FakeResponse) => {
            val xmlToSend = value.messageType match {
              case "goodsReleased"                   => (goodsReleasedXml, "IE025")
              case "unloadingPermissionWithSeals"    => (unloadingPermissionWithSealsXml, "IE043")
              case "unloadingPermissionWithoutSeals" => (unloadingPermissionWithoutSealsXml, "IE043")
            }
            destinationConnector.sendMessage(xmlToSend._1, value.arrivalId, value.version, xmlToSend._2).flatMap {
              _ =>
                renderer.render("fakeResponse.njk", json(form)).map(Ok(_))
            }
          }
        )
  }

  def onPageLoad(): Action[AnyContent] = Action.async {
    implicit request =>
      renderer.render("fakeResponse.njk", json(form)).map(Ok(_))
  }

  def json(form: Form[FakeResponse])(implicit request: MessagesRequest[AnyContent]): JsObject =
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