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

object MrnGeneratorService {

  def randomAlphaNumericString(length: Int): String = {
    val chars = ('A' to 'Z') ++ ('0' to '9')
    randomStringFromCharList(length, chars)
  }

  def randomAlphaString(length: Int): String = {
    val chars = ('A' to 'Z')
    randomStringFromCharList(length, chars)
  }

  def randomNumericString(length: Int): String = {
    val chars = ('0' to '9')
    randomStringFromCharList(length, chars)
  }

  def randomStringFromCharList(length: Int, chars: Seq[Char]): String = {
    val sb = new StringBuilder
    for (i <- 1 to length) {
      val randomNum = util.Random.nextInt(chars.length)
      sb.append(chars(randomNum))
    }
    sb.toString
  }

}
