package parser

import model.{ClicksSource, ClicksTarget}
import spray.json.JsonParser

object ClicksParser {
  def parser(jsonString : String) : List[ClicksTarget] = {
    val ast = JsonParser(jsonString)
    import model.ClicksProtocol._
    val DTs = ast.convertTo[List[ClicksSource]]
    DTs.map { dt =>
      val impression_id = dt.impression_id match {
        case Some(d) => d
        case None => "null"
      }
      val revenue = dt.revenue match {
        case Some(d) => d
        case None => 0
      }
      ClicksTarget(impression_id = impression_id, revenue = revenue)
    }
  }
}
