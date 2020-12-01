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

package services

import com.google.inject.ImplementedBy
import com.google.inject.Singleton
import play.api.Logger
import play.api.mvc.Headers

@Singleton
class HeaderValidatorServiceImpl extends HeaderValidatorService {

  def validate(headers: Headers): Boolean = {

    val contentType = headers.get("Content-Type").isDefined

    val messageCode = headers.get("X-Message-Type").isDefined

    val xCorrelationId = headers.get("X-Correlation-ID").isDefined

    val customProcessHost = headers.get("CustomProcessHost").isDefined

    contentType && messageCode && xCorrelationId && customProcessHost
  }

}

@ImplementedBy(classOf[HeaderValidatorServiceImpl])
trait HeaderValidatorService {
  def validate(headers: Headers): Boolean
}
