package model

import spray.json.{DefaultJsonProtocol, RootJsonFormat}

case class AppPerformanceByCountry(
                                    app_id: Int,
                                    country_code: String,
                                    impressions: Long,
                                    clicks: Long,
                                    revenue: Double
                                  )

object AppPerformanceByCountryJsonProtocol extends DefaultJsonProtocol {
  implicit val AppPerformanceFormat: RootJsonFormat[AppPerformanceByCountry] =
    jsonFormat5(AppPerformanceByCountry.apply)
}
