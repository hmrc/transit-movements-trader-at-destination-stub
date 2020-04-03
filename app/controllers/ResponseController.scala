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
import play.api.libs.json._
import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.MessagesAbstractController
import play.api.mvc.MessagesControllerComponents
import play.api.mvc.MessagesRequest
import play.api.mvc.Request
import renderer.Renderer
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.viewmodels.NunjucksSupport

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

  private val goodsReleasedXml = <CC025A><SynIdeMES1>UNOC</SynIdeMES1>
    <SynVerNumMES2>3</SynVerNumMES2>
    <MesSenMES3>NTA.GB</MesSenMES3>
    <MesRecMES6>SYST17B-NCTS_EU_EXIT</MesRecMES6>
    <DatOfPreMES9>20190912</DatOfPreMES9>
    <TimOfPreMES10>1510</TimOfPreMES10>
    <IntConRefMES11>70390912151020</IntConRefMES11>
    <AppRefMES14>NCTS</AppRefMES14>
    <TesIndMES18>0</TesIndMES18>
    <MesIdeMES19>70390912151020</MesIdeMES19>
    <MesTypMES20>GB025A</MesTypMES20>
    <HEAHEA><DocNumHEA5>19IT02110010007827</DocNumHEA5>
      <GooRelDatHEA176>20190912</GooRelDatHEA176>
    </HEAHEA>
    <TRADESTRD><NamTRD7>The Luggage Carriers</NamTRD7>
      <StrAndNumTRD22>225 Suedopolish Yard,</StrAndNumTRD22>
      <PosCodTRD23>SS8 2BB</PosCodTRD23>
      <CitTRD24>,</CitTRD24>
      <CouTRD25>GB</CouTRD25>
      <TINTRD59>GB163910077000</TINTRD59>
    </TRADESTRD>
    <CUSOFFPREOFFRES><RefNumRES1>GB000060</RefNumRES1>
    </CUSOFFPREOFFRES>
  </CC025A>

  def post(): Action[AnyContent] = Action.async {
    implicit request =>
      implicit val hc = HeaderCarrier()
      form
        .bindFromRequest()
        .fold(
          hasErrors => renderer.render("response.njk", json(hasErrors)).map(BadRequest(_)),
          (value: ResponseModel) => {
            println(s"\n\n\n ArrivalId ${value.arrivalId} \n\n\n")
            println(s"\n\n\n Version ${value.version} \n\n\n")
            println(s"\n\n\n messageType ${value.messageType} \n\n\n")
            destinationConnector.goodsReleased(goodsReleasedXml, value.arrivalId)
            Future.successful(Redirect(routes.ResponseController.onPageLoad()))
          }
        )
  }

  def onPageLoad(): Action[AnyContent] = Action.async {
    implicit request =>
      renderer.render("response.njk", json(form)).map(Ok(_))
  }

  def json(form: Form[ResponseModel])(implicit request: MessagesRequest[AnyContent]) =
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
        )
      )
    )

}
