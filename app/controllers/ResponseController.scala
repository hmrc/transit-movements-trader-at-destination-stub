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
import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.MessagesAbstractController
import play.api.mvc.MessagesControllerComponents
import uk.gov.hmrc.http.HeaderCarrier

import scala.concurrent.Future

class ResponseController @Inject()(cc: MessagesControllerComponents, destinationConnector: DestinationConnector) extends MessagesAbstractController(cc) {

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
      ResponseForm.form
        .bindFromRequest()
        .fold(
          hasErrors => Future.successful(BadRequest(views.html.response(hasErrors))),
          value => {
            destinationConnector.goodsReleased(goodsReleasedXml, value.arrivalId)
            Future.successful(Redirect(routes.ResponseController.onPageLoad()))
          }
        )
  }

  def onPageLoad(): Action[AnyContent] = Action.async {
    implicit request =>
      Future.successful(Ok(views.html.response(ResponseForm.form)))
  }

}
