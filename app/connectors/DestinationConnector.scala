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

package connectors

import config.AppConfig
import uk.gov.hmrc.http.HttpReads.Implicits.readRaw
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.http.HttpClient
import uk.gov.hmrc.http.HttpResponse

import javax.inject.Inject
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.xml.Node

class DestinationConnector @Inject()(val config: AppConfig, val http: HttpClient)(implicit ec: ExecutionContext) {

  def sendMessage(xml: Node, arrivalId: String, version: String, xMessageType: String)(implicit hc: HeaderCarrier): Future[HttpResponse] = {

    val serviceUrl = s"${config.routerUrl}/messages"

    val headers = Seq(("Content-Type", "application/xml"), ("X-Message-Sender", s"MDTP-$arrivalId-$version"), ("X-Message-Type", xMessageType))

    http.POSTString[HttpResponse](serviceUrl, xml.toString(), headers)
  }

}
