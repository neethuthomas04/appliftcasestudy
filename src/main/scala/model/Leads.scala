package model

case class Leads(
                  app_id: Int,
                  country_code: String,
                  impression_id: String,
                  advertiser_id: Int,
                  clicks: Int,
                  revenue: Double
                )
