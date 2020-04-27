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

  //todo: Move XML outside of this file so we can share with the test and tidy up the controller

  def post(): Action[AnyContent] = Action.async {
    implicit request =>
      implicit val hc: HeaderCarrier = HeaderCarrier()
      form
        .bindFromRequest()
        .fold(
          hasErrors => renderer.render("response.njk", json(hasErrors)).map(BadRequest(_)),
          (value: ResponseModel) => {
            value.messageType match {
              case x if (x == "1" || x == "2") =>
                destinationConnector.sendMessage(goodsReleasedXml, value.arrivalId, value.version, "IE025")
                Future.successful(Redirect(routes.ResponseController.onPageLoad()))
              case "3" =>
                destinationConnector.sendMessage(unloadingPermissionWithSealsXml, value.arrivalId, value.version, "IE043")
                Future.successful(Redirect(routes.ResponseController.onPageLoad()))
              case "4" =>
                destinationConnector.sendMessage(unloadingPermissionWithoutSealsXml, value.arrivalId, value.version, "IE043")
                Future.successful(Redirect(routes.ResponseController.onPageLoad()))
              case _ =>
                ??? //todo: error out
            }
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
          "value"    -> "1",
          "selected" -> true
        ),
        Json.obj(
          "text"     -> "Goods Rejected",
          "value"    -> "2",
          "selected" -> false
        ),
        Json.obj(
          "text"     -> "Unloading Persmission with seals",
          "value"    -> "3",
          "selected" -> false
        ),
        Json.obj(
          "text"     -> "Unloading Persmission without seals",
          "value"    -> "4",
          "selected" -> false
        )
        //todo: add in objects for permission to unload options
      )
    )

}
