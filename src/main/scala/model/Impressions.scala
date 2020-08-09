package model

import spray.json.{DefaultJsonProtocol, RootJsonFormat}

case class ImpressionsSource(
                              app_id: Option[Either[Int, String]],
                              advertiser_id: Option[Either[Int, String]],
                              country_code: Option[String],
                              id: Option[String]
                            )

case class ImpressionsTarget(
                              app_id: Int,
                              advertiser_id: Int,
                              country_code: String,
                              id: String
                            )

object ImpressionsProtocol extends DefaultJsonProtocol {
  implicit val doubleTestFormat: RootJsonFormat[ImpressionsSource] = jsonFormat4(ImpressionsSource)
}

