import model.{AppPerformanceByCountry, ClicksTarget, ImpressionsTarget, Leads}
import spray.json.{JsValue, enrichAny}


object T2AppPerformanceMetrics {

  def getLeads(clicks: List[ClicksTarget],
               impressions: List[ImpressionsTarget]): List[Leads] = {
    val clicksMap = clicks.map { case(x: ClicksTarget) => x.impression_id -> x }.toMap
    val impressionsMap = impressions.map { case(x: ImpressionsTarget) => x.id -> x }.toMap
    impressionsMap.map{ case(k,v) =>
      Leads(
        app_id = v.app_id,
        country_code = v.country_code,
        impression_id = v.id,
        advertiser_id = v.advertiser_id,
        clicks = clicksMap.get(k) match {
          case Some(_) => 1
          case None => 0
        },
        revenue = clicksMap.get(k) match {
          case Some(v) => v.revenue
          case None => 0
        }
      )
    }.toList
  }

  def appPerformanceByCountry(leads: List[Leads]):JsValue = {
    import model.AppPerformanceByCountryJsonProtocol._
    leads.groupBy(d=>(d.app_id,d.country_code)).map { case (k,v) =>
      AppPerformanceByCountry(
        app_id = v.head.app_id,
        country_code = v.head.country_code,
        impressions = v.map(_.impression_id).size,
        clicks = v.map(_.clicks).sum,
        revenue = v.map(_.revenue).sum
      )
    }.toList.toJson
  }
}
